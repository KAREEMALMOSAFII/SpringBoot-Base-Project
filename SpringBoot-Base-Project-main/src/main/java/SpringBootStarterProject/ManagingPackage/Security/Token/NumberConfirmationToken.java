package SpringBootStarterProject.ManagingPackage.Security.Token;

//import SpringBootStarterProject.UserPackage.Models.BaseUser;
//import SpringBootStarterProject.UserPackage.Models.Client;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
@Component
public class NumberConfirmationToken {
    @Id
    @GeneratedValue
    private int id;

    private String code;

    // @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private LocalDateTime createdDate=LocalDateTime.now();

    private Boolean valid;

    private LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(10);

//    @ManyToOne(targetEntity = Client.class,fetch = FetchType.EAGER)
//    @JoinColumn(nullable = false,name="Client_id")
//    public Client client;

    private String client_email;

}
