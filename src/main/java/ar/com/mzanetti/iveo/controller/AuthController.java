package ar.com.mzanetti.iveo.controller;

import ar.com.mzanetti.iveo.enums.RoleEnum;
import ar.com.mzanetti.iveo.payload.request.LoginRequest;
import ar.com.mzanetti.iveo.payload.request.SignupRequest;
import ar.com.mzanetti.iveo.payload.response.JwtResponse;
import ar.com.mzanetti.iveo.payload.response.MessageResponse;
import ar.com.mzanetti.iveo.persistence.User;
import ar.com.mzanetti.iveo.repository.UserRepository;
import ar.com.mzanetti.iveo.security.jwt.JwtUtils;
import ar.com.mzanetti.iveo.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername()).block()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Ya existe ese usuario!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail()).block()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Ya existe ese mail!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = new HashSet<>();

        strRoles = signUpRequest.getRole();
        Set<RoleEnum> roles = new HashSet<>();

        if (strRoles == null) {
            RoleEnum userRole = RoleEnum.ROLE_USUARIO;
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ROLE_ADMIN":
                        RoleEnum adminRole = RoleEnum.ROLE_ADMIN;
                        roles.add(adminRole);

                        break;
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user).block();

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}