package ma.dentalTech.entities.Ordonnance;

import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class PrescriptionDesMedicaments implements Comparable<PrescriptionDesMedicaments> {
    private Long id;
    private Integer quantite;
    private String frequence;
    private Integer dureeEnJrs;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PrescriptionDesMedicaments)) return false;
        PrescriptionDesMedicaments that = (PrescriptionDesMedicaments) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return """
        PrescriptionDesMedicaments {
          id = %d,
          quantite = %d,
          frequence = '%s',
          dureeEnJrs = %d
        }
        """.formatted(id, quantite, frequence, dureeEnJrs);
    }

    @Override
    public int compareTo(PrescriptionDesMedicaments other) {
        return id.compareTo(other.id);
    }
}