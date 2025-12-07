package ma.dentalTech.entities.DossierMedicale;

import java.time.LocalDate;
import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class DossierMedicale implements Comparable<DossierMedicale> {
    private Long id;
    private LocalDate dateDeCreation;
    private String patientId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DossierMedicale)) return false;
        DossierMedicale that = (DossierMedicale) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return """
        DossierMedicale {
          id = %d,
          dateDeCreation = %s
        }
        """.formatted(id, dateDeCreation);
    }

    @Override
    public int compareTo(DossierMedicale other) {
        return id.compareTo(other.id);
    }
}