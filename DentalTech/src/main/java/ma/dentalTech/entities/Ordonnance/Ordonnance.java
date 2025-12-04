package ma.dentalTech.entities.Ordonnance;

import java.time.LocalDate;
import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Ordonnance implements Comparable<Ordonnance> {
    private Long id;
    private LocalDate date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ordonnance)) return false;
        Ordonnance that = (Ordonnance) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return """
        Ordonnance {
          id = %d,
          date = %s
        }
        """.formatted(id, date);
    }

    @Override
    public int compareTo(Ordonnance other) {
        return id.compareTo(other.id);
    }
}