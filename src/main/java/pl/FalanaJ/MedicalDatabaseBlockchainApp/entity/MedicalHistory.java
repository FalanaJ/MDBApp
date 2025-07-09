package pl.FalanaJ.MedicalDatabaseBlockchainApp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "medical_history")
public class MedicalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Wybór typu wpisu jest obowiązkowy.")
    private MedicalHistoryType type;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Wybór statusu wpisu jest obowiązkowy.")
    private MedicalHistoryStatus status;

    @NotNull(message = "Data nie może być pusta.")
    private LocalDate date;

    @NotBlank(message = "Podanie powodu wizyty jest obowiązkowe.")
    private String description;

    @ManyToOne
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonBackReference
    private Patient patient;

    private String hash;
    private String previousHash;

    private LocalDateTime createdAt;

    public MedicalHistory(MedicalHistoryType type, Appointment appointment, LocalDate date,
                          Patient patient, LocalDateTime createdAt, MedicalHistoryStatus status) {
        this.type = type;
        this.appointment = appointment;
        this.date = date;
        this.patient = patient;
        this.createdAt = createdAt;
        this.status = status;
    }
    @Override
    public String toString() {
        return "MedicalHistory{" +
                "id=" + id +
                ", desc='" + description + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
