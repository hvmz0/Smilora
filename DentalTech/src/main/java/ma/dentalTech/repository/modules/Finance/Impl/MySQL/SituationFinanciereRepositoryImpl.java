package ma.dentalTech.repository.modules.Finance.Impl.MySQL;

import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.entities.Enums.PromoEnum;
import ma.dentalTech.entities.Enums.StatutEnum;
import ma.dentalTech.entities.Finance.SituationFinanciere;
import ma.dentalTech.entities.Users.User;
import ma.dentalTech.repository.modules.Finance.Api.SituationFinanciereRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SituationFinanciereRepositoryImpl implements SituationFinanciereRepository {

    private Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }

    @Override
    public List<SituationFinanciere> findAll() {
        List<SituationFinanciere> list = new ArrayList<>();
        String sql = "SELECT * FROM SituationFinanciere ORDER BY id DESC";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findAll SituationFinanciere", e);
        }
        return list;
    }

    @Override
    public SituationFinanciere findById(Long id) {
        String sql = "SELECT * FROM SituationFinanciere WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findById SituationFinanciere", e);
        }
        return null;
    }

    @Override
    public long create(User user) {
        String sql = """
            INSERT INTO SituationFinanciere (totalDesAchats, totalPaye, credit, statut, promo, patient_id)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDouble(1, sf.getTotalDesAchats() != null ? sf.getTotalDesAchats() : 0.0);
            ps.setDouble(2, sf.getTotalPaye() != null ? sf.getTotalPaye() : 0.0);
            ps.setDouble(3, sf.getCredit() != null ? sf.getCredit() : 0.0);
            ps.setString(4, sf.getStatut() != null ? sf.getStatut().name() : StatutEnum.NON_PAYE.name());
            ps.setString(5, sf.getPromo() != null ? sf.getPromo().name() : PromoEnum.AUCUNE.name());
            ps.setLong(6, sf.getPatientId());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    sf.setId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur create SituationFinanciere", e);
        }
        return 0;
    }

    @Override
    public void update(SituationFinanciere sf) {
        String sql = """
            UPDATE SituationFinanciere 
            SET totalDesAchats = ?, totalPaye = ?, credit = ?, statut = ?, promo = ?
            WHERE id = ?
        """;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDouble(1, sf.getTotalDesAchats());
            ps.setDouble(2, sf.getTotalPaye());
            ps.setDouble(3, sf.getCredit());
            ps.setString(4, sf.getStatut().name());
            ps.setString(5, sf.getPromo().name());
            ps.setLong(6, sf.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur update SituationFinanciere", e);
        }
    }

    @Override
    public void delete(SituationFinanciere sf) {
        deleteById(sf.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM SituationFinanciere WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur deleteById SituationFinanciere", e);
        }
    }

    @Override
    public SituationFinanciere findByPatientId(Long patientId) {
        String sql = "SELECT * FROM SituationFinanciere WHERE patient_id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByPatientId", e);
        }
        return null;
    }

    @Override
    public List<SituationFinanciere> findByStatut(StatutEnum statut) {
        List<SituationFinanciere> list = new ArrayList<>();
        String sql = "SELECT * FROM SituationFinanciere WHERE statut = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, statut.name());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByStatut", e);
        }
        return list;
    }

    @Override
    public List<SituationFinanciere> findByPromo(PromoEnum promo) {
        List<SituationFinanciere> list = new ArrayList<>();
        String sql = "SELECT * FROM SituationFinanciere WHERE promo = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, promo.name());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByPromo", e);
        }
        return list;
    }

    @Override
    public List<SituationFinanciere> findWithCredit() {
        List<SituationFinanciere> list = new ArrayList<>();
        String sql = "SELECT * FROM SituationFinanciere WHERE credit > 0 ORDER BY credit DESC";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findWithCredit", e);
        }
        return list;
    }

    @Override
    public List<SituationFinanciere> findByCreditGreaterThan(Double montant) {
        List<SituationFinanciere> list = new ArrayList<>();
        String sql = "SELECT * FROM SituationFinanciere WHERE credit > ? ORDER BY credit DESC";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDouble(1, montant);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByCreditGreaterThan", e);
        }
        return list;
    }

    @Override
    public Double calculateTotalCredits() {
        String sql = "SELECT SUM(credit) FROM SituationFinanciere";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur calculateTotalCredits", e);
        }
        return 0.0;
    }

    @Override
    public Double calculateTotalAchats() {
        String sql = "SELECT SUM(totalDesAchats) FROM SituationFinanciere";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur calculateTotalAchats", e);
        }
        return 0.0;
    }

    @Override
    public Double calculateTotalPaye() {
        String sql = "SELECT SUM(totalPaye) FROM SituationFinanciere";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur calculateTotalPaye", e);
        }
        return 0.0;
    }

    @Override
    public List<SituationFinanciere> findTopDebtors(int limit) {
        List<SituationFinanciere> list = new ArrayList<>();
        String sql = "SELECT * FROM SituationFinanciere WHERE credit > 0 ORDER BY credit DESC LIMIT ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findTopDebtors", e);
        }
        return list;
    }

    @Override
    public long countByStatut(StatutEnum statut) {
        String sql = "SELECT COUNT(*) FROM SituationFinanciere WHERE statut = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, statut.name());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur countByStatut", e);
        }
        return 0;
    }

    @Override
    public long countWithCredit() {
        String sql = "SELECT COUNT(*) FROM SituationFinanciere WHERE credit > 0";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur countWithCredit", e);
        }
        return 0;
    }

    private SituationFinanciere mapRow(ResultSet rs) throws SQLException {
        SituationFinanciere sf = new SituationFinanciere();
        sf.setId(rs.getLong("id"));
        sf.setTotalDesAchats(rs.getDouble("totalDesAchats"));
        sf.setTotalPaye(rs.getDouble("totalPaye"));
        sf.setCredit(rs.getDouble("credit"));

        String statut = rs.getString("statut");
        if (statut != null) sf.setStatut(StatutEnum.valueOf(statut));

        String promo = rs.getString("promo");
        if (promo != null) sf.setPromo(PromoEnum.valueOf(promo));

        sf.setPatientId(rs.getLong("patient_id"));

        return sf;
    }
}