package springboot.exeption;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class ValidationExeptionDetails extends ExeptionDetails{
    private final String fildsMassege;
    private final String filds;
}
