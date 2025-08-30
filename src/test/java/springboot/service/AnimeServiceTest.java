package springboot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import springboot.domain.Anime;
import springboot.exeption.BadRequestException;
import springboot.mapper.AnimeMapper;
import springboot.repository.AnimeRepository;
import springboot.requests.AnimePostRequestBody;
import springboot.requests.AnimePutRequestBody;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AnimeServiceTest {

    @Mock
    private AnimeRepository animeRepository;

    @Mock
    private AnimeMapper animeMapper;

    @InjectMocks
    private AnimeService animeService;

    private Anime anime;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        anime = new Anime();
        anime.setId(1L);
        anime.setName("Naruto");
    }

    @Test
    void listAll_ReturnsPageOfAnimes_WhenSuccessful() {
        PageRequest pageable = PageRequest.of(0, 5);
        Page<Anime> animePage = new PageImpl<>(List.of(anime));

        when(animeRepository.findAll(pageable)).thenReturn(animePage);

        Page<Anime> result = animeService.listAll(pageable);

        assertThat(result).isNotEmpty();
        assertThat(result.getContent().get(0).getName()).isEqualTo("Naruto");
    }

    @Test
    void findByIdOrThrowBadRequest_ReturnsAnime_WhenFound() {
        when(animeRepository.findById(1L)).thenReturn(Optional.of(anime));

        Anime result = animeService.findByIdOrThrowBadRequest(1L);

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Naruto");
    }

    @Test
    void findByIdOrThrowBadRequest_ThrowsException_WhenNotFound() {
        when(animeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> animeService.findByIdOrThrowBadRequest(1L))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Anime not found");
    }

    @Test
    void save_PersistsAnime_WhenSuccessful() {
        AnimePostRequestBody requestBody = AnimePostRequestBody.builder().build();
        requestBody.setName("Naruto");

        when(animeMapper.toAnime(requestBody)).thenReturn(anime);
        when(animeRepository.save(any(Anime.class))).thenReturn(anime);

        Anime result = animeService.save(requestBody);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Naruto");
    }

    @Test
    void delete_RemovesAnime_WhenSuccessful() {
        when(animeRepository.findById(1L)).thenReturn(Optional.of(anime));
        doNothing().when(animeRepository).delete(anime);

        animeService.delete(1L);

        verify(animeRepository, times(1)).delete(anime);
    }

    @Test
    void replace_UpdatesAnime_WhenSuccessful() {
        AnimePutRequestBody requestBody = new AnimePutRequestBody();
        requestBody.setId(1L);
        requestBody.setName("Naruto Shippuden");

        Anime animeUpdated = new Anime();
        animeUpdated.setId(1L);
        animeUpdated.setName("Naruto Shippuden");

        when(animeRepository.findById(1L)).thenReturn(Optional.of(anime));
        when(animeMapper.toAnime(requestBody)).thenReturn(animeUpdated);
        when(animeRepository.save(any(Anime.class))).thenReturn(animeUpdated);

        animeService.replace(requestBody);

        verify(animeRepository, times(1)).save(animeUpdated);
    }

    @Test
    void findByName_ReturnsListOfAnimes_WhenSuccessful() {
        when(animeRepository.findByName("Naruto")).thenReturn(List.of(anime));

        List<Anime> result = animeService.findByName("Naruto");

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getName()).isEqualTo("Naruto");
    }

    @Test
    void listAllNoPageable_ReturnsListOfAnimes_WhenSuccessful() {
        when(animeRepository.findAll()).thenReturn(List.of(anime));

        List<Anime> result = animeService.listAllNoPageable();

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getName()).isEqualTo("Naruto");
    }
}
