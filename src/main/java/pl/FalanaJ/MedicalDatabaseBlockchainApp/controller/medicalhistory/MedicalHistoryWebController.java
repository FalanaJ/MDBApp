package pl.FalanaJ.MedicalDatabaseBlockchainApp.controller.medicalhistory;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.Doctor;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.MedicalHistory;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.Patient;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.service.DoctorService;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.service.MedicalHistoryService;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.service.PatientService;
import java.util.Date;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MedicalHistoryWebController {

    private final MedicalHistoryService medicalHistoryService;
    private final PatientService patientService;
    private final DoctorService doctorService;

    @GetMapping("/add-medical-history")
    public String addNewMedicalHistoryForm(@RequestParam("patientId") Long patientId, Model model) {
        model.addAttribute("medicalHistory", new MedicalHistory());
        model.addAttribute("patientId", patientId);
        model.addAttribute("doctors", doctorService.findAll());
        return "add-medical-history";
    }

    @PostMapping("/view-medical-history")
    public String processAddNewMedicalHistoryForm(@Valid @ModelAttribute("medicalHistory") MedicalHistory medicalHistory,
                                                  @RequestParam("patientId") Long patientId){

        Patient patient = patientService.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Nie ma takiego pacjenta w bazie pacjentów"));

        //Doctor doctor = doctorService.findById(medicalHistory.getDoctor().getId())
                //.orElseThrow(() -> new RuntimeException("Nie ma takiego doktora w bazie doktorów"));

        medicalHistory.setPatient(patient);
        //medicalHistory.setDoctorLastName(doctor.getLastName());
        medicalHistory.setCreatedAt(new Date());
        medicalHistoryService.save(medicalHistory);

        log.info("Nowy wpis medyczny został dodany " + medicalHistory);
        return "redirect:/view-medical-history?patientId=" + patientId;
    }

    @GetMapping("/view-medical-history")
    public String viewMedicalHistory(@RequestParam("patientId") Long patientId, Model model) {
        model.addAttribute("patientId", patientId);

        Patient patient = patientService.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Nie ma takiego pacjenta w bazie"));

        model.addAttribute("patient", patient);
        model.addAttribute("medicalHistories", patient.getMedicalHistories());

        return "view-medical-history";
    }
}