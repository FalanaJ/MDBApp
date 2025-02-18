package pl.FalanaJ.MedicalDatabaseBlockchainApp.controller.medicalhistory;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.model.MedicalHistory;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.model.Patient;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.service.MedicalHistoryService;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.service.PatientService;

import java.util.Date;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MedicalHistoryFormController {

    private final MedicalHistoryService medicalHistoryService;
    private final PatientService patientService;

    @GetMapping("/addMedicalHistory")
    public String addNewMedicalHistoryForm(@RequestParam("patientId") Long patientId, Model model) {
        model.addAttribute("medicalHistory", new MedicalHistory());
        model.addAttribute("patientId", patientId);
        return "addMedicalHistory";
    }

    @PostMapping("/home")
    public String processAddNewMedicalHistoryForm(@Valid MedicalHistory medicalHistory, @RequestParam("patientId") Long patientId){

        Patient patient = patientService.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Nie ma takiego pacjenta w bazie pacjentów"));

        medicalHistory.setPatient(patient);
        medicalHistory.setCreatedAt(new Date());
        medicalHistoryService.save(medicalHistory);

        log.info("Nowy wpis medyczny został dodany " + medicalHistory);
        return "home";
    }

}
