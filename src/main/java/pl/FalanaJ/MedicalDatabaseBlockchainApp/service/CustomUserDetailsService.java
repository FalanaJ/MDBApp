package pl.FalanaJ.MedicalDatabaseBlockchainApp.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.component.CustomUserDetails;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.User;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new CustomUserDetails(user);
    }
}
