package ma.dentalTech.repository.modules.Referentiel.Impl.MySQL;

import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.entities.Referentiel.Certificat;
import ma.dentalTech.repository.modules.Referentiel.Api.CertificatRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CertificatRepositoryImpl implements CertificatRepository {

    private Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }

    @Override
    public List<Certificat> findAll() {
        List<Certificat> list = new ArrayList<>();
        String sql = "SELECT * FROM Certificats ORDER BY dateDébut DESC";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findAll Certificats", e);
        }
        return list;
    }

    @Override
    public Certificat findById(Long id) {
        String sql = "SELECT * FROM Certificats WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findById Certificat", e);
        }
        return null;
    }

    @Override
    public void create(Certificat cert) {
        String sql = """
            INSERT INTO Certificats (dateDébut, dateDeFin, duree, noteMedecin, consultation_id, patient_id)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, cert.getDateDébut() != null ? Date.valueOf(cert.getDateDébut()) : Date.valueOf(LocalDate.now()));
            ps.setDate(2, cert.getDateDeFin() != null ? Date.valueOf(cert.getDateDeFin()) : null);
            ps.setInt(3, cert.getDuree() != null ? cert.getDuree() : 0);
            ps.setString(4, cert.getNoteMedecin());
            ps.setLong(5, cert.getConsultationId());
            ps.setLong(6, cert.getPatientId());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    cert.setId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur create Certificat", e);
        }
    }

    @Override
    public void update(Certificat cert) {
        String sql = """
            UPDATE Certificats 
            SET dateDébut = ?, dateDeFin = ?, duree = ?, noteMedecin = ?
            WHERE id = ?
        """;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDate(1, cert.getDateDébut() != null ? Date.valueOf(cert.getDateDébut()) : null);
            ps.setDate(2, cert.getDateDeFin() != null ? Date.valueOf(cert.getDateDeFin()) : null);
            ps.setInt(3, cert.getDuree() != null ? cert.getDuree() : 0);
            ps.setString(4, cert.getNoteMedecin());
            ps.setLong(5, cert.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur update Certificat", e);
        }
    }

    @Override
    public void delete(Certificat cert) {
        deleteById(cert.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Certificats WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur deleteById Certificat", e);
        }
    }

    @Override
    public List<Certificat> findByConsultationId(Long consultationId) {
        List<Certificat> list = new ArrayList<>();
        String sql = "SELECT * FROM Certificats WHERE consultation_id = ? ORDER BY dateDébut DESC";

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
    public List<Certificat> findByPatientId(Long patientId) {
        List<Certificat> list = new ArrayList<>();
        String sql = "SELECT * FROM Certificats WHERE patient_id = ? ORDER BY dateDébut DESC";

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
    public List<Certificat> findByDateRange(LocalDate debut, LocalDate fin) {
        List<Certificat> list = new ArrayList<>();
        String sql = "SELECT * FROM Certificats WHERE dateDébut BETWEEN ? AND ? ORDER BY dateDébut DESC";

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
    public List<Certificat> findActive() {
        List<Certificat> list = new ArrayList<>();
        String sql = "SELECT * FROM Certificats WHERE dateDeFin >= CURDATE() ORDER BY dateDébut DESC";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findActive", e);
        }
        return list;
    }

    @Override
    public long countByPatientId(Long patientId) {
        String sql = "SELECT COUNT(*) FROM Certificats WHERE patient_id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur countByPatientId", e);
        }
        return 0;
    }

    private Certificat mapRow(ResultSet rs) throws SQLException {
        Certificat cert = new Certificat();
        cert.setId(rs.getLong("id"));

        Date dd = rs.getDate("dateDébut");
        if (dd != null) cert.setDateDébut(dd.toLocalDate());

        Date df = rs.getDate("dateDeFin");
        if (df != null) cert.setDateDeFin(df.toLocalDate());

        cert.setDuree(rs.getInt("duree"));
        cert.setNoteMedecin(rs.getString("noteMedecin"));
        cert.setConsultationId(rs.getLong("consultation_id"));
        cert.setPatientId(rs.getLong("patient_id"));

        return cert;
    }
}