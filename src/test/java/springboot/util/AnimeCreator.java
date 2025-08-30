package springboot.util;

import springboot.domain.Anime;

public class AnimeCreator {
    public static Anime createAnimeToBeSaved() {
        return Anime.builder()
                .name("Bob Esponja")
                .build();
    }

    public static Anime createValidAnime() {
        return Anime.builder()
                .name("Bob Esponja")
                .id(1L)
                .build();
    }
    public static Anime createValidUpdateAnime() {
        return Anime.builder()
                .name("Bob Esponja 2")
                .id(1L)
                .build();
    }
    
}
