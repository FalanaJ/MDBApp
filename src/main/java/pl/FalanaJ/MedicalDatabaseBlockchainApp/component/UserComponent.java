package pl.FalanaJ.MedicalDatabaseBlockchainApp.component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.addons.Role;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.User;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.repository.UserRepository;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserComponent implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("admin").isEmpty()
                && userRepository.findByUsername("patient.patient@example.com").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("adminpassword"));
            admin.setRole(Role.ADMIN);

            User patient = new User();
            patient.setUsername("patient.patient@example.com");
            patient.setPassword(passwordEncoder.encode("patientpassword"));
            patient.setRole(Role.PATIENT);

            userRepository.save(admin);
            userRepository.save(patient);
            log.info("[!] Użytkownik ADMIN został utworzony.");
            log.info("[!] Użytkownik PATIENT został utworzony.");
        }
    }
}
