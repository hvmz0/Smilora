package ma.dentalTech.entities.Secretaire;

import lombok.*;
import ma.dentalTech.entities.CabinetMedicale.CabinetMedicale;
import ma.dentalTech.entities.Staff.Staff;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Secretaire extends Staff {
    private String numCNSS;
    private CabinetMedicale cabinetGerer;
}