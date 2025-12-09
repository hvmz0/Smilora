package ma.dentalTech.entities.Users;

import java.time.LocalDate;
import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Staff {
    private Long id;
    private Double salaire;
    private Double prime;
    private LocalDate dateRecrutement;
    private Integer soldeConge;
    private Long userId;


}