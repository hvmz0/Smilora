package ma.dentalTech.entities.Referentiel;

import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Medicaments  {
    private Long id;
    private String nom;
    private Double prix;
    private String description;

}