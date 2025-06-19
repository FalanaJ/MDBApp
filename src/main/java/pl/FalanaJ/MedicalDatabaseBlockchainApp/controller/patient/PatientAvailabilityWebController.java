package pl.FalanaJ.MedicalDatabaseBlockchainApp.controller.patient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.service.DoctorAvailabilityService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PatientAvailabilityWebController {
    private final DoctorAvailabilityService doctorAvailabilityService;

    @GetMapping("patient/availability/list")
    public String viewAllAvailability(Model model){
        model.addAttribute("availabilities", doctorAvailabilityService.findAll());
        return "patient/availability/list";
    }
}
