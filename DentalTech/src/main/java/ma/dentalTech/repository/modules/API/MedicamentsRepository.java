package ma.dentalTech.repository.modules.API;

import ma.dentalTech.entities.Medicaments.Medicaments;
import ma.dentalTech.repository.common.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour la gestion des médicaments
 * Author : Hamza
 */
public interface MedicamentsRepository extends CrudRepository<Medicaments, Long> {

    /**
     * Trouve un médicament par son nom
     */
    Optional<Medicaments> findByNom(String nom);

    /**
     * Recherche des médicaments par nom (LIKE)
     */
    List<Medicaments> searchByNom(String keyword);

    /**
     * Recherche des médicaments par description
     */
    List<Medicaments> searchByDescription(String keyword);

    /**
     * Trouve les médicaments dans une fourchette de prix
     */
    List<Medicaments> findByPrixRange(Double prixMin, Double prixMax);

    /**
     * Trouve tous les médicaments triés par prix
     */
    List<Medicaments> findAllOrderedByPrix(boolean ascending);

    /**
     * Trouve tous les médicaments triés par nom
     */
    List<Medicaments> findAllOrderedByNom();

    /**
     * Trouve les médicaments les plus prescrits
     */
    List<Medicaments> findMostPrescribed(int limit);

    /**
     * Compte le nombre de prescriptions pour un médicament
     */
    long countPrescriptions(Long medicamentId);

    /**
     * Vérifie si un médicament existe
     */
    boolean existsById(Long id);

    /**
     * Vérifie si un médicament existe par nom
     */
    boolean existsByNom(String nom);

    /**
     * Compte le nombre total de médicaments
     */
    long count();

    /**
     * Récupère une page de médicaments
     */
    List<Medicaments> findPage(int limit, int offset);
}