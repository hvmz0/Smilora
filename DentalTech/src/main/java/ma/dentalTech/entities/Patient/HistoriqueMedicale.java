package ma.dentalTech.entities.Patient;

import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class HistoriqueMedicale implements Comparable<HistoriqueMedicale> {
    private Long id;
    private String libele;
    private Long dossierMedicalId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HistoriqueMedicale)) return false;
        HistoriqueMedicale that = (HistoriqueMedicale) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return """
        HistoriqueMedicale {
          id = %d,
          libele = '%s'
        }
        """.formatted(id, libele);
    }

    @Override
    public int compareTo(HistoriqueMedicale other) {
        return id.compareTo(other.id);
    }
}