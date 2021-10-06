package ar.com.mzanetti.iveo.service;


import ar.com.mzanetti.iveo.persistence.User;
import reactor.core.publisher.Mono;

public interface UserService {
    void save(User user);
    Mono<User> findByUsername(String username);
}
