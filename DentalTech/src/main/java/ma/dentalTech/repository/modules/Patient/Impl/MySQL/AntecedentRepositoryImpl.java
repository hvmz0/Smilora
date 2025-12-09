package ma.dentalTech.repository.modules.Patient.Impl.MySQL;

import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.entities.Enums.CategorieAntecedent;
import ma.dentalTech.entities.Enums.RisqueEnum;
import ma.dentalTech.entities.Patient.Antecedent;
import ma.dentalTech.entities.Users.User;
import ma.dentalTech.repository.modules.Patient.Api.AntecedentRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AntecedentRepositoryImpl implements AntecedentRepository {

    private Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }

    // --- FIND ALL ---

    @Override
    public List<Antecedent> findAll() {
        List<Antecedent> list = new ArrayList<>();
        String sql = "SELECT * FROM Antecedents ORDER BY id DESC";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findAll Antecedent", e);
        }
        return list;
    }

    // --- FIND BY ID ---

    // Si votre interface exige Optional<Antecedent>, utilisez Optional.of(mapRow(rs)) et Optional.empty()
    @Override
    public Antecedent findById(Long id) {
        String sql = "SELECT * FROM Antecedents WHERE id = ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findById Antecedent", e);
        }
        return null;
    }

    // --- CREATE ---

    @Override
    public long create(User user) {
        String sql = "INSERT INTO Antecedents (description, categorie, patient_id) VALUES (?, ?, ?)";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, a.getDescription());
            ps.setString(2, a.getCategorie());
            ps.setLong(3, a.getPatientId());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) a.setId(keys.getLong(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur create Antecedent", e);
        }
        return 0;
    }

    // --- UPDATE ---

    @Override
    public void update(Antecedent a) {
        String sql = "UPDATE Antecedents SET description=?, categorie=?, patient_id=? WHERE id=?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, a.getDescription());
            ps.setString(2, a.getCategorie());
            ps.setLong(3, a.getPatientId());
            ps.setLong(4, a.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur update Antecedent", e);
        }
    }

    // --- DELETE (par ID) ---

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM Antecedents WHERE id = ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur deleteById Antecedent", e);
        }
    }

    // --- DELETE (par Entité - requis par CrudRepository) ---

    @Override
    public void delete(Antecedent a) {
        if (a != null && a.getId() != null) {
            deleteById(a.getId());
        }
    }





    // --- MAP ROW ---

    private Antecedent mapRow(ResultSet rs) throws SQLException {
        Antecedent a = new Antecedent();
        a.setId(rs.getLong("id"));
        a.setDescription(rs.getString("description"));
        a.setCategorie(rs.getString("categorie"));
        a.setPatientId(rs.getLong("patient_id"));

        return a;
    }

    @Override
    public List<Antecedent> findByCategorie(CategorieAntecedent categorie) {
        return List.of();
    }

    @Override
    public List<Antecedent> findByNiveauRisque(RisqueEnum niveau) {
        return List.of();
    }

    @Override
    public List<Antecedent> searchByNom(String keyword) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }
}