package pl.FalanaJ.MedicalDatabaseBlockchainApp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
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

    private LocalDate dateOfAppointment;

    private String reason;

    @Enumerated(EnumType.STRING)
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

//todo ZABEZPIECZYĆ FORMULARZ, Poprawić design STRON
//private Prescription prescription;
//Pola związane z Blockchainem
