package ma.dentalTech.entities.Referentiel;

import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Medicaments implements Comparable<Medicaments> {
    private Long id;
    private String nom;
    private Double prix;
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Medicaments)) return false;
        Medicaments that = (Medicaments) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return """
        Medicaments {
          id = %d,
          nom = '%s',
          prix = %.2f,
          description = '%s'
        }
        """.formatted(id, nom, prix, description);
    }

    @Override
    public int compareTo(Medicaments other) {
        return id.compareTo(other.id);
    }
}