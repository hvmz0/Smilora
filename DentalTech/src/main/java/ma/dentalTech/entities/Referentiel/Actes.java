package ma.dentalTech.entities.Referentiel;

import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Actes  {
    private Long id;
    private String libelle;
    private String categorie;
    private Double prixDeBase;

}