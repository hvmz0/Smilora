package ma.dentalTech.entities.Referentiel;

import java.time.LocalDate;
import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Certificat  {
    private Long id;
    private LocalDate dateDébut;
    private LocalDate dateDeFin;
    private Integer duree;
    private String noteMedecin;
    private Long consultationId;
    private Long patientId;

}