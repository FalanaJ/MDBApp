package pl.FalanaJ.MedicalDatabaseBlockchainApp.controller.patient;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.Patient;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.service.PatientService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PatientsListController {

    private final PatientService patientService;
    @GetMapping("/patientsList")
    public String getAllPatientsView(Model model) {
        List<Patient> patients = patientService.findAll();
        model.addAttribute("patients", patients);
        return "patientsList";
    }
}
