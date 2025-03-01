package pl.FalanaJ.MedicalDatabaseBlockchainApp.controller.patient;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.Patient;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.service.PatientService;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/addPatient")
public class PatientFormController {

    private final PatientService patientService;
    @GetMapping
    public String AddNewPatientForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "addPatient";
    }

    @PostMapping
    public String processAddNewPatientForm(@ModelAttribute("patient") @Valid Patient patient, Errors errors) {
        if(errors.hasErrors()) return "addPatient";

        patient.setCreatedAt(new Date());
        patientService.save(patient);

        log.info("Nowy pacjent został dodany: " + patient);
        return "home";
    }
}
