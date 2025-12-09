package ma.dentalTech.entities.Ordonnance;

import java.time.LocalDate;
import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Ordonnance {
    private Long id;
    private LocalDate date;

}