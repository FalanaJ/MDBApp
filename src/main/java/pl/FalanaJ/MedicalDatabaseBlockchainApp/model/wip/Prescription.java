package pl.FalanaJ.MedicalDatabaseBlockchainApp.model.wip;

import pl.FalanaJ.MedicalDatabaseBlockchainApp.model.wip.Medicine;

import java.time.LocalDate;
import java.util.ArrayList;

public class Prescription {
    private Long id;
    private Long patientId;
    private Long doctorId;
    private LocalDate dateOfPrescription;
    private ArrayList<Medicine> medicines;
}
