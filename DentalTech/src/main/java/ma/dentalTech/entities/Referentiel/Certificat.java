package ma.dentalTech.entities.Referentiel;

import java.time.LocalDate;
import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class Certificat implements Comparable<Certificat> {
    private Long id;
    private LocalDate dateDébut;
    private LocalDate dateDeFin;
    private Integer duree;
    private String noteMedecin;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Certificat)) return false;
        Certificat that = (Certificat) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return """
        Certificat {
          id = %d,
          dateDébut = %s,
          dateDeFin = %s,
          duree = %d,
          noteMedecin = '%s'
        }
        """.formatted(id, dateDébut, dateDeFin, duree, noteMedecin);
    }

    @Override
    public int compareTo(Certificat other) {
        return id.compareTo(other.id);
    }
}