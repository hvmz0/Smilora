package ma.dentalTech.entities.Referentiel;

import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Actes implements Comparable<Actes> {
    private Long id;
    private String libelle;
    private String categorie;
    private Double prixDeBase;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Actes)) return false;
        Actes that = (Actes) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return """
        Acte {
          id = %d,
          libelle = '%s',
          categorie = '%s',
          prixDeBase = %.2f
        }
        """.formatted(id, libelle, categorie, prixDeBase);
    }

    @Override
    public int compareTo(Actes other) {
        return id.compareTo(other.id);
    }
}