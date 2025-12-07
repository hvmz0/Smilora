package ma.dentalTech.entities.RDV;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.*;
import ma.dentalTech.entities.Enums.StatutEnum;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class RDV implements Comparable<RDV> {
    private Long id;
    private LocalDateTime heure;
    private LocalDate date;
    private String motif;
    private StatutEnum statut;
    private String noteMedecin;
    private Long patientId;   // Lombok va créer getPatientId() et setPatientId()
    private Long medecinId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RDV)) return false;
        RDV that = (RDV) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return """
        RDV {
          id = %d,
          heure = %s,
          date = %s,
          motif = '%s',
          statut = %s,
          noteMedecin = '%s'
        }
        """.formatted(id, heure, date, motif, statut, noteMedecin);
    }

    @Override
    public int compareTo(RDV other) {
        return id.compareTo(other.id);
    }
}