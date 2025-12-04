package ma.dentalTech.entities.Referentiel;

import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class CabinetMedicale implements Comparable<CabinetMedicale> {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CabinetMedicale)) return false;
        CabinetMedicale that = (CabinetMedicale) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return """
        CabinetMedicale {
          id = %d,
          nom = '%s',
          email = '%s',
          logo = '%s',
          adresse = '%s',
          tel = '%s',
          tel2 = '%s',
          siteWeb = '%s',
          instagram = '%s',
          facebook = '%s',
          description = '%s'
        }
        """.formatted(id, nom, email, logo, adresse, tel, tel2, siteWeb, instagram, facebook, description);
    }

    @Override
    public int compareTo(CabinetMedicale other) {
        return id.compareTo(other.id);
    }
}