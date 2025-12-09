package ma.dentalTech.repository.modules.Patient.Impl.MySQL;

import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.entities.Patient.HistoriqueMedicale;
import ma.dentalTech.entities.Users.User;
import ma.dentalTech.repository.modules.Patient.Api.HistoriqueMedicaleRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoriqueMedicaleRepositoryImpl implements HistoriqueMedicaleRepository {

    private Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }

    @Override
    public List<HistoriqueMedicale> findAll() {
        List<HistoriqueMedicale> list = new ArrayList<>();
        String sql = "SELECT * FROM HistoriqueMedicale ORDER BY id DESC";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findAll HistoriqueMedicale", e);
        }
        return list;
    }

    @Override
    public HistoriqueMedicale findById(Long id) {
        String sql = "SELECT * FROM HistoriqueMedicale WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findById HistoriqueMedicale", e);
        }
        return null;
    }

    @Override
    public long create(User user) {
        String sql = "INSERT INTO HistoriqueMedicale (libele, dossierMedical_id) VALUES (?, ?)";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, h.getLibele());
            ps.setLong(2, h.getDossierMedicalId());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    h.setId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur create HistoriqueMedicale", e);
        }
        return 0;
    }

    @Override
    public void update(HistoriqueMedicale h) {
        String sql = "UPDATE HistoriqueMedicale SET libele = ? WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, h.getLibele());
            ps.setLong(2, h.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur update HistoriqueMedicale", e);
        }
    }

    @Override
    public void delete(HistoriqueMedicale h) {
        deleteById(h.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM HistoriqueMedicale WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur deleteById HistoriqueMedicale", e);
        }
    }

    @Override
    public List<HistoriqueMedicale> findByDossierMedicaleId(Long dossierId) {
        List<HistoriqueMedicale> list = new ArrayList<>();
        String sql = "SELECT * FROM HistoriqueMedicale WHERE dossierMedical_id = ? ORDER BY id DESC";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, dossierId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByDossierMedicaleId", e);
        }
        return list;
    }

    @Override
    public long countByDossierMedicaleId(Long dossierId) {
        String sql = "SELECT COUNT(*) FROM HistoriqueMedicale WHERE dossierMedical_id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, dossierId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur countByDossierMedicaleId", e);
        }
        return 0;
    }

    private HistoriqueMedicale mapRow(ResultSet rs) throws SQLException {
        HistoriqueMedicale h = new HistoriqueMedicale();
        h.setId(rs.getLong("id"));
        h.setLibele(rs.getString("libele"));
        h.setDossierMedicalId(rs.getLong("dossierMedical_id"));
        return h;
    }
}