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
                && userRepository.findByUsername("patient.patient@example.com").isEmpty()
                && userRepository.findByUsername("doctor.doctor@example.com").isEmpty()) {

            User adminTEST = new User();
            adminTEST.setUsername("admin");
            adminTEST.setPassword(passwordEncoder.encode("adminpassword"));
            adminTEST.setRole(Role.ADMIN);

            User patientTEST = new User();
            patientTEST.setUsername("patient.patient@example.com");
            patientTEST.setPassword(passwordEncoder.encode("patientpassword"));
            patientTEST.setRole(Role.PATIENT);

            User doctorTEST = new User();
            doctorTEST.setUsername("doctor.doctor@example.com");
            doctorTEST.setPassword(passwordEncoder.encode("doctorpassword"));
            doctorTEST.setRole(Role.DOCTOR);

            userRepository.save(adminTEST);
            userRepository.save(patientTEST);
            userRepository.save(doctorTEST);

            log.info("[!] Użytkownik ADMIN został utworzony.");
            log.info("[!] Użytkownik PATIENT został utworzony.");
            log.info("[!] Użytkownik DOCTOR został utworzony.");
        }
    }
}
