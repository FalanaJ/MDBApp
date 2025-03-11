package pl.FalanaJ.MedicalDatabaseBlockchainApp.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.ToString;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.addons.Gender;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Podanie imienia jest obowiązkowe.")
    @Pattern(regexp = "^[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]+$", message = "Imię musi zaczynać się od wielkiej litery")
    private String firstName;

    @NotBlank(message = "Podanie nazwiska jest obowiązkowe.")
    @Pattern(regexp = "^[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]+$", message = "Nazwisko musi zaczynać się od wielkiej litery")
    private String lastName;

    @NotNull(message = "Data urodzenia nie może być pusta.")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Wybór płci jest obowiązkowy.")
    private Gender gender;

    @NotBlank(message = "Podanie numeru PESEL jest obowiązkowe.")
    @Pattern(regexp = "\\d{11}", message = "Numer PESEL musi składać się z 11 cyfr.")
    private String peselNumber;

    @NotBlank(message = "Podanie numeru telefonu jest obowiązkowe.")
    @Pattern(regexp = "\\d{9}", message = "Numer telefonu musi składać się z 9 cyfr.")
    private String phoneNumber;

    @NotBlank(message = "Podanie adresu email jest obowiązkowe.")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Niepoprawny adres e-mail")
    private String email;

    @NotBlank(message = "Podanie adresu zamieszkania jest obowiązkowe.")
    private String address;

    private Date createdAt;

    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<MedicalHistory> medicalHistories = new ArrayList<>();
}