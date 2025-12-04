package ma.dentalTech.entities.HistoriqueMedicale;

import lombok.*;
import ma.dentalTech.entities.patient.Patient;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class HistoriqueMedicale {
    private Long idHM;
    private String libelle;
    private Patient patient; // Relation N:1
}
