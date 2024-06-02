package SpringBootStarterProject.ManagingPackage.Security.Token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import SpringBootStarterProject.ManagingPackage.Security.Token.RelationshipType;

import java.util.List;
import java.util.Optional;


public interface TokenRepository extends JpaRepository<Token,Integer> {


@Query(value = """
select t from Token  t
where t.RelationId=:id and (t.expired=false or t.revoked=false ) and t.type=SpringBootStarterProject.ManagingPackage.Security.Token.RelationshipType.CLIENT \s
""")
    List<Token> findAllValidClientTokensByRelationId(Integer id);
    @Query(value = """
select t from Token  t
where t.RelationId=:id and (t.expired=false or t.revoked=false ) and t.type=SpringBootStarterProject.ManagingPackage.Security.Token.RelationshipType.MANAGER\s
""")
    List<Token> findAllValidManagerTokensByRelationId(Integer id);

  //  List<Token> findTokensByClientId(Integer id);
    Optional<Token> findByToken(String token);

}
