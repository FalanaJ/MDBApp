package pl.FalanaJ.MedicalDatabaseBlockchainApp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
}
