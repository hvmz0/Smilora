package ma.dentalTech.entities.Medecin;

import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Medecin {
    private Long id;
    private String specialite;
    private String agenda;

}