package springboot.client;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j2;
import springboot.domain.Movie;

@Log4j2
public class SpringClient2 {
    private static final String url1 = "https://imdb.iamidiotareyoutoo.com/search?q={title}";

    public static void main(String[] args) {
         String titleForSeach = "Homen-Aranha";
         
        ResponseEntity<List<Movie>> movie = new RestTemplate().exchange(url1,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Movie>>() {

                });

    }
}
