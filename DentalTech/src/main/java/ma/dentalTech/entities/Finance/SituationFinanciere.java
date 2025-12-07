package ma.dentalTech.entities.Finance;

import lombok.*;
import ma.dentalTech.entities.Enums.StatutEnum;
import ma.dentalTech.entities.Enums.PromoEnum;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class SituationFinanciere implements Comparable<SituationFinanciere> {
    private Long id;
    private Double totalDesAchats;
    private Double totalPaye;
    private Double credit;
    private StatutEnum statut;
    private PromoEnum promo;
    private Long patientId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SituationFinanciere)) return false;
        SituationFinanciere that = (SituationFinanciere) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return """
        SituationFinanciere {
          id = %d,
          totalDesAchats = %.2f,
          totalPaye = %.2f,
          credit = %.2f,
          statut = %s,
          promo = %s
        }
        """.formatted(id, totalDesAchats, totalPaye, credit, statut, promo);
    }

    @Override
    public int compareTo(SituationFinanciere other) {
        return id.compareTo(other.id);
    }
}