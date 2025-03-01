package pl.FalanaJ.MedicalDatabaseBlockchainApp.controller.patient;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.Patient;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.service.PatientService;

import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatient(@PathVariable Long id){
        return patientService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients(){
        List<Patient> patients = patientService.findAll();
        return patients.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(patients);
    }

    @PostMapping
    public Patient addPatient(@RequestBody Patient patient){
        return patientService.save(patient);
    }

}
