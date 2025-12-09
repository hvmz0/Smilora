package ma.dentalTech.entities.Patient;

import lombok.*;
import ma.dentalTech.entities.Enums.RisqueEnum;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Antecedent {
    private Long id;
    private String nom;
    private String categorie;
    private RisqueEnum niveauDeRisque;
    private Long patientId;
    private String description;

}