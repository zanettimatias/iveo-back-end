package ar.com.mzanetti.iveo.service;

import ar.com.mzanetti.iveo.persistence.User;
import ar.com.mzanetti.iveo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.HashSet;

@Service
    public class UserServiceImpl implements UserService {
        @Autowired
        UserRepository userRepository;

        @Override
        public void save(User user) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setRoles(user.getRoles());
            userRepository.save(user);
        }

        @Override
        public Mono<User> findByUsername(String username) {
            return userRepository.findByUsername(username);
        }
}
