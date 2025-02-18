package pl.FalanaJ.MedicalDatabaseBlockchainApp.model.wip;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class Doctor{
    private Long id;
    private String speciality;
    private String email;
    private String address;
    private String harmonogram;
    //harmonogram (every Monday and Friday etc.)
}
