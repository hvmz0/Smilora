package ma.dentalTech.entities.Medicaments;

import lombok.*;
import ma.dentalTech.entities.PrescriptionMedicaments.PrescriptionMedicaments;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Medicaments {
    private Long idM;
    private String nom;
    private Double prix;
    private String description;
    private List<PrescriptionMedicaments> prescriptions;
}