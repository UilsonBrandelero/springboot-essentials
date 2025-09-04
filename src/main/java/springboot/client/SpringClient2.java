package springboot.client;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SpringClient2 {
    private static final String url1 = "https://imdb.iamidiotareyoutoo.com/search?q={title}";

    public static void main(String[] args) {
         PasswordEncoder ps = new BCryptPasswordEncoder();
        System.out.println(ps.encode("1234"));

    }
}
