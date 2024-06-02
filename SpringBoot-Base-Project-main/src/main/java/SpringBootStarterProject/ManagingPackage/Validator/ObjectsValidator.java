package SpringBootStarterProject.ManagingPackage.Validator;

import SpringBootStarterProject.ManagingPackage.exception.ObjectNotValidException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ObjectsValidator<T> {
    private  final ValidatorFactory factory= Validation.buildDefaultValidatorFactory();
    private final Validator validator=factory.getValidator();

    public void validate(T ObjectTOValidate)
    {
        Set<ConstraintViolation<T>> violation =validator.validate(ObjectTOValidate);
        if(!violation.isEmpty())
        {
            var errormessage=violation
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toSet());
            throw new ObjectNotValidException(errormessage);
        }
    }
}
