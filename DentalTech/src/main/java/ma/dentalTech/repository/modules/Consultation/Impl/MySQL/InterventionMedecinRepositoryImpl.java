package ma.dentalTech.repository.modules.Consultation.Impl.MySQL;

import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.entities.Consultation.InterventionMedecin;
import ma.dentalTech.entities.Users.User;
import ma.dentalTech.repository.modules.Consultation.Api.InterventionMedecinRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InterventionMedecinRepositoryImpl implements InterventionMedecinRepository {

    private Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }

    @Override
    public List<InterventionMedecin> findAll() {
        List<InterventionMedecin> list = new ArrayList<>();
        String sql = "SELECT * FROM InterventionMedecin ORDER BY id DESC";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findAll InterventionMedecin", e);
        }
        return list;
    }

    @Override
    public InterventionMedecin findById(Long id) {
        String sql = "SELECT * FROM InterventionMedecin WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findById InterventionMedecin", e);
        }
        return null;
    }


    @Override
    public long create(User user) {
        String sql = "INSERT INTO InterventionMedecin (prixDePatient, numDent, consultation_id, acte_id) VALUES (?, ?, ?, ?)";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDouble(1, Double.parseDouble(user.getMotDePass()));

            if (user.getNom() != null) {
                ps.setInt(2, Integer.parseInt(user.getNom()));
            } else {
                ps.setNull(2, Types.INTEGER);
            }

            ps.setLong(3, Long.parseLong(user.getCin()));
            ps.setLong(4, user.getId());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    user.setId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur create InterventionMedecin", e);
        }
        return 0;
    }

    @Override
    public void update(InterventionMedecin im) {
        String sql = "UPDATE InterventionMedecin SET prixDePatient = ?, numDent = ? WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDouble(1, im.getPrixDePatient());

            if (im.getNumDent() != null) {
                ps.setInt(2, im.getNumDent());
            } else {
                ps.setNull(2, Types.INTEGER);
            }

            ps.setLong(3, im.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur update InterventionMedecin", e);
        }
    }

    @Override
    public void delete(InterventionMedecin im) {
        if (im != null && im.getId() != null) {
            deleteById(im.getId());
        }
    }

    // Méthode helper pour la suppression par ID
    public void deleteById(Long id) {
        String sql = "DELETE FROM InterventionMedecin WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur deleteById InterventionMedecin", e);
        }
    }

    @Override
    public List<InterventionMedecin> findByConsultationId(Long consultationId) {
        List<InterventionMedecin> list = new ArrayList<>();
        String sql = "SELECT * FROM InterventionMedecin WHERE consultation_id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, consultationId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByConsultationId", e);
        }
        return list;
    }

    @Override
    public List<InterventionMedecin> findByActeId(Long acteId) {
        List<InterventionMedecin> list = new ArrayList<>();
        String sql = "SELECT * FROM InterventionMedecin WHERE acte_id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, acteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByActeId", e);
        }
        return list;
    }

    // =====================================================================
    // C'EST ICI QUE J'AI CORRIGÉ L'ERREUR (Nom de la méthode)
    // =====================================================================
    @Override
    public Double calculateTotal(Long consultationId) {
        String sql = "SELECT SUM(prixDePatient) FROM InterventionMedecin WHERE consultation_id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, consultationId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur calculateTotal", e);
        }
        return 0.0;
    }

    @Override
    public long countByConsultationId(Long consultationId) {
        String sql = "SELECT COUNT(*) FROM InterventionMedecin WHERE consultation_id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, consultationId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur countByConsultationId", e);
        }
        return 0;
    }

    private InterventionMedecin mapRow(ResultSet rs) throws SQLException {
        InterventionMedecin im = new InterventionMedecin();
        im.setId(rs.getLong("id"));
        im.setPrixDePatient(rs.getDouble("prixDePatient"));

        int numDent = rs.getInt("numDent");
        if (!rs.wasNull()) im.setNumDent(numDent);

        im.setConsultationId(rs.getLong("consultation_id"));
        im.setActeId(rs.getLong("acte_id"));

        return im;
    }
}