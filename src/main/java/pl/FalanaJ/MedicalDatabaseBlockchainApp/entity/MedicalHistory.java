package pl.FalanaJ.MedicalDatabaseBlockchainApp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(name = "medical_history")
public class MedicalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Wybór statusu wpisu jest obowiązkowy.")
    private MedicalHistoryType type;

    @NotNull(message = "Data nie może być pusta.")
    private LocalDate date;

    @NotBlank(message = "Podanie powodu wizyty jest obowiązkowe.")
    private String description;

    @OneToOne
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonBackReference
    private Patient patient;

    private String blockchainHash;

    private Date createdAt;
    @Override
    public String toString() {
        return "MedicalHistory{" +
                "id=" + id +
                ", desc='" + description + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

}

//Pola innych typów
