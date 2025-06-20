package pl.FalanaJ.MedicalDatabaseBlockchainApp.controller.doctor;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.component.CustomUserDetails;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.Appointment;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.Doctor;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.User;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.Role;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.repository.AppointmentRepository;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.service.DoctorService;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.service.UserService;

import java.util.Date;
import java.util.List;
@Slf4j
@Controller
@RequiredArgsConstructor
public class DoctorWebController {

    private final DoctorService doctorService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AppointmentRepository appointmentRepository;

    @GetMapping("/admin/doctor-list")
    public String getAllDoctorsView(Model model) {
        List<Doctor> doctors = doctorService.findAll();
        model.addAttribute("doctors", doctors);
        return "admin/doctor-list";
    }

    @GetMapping("/admin/add-doctor")
    public String addDoctor(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "admin/add-doctor";
    }

    @Transactional
    @GetMapping("/doctor/appointment-list")
    public String viewDoctorAppointments(Model model,
                                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        Doctor doctor = userDetails.getUser().getDoctor();
        List<Appointment> appointments = appointmentRepository.findByDoctor(doctor);
        model.addAttribute("appointments", appointments);
        return "doctor/appointment-list";
    }
    @PostMapping("/admin/add-doctor")
    public String processAddNewDoctorForm(@ModelAttribute("doctor") @Valid Doctor doctor, Errors errors, Model model) {
        if(errors.hasErrors()) return "admin/add-doctor";

        if (userService.existsByUsername(doctor.getEmail())) {
            model.addAttribute("errorMessage", "Użytkownik o podanym adresie e-mail już istnieje.");
            return "admin/add-doctor";
        }

        User user = new User();
        user.setUsername(doctor.getEmail());
        user.setPassword(passwordEncoder.encode(doctor.getPeselNumber())); // TODO TYMCZASOWE!
        user.setRole(Role.DOCTOR);

        doctor.setUser(user);
        user.setDoctor(doctor);

        doctor.setCreatedAt(new Date());
        doctorService.save(doctor);

        log.info("Nowy doktor został dodany: " + doctor);
        return "admin/doctor-registered";
    }
}
