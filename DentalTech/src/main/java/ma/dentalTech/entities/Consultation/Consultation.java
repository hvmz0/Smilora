package ma.dentalTech.entities.Consultation;

import java.time.LocalDate;
import lombok.*;
import ma.dentalTech.entities.Enums.StatutEnum;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Consultation  {
    private Long id;
    private LocalDate dateCons;
    private StatutEnum statut;
    private String observationMed;

    private Long patientId;
    private Long medecinId;
    private Long rdvId;
    private Long dossierMedicalId;

}