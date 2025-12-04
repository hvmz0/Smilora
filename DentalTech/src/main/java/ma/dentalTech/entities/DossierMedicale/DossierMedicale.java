package ma.dentalTech.entities.DossierMedicale;

import lombok.*;
import ma.dentalTech.entities.Consultation.Consultation;
import ma.dentalTech.entities.patient.Patient;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class DossierMedicale {
    private Long idDM;
    private LocalDate dateCreation;
    private String localite;

    private Patient patient; // Relation 1:1
    private List<Consultation> consultations;
}