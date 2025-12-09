package ma.dentalTech.entities.RDV;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.*;
import ma.dentalTech.entities.Enums.StatutEnum;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class RDV {
    private Long id;
    private LocalDateTime heure;
    private LocalDate date;
    private String motif;
    private StatutEnum statut;
    private String noteMedecin;
    private Long patientId;   // Lombok va créer getPatientId() et setPatientId()
    private Long medecinId;

}