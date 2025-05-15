package pl.FalanaJ.MedicalDatabaseBlockchainApp.controller.patient;

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
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.Patient;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.User;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.addons.Role;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.service.PatientService;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.service.UserService;

import java.util.Date;
import java.util.List;
@Slf4j
@Controller
@RequiredArgsConstructor
public class PatientWebController {

    private final PatientService patientService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    @GetMapping("admin/patient-list")
    public String getAllPatientsView(Model model) {
        List<Patient> patients = patientService.findAll();
        model.addAttribute("patients", patients);
        return "admin/patient-list";
    }

    @GetMapping("admin/add-patient")
    public String AddNewPatientForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "admin/add-patient";
    }

    @PostMapping("admin/add-patient")
    public String processAddNewPatientForm(@ModelAttribute("patient") @Valid Patient patient, Errors errors, Model model) {
        if(errors.hasErrors()) return "admin/add-patient";

        if (userService.existsByUsername(patient.getEmail())) {
            model.addAttribute("errorMessage", "Użytkownik o podanym adresie e-mail już istnieje.");
            return "admin/add-patient";
        }

        User user = new User();
        user.setUsername(patient.getEmail());
        user.setPassword(passwordEncoder.encode(patient.getPeselNumber())); // TODO TYMCZASOWE!
        user.setRole(Role.PATIENT);

        patient.setUser(user);
        user.setPatient(patient);

        patient.setCreatedAt(new Date());
        patientService.save(patient);

        log.info("Nowy pacjent został dodany: " + patient);
        return "admin/patient-registered";
    }
}
