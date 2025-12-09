package ma.dentalTech.repository.modules.RDV.Impl.MySQL;

import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.entities.Enums.StatutEnum;
import ma.dentalTech.entities.RDV.RDV;
import ma.dentalTech.entities.Users.User;
import ma.dentalTech.repository.modules.RDV.Api.RDVRepository;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RDVRepositoryImpl implements RDVRepository {

    private Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }

    @Override
    public List<RDV> findAll() {
        List<RDV> list = new ArrayList<>();
        String sql = "SELECT * FROM RDV ORDER BY date DESC, heure DESC";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findAll RDV", e);
        }
        return list;
    }

    @Override
    public RDV findById(Long id) {
        String sql = "SELECT * FROM RDV WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findById RDV", e);
        }
        return null;
    }

    @Override
    public long create(User user) {
        String sql = """
            INSERT INTO RDV (heure, date, motif, statut, noteMedecin, patient_id, medecin_id)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setTimestamp(1, rdv.getHeure() != null ? Timestamp.valueOf(rdv.getHeure()) : null);
            ps.setDate(2, rdv.getDate() != null ? Date.valueOf(rdv.getDate()) : null);
            ps.setString(3, rdv.getMotif());
            ps.setString(4, rdv.getStatut() != null ? rdv.getStatut().name() : StatutEnum.EN_ATTENTE.name());
            ps.setString(5, rdv.getNoteMedecin());
            ps.setLong(6, rdv.getPatientId());
            ps.setLong(7, rdv.getMedecinId());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    rdv.setId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur create RDV", e);
        }
        return 0;
    }

    @Override
    public void update(RDV rdv) {
        String sql = """
            UPDATE RDV 
            SET heure = ?, date = ?, motif = ?, statut = ?, noteMedecin = ?
            WHERE id = ?
        """;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setTimestamp(1, rdv.getHeure() != null ? Timestamp.valueOf(rdv.getHeure()) : null);
            ps.setDate(2, rdv.getDate() != null ? Date.valueOf(rdv.getDate()) : null);
            ps.setString(3, rdv.getMotif());
            ps.setString(4, rdv.getStatut() != null ? rdv.getStatut().name() : null);
            ps.setString(5, rdv.getNoteMedecin());
            ps.setLong(6, rdv.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur update RDV", e);
        }
    }

    @Override
    public void delete(RDV rdv) {
        deleteById(rdv.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM RDV WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur deleteById RDV", e);
        }
    }

    @Override
    public List<RDV> findByPatientId(Long patientId) {
        List<RDV> list = new ArrayList<>();
        String sql = "SELECT * FROM RDV WHERE patient_id = ? ORDER BY date DESC, heure DESC";

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
    public List<RDV> findByMedecinId(Long medecinId) {
        List<RDV> list = new ArrayList<>();
        String sql = "SELECT * FROM RDV WHERE medecin_id = ? ORDER BY date DESC, heure DESC";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, medecinId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByMedecinId", e);
        }
        return list;
    }

    @Override
    public List<RDV> findByDate(LocalDate date) {
        List<RDV> list = new ArrayList<>();
        String sql = "SELECT * FROM RDV WHERE date = ? ORDER BY heure";

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
    public List<RDV> findByDateRange(LocalDate debut, LocalDate fin) {
        List<RDV> list = new ArrayList<>();
        String sql = "SELECT * FROM RDV WHERE date BETWEEN ? AND ? ORDER BY date, heure";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(debut));
            ps.setDate(2, Date.valueOf(fin));
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
    public List<RDV> findByStatut(StatutEnum statut) {
        List<RDV> list = new ArrayList<>();
        String sql = "SELECT * FROM RDV WHERE statut = ? ORDER BY date DESC, heure DESC";

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
    public List<RDV> findUpcoming(Long medecinId) {
        List<RDV> list = new ArrayList<>();
        String sql = """
            SELECT * FROM RDV 
            WHERE medecin_id = ? AND date >= CURDATE() AND statut IN ('EN_ATTENTE', 'EN_COURS')
            ORDER BY date, heure
        """;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, medecinId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findUpcoming", e);
        }
        return list;
    }

    @Override
    public boolean isSlotAvailable(Long medecinId, LocalDateTime heure) {
        String sql = """
            SELECT COUNT(*) FROM RDV 
            WHERE medecin_id = ? AND heure = ? AND statut != 'ANNULE'
        """;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, medecinId);
            ps.setTimestamp(2, Timestamp.valueOf(heure));

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur isSlotAvailable", e);
        }
        return false;
    }

    @Override
    public long countByDate(LocalDate date) {
        String sql = "SELECT COUNT(*) FROM RDV WHERE date = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur countByDate", e);
        }
        return 0;
    }

    private RDV mapRow(ResultSet rs) throws SQLException {
        RDV rdv = new RDV();
        rdv.setId(rs.getLong("id"));

        Timestamp h = rs.getTimestamp("heure");
        if (h != null) rdv.setHeure(h.toLocalDateTime());

        Date d = rs.getDate("date");
        if (d != null) rdv.setDate(d.toLocalDate());

        rdv.setMotif(rs.getString("motif"));

        String statut = rs.getString("statut");
        if (statut != null) rdv.setStatut(StatutEnum.valueOf(statut));

        rdv.setNoteMedecin(rs.getString("noteMedecin"));
        rdv.setPatientId(rs.getLong("patient_id"));
        rdv.setMedecinId(rs.getLong("medecin_id"));

        return rdv;
    }
}
