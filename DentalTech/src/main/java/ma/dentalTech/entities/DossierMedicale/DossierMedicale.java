package ma.dentalTech.entities.DossierMedicale;

import lombok.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DossierMedicale {
    private Long id;
    private LocalDate dateDeCreation;
    private Long patientId;
}
