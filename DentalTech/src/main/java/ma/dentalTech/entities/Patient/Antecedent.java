package ma.dentalTech.entities.Patient;

import lombok.*;
import ma.dentalTech.entities.Enums.RisqueEnum;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Antecedent implements Comparable<Antecedent> {
    private Long id;
    private String nom;
    private String categorie;
    private RisqueEnum niveauDeRisque;
    private Long patientId;
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Antecedent)) return false;
        Antecedent that = (Antecedent) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return """
        Antecedent {
          id = %d,
          nom = '%s',
          categorie = '%s',
          niveauDeRisque = %s
        }
        """.formatted(id, nom, categorie, niveauDeRisque);
    }

    @Override
    public int compareTo(Antecedent other) {
        return id.compareTo(other.id);
    }
}