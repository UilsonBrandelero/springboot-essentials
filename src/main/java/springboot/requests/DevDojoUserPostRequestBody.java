package springboot.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DevDojoUserPostRequestBody {

    private Long id;
    private String username;
    private String name;
    private String password;
    private String authorities;
}
