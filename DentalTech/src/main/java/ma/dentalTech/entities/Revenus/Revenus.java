package ma.dentalTech.entities.Revenus;

import lombok.*;
import ma.dentalTech.entities.CabinetMedicale.CabinetMedicale;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Revenus {
    private Long idRevenu;
    private String titre;
    private Double montant;
    private LocalDateTime dateHeure;
    private CabinetMedicale cabinetMedicale;
}