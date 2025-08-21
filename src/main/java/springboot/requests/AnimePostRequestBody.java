package springboot.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AnimePostRequestBody {
    private Long id;
    @NotEmpty(message = "The name of anime cannot be empity")
    @NotNull(message = "The name of anime cannot be null")
    private String name;
    

}
