package SpringBootStarterProject.ManagingPackage.Security.Config;

//import SpringBootStarterProject.ManagingPackage.auditing.ApplicationAuditingAware;
//import SpringBootStarterProject.ManagingPackage.auditing.ApplicationAuditingAware;
//import SpringBootStarterProject.UserPackage.Models.Client;
//import SpringBootStarterProject.UserPackage.Models.Manager;
//import SpringBootStarterProject.UserPackage.Repositories.ClientRepository;
//import SpringBootStarterProject.UserPackage.Repositories.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final PatronRepository bookRepository;
//    private final ManagerRepository managerRepository;



//    private final UserDetailsService clientDetailsService;
//    private final UserDetailsService managerDetailsService;

//    @Autowired
//    private UserDetailsService userDetailsService;


//  private final HttpServletRequest request;


//    @Bean(name = "ClientDetailsService")
//    public UserDetailsService ClientDetailsService() {
//        return username ->
//                clientRepository.findByEmail(username)
//                        .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
//    }
//
//    @Bean(name = "ManagerDetailsService")
//
//    public UserDetailsService ManagerDetailsService() {
//        return username ->
//                managerRepository.findByEmail(username)
//                        .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
//    }

//    };
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return username -> {
//            Optional<Client> client = clientRepository.findByEmail(username);
//            if (client.isPresent()) {
//                Client foundClient = client.get();
//                return new User(
//                        foundClient.getEmail(),
//                        foundClient.getPassword(),
//                        new ArrayList<>() // Add authorities if necessary
//                );
//            }
//
//            Optional<Manager> manager = managerRepository.findByEmail(username);
//            if (manager.isPresent()) {
//                Manager foundManager = manager.get();
//                return new User(
//                        foundManager.getEmail(),
//                        foundManager.getPassword(),
//                        new ArrayList<>() // Add authorities if necessary
//                );
//            }
//            throw new UsernameNotFoundException("User not found with email: " + username);
//        };
//    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username ->bookRepository
                .findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

//    @Bean(name = "ClientauthenticationProvider")
//    public AuthenticationProvider ClientauthenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(ClientDetailsService());
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }

//    @Bean(name = "ManagerauthenticationProvider")
//    public AuthenticationProvider ManagerauthenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(ManagerDetailsService());
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }


//    @Bean
//    public AuditorAware<Integer> auditorAware(){
//        return new ApplicationAuditingAware();
//    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
    {
        return config.getAuthenticationManager();
    }

    //TRY ADDING SALT BY MYSELF
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
