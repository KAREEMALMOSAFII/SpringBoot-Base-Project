package SpringBootStarterProject.ManagingPackage.email;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor

public class EmailController {
    private final EmailService emailService;
    @PostMapping("/send/{mail}")
    public String sendMail(@PathVariable String mail
    , @RequestBody EmailStructure emailStructure)
    {
        emailService.sendMail(mail,emailStructure);
        return "SUCCSESSFULLY";
    }
}
