package pl.FalanaJ.MedicalDatabaseBlockchainApp.controller.patient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.component.CustomUserDetails;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.Appointment;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.DoctorAvailability;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.service.AppointmentService;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.service.DoctorAvailabilityService;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PatientAvailabilityWebController {
    private final DoctorAvailabilityService doctorAvailabilityService;
    private final AppointmentService appointmentService;

    @GetMapping("patient/availability/list")
    public String viewAllAvailability(Model model){
        model.addAttribute("availabilities", doctorAvailabilityService.findAll());
        return "patient/availability/list";
    }

    @GetMapping("/patient/availability/confirm-reservation/{id}")
    public String showConfirmReservation(@PathVariable Long id, Model model) {
        DoctorAvailability availability = doctorAvailabilityService.getById(id).orElseThrow(() -> new RuntimeException("Nie znaleziono tej dostępności"));
        model.addAttribute("availability", availability);
        return "patient/availability/confirm-reservation";
    }

    @PostMapping("/patient/availability/confirm/{id}")
    public String confirmReservation(@PathVariable Long id,
                                     @AuthenticationPrincipal CustomUserDetails userDetails) {
        appointmentService.reserveAppointment(id, userDetails.getUser());
        return "redirect:/patient/dashboard";
    }

    @GetMapping("/patient/availability/cancel-reservation/{id}")
    public String showCancelReservation(@PathVariable Long id, Model model) {
        Appointment appointment = appointmentService.getById(id).orElseThrow(() -> new RuntimeException("Nie znaleziono tej wizyty"));
        model.addAttribute("appointment", appointment);
        return "patient/availability/cancel-reservation";
    }

    @PostMapping("/patient/availability/cancel-confirm/{id}")
    public String confirmCancelReservation(@PathVariable Long id,
                                           @AuthenticationPrincipal CustomUserDetails userDetails) {
        appointmentService.cancelAppointment(id, userDetails.getUser());
        return "redirect:/patient/dashboard";
    }
}
