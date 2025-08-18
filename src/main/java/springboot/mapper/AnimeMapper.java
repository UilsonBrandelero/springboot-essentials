package springboot.mapper;

import org.mapstruct.Mapper;

import springboot.domain.Anime;
import springboot.requests.AnimePostRequestBody;
import springboot.requests.AnimePutRequestBody;

@Mapper(componentModel = "spring")
//permite DI 

public interface AnimeMapper {
//Interface mapeia os objetos de uma classe DTO RequestyBody para uma classe modelo que é 
// utilizada pelo repository
    
    //public static final AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);
    public  Anime toAnime(AnimePostRequestBody animePostRequestBody);

    public  Anime toAnime(AnimePutRequestBody animePutRequestBody);
}
