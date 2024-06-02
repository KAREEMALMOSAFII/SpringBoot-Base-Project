package SpringBootStarterProject.ManagingPackage.ExceptionHandler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ApiExceptionResponse {

    private final String message;
    private final HttpStatus status;
    private final LocalDateTime localDateTime;


}
