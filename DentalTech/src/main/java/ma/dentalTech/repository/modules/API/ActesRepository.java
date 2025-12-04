package ma.dentalTech.repository.modules.API;

import ma.dentalTech.entities.Actes.Actes;
import ma.dentalTech.entities.patient.Antecedent;
import ma.dentalTech.entities.patient.Patient;
import ma.dentalTech.repository.common.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Author : Houssam
 */
public interface ActesRepository extends CrudRepository<Actes, Integer> {
    /**
     * Recherche un acte par son nom
     */
    Optional<Actes> findByNom(String nom);

    /**
     * Recherche des actes dans une fourchette de prix
     */
    List<Actes> findByPrixRange(double minprix, double maxprix);

    /**
     * Recherche des actes par nom (LIKE)
     */
    List<Actes> searchByNom(String keyword);

    /**
     * Vérifie si un acte existe par son ID
     */
    boolean existsById(Integer id);

    /**
     * Compte le nombre total d'actes
     */
    long count();

    /**
     * Récupère une page d'actes
     */
    List<Actes> findPage(int limit, int offset);

    /**
     * Récupère tous les actes triés par prix
     */
    List<Actes> findAllOrderedByPrix(boolean ascending);
}
