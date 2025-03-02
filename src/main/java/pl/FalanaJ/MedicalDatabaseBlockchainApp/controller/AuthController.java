package pl.FalanaJ.MedicalDatabaseBlockchainApp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.addons.Role;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.User;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.repository.UserRepository;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @GetMapping("/login")
    public String loginForm(){
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(){
        return "/register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String email,
                               @RequestParam String password,
                               @RequestParam String confirmPassword) {


        if (!password.equals(confirmPassword)) {
            return "redirect:/register?error";
        }

        if (userRepository.findByUsername(email).isPresent()) {
            return "redirect:/register?error";
        }

        User user = new User();
        user.setUsername(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.ADMIN);

        userRepository.save(user);
        return "redirect:/login";
    }
}
