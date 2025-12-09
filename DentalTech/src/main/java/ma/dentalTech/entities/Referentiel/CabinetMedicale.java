package ma.dentalTech.entities.Referentiel;

import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class CabinetMedicale  {
    private Long id;
    private String nom;
    private String email;
    private String logo;
    private String adresse;
    private String tel;
    private String tel2;
    private String siteWeb;
    private String instagram;
    private String facebook;
    private String description;

}