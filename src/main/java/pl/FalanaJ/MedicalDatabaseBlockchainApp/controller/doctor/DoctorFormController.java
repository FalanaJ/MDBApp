package pl.FalanaJ.MedicalDatabaseBlockchainApp.controller.doctor;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.Doctor;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.User;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.addons.Role;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.service.DoctorService;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/add-doctor")
public class DoctorFormController {

    public final DoctorService doctorService;
    private final PasswordEncoder passwordEncoder;
    @GetMapping
    public String addDoctor(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "admin/add-doctor";
    }

    @PostMapping
    public String processAddNewDoctorForm(@ModelAttribute("doctor") @Valid Doctor doctor, Errors errors) {
        if(errors.hasErrors()) return "admin/add-doctor";

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
