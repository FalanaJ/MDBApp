package pl.FalanaJ.MedicalDatabaseBlockchainApp.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@Entity
@Table(name = "doctor")
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

    @NotBlank(message = "Podanie numeru PESEL jest obowiązkowe.")
    @Pattern(regexp = "\\d{11}", message = "Numer PESEL musi składać się z 11 cyfr.")
    private String peselNumber;

    @NotBlank(message = "Podanie numeru telefonu jest obowiązkowe.")
    @Pattern(regexp = "\\d{9}", message = "Numer telefonu musi składać się z 9 cyfr.")
    private String phoneNumber;

    @NotBlank(message = "Podanie adresu email jest obowiązkowe.")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Niepoprawny adres e-mail")
    private String email;

    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    private Date createdAt;

    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    //TODO pole do dodania
    private String harmonogram;
    //harmonogram (every Monday and Friday etc.) będzie potem
}
