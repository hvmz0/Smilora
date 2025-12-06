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

    private Long patientId;
    private Long medecinId;
    private Long rdvId;
    private Long dossierMedicalId;

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
          observationMed = '%s',
          patientId = %d,
          medecinId = %d,
          rdvId = %d,
          dossierMedicalId = %d,
        }
        """.formatted(id, dateCons, statut, observationMed,patientId, medecinId, rdvId, dossierMedicalId, rdvId);
    }

    @Override
    public int compareTo(Consultation other) {
        return id.compareTo(other.id);
    }
}