package ma.dentalTech.entities.DossierMedicale;

import java.time.LocalDate;
import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class DossierMedicale  {
    private Long id;
    private LocalDate dateDeCreation;
    private Long patientId;

    public void setDateCreation(LocalDate localDate) {
    }

    public LocalDate getDateCreation() {
    }
}