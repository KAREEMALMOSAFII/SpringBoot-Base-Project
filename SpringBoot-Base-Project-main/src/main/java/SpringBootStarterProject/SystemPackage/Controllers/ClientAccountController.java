package SpringBootStarterProject.UserPackage.Controller;

import SpringBootStarterProject.ManagingPackage.Security.Token.Token;
import SpringBootStarterProject.ManagingPackage.Security.Token.TokenType;
import SpringBootStarterProject.UserPackage.Request.*;
import SpringBootStarterProject.UserPackage.Services.ClinetAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/CLient/Account")
@RequiredArgsConstructor
public class ClientAccountController
{
    private final ClinetAccountService clinetAccountService;



    @GetMapping("Get_My_Account")
    public ResponseEntity<?>GetMyAccount()
    {
        return ResponseEntity.ok(clinetAccountService.GetMyAccount());

    }

    @PutMapping("Edit_My_Account")
    public ResponseEntity<?>EditMyAccount(@RequestBody EditClientRequest request)
    {
        return ResponseEntity.ok(clinetAccountService.EditMyAccount(request));

    }

    @PostMapping("/Add_Client_Details")
    private ResponseEntity<?> AddMyDetails (@RequestBody ClientDetailsRequest request)
    {
        return ResponseEntity.ok(clinetAccountService.AddMyDetails(request));
    }

    @PostMapping("/Add_Client_Passport")
    private ResponseEntity<?> AddMyPassport (@RequestBody PassportRequest request)
    {
        return ResponseEntity.ok(clinetAccountService.AddMyPassport(request));
    }

    @PostMapping("/Add_Passenegr_Passport")
    private ResponseEntity<?> AddPassenegrPassport (@RequestBody PassportRequest request)
    {
        return ResponseEntity.ok(clinetAccountService.AddMyPassengersPassport(request));
    }

    @PostMapping("/Add_Client_Personalid")
    private ResponseEntity<?> AddMyPersonalid (@RequestBody PersonalidRequest request)
    {
        return ResponseEntity.ok(clinetAccountService.AddMyPersonalid(request));
    }

    @PostMapping("/Add_Passenger_Personalid")
    private ResponseEntity<?> AddPassengerPersonalid (@RequestBody PersonalidRequest request)
    {
        return ResponseEntity.ok(clinetAccountService.AddPassengerPersonalid(request));
    }


    @PostMapping("/Add_Client_Visa")
    private ResponseEntity<?> AddMyVisa (@RequestBody VisaRequest request)
    {
        return ResponseEntity.ok(clinetAccountService.AddMyVisa(request));
    }

    @PostMapping("/Add_Passenger_Visa")
    private ResponseEntity<?> AddPassengerVisa (@RequestBody VisaRequest request)
    {
        return ResponseEntity.ok(clinetAccountService.AddPassengerVisa(request));
    }

    @GetMapping("/Get_My_Passport")
    private ResponseEntity<?> GetMyPassport ()
    {
        return ResponseEntity.ok(clinetAccountService.GetMyPassport());
    }


    @GetMapping("/Get_My_PersonalId")
    private ResponseEntity<?> GetMyPersonalId ()
    {
        return ResponseEntity.ok(clinetAccountService.GetMyPersonalId());
    }

    @GetMapping("/Get_My_Visa")
    private ResponseEntity<?> GetMyVisa ()
    {
        return ResponseEntity.ok(clinetAccountService.GetMyVisa());
    }


    @GetMapping("/Get_Passenger_Passport/{passengerId}")
    private ResponseEntity<?> GetPassengerPassport (@PathVariable Integer passengerId)
    {
        return ResponseEntity.ok(clinetAccountService.GetPassengerPassport(passengerId));
    }


    @GetMapping("/Get_Passenger_PersonalId/{passengerId}")
    private ResponseEntity<?> GetPassengerPersonalId (@PathVariable Integer passengerId)
    {
        return ResponseEntity.ok(clinetAccountService.GetPassengerPersonalId(passengerId));
    }

    @GetMapping("/Get_Passenger_Visa/{passengerId}")
    private ResponseEntity<?> GetPassengerVisa (@PathVariable Integer passengerId)
    {
        return ResponseEntity.ok(clinetAccountService.GetPassengerVisa(passengerId));
    }


    @DeleteMapping("/Delete_My_Passport")
    private ResponseEntity<?> Delete_My_Passport ()
    {
        return ResponseEntity.ok(clinetAccountService.DeleteMyPassport());
    }


    @DeleteMapping("/Delete_My_PersonalId")
    private ResponseEntity<?> Delete_My_PersonalId ()
    {
        return ResponseEntity.ok(clinetAccountService.DeleteMyPersonalId());
    }

    @DeleteMapping("/Delete_My_Visa")
    private ResponseEntity<?> Delete_My_Visa ()
    {
        return ResponseEntity.ok(clinetAccountService.DeleteMyVisa());
    }


    @DeleteMapping("/Delete_Passenegr_Passport/{passengerId}")
    private ResponseEntity<?> DeletePassenegrPassport (@PathVariable Integer passengerId)
    {
        return ResponseEntity.ok(clinetAccountService.DeletePassenegrPassport(passengerId));
    }


    @DeleteMapping("/Delete_Passenegr_PersonalId/{passengerId}")
    private ResponseEntity<?> DeletePassenegrPersonalId (@PathVariable Integer passengerId)
    {
        return ResponseEntity.ok(clinetAccountService.DeletePassenegrPersonalId(passengerId));
    }

    @DeleteMapping("/Delete_Passenegr_Visa/{passengerId}")
    private ResponseEntity<?> DeletePassenegrVisa (@PathVariable Integer passengerId)
    {
        return ResponseEntity.ok(clinetAccountService.DeletePassenegrVisa(passengerId));
    }


    @PostMapping("/Add_Client_Passenger")
    private ResponseEntity<?> AddMyPassenger (@RequestBody AddPassengerRequest request)
    {
        return ResponseEntity.ok(clinetAccountService.AddMyPassengers(request));
    }


    @GetMapping("/Get_Client_Passenger/{id}")
    private ResponseEntity<?> GetOnePassenger (@PathVariable Integer id)
    {
        return ResponseEntity.ok(clinetAccountService.GetOnePassenger(id));
    }


    @GetMapping("/Get_All_Client_Passengers")
    private ResponseEntity<?> GetMyAllPassengers ()
    {
        return ResponseEntity.ok(clinetAccountService.GetMyAllPassengers());
    }


        @PutMapping("/Edit_Client_Passenger")
    private ResponseEntity<?> EditClientPassenger (@RequestBody AddPassengerRequest request)
    {
        return ResponseEntity.ok(clinetAccountService.EditMyPassenger(request));
    }



    @DeleteMapping("/Delete_Client_Passenger/{id}")
    private ResponseEntity<?> DeleteMyPassenger (@PathVariable Integer id)
    {
        return ResponseEntity.ok(clinetAccountService.DeleteMyPassenger(id));
    }


    @PostMapping("/Create_My_Wallet")
    private ResponseEntity<?> CreateMyWallet (@RequestBody CreateWalletRequest request)
    {
        return ResponseEntity.ok(clinetAccountService.CreateMyWallet(request));
    }


    @GetMapping("/Get_My_Wallet")
    private ResponseEntity<?> GetMyWallet ()
    {
        return ResponseEntity.ok(clinetAccountService.GetMyWallet());
    }

    @DeleteMapping("/Delete_My_Wallet")
    private ResponseEntity<?> DeleteMyWallet ()
    {
        return ResponseEntity.ok(clinetAccountService.DeleteMyWallet());
    }

//    @PostMapping("/Add_Money_To_Wallet_WithoutCheck")
//    private ResponseEntity<?> AddMoneyToWallet (@RequestBody CreateWalletRequest request)
//    {
//        return ResponseEntity.ok(clinetAccountService.AddMoneyToWallet(request));
//    }

    @PostMapping("/Add_Money_To_Wallet")
    private ResponseEntity<?> AddMoneyToWallet (@RequestBody MoneyCodeRequest request)
    {
        return ResponseEntity.ok(clinetAccountService.AddMoneyToWallet(request));
    }

    @DeleteMapping("/Delete_My_Account")
    private ResponseEntity<?> DeleteMyAccount ()
    {
        return ResponseEntity.ok(clinetAccountService.DeleteMyAccount());
    }

}
