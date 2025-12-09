package ma.dentalTech.entities.Finance;

import java.time.LocalDateTime;
import lombok.*;
import ma.dentalTech.entities.Enums.StatutEnum;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Facture  {
    private Long id;
    private Double totalFacture;
    private Double totalPaye;
    private Double reste;
    private StatutEnum statut;
    private LocalDateTime dateFacture;
    private Long patientId;
    private Long consultationId;

}