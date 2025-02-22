package pl.FalanaJ.MedicalDatabaseBlockchainApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "doctors")
public class Doctor{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Podanie imienia jest obowiązkowe.")
    @Pattern(regexp = "^[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]+$", message = "Imię musi zaczynać się od wielkiej litery")
    private String firstName;

    @NotBlank(message = "Podanie nazwiska jest obowiązkowe.")
    @Pattern(regexp = "^[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]+$", message = "Nazwisko musi zaczynać się od wielkiej litery")
    private String lastName;

    @NotBlank(message = "Podanie specjalizacji jest obowiązkowe.")
    @Pattern(regexp = "^[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]+$", message = "Nazwisko musi zaczynać się od wielkiej litery")
    private String speciality;

    @NotBlank(message = "Podanie numeru telefonu jest obowiązkowe.")
    @Pattern(regexp = "\\d{9}", message = "Numer telefonu musi składać się z 9 cyfr.")
    private String phoneNumber;

    @NotBlank(message = "Podanie adresu email jest obowiązkowe.")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Niepoprawny adres e-mail")
    private String email;

    @NotBlank(message = "Podanie adresu zamieszkania jest obowiązkowe.")
    private String address;

    private Date createdAt;

    private String harmonogram;
    //harmonogram (every Monday and Friday etc.) będzie potem
}
