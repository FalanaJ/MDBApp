package pl.FalanaJ.MedicalDatabaseBlockchainApp.controller.doctor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DoctorFormController {
    @GetMapping("/addDoctor")
    public String addDoctor() {
        return "addDoctor";
    }
}
