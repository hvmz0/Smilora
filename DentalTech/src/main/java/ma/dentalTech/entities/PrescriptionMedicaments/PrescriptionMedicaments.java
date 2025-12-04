package ma.dentalTech.entities.PrescriptionMedicaments;

import ma.dentalTech.entities.Medicaments.Medicaments;
import ma.dentalTech.entities.Ordonnance.Ordonnance;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor

public class PrescriptionMedicaments {
    private Long idPrescription;
    private Double qteParPrise;
    private String uniteParPrise;
    private String frequenceString;
    private String dureeString;

    private Ordonnance ordonnance;
    private Medicaments medicament;
}
