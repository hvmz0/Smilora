package ma.dentalTech.entities.Users;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.time.LocalDate;
import java.time.LocalTime;
// On garde TypeEnum s'il existe dans ton diagramme, sinon passe-le aussi en String
import ma.dentalTech.entities.Enums.TypeEnum;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Notification {
    private Long id;
    private String titre;
    private String message;
    private LocalDate date;
    private LocalTime time;
    private TypeEnum type;
    private String priorite;
    private Long UserId;
    private boolean lu;
}