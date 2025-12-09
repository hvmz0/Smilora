package ma.dentalTech.entities.Ordonnance;

import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class PrescriptionDesMedicaments  {
    private Long id;
    private Integer quantite;
    private String frequence;
    private Integer dureeEnJrs;
    private Long ordonnanceId;
    private Long medicamentId;

}