package ma.dentalTech.entities.Statistique;

import lombok.*;
import ma.dentalTech.entities.CabinetMedicale.CabinetMedicale;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Statistique {
    private Long idStat;
    private Double totalCharges;
    private Double totalRevenues;
    private LocalDateTime dateLocalTime;
    private CabinetMedicale cabinetMedicale;
}