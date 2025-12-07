package ma.dentalTech.repository.modules.DossierMedicale.Impl.MySQL;

import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.entities.DossierMedicale.DossierMedicale;
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

    @Override
    public List<DossierMedicale> findAll() {
        List<DossierMedicale> list = new ArrayList<>();
        String sql = "SELECT * FROM DossierMedicale ORDER BY dateDeCreation DESC";

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
    public void create(DossierMedicale dm) {
        String sql = """
            INSERT INTO DossierMedicale (dateDeCreation, patient_id)
            VALUES (?, ?)
        """;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, dm.getDateDeCreation() != null ? Date.valueOf(dm.getDateDeCreation()) : Date.valueOf(LocalDate.now()));
            ps.setLong(2, dm.getPatientId());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    dm.setId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur create DossierMedicale", e);
        }
    }

    @Override
    public void update(DossierMedicale dm) {
        String sql = "UPDATE DossierMedicale SET dateDeCreation = ? WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDate(1, dm.getDateDeCreation() != null ? Date.valueOf(dm.getDateDeCreation()) : null);
            ps.setLong(2, dm.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur update DossierMedicale", e);
        }
    }

    @Override
    public void delete(DossierMedicale dm) {
        deleteById(dm.getId());
    }

    @Override
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
        String sql = "SELECT * FROM DossierMedicale WHERE dateDeCreation = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(date));
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
        String sql = "SELECT * FROM DossierMedicale WHERE dateDeCreation BETWEEN ? AND ? ORDER BY dateDeCreation DESC";

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

    private DossierMedicale mapRow(ResultSet rs) throws SQLException {
        DossierMedicale dm = new DossierMedicale();
        dm.setId(rs.getLong("id"));

        Date dc = rs.getDate("dateDeCreation");
        if (dc != null) dm.setDateDeCreation(dc.toLocalDate());

        dm.setPatientId(rs.getLong("patient_id"));

        return dm;
    }
}