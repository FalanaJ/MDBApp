package pl.FalanaJ.MedicalDatabaseBlockchainApp.model;

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

    @NotNull(message = "Data spotkania nie może być pusta.")
    private LocalDate dateOfAppointment;

    @NotBlank(message = "Podanie powodu wizyty jest obowiązkowe.")
    private String reason;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Wybór statusu wpisu jest obowiązkowy.")
    private MedicalHistoryStatus status;

    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonBackReference
    private Patient patient;

    @Override
    public String toString() {
        return "MedicalHistory{" +
                "id=" + id +
                ", reason='" + reason + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

}

//todo ZABEZPIECZYĆ FORMULARZ 1/2 bo funkcjonuje ale tylko po stronie klienta
//private Prescription prescription;
//Pola związane z Blockchainem
