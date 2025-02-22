package pl.FalanaJ.MedicalDatabaseBlockchainApp.controller.doctor;

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
import pl.FalanaJ.MedicalDatabaseBlockchainApp.model.Doctor;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.service.DoctorService;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/addDoctor")
public class DoctorFormController {

    public final DoctorService doctorService;
    @GetMapping
    public String addDoctor(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "addDoctor";
    }

    @PostMapping
    public String processAddNewDoctorForm(@ModelAttribute("doctor") @Valid Doctor doctor, Errors errors) {
        if(errors.hasErrors()) return "addDoctor";

        doctor.setCreatedAt(new Date());
        doctorService.save(doctor);

        log.info("Nowy doktor został dodany: " + doctor);
        return "home";
    }
}
