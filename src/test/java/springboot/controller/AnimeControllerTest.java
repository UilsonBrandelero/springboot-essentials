package springboot.controller;

import java.util.Collections;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import springboot.domain.Anime;
import springboot.requests.AnimePostRequestBody;
import springboot.service.AnimeService;
import springboot.util.AnimeCreator;
import springboot.util.AnimePostRequestBodyCreator;

@ExtendWith(SpringExtension.class)
class AnimeControllerTest {
        @InjectMocks
        private AnimeController animeController;
        @Mock
        private AnimeService animeServiceMock;

        // Esta anotação define o comportamento que o metodo list do animeServiceMock
        // tera quando executado
        // A classe testada nao acessa a classe mokada original somente o comportamneto
        // definido
        @BeforeEach
        void setup() {
                PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createAnimeToBeSaved()));
                BDDMockito.when(animeServiceMock.listAll(ArgumentMatchers.any()))
                                .thenReturn(animePage);
                BDDMockito.when(animeServiceMock.listAllNoPageable())
                                .thenReturn(List.of(AnimeCreator.createAnimeToBeSaved()));
                BDDMockito.when(animeServiceMock.findByIdOrThrowBadRequest(ArgumentMatchers.anyLong()))
                                .thenReturn(AnimeCreator.createAnimeToBeSaved());
                BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
                                .thenReturn(List.of(AnimeCreator.createAnimeToBeSaved()));
                BDDMockito.when(animeServiceMock.save(ArgumentMatchers.any(AnimePostRequestBody.class)))
                                .thenReturn(AnimeCreator.createValidAnime());
        }

        @SuppressWarnings("null")
        @Test
        void list_ReturnsListOfAnimeInsidePageObject_WhenSuccessful() {
                String expectedName = AnimeCreator.createAnimeToBeSaved().getName();
                Page<Anime> animePage = animeController.list(null).getBody();

                Assertions.assertThat(animePage).isNotNull();
                Assertions.assertThat(animePage.toList()).isNotEmpty().hasSize(1);

                Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);

        }

        @SuppressWarnings("null")
        @Test
        void listAll_ReturnsListOfAnime_WhenSuccessful() {
                String expectedName = AnimeCreator.createAnimeToBeSaved().getName();
                List<Anime> animes = animeController.listAllNoPageable().getBody();

                Assertions.assertThat(animes)
                                .isNotNull()
                                .isNotEmpty()
                                .hasSize(1);
                Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);

        }

        @SuppressWarnings("null")
        @Test
        void findByIdOrThrowBadRequest_ReturnsAnime_WhenSuccessful() {
                Long expectedId = AnimeCreator.createAnimeToBeSaved().getId();
                Anime anime = animeController.findById(1L).getBody();

                Assertions.assertThat(anime)
                                .isNotNull();
                Assertions.assertThat(anime.getId()).isEqualTo(expectedId);

        }

        @SuppressWarnings("null")
        @Test
        void findByName_ReturnsAnimeList_WhenSuccessful() {
                String expectedName = AnimeCreator.createValidAnime().getName();
                List<Anime> animes = animeController.findByName("Anime").getBody();
                Assertions.assertThat(animes)
                                .isNotNull()
                                .isNotEmpty()
                                .hasSize(1);
                Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);

        }

        @SuppressWarnings("unchecked")
        @Test
        void findByName_ReturnsEmptyList_WhenSuccessful() {
                // SObrescreve o comportamento definido pelo BeforeEach
                // O metodo neste escopo passa a ter o comportamento definido abaixo
                BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
                                .thenReturn(Collections.EMPTY_LIST);
                List<Anime> animes = animeController.findByName("Anime").getBody();
                Assertions.assertThat(animes)
                                .isNotNull()
                                .isEmpty();

        }

        @Test
        void save_ReturnsAnime_WhenSuccessful() {
                Anime anime = animeController.save(AnimePostRequestBodyCreator.createAnimePostRequestBody()).getBody();

                Assertions.assertThat(anime)
                                .isNotNull();
                Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createValidAnime());

        }
}
