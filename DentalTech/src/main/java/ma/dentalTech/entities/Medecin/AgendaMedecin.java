package ma.dentalTech.entities.Medecin;

import java.time.LocalDateTime;
import java.util.List;
import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class AgendaMedecin {
    private Long id;
    private Long medecinId;
    private LocalDateTime dateCreation;
    private List<LocalDateTime> disponibilites;
    private List<String> joursDisponibles;
    private String horairesTravail;
    private List<String> conflits;
    private LocalDateTime dateDerniereMaj;

}