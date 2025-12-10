package ma.dentalTech.repository.modules.Consultation.Impl.MySQL;

import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.entities.Consultation.Consultation;
import ma.dentalTech.entities.Enums.StatutEnum;
import ma.dentalTech.entities.Users.User;
import ma.dentalTech.repository.modules.Consultation.Api.ConsultationRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConsultationRepositoryImpl implements ConsultationRepository {

    private Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }

    // =========================================================================
    // IMPLEMENTATION CRUD (Méthodes de base)
    // =========================================================================

    @Override
    public List<Consultation> findAll() {
        List<Consultation> list = new ArrayList<>();
        String sql = "SELECT * FROM Consultation ORDER BY date_cons DESC";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findAll Consultation", e);
        }
        return list;
    }

    @Override
    public Consultation findById(Long id) {
        String sql = "SELECT * FROM Consultation WHERE id = ?";

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
    public long create(User user) {
        return 0;
    }

    @Override
    public Consultation create(Consultation cons) {
        String sql = "INSERT INTO Consultation (date_cons, statut, observation_med, patient_id, medecin_id, rdv_id, dossier_medical_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // 1. Date
            if (cons.getDateCons() != null) {
                ps.setDate(1, java.sql.Date.valueOf(cons.getDateCons()));
            } else {
                ps.setNull(1, Types.DATE);
            }

            // 2. Statut (Enum vers String)
            if (cons.getStatut() != null) {
                ps.setString(2, cons.getStatut().name());
            } else {
                ps.setNull(2, Types.VARCHAR);
            }

            // 3. Observation
            ps.setString(3, cons.getObservationMed());

            // 4. IDs (Foreign Keys)
            setLongOrNull(ps, 4, cons.getPatientId());
            setLongOrNull(ps, 5, cons.getMedecinId());
            setLongOrNull(ps, 6, cons.getRdvId());
            setLongOrNull(ps, 7, cons.getDossierMedicalId());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    cons.setId(keys.getLong(1));
                }
            }
            return cons;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur create Consultation", e);
        }
    }

    @Override
    public void update(Consultation cons) {
        String sql = "UPDATE Consultation SET date_cons=?, statut=?, observation_med=?, patient_id=?, medecin_id=?, rdv_id=?, dossier_medical_id=? WHERE id=?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            if (cons.getDateCons() != null) {
                ps.setDate(1, java.sql.Date.valueOf(cons.getDateCons()));
            } else {
                ps.setNull(1, Types.DATE);
            }

            if (cons.getStatut() != null) {
                ps.setString(2, cons.getStatut().name());
            } else {
                ps.setNull(2, Types.VARCHAR);
            }

            ps.setString(3, cons.getObservationMed());

            setLongOrNull(ps, 4, cons.getPatientId());
            setLongOrNull(ps, 5, cons.getMedecinId());
            setLongOrNull(ps, 6, cons.getRdvId());
            setLongOrNull(ps, 7, cons.getDossierMedicalId());

            ps.setLong(8, cons.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur update Consultation", e);
        }
    }

    @Override
    public void delete(Consultation cons) {
        if (cons != null && cons.getId() != null) {
            deleteById(cons.getId());
        }
    }

    // Supposons que deleteById soit nécessaire via CrudRepository ou helper interne
    public void deleteById(Long id) {
        String sql = "DELETE FROM Consultation WHERE id = ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur deleteById Consultation", e);
        }
    }

    // =========================================================================
    // IMPLEMENTATION DES RECHERCHES SPECIFIQUES (Interface ConsultationRepository)
    // =========================================================================

    @Override
    public List<Consultation> findByDossierMedicaleId(Long dossierId) {
        return findByColumn("dossier_medical_id", dossierId);
    }

    @Override
    public List<Consultation> findByPatientId(Long patientId) {
        return findByColumn("patient_id", patientId);
    }

    @Override
    public List<Consultation> findByMedecinId(Long medecinId) {
        return findByColumn("medecin_id", medecinId);
    }

    @Override
    public List<Consultation> findByDate(LocalDate date) {
        List<Consultation> list = new ArrayList<>();
        String sql = "SELECT * FROM Consultation WHERE date_cons = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDate(1, java.sql.Date.valueOf(date));
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
        String sql = "SELECT * FROM Consultation WHERE date_cons BETWEEN ? AND ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDate(1, java.sql.Date.valueOf(debut));
            ps.setDate(2, java.sql.Date.valueOf(fin));
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
        String sql = "SELECT * FROM Consultation WHERE statut = ?";

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
        String sql = "SELECT COUNT(*) FROM Consultation WHERE patient_id = ?";

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

    // =========================================================================
    // METHODES UTILITAIRES (Helpers)
    // =========================================================================

    // Helper pour éviter la répétition du code de recherche simple
    private List<Consultation> findByColumn(String columnName, Long id) {
        List<Consultation> list = new ArrayList<>();
        String sql = "SELECT * FROM Consultation WHERE " + columnName + " = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByColumn " + columnName, e);
        }
        return list;
    }

    // Helper pour gérer les Longs qui peuvent être null dans la base
    private void setLongOrNull(PreparedStatement ps, int index, Long value) throws SQLException {
        if (value != null) {
            ps.setLong(index, value);
        } else {
            ps.setNull(index, Types.BIGINT);
        }
    }

    // Mapping du ResultSet vers l'Objet Java
    private Consultation mapRow(ResultSet rs) throws SQLException {
        Consultation c = new Consultation();
        c.setId(rs.getLong("id"));

        // Conversion SQL Date -> LocalDate
        java.sql.Date sqlDate = rs.getDate("date_cons");
        if (sqlDate != null) {
            c.setDateCons(sqlDate.toLocalDate());
        }

        // Conversion String -> Enum
        String statutStr = rs.getString("statut");
        if (statutStr != null) {
            try {
                c.setStatut(StatutEnum.valueOf(statutStr));
            } catch (IllegalArgumentException e) {
                // Gérer le cas où la DB contient une valeur qui n'est pas dans l'Enum
                e.printStackTrace();
            }
        }

        c.setObservationMed(rs.getString("observation_med"));

        // Gestion des Foreign Keys (peuvent être null ou 0 selon la DB)
        long patientId = rs.getLong("patient_id");
        if (!rs.wasNull()) c.setPatientId(patientId);

        long medecinId = rs.getLong("medecin_id");
        if (!rs.wasNull()) c.setMedecinId(medecinId);

        long rdvId = rs.getLong("rdv_id");
        if (!rs.wasNull()) c.setRdvId(rdvId);

        long dossierId = rs.getLong("dossier_medical_id");
        if (!rs.wasNull()) c.setDossierMedicalId(dossierId);

        return c;
    }
}