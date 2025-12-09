package ma.dentalTech.repository.modules.Ordonnance.Impl.MySQL;

import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.entities.Ordonnance.PrescriptionDesMedicaments;
import ma.dentalTech.entities.Users.User;
import ma.dentalTech.repository.modules.Ordonnance.Api.PrescriptionDesMedicamentsRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionDesMedicamentsRepositoryImpl implements PrescriptionDesMedicamentsRepository {

    private Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }

    @Override
    public List<PrescriptionDesMedicaments> findAll() {
        List<PrescriptionDesMedicaments> list = new ArrayList<>();
        String sql = "SELECT * FROM PrescriptionDesMedicaments ORDER BY id";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findAll PrescriptionDesMedicaments", e);
        }
        return list;
    }

    @Override
    public PrescriptionDesMedicaments findById(Long id) {
        String sql = "SELECT * FROM PrescriptionDesMedicaments WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findById PrescriptionDesMedicaments", e);
        }
        return null;
    }

    @Override
    public long create(User user) {
        String sql = """
            INSERT INTO PrescriptionDesMedicaments (quantite, frequence, dureeEnJrs, ordonnance_id, medicament_id)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, p.getQuantite());
            ps.setString(2, p.getFrequence());
            ps.setInt(3, p.getDureeEnJrs());
            ps.setLong(4, p.getOrdonnanceId());
            ps.setLong(5, p.getMedicamentId());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    p.setId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur create PrescriptionDesMedicaments", e);
        }
        return 0;
    }

    @Override
    public void update(PrescriptionDesMedicaments p) {
        String sql = """
            UPDATE PrescriptionDesMedicaments 
            SET quantite = ?, frequence = ?, dureeEnJrs = ?
            WHERE id = ?
        """;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, p.getQuantite());
            ps.setString(2, p.getFrequence());
            ps.setInt(3, p.getDureeEnJrs());
            ps.setLong(4, p.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur update PrescriptionDesMedicaments", e);
        }
    }

    @Override
    public void delete(PrescriptionDesMedicaments p) {
        deleteById(p.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM PrescriptionDesMedicaments WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur deleteById PrescriptionDesMedicaments", e);
        }
    }

    @Override
    public List<PrescriptionDesMedicaments> findByOrdonnanceId(Long ordonnanceId) {
        List<PrescriptionDesMedicaments> list = new ArrayList<>();
        String sql = "SELECT * FROM PrescriptionDesMedicaments WHERE ordonnance_id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, ordonnanceId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByOrdonnanceId", e);
        }
        return list;
    }

    @Override
    public List<PrescriptionDesMedicaments> findByMedicamentId(Long medicamentId) {
        List<PrescriptionDesMedicaments> list = new ArrayList<>();
        String sql = "SELECT * FROM PrescriptionDesMedicaments WHERE medicament_id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, medicamentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByMedicamentId", e);
        }
        return list;
    }

    @Override
    public long countByOrdonnanceId(Long ordonnanceId) {
        String sql = "SELECT COUNT(*) FROM PrescriptionDesMedicaments WHERE ordonnance_id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, ordonnanceId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur countByOrdonnanceId", e);
        }
        return 0;
    }

    private PrescriptionDesMedicaments mapRow(ResultSet rs) throws SQLException {
        PrescriptionDesMedicaments p = new PrescriptionDesMedicaments();
        p.setId(rs.getLong("id"));
        p.setQuantite(rs.getInt("quantite"));
        p.setFrequence(rs.getString("frequence"));
        p.setDureeEnJrs(rs.getInt("dureeEnJrs"));
        p.setOrdonnanceId(rs.getLong("ordonnance_id"));
        p.setMedicamentId(rs.getLong("medicament_id"));
        return p;
    }
}