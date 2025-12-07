package ma.dentalTech.repository.modules.Patient.Impl.MySQL;

import ma.dentalTech.conf.SessionFactory;
import ma.dentalTech.entities.Enums.RisqueEnum;
import ma.dentalTech.entities.Patient.Antecedent;
import ma.dentalTech.repository.modules.Patient.Api.AntecedentRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AntecedentRepositoryImpl implements AntecedentRepository {

    private Connection getConnection() throws SQLException {
        return SessionFactory.getInstance().getConnection();
    }

    @Override
    public List<Antecedent> findAll() {
        List<Antecedent> list = new ArrayList<>();
        String sql = "SELECT * FROM Antecedents ORDER BY nom";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findAll Antecedents", e);
        }
        return list;
    }

    @Override
    public Antecedent findById(Long id) {
        String sql = "SELECT * FROM Antecedents WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findById Antecedent", e);
        }
        return null;
    }

    @Override
    public void create(Antecedent a) {
        String sql = "INSERT INTO Antecedents (nom, categorie, niveauDeRisque) VALUES (?, ?, ?)";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, a.getNom());
            ps.setString(2, a.getCategorie());
            ps.setString(3, a.getNiveauDeRisque().name());

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    a.setId(keys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur create Antecedent", e);
        }
    }

    @Override
    public void update(Antecedent a) {
        String sql = "UPDATE Antecedents SET nom = ?, categorie = ?, niveauDeRisque = ? WHERE id = ?";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, a.getNom());
            ps.setString(2, a.getCategorie());
            ps.setString(3, a.getNiveauDeRisque().name());
            ps.setLong(4, a.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur update Antecedent", e);
        }
    }

    @Override
    public void delete(Antecedent a) {
        deleteById(a.getId());
    }

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

    @Override
    public List<Antecedent> findByCategorie(String categorie) {
        List<Antecedent> list = new ArrayList<>();
        String sql = "SELECT * FROM Antecedents WHERE categorie = ? ORDER BY nom";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, categorie);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByCategorie", e);
        }
        return list;
    }

    @Override
    public List<Antecedent> findByNiveauRisque(RisqueEnum niveau) {
        List<Antecedent> list = new ArrayList<>();
        String sql = "SELECT * FROM Antecedents WHERE niveauDeRisque = ? ORDER BY nom";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, niveau.name());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur findByNiveauRisque", e);
        }
        return list;
    }

    @Override
    public List<Antecedent> searchByNom(String keyword) {
        List<Antecedent> list = new ArrayList<>();
        String sql = "SELECT * FROM Antecedents WHERE nom LIKE ? ORDER BY nom";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur searchByNom", e);
        }
        return list;
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM Antecedents";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur count Antecedents", e);
        }
        return 0;
    }

    private Antecedent mapRow(ResultSet rs) throws SQLException {
        Antecedent a = new Antecedent();
        a.setId(rs.getLong("id"));
        a.setNom(rs.getString("nom"));
        a.setCategorie(rs.getString("categorie"));
        a.setNiveauDeRisque(RisqueEnum.valueOf(rs.getString("niveauDeRisque")));
        return a;
    }
}