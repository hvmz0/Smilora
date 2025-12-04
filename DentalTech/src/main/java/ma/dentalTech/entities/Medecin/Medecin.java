package ma.dentalTech.entities.Medecin;

import lombok.*;
import ma.dentalTech.entities.AgendaMedecin.AgendaMedecin;
import ma.dentalTech.entities.Consultation.Consultation;
import ma.dentalTech.entities.InterventionMedecin.InterventionMedecin;
import ma.dentalTech.entities.Staff.Staff;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Medecin extends Staff {
    private String numCNSS;
    private Double commission;
    private String specialite;

    private AgendaMedecin agenda;
    private List<InterventionMedecin> interventions;
    private List<Consultation> consultations;
}