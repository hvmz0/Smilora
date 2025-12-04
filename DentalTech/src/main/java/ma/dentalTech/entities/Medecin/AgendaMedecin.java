package ma.dentalTech.entities.Medecin;

import java.time.LocalDateTime;
import java.util.List;
import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class AgendaMedecin implements Comparable<AgendaMedecin> {
    private Long id;
    private Long medecinId;
    private LocalDateTime dateCreation;
    private List<LocalDateTime> disponibilites;
    private List<String> joursDisponibles;
    private String horairesTravail;
    private List<String> conflits;
    private LocalDateTime dateDerniereMaj;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AgendaMedecin)) return false;
        AgendaMedecin that = (AgendaMedecin) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return """
        AgendaMedecin {
          id = %d,
          medecinId = %d,
          dateCreation = %s,
          horairesTravail = '%s',
          dateDerniereMaj = %s,
          disponibilitesCount = %d,
          joursDisponiblesCount = %d,
          conflitsCount = %d
        }
        """.formatted(
                id, medecinId, dateCreation, horairesTravail, dateDerniereMaj,
                disponibilites == null ? 0 : disponibilites.size(),
                joursDisponibles == null ? 0 : joursDisponibles.size(),
                conflits == null ? 0 : conflits.size()
        );
    }

    @Override
    public int compareTo(AgendaMedecin other) {
        return id.compareTo(other.id);
    }
}