package pl.FalanaJ.MedicalDatabaseBlockchainApp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.addons.MedicalHistoryStatus;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(name = "medical_history")
public class MedicalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @JsonBackReference
    private Doctor doctor;

    private String doctorLastName;

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
                ", doctor='" + doctorLastName + '\'' +
                ", reason='" + reason + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

}

//1. private Prescription prescription;
//2. Pola związane z Blockchainem
