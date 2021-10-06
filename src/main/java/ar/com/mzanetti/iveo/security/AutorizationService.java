package ar.com.mzanetti.iveo.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutorizationService {

    public boolean hasRole(Object obj, String role) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) obj;
        List<GrantedAuthority> grantedRoles = usernamePasswordAuthenticationToken.getAuthorities().stream().filter
                (grantedAuthority -> grantedAuthority.getAuthority().equals(role)).collect(Collectors.toList());
        return !grantedRoles.isEmpty();
    }

}
