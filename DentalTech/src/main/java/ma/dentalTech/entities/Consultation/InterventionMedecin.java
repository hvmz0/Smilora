package ma.dentalTech.entities.Consultation;

import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class InterventionMedecin  {
    private Long id;
    private Double prixDePatient;
    private Integer numDent;
    private Long consultationId;
    private Long acteId;
    private Long MedecinId;
}