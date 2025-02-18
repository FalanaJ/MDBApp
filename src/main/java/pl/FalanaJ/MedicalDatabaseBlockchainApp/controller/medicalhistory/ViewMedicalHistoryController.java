package pl.FalanaJ.MedicalDatabaseBlockchainApp.controller.medicalhistory;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.model.Patient;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.service.PatientService;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ViewMedicalHistoryController {

    private final PatientService patientService;
    @GetMapping("/viewMedicalHistory")
    public String viewMedicalHistory(@RequestParam("patientId") Long patientId, Model model) {
        model.addAttribute("patientId", patientId);

        Patient patient = patientService.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Nie ma takiego pacjenta w bazie"));

        model.addAttribute("patient", patient);
        model.addAttribute("medicalHistories", patient.getMedicalHistories());

        return "viewMedicalHistory";
    }
}
