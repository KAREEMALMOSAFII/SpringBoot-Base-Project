package SpringBootStarterProject.SystemPackage.Requests;

import SpringBootStarterProject.ManagingPackage.annotation.ValidPassword;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ClientRegisterRequest {


    @NotBlank(message = "first_name required")
    private String first_name;

    @NotBlank(message = "last_name required")
    private String last_name;


    @Column(unique = true)
    @NotBlank(message = "username required")
    private String username;

    @NotBlank(message = "phone_number required")
    @Size(message = "phone_number must be 10 ",min = 10, max = 10)
    private String phone_number;


    @NotBlank(message = "email required")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",flags = Pattern.Flag.CASE_INSENSITIVE)
    @Column(unique = true)
    private String email;

   // @NotBlank(message = "password required")
    @ValidPassword
    private String password;

    @NotBlank(message = "Confirmation password required")
    private String confirmation_password;

}
