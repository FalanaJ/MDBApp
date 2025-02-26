package pl.FalanaJ.MedicalDatabaseBlockchainApp.controller.medicalhistory;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.model.Doctor;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.model.MedicalHistory;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.model.Patient;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.service.DoctorService;
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
    private final DoctorService doctorService;

    @GetMapping("/addMedicalHistory")
    public String addNewMedicalHistoryForm(@RequestParam("patientId") Long patientId, Model model) {
        model.addAttribute("medicalHistory", new MedicalHistory());
        model.addAttribute("patientId", patientId);
        model.addAttribute("doctors", doctorService.findAll());
        return "addMedicalHistory";
    }

    @PostMapping("viewMedicalHistory")
    public String processAddNewMedicalHistoryForm(@Valid @ModelAttribute("medicalHistory") MedicalHistory medicalHistory,
                                                  @RequestParam("patientId") Long patientId){

        Patient patient = patientService.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Nie ma takiego pacjenta w bazie pacjentów"));

        Doctor doctor = doctorService.findById(medicalHistory.getDoctor().getId())
                .orElseThrow(() -> new RuntimeException("Nie ma takiego doktora w bazie doktorów"));

        medicalHistory.setPatient(patient);
        medicalHistory.setDoctorLastName(doctor.getLastName());
        medicalHistory.setCreatedAt(new Date());
        medicalHistoryService.save(medicalHistory);

        log.info("Nowy wpis medyczny został dodany " + medicalHistory);
        return "redirect:/viewMedicalHistory?patientId=" + patientId;
    }
}