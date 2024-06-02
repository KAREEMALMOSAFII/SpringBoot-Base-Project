package SpringBootStarterProject.ManagingPackage.Security.Token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NumberConfirmationTokenRepository extends JpaRepository<NumberConfirmationToken,Integer> {
    @Query(value = "SELECT * FROM number_confirmation_token WHERE expiration_date < current_timestamp", nativeQuery = true)
    List<NumberConfirmationToken> GetExpiredCodes();
    Optional< NumberConfirmationToken> findByCode(String codeNumber);



//   Optional<NumberConfirmationToken >deleteNumberConfirmationTokenByClient_email(String email);
//
//   Optional<NumberConfirmationToken >getNumberConfirmationTokenByClient_email(String email);
//
//    Optional<NumberConfirmationToken >findByClientId(Integer id);




}
