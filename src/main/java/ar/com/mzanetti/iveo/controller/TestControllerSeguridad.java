package ar.com.mzanetti.iveo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestControllerSeguridad {
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/farmaceutico")
    @PreAuthorize("hasRole('FARMACEUTICO') or hasRole('ADMIN')")
    public String farmaceuticoAccess() {
        return "FARMACEUTICO";
    }

    @GetMapping("/auxiliardeposito")
    @PreAuthorize("hasRole('AUXILIAR_DEPOSITO') or hasRole('ADMIN')")
    public String auxiliarDepositoAcess() {
        return "AUXILIAR_DEPOSITO";
    }

    @GetMapping("/personaldeposito")
    @PreAuthorize("hasRole('PERSONAL_DEPOSITO') or hasRole('ADMIN')")
    public String adminAccess() {
        return "PERSONAL_DEPOSITO";
    }
}