package springboot.repository;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import jakarta.validation.ConstraintViolationException;
import springboot.domain.Anime;

@ActiveProfiles("test")

@DataJpaTest
@DisplayName("Test for Anime Repository")
class AnimeRepositoryTest {
    @Autowired
    private AnimeRepository animeRepository;

    @Test
    @DisplayName("Save persists anime when Seccessful")
    void save_PersistAnine_WhenSuccessful() {
        Anime animeToBeSaved = createAnime();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);
        Assertions.assertThat(animeSaved).isNotNull();
        Assertions.assertThat(animeSaved.getId()).isNotNull();
        Assertions.assertThat(animeSaved.getName()).isEqualTo(animeToBeSaved.getName());

    }

    @Test
    @DisplayName("Save update anime when Seccessful")
    void save_UodateAnine_WhenSuccessful() {
        Anime animeToBeSaved = createAnime();
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);
        animeSaved.setName("Tres espiãs demais");

        Anime animeUpdated = this.animeRepository.save(animeSaved);
        Assertions.assertThat(animeUpdated).isNotNull();
        Assertions.assertThat(animeUpdated.getId()).isNotNull();
        Assertions.assertThat(animeUpdated.getName()).isEqualTo(animeToBeSaved.getName());

    }

    @Test
    @DisplayName("Find by Name returns empty list when no anime is found")
    void findByName_ReturnsEmpityList_WhenAnimeIsNotFound() {
        List<Anime> animes = this.animeRepository.findByName("xaxa");
        Assertions.assertThat(animes).isEmpty();
    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when name is empty")
    void save_ConstraintViolationException_WhenNameIsEmpty() {
        Anime anime = new Anime();
        Assertions.assertThatThrownBy(() -> this.animeRepository.save(anime))
                .isInstanceOf(ConstraintViolationException.class);
    }

    private Anime createAnime() {
        return Anime.builder()
                .name("Bob Esponja")
                .build();
    }
}
