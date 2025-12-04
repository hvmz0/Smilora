package ma.dentalTech.entities.Consultation;

import java.time.LocalDate;
import lombok.*;
import ma.dentalTech.entities.Enums.StatutEnum;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Consultation implements Comparable<Consultation> {
    private Long id;
    private LocalDate dateCons;
    private StatutEnum statut;
    private String observationMed;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Consultation)) return false;
        Consultation that = (Consultation) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return """
        Consultation {
          id = %d,
          dateCons = %s,
          statut = %s,
          observationMed = '%s'
        }
        """.formatted(id, dateCons, statut, observationMed);
    }

    @Override
    public int compareTo(Consultation other) {
        return id.compareTo(other.id);
    }
}