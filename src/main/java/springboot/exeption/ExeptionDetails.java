package springboot.exeption;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.experimental.SuperBuilder;
@Data
@SuperBuilder
public class ExeptionDetails {
    protected String title;
    protected int status;
    protected String details;
    protected String developerMessage;
    protected LocalDateTime timestamp;
}
