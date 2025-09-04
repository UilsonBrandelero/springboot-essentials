package springboot.mapper;

import org.mapstruct.Mapper;

import springboot.domain.DevDojoUser;
import springboot.requests.DevDojoUserPostRequestBody;

@Mapper(componentModel = "spring")
public interface DevDojoUserMapper {
    public DevDojoUser toUser(DevDojoUserPostRequestBody devDojoUserPostRequestBody);
}
