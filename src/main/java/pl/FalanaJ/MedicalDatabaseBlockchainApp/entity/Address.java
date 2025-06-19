package pl.FalanaJ.MedicalDatabaseBlockchainApp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Getter
@Setter
@Data
@Entity
@Table(name = "address")
@RequiredArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nazwa ulicy nie może być pusta.")
    private String street;

    @NotBlank(message = "Numer domu nie może być pusty.")
    private String houseNumber;

    @NotBlank(message = "Miasto nie może być puste.")
    private String city;

    @NotBlank(message = "Kod pocztowy nie może być pusty.")
    @Pattern(regexp = "\\d{2}-\\d{3}", message = "Kod pocztowy powinien być w formacie XX-XXX.")
    private String postalCode;

    @NotBlank(message = "Kraj nie może być pusty.")
    private String country;

    public Address(String street, String houseNumber, String city, String postalCode, String country) {
        this.street = street;
        this.houseNumber = houseNumber;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }
}
