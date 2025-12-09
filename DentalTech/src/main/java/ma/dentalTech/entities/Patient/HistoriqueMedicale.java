package ma.dentalTech.entities.Patient;

import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class HistoriqueMedicale {
    private Long id;
    private String libele;
    private Long dossierMedicalId;
}