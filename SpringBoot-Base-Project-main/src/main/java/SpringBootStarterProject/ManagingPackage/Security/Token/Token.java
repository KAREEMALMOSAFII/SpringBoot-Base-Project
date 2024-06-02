package SpringBootStarterProject.ManagingPackage.Security.Token;


//import SpringBootStarterProject.UserPackage.Models.BaseUser;
//import SpringBootStarterProject.UserPackage.Models.Client;
//import SpringBootStarterProject.UserPackage.Models.Manager;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {

    @Id
    @GeneratedValue
    public Integer id;

    @Column(unique = true)
    public String token;

    @Enumerated(EnumType.STRING)
    public  TokenType tokenType= TokenType.BEARER;

    public boolean revoked;

    public boolean expired;

    @Enumerated(EnumType.STRING)
    public RelationshipType type;

    public Integer RelationId;

//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "CLIENT_ID")
//    @Nullable
//     public Client client;
//
//   public BaseUser baseUser;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "MANAGER_ID")
//    @Nullable
//    public Manager manager;

}
