package pl.FalanaJ.MedicalDatabaseBlockchainApp.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "medical_notes")
public class MedicalNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "appointment_id", nullable = false, unique = true)
    private Appointment appointment;

    private String diagnosis;
    private String recommendations;
    private String prescription;

    private LocalDateTime createdAt = LocalDateTime.now();
}
