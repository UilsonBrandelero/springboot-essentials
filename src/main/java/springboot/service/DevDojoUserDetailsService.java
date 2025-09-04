package springboot.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import springboot.domain.DevDojoUser;
import springboot.mapper.DevDojoUserMapper;
import springboot.repository.DevDojoUserRepository;
import springboot.requests.DevDojoUserPostRequestBody;

@Service
@RequiredArgsConstructor

public class DevDojoUserDetailsService implements UserDetailsService {
    private final DevDojoUserRepository devDojoUserRepository;
    private final DevDojoUserMapper devDojoUserMapper;
    //Sobreescreve o metodo que carrega o usuario do banco de dados da classe UserDetailsService para a implementação personalizada
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return  Optional.ofNullable(devDojoUserRepository.findByUsername(username))
                .orElseThrow(() -> new UsernameNotFoundException("DevDojo User not found") );
    }
    public DevDojoUser saveUser(DevDojoUserPostRequestBody devDojoUserPostRequestBody){
        DevDojoUser devDojoUser = devDojoUserMapper.toUser(devDojoUserPostRequestBody);
        devDojoUser.setPassword(new BCryptPasswordEncoder().encode(devDojoUserPostRequestBody.getPassword()));
        return devDojoUserRepository.save(devDojoUser);
    }
}
