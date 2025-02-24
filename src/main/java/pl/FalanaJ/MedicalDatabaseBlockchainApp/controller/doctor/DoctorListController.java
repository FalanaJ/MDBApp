package pl.FalanaJ.MedicalDatabaseBlockchainApp.controller.doctor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.model.Doctor;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.service.DoctorService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DoctorListController {

    private final DoctorService doctorService;

    @GetMapping("/doctorsList")
    public String getAllPatientsView(Model model) {
        List<Doctor> doctors = doctorService.findAll();
        model.addAttribute("doctors", doctors);
        return "doctorsList";
    }
}
