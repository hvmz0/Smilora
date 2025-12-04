package ma.dentalTech.entities.InterventionMedecin;

import lombok.*;
import ma.dentalTech.entities.Actes.Actes;
import ma.dentalTech.entities.Consultation.Consultation;
import ma.dentalTech.entities.Medecin.Medecin;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class InterventionMedecin {
    private Long idIM;
    private Double prixNonCouvert;
    private String dentInt;

    private Consultation consultation;
    private Actes acte;
    private Medecin medecin;
}