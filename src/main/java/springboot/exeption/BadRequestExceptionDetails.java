package springboot.exeption;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
//Quer que os campos herdados entrem na igualdade? → callSuper = true
//Não quer? → callSuper = false
@EqualsAndHashCode(callSuper = false)
@SuperBuilder

public class BadRequestExceptionDetails extends ExeptionDetails {

}
