package ma.dentalTech.repository.modules.Finance.Impl.MySQL;

import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.entities.Enums.StatutEnum;
import ma.dentalTech.entities.Finance.Facture;
import ma.dentalTech.entities.Users.User;
import ma.dentalTech.repository.modules.Finance.Api.FactureRepository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FactureRepositoryImpl implements FactureRepository {

    private Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }

    @Override
    public List<Facture> findAll() {
        List<Facture> list = new ArrayList<>();
        String sql = "SELECT * FROM Factures ORDER BY dateFacture DESC";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findAll Factures", e);
        }
        return list;
    }

    @Override
    public Facture findById(Long id) {
        String sql = "SELECT * FROM Factures WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findById Facture", e);
        }
        return null;
    }

    @Override
    public long create(User user) {
        String sql = """
            INSERT INTO Factures (totalFacture, totalPaye, reste, statut, dateFacture, patient_id, consultation_id)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDouble(1, f.getTotalFacture() != null ? f.getTotalFacture() : 0.0);
            ps.setDouble(2, f.getTotalPaye() != null ? f.getTotalPaye() : 0.0);
            ps.setDouble(3, f.getReste() != null ? f.getReste() : f.getTotalFacture());
            ps.setString(4, f.getStatut() != null ? f.getStatut().name() : StatutEnum.NON_PAYE.name());
            ps.setTimestamp(5, f.getDateFacture() != null ? Timestamp.valueOf(f.getDateFacture()) : Timestamp.valueOf(LocalDateTime.now()));
            ps.setLong(6, f.getPatientId());

            if (f.getConsultationId() != null) {
                ps.setLong(7, f.getConsultationId());
            } else {
                ps.setNull(7, Types.BIGINT);
            }

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    f.setId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur create Facture", e);
        }
        return 0;
    }

    @Override
    public void update(Facture f) {
        String sql = """
            UPDATE Factures 
            SET totalFacture = ?, totalPaye = ?, reste = ?, statut = ?
            WHERE id = ?
        """;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDouble(1, f.getTotalFacture());
            ps.setDouble(2, f.getTotalPaye());
            ps.setDouble(3, f.getReste());
            ps.setString(4, f.getStatut().name());
            ps.setLong(5, f.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur update Facture", e);
        }
    }

    @Override
    public void delete(Facture f) {
        deleteById(f.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Factures WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur deleteById Facture", e);
        }
    }

    @Override
    public List<Facture> findByPatientId(Long patientId) {
        List<Facture> list = new ArrayList<>();
        String sql = "SELECT * FROM Factures WHERE patient_id = ? ORDER BY dateFacture DESC";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByPatientId", e);
        }
        return list;
    }

    @Override
    public List<Facture> findByPatientAndStatut(Long patientId, StatutEnum statut) {
        List<Facture> list = new ArrayList<>();
        String sql = "SELECT * FROM Factures WHERE patient_id = ? AND statut = ? ORDER BY dateFacture DESC";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, patientId);
            ps.setString(2, statut.name());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByPatientAndStatut", e);
        }
        return list;
    }

    @Override
    public Facture findByConsultationId(Long consultationId) {
        String sql = "SELECT * FROM Factures WHERE consultation_id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, consultationId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByConsultationId", e);
        }
        return null;
    }

    @Override
    public List<Facture> findByStatut(StatutEnum statut) {
        List<Facture> list = new ArrayList<>();
        String sql = "SELECT * FROM Factures WHERE statut = ? ORDER BY dateFacture DESC";

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
    public List<Facture> findUnpaid() {
        return findByStatut(StatutEnum.NON_PAYE);
    }

    @Override
    public List<Facture> findPaid() {
        return findByStatut(StatutEnum.PAYE);
    }

    @Override
    public List<Facture> findByDate(LocalDate date) {
        List<Facture> list = new ArrayList<>();
        String sql = "SELECT * FROM Factures WHERE DATE(dateFacture) = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByDate", e);
        }
        return list;
    }

    @Override
    public List<Facture> findByDateRange(LocalDateTime debut, LocalDateTime fin) {
        List<Facture> list = new ArrayList<>();
        String sql = "SELECT * FROM Factures WHERE dateFacture BETWEEN ? AND ? ORDER BY dateFacture DESC";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(debut));
            ps.setTimestamp(2, Timestamp.valueOf(fin));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByDateRange", e);
        }
        return list;
    }

    @Override
    public List<Facture> findByMonth(int year, int month) {
        List<Facture> list = new ArrayList<>();
        String sql = "SELECT * FROM Factures WHERE YEAR(dateFacture) = ? AND MONTH(dateFacture) = ? ORDER BY dateFacture DESC";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, year);
            ps.setInt(2, month);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByMonth", e);
        }
        return list;
    }

    @Override
    public Double calculateTotalByPatient(Long patientId) {
        String sql = "SELECT SUM(totalFacture) FROM Factures WHERE patient_id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur calculateTotalByPatient", e);
        }
        return 0.0;
    }

    @Override
    public Double calculateUnpaidByPatient(Long patientId) {
        String sql = "SELECT SUM(reste) FROM Factures WHERE patient_id = ? AND statut = 'NON_PAYE'";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur calculateUnpaidByPatient", e);
        }
        return 0.0;
    }

    @Override
    public Double calculateTotalByDateRange(LocalDateTime debut, LocalDateTime fin) {
        String sql = "SELECT SUM(totalFacture) FROM Factures WHERE dateFacture BETWEEN ? AND ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(debut));
            ps.setTimestamp(2, Timestamp.valueOf(fin));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur calculateTotalByDateRange", e);
        }
        return 0.0;
    }

    @Override
    public Double calculateTotalUnpaid() {
        String sql = "SELECT SUM(reste) FROM Factures WHERE statut = 'NON_PAYE'";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur calculateTotalUnpaid", e);
        }
        return 0.0;
    }

    @Override
    public long countByStatut(StatutEnum statut) {
        String sql = "SELECT COUNT(*) FROM Factures WHERE statut = ?";

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
    public long countUnpaidByPatient(Long patientId) {
        String sql = "SELECT COUNT(*) FROM Factures WHERE patient_id = ? AND statut = 'NON_PAYE'";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur countUnpaidByPatient", e);
        }
        return 0;
    }

    @Override
    public List<Facture> findOverdueFactures(LocalDateTime beforeDate) {
        List<Facture> list = new ArrayList<>();
        String sql = "SELECT * FROM Factures WHERE statut = 'NON_PAYE' AND dateFacture < ? ORDER BY dateFacture";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(beforeDate));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findOverdueFactures", e);
        }
        return list;
    }

    private Facture mapRow(ResultSet rs) throws SQLException {
        Facture f = new Facture();
        f.setId(rs.getLong("id"));
        f.setTotalFacture(rs.getDouble("totalFacture"));
        f.setTotalPaye(rs.getDouble("totalPaye"));
        f.setReste(rs.getDouble("reste"));

        String statut = rs.getString("statut");
        if (statut != null) f.setStatut(StatutEnum.valueOf(statut));

        Timestamp df = rs.getTimestamp("dateFacture");
        if (df != null) f.setDateFacture(df.toLocalDateTime());

        f.setPatientId(rs.getLong("patient_id"));

        long consId = rs.getLong("consultation_id");
        if (!rs.wasNull()) f.setConsultationId(consId);

        return f;
    }
}