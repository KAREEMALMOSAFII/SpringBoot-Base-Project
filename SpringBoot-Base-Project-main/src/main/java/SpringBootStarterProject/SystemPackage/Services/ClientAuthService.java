package SpringBootStarterProject.UserPackage.Services;


import SpringBootStarterProject.ManagingPackage.Security.Config.JwtService;
import SpringBootStarterProject.ManagingPackage.Security.Config.RateLimiterConfig;
import SpringBootStarterProject.ManagingPackage.Security.Token.*;
import SpringBootStarterProject.ManagingPackage.Security.auth.AuthenticationResponse;
import SpringBootStarterProject.ManagingPackage.Validator.ObjectsValidator;
import SpringBootStarterProject.ManagingPackage.email.EmailService;
import SpringBootStarterProject.ManagingPackage.exception.EmailTakenException;
import SpringBootStarterProject.ManagingPackage.exception.TooManyRequestException;
import SpringBootStarterProject.UserPackage.Models.Client;
import SpringBootStarterProject.UserPackage.Models.Manager;
import SpringBootStarterProject.UserPackage.Repositories.ClientRepository;
import SpringBootStarterProject.UserPackage.Repositories.ManagerRepository;
import SpringBootStarterProject.UserPackage.Request.*;
import SpringBootStarterProject.ManagingPackage.Response.ApiResponseClass;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ClientAuthService {

    private final ObjectsValidator<ClientRegisterRequest>ClientRegisterValidator;

    private final ObjectsValidator<LoginRequest>LoginRequestValidator;

    private final ObjectsValidator<ManagerRegisterRequest>ManagerRequestValidator;

    private final ObjectsValidator<ChangePasswordRequest>ChangePasswordRequest;
    //TODO:: TRY   private final ObjectsValidator<Object>validator;


    private final PasswordEncoder passwordEncoder;
    private final ClientRepository clientRepository;
    private final NumberConfirmationTokenRepository numberConfTokenRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final ManagerRepository managerRepository;
    private final JwtService jwtService;
    private final RateLimiterConfig rateLimiterConfig;
    private final RateLimiterRegistry rateLimiterRegistry;
    private static final String LOGIN_RATE_LIMITER = "loginRateLimiter";

    //TODO :: ApiResponse
    @Transactional
    public ApiResponseClass ClientRegister(ClientRegisterRequest request)
    {
        ClientRegisterValidator.validate(request);

        Optional<Client> client_found= clientRepository.findByEmail(request.getEmail());

        //TODO :: الفرونت مابيعرف يربط مع كونفيرميشن كود

        if (client_found.isPresent()) {
          //  throw new EmailTakenException("email taken");

            var client = client_found.get();
            if (client.getActive() == false) {
                GenreateCode(client);
                return new ApiResponseClass("THE CODE SENT TO YOUR ACCOUNT , PLEASE VIREFY YOUR EMAIL",HttpStatus.CREATED,LocalDateTime.now());//ResponseEntity.created(URI.create("")).body("THE CODE SENT TO YOUR ACCOUNT , PLEASE VIREFY YOUR EMAIL");
            } else {
              throw  new IllegalStateException("email taken"); // ResponseEntity.badRequest().body("email taken");

            }
        }

//TODO ::  .active(false )

        if(!clientRepository.findClientByTheusername(request.getUsername()).isEmpty())
            throw new EmailTakenException("User name Taken");

        var The_client= Client.builder()
                .first_name(request.getFirst_name())
                .last_name(request.getLast_name())
                .theusername(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .active(false)
                .creationDate(LocalDateTime.now())
                .phone_number(request.getPhone_number())
                .build();

             var savedClient = clientRepository.save(The_client);
             GenreateCode(savedClient);

        //TODO ::  Uncomment for DeActivate Confirmation code
     //   String token =jwtService.generateToken(The_client);

     //  SaveClientToken(The_client,token);

      //  return    AuthenticationResponse.builder().token(token).build();
      return    new ApiResponseClass("THE CODE SENT TO YOUR ACCOUNT , PLEASE VIREFY YOUR EMAIL",HttpStatus.CREATED,LocalDateTime.now());
        }

    public  ApiResponseClass ClientLogin(LoginRequest request
    , HttpServletRequest httpServletRequest) {
        LoginRequestValidator.validate(request);
        String userIp = httpServletRequest.getRemoteAddr();
        if (rateLimiterConfig.getBlockedIPs().contains(userIp)) {
            throw new TooManyRequestException("Too many login attempts. Please try again later.");
        }

        String rateLimiterKey = LOGIN_RATE_LIMITER + "-" + userIp;
        RateLimiter rateLimiter = rateLimiterRegistry.rateLimiter(rateLimiterKey);

        Optional<Client> theclient = clientRepository.findByEmail(request.getEmail());


        if (theclient.isEmpty())
            throw new UsernameNotFoundException("The email not found, please register");
        if (rateLimiter.acquirePermission()) {
            Authentication authentication;
            try {


                authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        ));
            } catch (AuthenticationException exception) {
                throw new BadCredentialsException("invalid email or password");
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);

            Client client = theclient.get();
            if (client.getActive() == true) {
                Map<String, Object> extraClaims = new HashMap<>();
                Object Type= "Client";
                extraClaims.put("UserType", Type);
            //    AuthenticationResponse jwtToken = AuthenticationResponse.builder()
                    //    .token(jwtService.generateToken(extraClaims,client)).build();
               var jwtToken= jwtService.generateToken(extraClaims,client);
                RevokeAllClientTokens(client);
             //   SaveClientToken(client, jwtToken.getToken());
                SaveClientToken(client, jwtToken);
                Map<String,Object> response= new HashMap<>();
                response.put("User",client);
                response.put("Token",jwtToken);

               return    new ApiResponseClass("LOGIN SUCCESSFULLY",HttpStatus.ACCEPTED,LocalDateTime.now(), response);


//                return AuthenticationResponse.builder()
//                        .token(jwtToken)
//                        .build();
            } else {
                GenreateCode(client);
                //TODO:: MAKE A CUSTOM EXCEPTION
                throw new UsernameNotFoundException("PLEASE CONFIRM YOUR ACCOUNT, EMAIL SENT TO YOUR ACCOUNT");
            }
        } else {
            rateLimiterConfig.blockIP(userIp);

            throw new TooManyRequestException("Too many login attempts. Please try again later.");
        }
    }




    private void SaveClientToken(Client client, String jwtToken)
    {

        var token= Token.builder()
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .RelationId(client.getId())
                .type(RelationshipType.CLIENT)
                .expired(false)
                .revoked(false)
                .build();
                tokenRepository.save(token);
    }



    public void RevokeAllClientTokens(Client client)
    {
        var ValidClientTokens= tokenRepository.findAllValidClientTokensByRelationId(client.getId());
        if(ValidClientTokens.isEmpty())
            return;
        ValidClientTokens.forEach(token ->{
            token.setRevoked(true);
            token.setExpired(true);
                });
        tokenRepository.saveAll(ValidClientTokens);

    }




    private void GenreateCode(Client client)
    {
        if (client.getActive()==true)
            throw new BadCredentialsException("EMAIL TAKEN");

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random randoms = new Random();
        StringBuilder codes = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int index = randoms.nextInt(characters.length());
            codes.append(characters.charAt(index));
        }

        System.out.println(codes.toString());


        //Random random = new Random();
       // int code = 100000 + random.nextInt(900000);
       // String thecode = Integer.toString(code);
        String thecode =  codes.toString();
        Optional<NumberConfirmationToken> optional1 = numberConfTokenRepository.getNumberConfirmationTokenByClient_email(client.getEmail());
        if (optional1.isPresent()) {
            NumberConfirmationToken oldCode = optional1.get();
            numberConfTokenRepository.delete(oldCode);
            NumberConfirmationToken confirmation = new NumberConfirmationToken();
            confirmation.setValid(true);
            confirmation.setClient(client);
            confirmation.setClient_email(client.getEmail());
            confirmation.setCode(thecode);
            numberConfTokenRepository.save(confirmation);
            emailService.sendMailcode(client.getFirst_name(), client.getEmail(), thecode);

        } else {

            NumberConfirmationToken confirmation = new NumberConfirmationToken();
            confirmation.setValid(true);
            confirmation.setClient(client);
            confirmation.setClient_email(client.getEmail());
            confirmation.setCode(thecode);
            numberConfTokenRepository.save(confirmation);
            emailService.sendMailcode(client.getFirst_name(), client.getEmail(), thecode);

        }
    }

    public ApiResponseClass RegenerateConfCode(EmailRequest request)
    {
        Optional<Client> optional = clientRepository.findByEmail(request.getEmail());
        if (optional.isEmpty())

            throw new IllegalStateException("EMAIL NOT FOUND , PLEASE REGISTER");

        var user = optional.get();
        GenreateCode(user);

        return  new ApiResponseClass("CODE SENT TO YOUR ACCOUNT SUCCSESSFULLY",HttpStatus.CREATED,LocalDateTime.now());

        //return ResponseEntity.ok().body("CODE SENT TO YOUR ACCOUNT SUCCSESSFULLY");
    }


    public ApiResponseClass checkCodeNumber(EmailConfirmationRequest request) {

        Optional <NumberConfirmationToken> NumberConfCode = numberConfTokenRepository.findByCode(request.getCodeNumber());
        if (NumberConfCode.isEmpty())
            throw new UsernameNotFoundException("THE CODE NOT CORRECT");

        var checker=NumberConfCode.get();

//        if(checker.getExpirationDate().isBefore(LocalDateTime.now()))
//        {
//            throw new IllegalStateException("CODE EXPIRED");
//        }
        if (checker!=null && checker.getClient_email().equals(request.getUser_email())) {
            if (!checker.getValid())
                throw new BadCredentialsException("CODE NOT VALID ANYMORE ,PLEASE REGENERATE ANOTHER CODE");
            Optional<Client> optionalUser = clientRepository.findByEmail(request.getUser_email());
            if (optionalUser.isPresent()) {
                Client client = optionalUser.get();
                checker.setValid(false);
                client.setActive(true);
                Client savedUser = clientRepository.save(client);
               // Token jwtToken =
                     //   Token.builder()
                             //   .token(jwtService.generateToken(savedUser)).build();
             var   jwtToken= jwtService.generateToken(savedUser);
            //    SaveClientToken(savedUser, jwtToken.getToken());
                SaveClientToken(savedUser, jwtToken);
                numberConfTokenRepository.save(checker);
                Map<String,Object> response= new HashMap<>();
                response.put("User",client);
                response.put("Token",jwtToken);

               return  new ApiResponseClass("CODE CHECKED SUCCESSFULLY",HttpStatus.ACCEPTED,LocalDateTime.now(),response);


            }

        }
        throw new UsernameNotFoundException("the code not correct");
    }

    @Scheduled(fixedDelay = 60000) // 1 minute delay
    public void changeCodeValidity() {
        var Expiredcodes = numberConfTokenRepository.GetExpiredCodes();
    Expiredcodes.forEach(
            numberConfirmationToken ->
                    numberConfirmationToken.setValid(false)
    );
    numberConfTokenRepository.saveAll(Expiredcodes);


    }


    public ApiResponseClass ClientChangePassword(ChangePasswordRequest request, Principal connectedUser)
    {


        UserDetails userDetails = (UserDetails)(((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal());


        if (!passwordEncoder.matches(request.getOldPassword(), userDetails.getPassword())) {
            throw new BadCredentialsException("Password not Correct");
        }

        ChangePasswordRequest.validate(request);

        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new BadCredentialsException("New Password Does Not Match Confirmation Password ");
        }

        if (request.getNewPassword().equals( request.getOldPassword())) {
            throw new BadCredentialsException("The Password Same As The Old one , Please Change it ");
        }

        Client updatedUser = clientRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Client with email " + userDetails.getUsername() + " not found"));

        // Update password securely (use setter or dedicated method)
        updatedUser.setPassword(passwordEncoder.encode(request.getNewPassword()));

        clientRepository.save(updatedUser);


        return new ApiResponseClass("Password Changed Successfully", HttpStatus.ACCEPTED, LocalDateTime.now());
    }
}
