package SpringBootStarterProject.UserPackage.Repositories;

import SpringBootStarterProject.UserPackage.Models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client,Integer> {
   Optional<Client> findByEmail(String username);

    Optional<Client> findClientByTheusername(String username);
 Client getClientById(Integer id);
}
