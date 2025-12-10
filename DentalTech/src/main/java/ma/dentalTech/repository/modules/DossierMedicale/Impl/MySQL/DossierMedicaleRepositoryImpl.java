package ma.dentalTech.repository.modules.DossierMedicale.Impl.MySQL;

import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.entities.DossierMedicale.DossierMedicale;
import ma.dentalTech.entities.Users.User;
import ma.dentalTech.repository.modules.DossierMedicale.Api.DossierMedicaleRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DossierMedicaleRepositoryImpl implements DossierMedicaleRepository {

    private Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }

    // =========================================================================
    // IMPLEMENTATION CRUD (Méthodes de base)
    // =========================================================================

    @Override
    public List<DossierMedicale> findAll() {
        List<DossierMedicale> list = new ArrayList<>();
        String sql = "SELECT * FROM DossierMedicale ORDER BY date_creation DESC";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findAll DossierMedicale", e);
        }
        return list;
    }

    @Override
    public DossierMedicale findById(Long id) {
        String sql = "SELECT * FROM DossierMedicale WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findById DossierMedicale", e);
        }
        return null;
    }

    @Override
    public long create(User user) {
        return 0;
    }

    @Override
    public DossierMedicale create(DossierMedicale dm) {
        String sql = "INSERT INTO DossierMedicale (date_creation, patient_id) VALUES (?, ?)";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // 1. Date Creation
            if (dm.getDateCreation() != null) {
                ps.setDate(1, java.sql.Date.valueOf(dm.getDateCreation()));
            } else {
                ps.setDate(1, new java.sql.Date(System.currentTimeMillis())); // Date actuelle par défaut
            }

            // 2. Patient ID (Clé étrangère)
            if (dm.getPatientId() != null) {
                ps.setLong(2, dm.getPatientId());
            } else {
                ps.setNull(2, Types.BIGINT);
            }

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    dm.setId(keys.getLong(1));
                }
            }
            return dm;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur create DossierMedicale", e);
        }
    }

    @Override
    public void update(DossierMedicale dm) {
        String sql = "UPDATE DossierMedicale SET date_creation = ?, patient_id = ? WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            if (dm.getDateCreation() != null) {
                ps.setDate(1, java.sql.Date.valueOf(dm.getDateCreation()));
            } else {
                ps.setNull(1, Types.DATE);
            }

            if (dm.getPatientId() != null) {
                ps.setLong(2, dm.getPatientId());
            } else {
                ps.setNull(2, Types.BIGINT);
            }

            ps.setLong(3, dm.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur update DossierMedicale", e);
        }
    }

    @Override
    public void delete(DossierMedicale dm) {
        if (dm != null && dm.getId() != null) {
            deleteById(dm.getId());
        }
    }

    // Méthode utilitaire interne pour la suppression
    public void deleteById(Long id) {
        String sql = "DELETE FROM DossierMedicale WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur deleteById DossierMedicale", e);
        }
    }

    // =========================================================================
    // IMPLEMENTATION DES RECHERCHES SPECIFIQUES (Interface DossierMedicaleRepository)
    // =========================================================================

    // NOTE : Assurez-vous d'avoir retiré le mot clé 'static' dans l'interface pour que @Override fonctionne
    @Override
    public Optional<DossierMedicale> findByPatientId(Long patientId) {
        String sql = "SELECT * FROM DossierMedicale WHERE patient_id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByPatientId", e);
        }
        return Optional.empty();
    }

    @Override
    public List<DossierMedicale> findByDateCreation(LocalDate date) {
        List<DossierMedicale> list = new ArrayList<>();
        String sql = "SELECT * FROM DossierMedicale WHERE date_creation = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDate(1, java.sql.Date.valueOf(date));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByDateCreation", e);
        }
        return list;
    }

    @Override
    public List<DossierMedicale> findByDateRange(LocalDate debut, LocalDate fin) {
        List<DossierMedicale> list = new ArrayList<>();
        String sql = "SELECT * FROM DossierMedicale WHERE date_creation BETWEEN ? AND ?";

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
    public long count() {
        String sql = "SELECT COUNT(*) FROM DossierMedicale";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur count DossierMedicale", e);
        }
        return 0;
    }

    // =========================================================================
    // MAPPER (ResultSet -> Objet)
    // =========================================================================

    private DossierMedicale mapRow(ResultSet rs) throws SQLException {
        // Utilisation du Builder si disponible, sinon constructeur par défaut
        // Ici j'utilise les setters basés sur ton entité standard
        DossierMedicale dm = new DossierMedicale();

        dm.setId(rs.getLong("id"));

        Date sqlDate = rs.getDate("date_creation");
        if (sqlDate != null) {
            dm.setDateCreation(sqlDate.toLocalDate());
        }

        // Gestion de la clé étrangère patient_id
        long pId = rs.getLong("patient_id");
        if (!rs.wasNull()) {
            dm.setPatientId(pId);
        }

        return dm;
    }
}