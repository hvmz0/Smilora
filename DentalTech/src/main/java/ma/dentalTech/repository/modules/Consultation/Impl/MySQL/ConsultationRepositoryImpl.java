package ma.dentalTech.repository.modules.Consultation.Impl.MySQL;

import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.entities.Consultation.Consultation;
import ma.dentalTech.entities.Enums.StatutEnum;
import ma.dentalTech.repository.modules.Consultation.Api.ConsultationRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConsultationRepositoryImpl implements ConsultationRepository {

    private Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }

    @Override
    public List<Consultation> findAll() {
        List<Consultation> list = new ArrayList<>();
        String sql = "SELECT * FROM Consultations ORDER BY dateCons DESC";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findAll Consultations", e);
        }
        return list;
    }

    @Override
    public Consultation findById(Long id) {
        String sql = "SELECT * FROM Consultations WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findById Consultation", e);
        }
        return null;
    }

    @Override
    public void create(Consultation cons) {
        String sql = """
            INSERT INTO Consultations (dateCons, statut, observationMed, patient_id, medecin_id, rdv_id, dossierMedical_id)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, cons.getDateCons() != null ? Date.valueOf(cons.getDateCons()) : Date.valueOf(LocalDate.now()));
            ps.setString(2, cons.getStatut() != null ? cons.getStatut().name() : StatutEnum.EN_COURS.name());
            ps.setString(3, cons.getObservationMed());
            ps.setLong(4, cons.getPatientId());
            ps.setLong(5, cons.getMedecinId());

            if (cons.getRdvId() != null) {
                ps.setLong(6, cons.getRdvId());
            } else {
                ps.setNull(6, Types.BIGINT);
            }

            if (cons.getDossierMedicalId() != null) {
                ps.setLong(7, cons.getDossierMedicalId());
            } else {
                ps.setNull(7, Types.BIGINT);
            }

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    cons.setId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur create Consultation", e);
        }
    }

    @Override
    public void update(Consultation cons) {
        String sql = """
            UPDATE Consultations 
            SET dateCons = ?, statut = ?, observationMed = ?
            WHERE id = ?
        """;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDate(1, cons.getDateCons() != null ? Date.valueOf(cons.getDateCons()) : null);
            ps.setString(2, cons.getStatut() != null ? cons.getStatut().name() : null);
            ps.setString(3, cons.getObservationMed());
            ps.setLong(4, cons.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur update Consultation", e);
        }
    }

    @Override
    public void delete(Consultation cons) {
        deleteById(cons.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Consultations WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur deleteById Consultation", e);
        }
    }

    @Override
    public List<Consultation> findByDossierMedicaleId(Long dossierId) {
        List<Consultation> list = new ArrayList<>();
        String sql = "SELECT * FROM Consultations WHERE dossierMedical_id = ? ORDER BY dateCons DESC";

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
    public List<Consultation> findByPatientId(Long patientId) {
        List<Consultation> list = new ArrayList<>();
        String sql = "SELECT * FROM Consultations WHERE patient_id = ? ORDER BY dateCons DESC";

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
    public List<Consultation> findByMedecinId(Long medecinId) {
        List<Consultation> list = new ArrayList<>();
        String sql = "SELECT * FROM Consultations WHERE medecin_id = ? ORDER BY dateCons DESC";

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
    public List<Consultation> findByDate(LocalDate date) {
        List<Consultation> list = new ArrayList<>();
        String sql = "SELECT * FROM Consultations WHERE dateCons = ?";

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
    public List<Consultation> findByDateRange(LocalDate debut, LocalDate fin) {
        List<Consultation> list = new ArrayList<>();
        String sql = "SELECT * FROM Consultations WHERE dateCons BETWEEN ? AND ? ORDER BY dateCons DESC";

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
    public List<Consultation> findByStatut(StatutEnum statut) {
        List<Consultation> list = new ArrayList<>();
        String sql = "SELECT * FROM Consultations WHERE statut = ? ORDER BY dateCons DESC";

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
    public long countByPatientId(Long patientId) {
        String sql = "SELECT COUNT(*) FROM Consultations WHERE patient_id = ?";

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

    private Consultation mapRow(ResultSet rs) throws SQLException {
        Consultation cons = new Consultation();
        cons.setId(rs.getLong("id"));

        Date dc = rs.getDate("dateCons");
        if (dc != null) cons.setDateCons(dc.toLocalDate());

        String statut = rs.getString("statut");
        if (statut != null) cons.setStatut(StatutEnum.valueOf(statut));

        cons.setObservationMed(rs.getString("observationMed"));
        cons.setPatientId(rs.getLong("patient_id"));
        cons.setMedecinId(rs.getLong("medecin_id"));

        long rdvId = rs.getLong("rdv_id");
        if (!rs.wasNull()) cons.setRdvId(rdvId);

        long dossId = rs.getLong("dossierMedical_id");
        if (!rs.wasNull()) cons.setDossierMedicalId(dossId);

        return cons;
    }
}