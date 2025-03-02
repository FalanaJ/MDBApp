package pl.FalanaJ.MedicalDatabaseBlockchainApp.entity;

import jakarta.persistence.*;
import lombok.Data;
import pl.FalanaJ.MedicalDatabaseBlockchainApp.entity.addons.Role;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Patient patient;
}
