package ma.dentalTech.entities.Consultation;

import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class InterventionMedecin implements Comparable<InterventionMedecin> {
    private Long id;
    private Double prixDePatient;
    private Integer numDent;
    private Long consultationId;
    private Long acteId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InterventionMedecin)) return false;
        InterventionMedecin that = (InterventionMedecin) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return """
        InterventionMedecin {
          id = %d,
          prixDePatient = %.2f,
          numDent = %d,
          consultationId = %d,
          acteId = %d
        }
        """.formatted(id, prixDePatient, numDent, consultationId, acteId);
    }

    @Override
    public int compareTo(InterventionMedecin other) {
        return id.compareTo(other.id);
    }
}