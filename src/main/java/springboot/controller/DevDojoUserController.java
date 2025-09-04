package springboot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import springboot.domain.DevDojoUser;
import springboot.requests.DevDojoUserPostRequestBody;
import springboot.service.DevDojoUserDetailsService;
@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class DevDojoUserController {

    private final DevDojoUserDetailsService detailsService;

    @PostMapping("/create_user")
    public ResponseEntity<DevDojoUser> createUser(@RequestBody @Valid DevDojoUserPostRequestBody devDojoUser) {
        return new ResponseEntity<>(detailsService.saveUser(devDojoUser), HttpStatus.CREATED);
    }

}
