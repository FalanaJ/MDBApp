package pl.FalanaJ.MedicalDatabaseBlockchainApp.component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.Address;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.Doctor;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.Patient;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.Gender;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.Role;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.User;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.repository.DoctorRepository;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.repository.PatientRepository;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.repository.UserRepository;

import java.time.LocalDate;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserComponent implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (userRepository.findByUsername("admin").isEmpty()
                && userRepository.findByUsername("patient.patient@example.com").isEmpty()
                && userRepository.findByUsername("doctor.doctor@example.com").isEmpty()) {

            User adminTEST = new User("admin", passwordEncoder.encode("adminpass"), Role.ADMIN);
            User patientTEST = new User("patient", passwordEncoder.encode("patientpass"), Role.PATIENT);
            User doctorTEST = new User("doctor", passwordEncoder.encode("doctorpass"), Role.DOCTOR);

            userRepository.save(adminTEST);
            userRepository.save(patientTEST);
            userRepository.save(doctorTEST);

            Patient patient = new Patient();
            patient.setFirstName("Jan");
            patient.setLastName("Kowalski");
            patient.setDateOfBirth(LocalDate.of(2002, 5, 5));
            patient.setGender(Gender.MALE);
            patient.setPeselNumber("12345678901");
            patient.setPhoneNumber("123456789");
            patient.setEmail("patient.patient@example.com");
            patient.setAddress(new Address("Kwiatowa", "1", "Warszawa", "00-001", "Polska"));
            patient.setUser(patientTEST);
            patient.setCreatedAt(new Date());

            Doctor doctor = new Doctor();
            doctor.setFirstName("Anna");
            doctor.setLastName("Nowak");
            doctor.setSpeciality("Dermatolog");
            doctor.setPeselNumber("98765432109");
            doctor.setPhoneNumber("987654321");
            doctor.setEmail("doctor.doctor@example.com");
            doctor.setAddress(new Address("Zdrowia", "2A", "Kraków", "31-001", "Polska"));
            doctor.setUser(doctorTEST);
            doctor.setCreatedAt(new Date());

            patientRepository.save(patient);
            doctorRepository.save(doctor);

            log.info("[!] Użytkownik ADMIN został utworzony.");
            log.info("[!] Użytkownik PATIENT został utworzony.");
            log.info("[!] Użytkownik DOCTOR został utworzony.");
        }
    }
}
