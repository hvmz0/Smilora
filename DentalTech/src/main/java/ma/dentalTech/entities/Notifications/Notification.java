package ma.dentalTech.entities.Notifications;

import lombok.*;
import ma.dentalTech.entities.User.User;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Notification {
    private Long idNotif;
    private String message;
    private LocalDateTime dateHeure;
    private String typeEnum;
    private String niveauEnum;

    private User receveur;
    private User envoyeur;
}