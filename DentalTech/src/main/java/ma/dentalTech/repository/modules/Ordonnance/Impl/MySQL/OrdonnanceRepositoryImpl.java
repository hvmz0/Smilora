package ma.dentalTech.repository.modules.Ordonnance.Impl.MySQL;

import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.entities.Ordonnance.Ordonnance;
import ma.dentalTech.entities.Users.User;
import ma.dentalTech.repository.modules.Ordonnance.Api.OrdonnanceRepository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdonnanceRepositoryImpl implements OrdonnanceRepository {

    private Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }

    // --- FIND ALL ---

    @Override
    public List<Ordonnance> findAll() {
        List<Ordonnance> list = new ArrayList<>();
        String sql = "SELECT * FROM Ordonnances ORDER BY id DESC";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findAll Ordonnance", e);
        }
        return list;
    }

    // --- FIND BY ID ---

    @Override
    public Ordonnance findById(Long id) {
        String sql = "SELECT * FROM Ordonnances WHERE id = ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findById Ordonnance", e);
        }
        return null;
    }

    // --- CREATE ---

    @Override
    public long create(User user) {
        // Colonnes SQL : patientId, medecinId, date
        String sql = "INSERT INTO Ordonnances (patientId, medecinId, date) VALUES (?, ?, ?)";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, o.getPatientId());
            ps.setLong(2, o.getMedecinId());
            // CORRECTION: Utilisation de getDate()
            ps.setDate(3, Date.valueOf(o.getDate()));

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) o.setId(keys.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur create Ordonnance", e);
        }
        return 0;
    }

    // --- UPDATE ---

    @Override
    public void update(Ordonnance o) {
        String sql = "UPDATE Ordonnances SET patientId=?, medecinId=?, date=? WHERE id=?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, o.getPatientId());
            ps.setLong(2, o.getMedecinId());
            // CORRECTION: Utilisation de getDate()
            ps.setDate(3, Date.valueOf(o.getDate()));
            ps.setLong(4, o.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur update Ordonnance", e);
        }
    }

    // --- DELETE (par ID) ---

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Ordonnances WHERE id = ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur deleteById Ordonnance", e);
        }
    }

    // --- DELETE (par Entité - requis par CrudRepository) ---
    @Override
    public void delete(Ordonnance o) {
        if (o != null && o.getId() != null) {
            deleteById(o.getId());
        }
    }

    @Override
    public Optional<Ordonnance> findByConsultationId(Long consultationId) {
        return Optional.empty();
    }

    // --- FIND BY PATIENT ID (Méthode spécifique) ---
    @Override
    public List<Ordonnance> findByPatientId(Long patientId) {
        List<Ordonnance> list = new ArrayList<>();
        String sql = "SELECT * FROM Ordonnances WHERE patientId = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, patientId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByPatientId", e);
        }
        return list;
    }

    // --- FIND BY MEDECIN ID (Méthode spécifique) ---
    @Override
    public List<Ordonnance> findByMedecinId(Long medecinId) {
        List<Ordonnance> list = new ArrayList<>();
        String sql = "SELECT * FROM Ordonnances WHERE medecinId = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, medecinId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByMedecinId", e);
        }
        return list;
    }

    // --- FIND BY DATE (Méthode spécifique) ---
    @Override
    public List<Ordonnance> findByDate(LocalDate date) {
        List<Ordonnance> list = new ArrayList<>();
        // Colonne SQL utilisée : date
        String sql = "SELECT * FROM Ordonnances WHERE date = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(date));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByDate", e);
        }
        return list;
    }

    @Override
    public List<Ordonnance> findByDateRange(LocalDate debut, LocalDate fin) {
        return List.of();
    }

    @Override
    public long countByPatientId(Long patientId) {
        return 0;
    }

    // --- MAP ROW ---

    private Ordonnance mapRow(ResultSet rs) throws SQLException {
        Ordonnance o = new Ordonnance();
        o.setId(rs.getLong("id"));
        o.setPatientId(rs.getLong("patientId"));
        o.setMedecinId(rs.getLong("medecinId"));

        // CORRECTION: Récupération de la colonne 'date'
        Date dateSql = rs.getDate("date");
        if (dateSql != null) {
            o.setDate(dateSql.toLocalDate());
        }

        return o;
    }
}