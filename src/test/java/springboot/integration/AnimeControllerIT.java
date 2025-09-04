package springboot.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import springboot.domain.Anime;
import springboot.repository.AnimeRepository;
import springboot.requests.DevDojoUserPostRequestBody;
import springboot.service.DevDojoUserDetailsService;
import springboot.util.AnimeCreator;
import springboot.wrapper.PageableResponse;

//Roda testes com o spring utilizando um porta aleatoria
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class AnimeControllerIT {
        @Autowired
        @Qualifier(value = "testRestTemplateRoleAdminCreator")
        private TestRestTemplate testRestRoleAdminTemplate;
        @Autowired
        @Qualifier(value = "testRestTemplateRoleUserCreator")
        private TestRestTemplate testRestRoleUserTemplate;
        @Autowired
        private DevDojoUserDetailsService devDojoUserDetailsService;
        @Autowired
        private AnimeRepository animeRepository;

        private static final DevDojoUserPostRequestBody USER = DevDojoUserPostRequestBody.builder().name("Usuario")
                        .username("user")
                        .password("1234")
                        .authorities("ROLE_USER")
                        .build();
        private static final DevDojoUserPostRequestBody ADMIM = DevDojoUserPostRequestBody.builder()
                        .name("Administrador")
                        .username("admin")
                        .password("1234")
                        .authorities("ROLE_USER,ROLE_ADMIN")
                        .build();

        // Classe com metodos para gerar RestTameplates com parametros diferentes para
        // utilizar nos metodos
        // Estes tamplates seram utilizados para fazer as requisiçoes no controller
        @TestConfiguration
        @Lazy
        static class Config {
                @Bean(name = "testRestTemplateRoleAdminCreator")
                public TestRestTemplate testRestTemplateRoleAdminCreator(@Value("${local.server.port}") int port) {
                        RestTemplateBuilder restTameplateBuilder = new RestTemplateBuilder()
                                        .rootUri("http://localhost:" + port)
                                        .basicAuthentication("admin", "1234");
                        return new TestRestTemplate(restTameplateBuilder);
                }

                @Bean(name = "testRestTemplateRoleUserCreator")
                public TestRestTemplate testRestTemplateRoleUserCreator(@Value("${local.server.port}") int port) {
                        RestTemplateBuilder restTameplateBuilder = new RestTemplateBuilder()
                                        .rootUri("http://localhost:" + port)
                                        .basicAuthentication("user", "1234");
                        return new TestRestTemplate(restTameplateBuilder);
                }
        }

        @Test
        void list_Returns204DeleteAnime_WhenSuccessful() {
                Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
                devDojoUserDetailsService.saveUser(ADMIM);

                ResponseEntity<Void> delete = testRestRoleAdminTemplate.exchange("/animes/admin/{id}",
                                HttpMethod.DELETE,
                                null,
                                Void.class, savedAnime.getId());
                Assertions.assertThat(delete).isNotNull();
                Assertions.assertThat(delete.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        }

        @Test
        void list_Returns403Forbidden_WhenUserIsNotPermitionsRole() {
                devDojoUserDetailsService.saveUser(USER);

                ResponseEntity<Void> delete = testRestRoleUserTemplate.exchange("/animes/admin/1", HttpMethod.DELETE,
                                null,
                                Void.class);
                Assertions.assertThat(delete).isNotNull();
                Assertions.assertThat(delete.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

        }

        @SuppressWarnings("null")
        @Test
        void list_ReturnsListOfAnimeInsidePageObject_WhenSuccessful() {
                devDojoUserDetailsService.saveUser(ADMIM);
                Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());
                String expectedName = savedAnime.getName();

                PageableResponse<Anime> animePage = testRestRoleAdminTemplate
                                .exchange("/animes/list", HttpMethod.GET, null,
                                                new ParameterizedTypeReference<PageableResponse<Anime>>() {
                                                })
                                .getBody();
                Assertions.assertThat(animePage).isNotNull();
                Assertions.assertThat(animePage.toList()).isNotEmpty().hasSize(1);

                Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);

        }
        /*
         * forma de recuperar a pagina html do erro gerado pelo controller
         * ResponseEntity<String> response = testRestTemplate.exchange(
         * "/animes/list", // use o endpoint real
         * HttpMethod.GET,
         * null,
         * String.class
         * );
         */

}
