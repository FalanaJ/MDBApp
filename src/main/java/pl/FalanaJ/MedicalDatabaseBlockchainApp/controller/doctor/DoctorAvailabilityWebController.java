package pl.FalanaJ.MedicalDatabaseBlockchainApp.controller.doctor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.component.CustomUserDetails;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.Doctor;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.DoctorAvailability;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.service.DoctorAvailabilityService;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.service.DoctorService;

import java.time.DayOfWeek;

@Slf4j
@Controller
@RequiredArgsConstructor
public class DoctorAvailabilityWebController {
    private final DoctorAvailabilityService doctorAvailabilityService;
    private final DoctorService doctorService;

    @GetMapping("/doctor/availability/form")
    public String showAddForm(Model model){
        model.addAttribute("availability", new DoctorAvailability());
        return "doctor/availability/form";
    }

    @PostMapping("/doctor/availability/form")
    public String saveAvailability(@ModelAttribute DoctorAvailability availability,
                                   @AuthenticationPrincipal CustomUserDetails userDetails,
                                   Model model) {
        Doctor doctor = doctorService.getDoctorByUser(userDetails.getUser());
        availability.setDoctor(doctor);
        doctorAvailabilityService.save(availability);

        model.addAttribute("user", userDetails.getUser());
        log.info("Nowy termin dostępności został dodany przez lekarza: " + doctor.getLastName());
        return "doctor/dashboard";
    }

    @GetMapping("/doctor/availability/list")
    public String listMyAvailability(Model model, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Doctor doctor = doctorService.getDoctorByUser(userDetails.getUser());
        model.addAttribute("availabilities", doctorAvailabilityService.findAllByDoctor(doctor));
        return "doctor/availability/list";
    }
}
