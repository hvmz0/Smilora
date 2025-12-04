package ma.dentalTech.entities.Charges;

import lombok.*;
import ma.dentalTech.entities.CabinetMedicale.CabinetMedicale;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Charges {
    private Long idCharge;
    private String titre;
    private Double montant;
    private LocalDateTime dateHeure;
    private CabinetMedicale cabinetMedicale;
}