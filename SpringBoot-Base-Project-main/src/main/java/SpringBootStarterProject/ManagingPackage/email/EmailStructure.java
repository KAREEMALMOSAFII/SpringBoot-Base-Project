package SpringBootStarterProject.ManagingPackage.email;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@Builder
public class EmailStructure {
    private String subject;
    private String message;

}
