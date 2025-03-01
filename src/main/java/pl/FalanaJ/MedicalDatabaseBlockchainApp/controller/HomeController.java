package pl.FalanaJ.MedicalDatabaseBlockchainApp.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.User;

import java.security.Principal;

@Slf4j
@Controller
public class HomeController {
    @GetMapping("/home")
    public String home(Principal user, Model model) {
        if (user != null) {
            log.info("Zalogowany użytkownik: " + user.getName());
            model.addAttribute("username", user.getName());
        } else {
            log.info("Brak zalogowanego użytkownika.");
            model.addAttribute("username", "Gość");
        }
        return "home";
    }
}
