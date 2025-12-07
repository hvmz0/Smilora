package ma.dentalTech.repository.modules.Patient.Impl.MySQL;

import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.entities.Enums.Assurance;
import ma.dentalTech.entities.Enums.Sexe;
import ma.dentalTech.entities.Patient.Antecedent;
import ma.dentalTech.entities.Patient.Patient;
import ma.dentalTech.repository.modules.Patient.Api.PatientRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PatientRepositoryImpl implements PatientRepository {

    private Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }

    @Override
    public List<Patient> findAll() {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM Patients ORDER BY id DESC";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findAll Patients", e);
        }
        return list;
    }

    @Override
    public Patient findById(Long id) {
        String sql = "SELECT * FROM Patients WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findById Patient", e);
        }
        return null;
    }

    @Override
    public void create(Patient p) {
        String sql = """
            INSERT INTO Patients (nom, adresse, tel, dateNaissance, sexe, assurance)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getNom());
            ps.setString(2, p.getAdresse());
            ps.setString(3, p.getTel());
            ps.setDate(4, p.getDateNaissance() != null ? Date.valueOf(p.getDateNaissance()) : null);
            ps.setString(5, p.getSexe().name());
            ps.setString(6, p.getAssurance().name());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    p.setId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur create Patient", e);
        }
    }

    @Override
    public void update(Patient p) {
        String sql = """
            UPDATE Patients 
            SET nom = ?, adresse = ?, tel = ?, dateNaissance = ?, sexe = ?, assurance = ?
            WHERE id = ?
        """;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, p.getNom());
            ps.setString(2, p.getAdresse());
            ps.setString(3, p.getTel());
            ps.setDate(4, p.getDateNaissance() != null ? Date.valueOf(p.getDateNaissance()) : null);
            ps.setString(5, p.getSexe().name());
            ps.setString(6, p.getAssurance().name());
            ps.setLong(7, p.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur update Patient", e);
        }
    }

    @Override
    public void delete(Patient p) {
        deleteById(p.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Patients WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur deleteById Patient", e);
        }
    }

    @Override
    public Optional<Patient> findByEmail(String email) {
        String sql = "SELECT * FROM Patients WHERE email = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByEmail Patient", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Patient> searchByNomPrenom(String keyword) {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM Patients WHERE nom LIKE ? ORDER BY nom";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur searchByNomPrenom", e);
        }
        return list;
    }

    @Override
    public List<Patient> findPage(int pageSize, int offset) {
        List<Patient> list = new ArrayList<>();
        String sql = "SELECT * FROM Patients ORDER BY id DESC LIMIT ? OFFSET ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, pageSize);
            ps.setInt(2, offset);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findPage", e);
        }
        return list;
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM Patients";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur count Patients", e);
        }
        return 0;
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT 1 FROM Patients WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur existsById", e);
        }
    }

    @Override
    public List<Antecedent> getAntecedentsOfPatient(Long patientId) {
        List<Antecedent> list = new ArrayList<>();
        String sql = """
            SELECT a.* FROM Antecedents a
            JOIN Patient_Antecedents pa ON a.id = pa.antecedent_id
            WHERE pa.patient_id = ?
        """;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapAntecedent(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur getAntecedentsOfPatient", e);
        }
        return list;
    }

    @Override
    public List<Patient> getPatientsByAntecedent(Long antecedentId) {
        List<Patient> list = new ArrayList<>();
        String sql = """
            SELECT p.* FROM Patients p
            JOIN Patient_Antecedents pa ON p.id = pa.patient_id
            WHERE pa.antecedent_id = ?
        """;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, antecedentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur getPatientsByAntecedent", e);
        }
        return list;
    }

    @Override
    public void addAntecedentToPatient(Long patientId, Long antecedentId) {
        String sql = "INSERT IGNORE INTO Patient_Antecedents (patient_id, antecedent_id) VALUES (?, ?)";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, patientId);
            ps.setLong(2, antecedentId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur addAntecedentToPatient", e);
        }
    }

    @Override
    public void removeAntecedentFromPatient(Long patientId, Long antecedentId) {
        String sql = "DELETE FROM Patient_Antecedents WHERE patient_id = ? AND antecedent_id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, patientId);
            ps.setLong(2, antecedentId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur removeAntecedentFromPatient", e);
        }
    }

    @Override
    public void removeAllAntecedentsFromPatient(Long patientId) {
        String sql = "DELETE FROM Patient_Antecedents WHERE patient_id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, patientId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur removeAllAntecedentsFromPatient", e);
        }
    }

    @Override
    public Long getDossierMedicaleId(Long patientId) {
        String sql = "SELECT id FROM DossierMedicale WHERE patient_id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur getDossierMedicaleId", e);
        }
        return null;
    }

    @Override
    public Long getSituationFinanciereId(Long patientId) {
        String sql = "SELECT id FROM SituationFinanciere WHERE patient_id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur getSituationFinanciereId", e);
        }
        return null;
    }

    private Patient mapRow(ResultSet rs) throws SQLException {
        Patient p = new Patient();
        p.setId(rs.getLong("id"));
        p.setNom(rs.getString("nom"));
        p.setAdresse(rs.getString("adresse"));
        p.setTel(rs.getString("tel"));

        Date dn = rs.getDate("dateNaissance");
        if (dn != null) p.setDateNaissance(dn.toLocalDate());

        p.setSexe(Sexe.valueOf(rs.getString("sexe")));
        p.setAssurance(Assurance.valueOf(rs.getString("assurance")));

        return p;
    }

    private Antecedent mapAntecedent(ResultSet rs) throws SQLException {
        Antecedent a = new Antecedent();
        a.setId(rs.getLong("id"));
        a.setNom(rs.getString("nom"));
        a.setCategorie(rs.getString("categorie"));
        a.setNiveauDeRisque(ma.dentalTech.entities.Enums.RisqueEnum.valueOf(rs.getString("niveauRisque")));
        return a;
    }
}
