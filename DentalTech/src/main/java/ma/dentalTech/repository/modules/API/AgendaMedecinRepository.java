package ma.dentalTech.repository.modules.API;

import ma.dentalTech.entities.AgendaMedecin.AgendaMedecin;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Author : Houssam
 */
public interface AgendaMedecinRepository {
    /**
     * Trouve l'agenda d'un médecin spécifique
     */
    Optional<AgendaMedecin> findByMedecinId(int medecinId);

    /**
     * Trouve les agendas par date
     */
    List<AgendaMedecin> findByDate(Date date);

    /**
     * Trouve les agendas par médecin et date
     */
    List<AgendaMedecin> findByMedecinIdAndDate(int medecinId, Date date);

    /**
     * Trouve les agendas disponibles (statut = "DISPONIBLE")
     */
    List<AgendaMedecin> findAvailableSlots(int medecinId, Date date);

    /**
     * Trouve les agendas par statut
     */
    List<AgendaMedecin> findByStatut(String statut);

    /**
     * Trouve les agendas dans une plage horaire
     */
    List<AgendaMedecin> findByTimeRange(int medecinId, Date date, Time heureDebut, Time heureFin);

    /**
     * Vérifie la disponibilité d'un créneau
     */
    boolean isSlotAvailable(int medecinId, Date date, Time heureDebut, Time heureFin);

    /**
     * Met à jour le statut d'un agenda
     */
    void updateStatut(int agendaId, String newStatut);

    /**
     * Compte le nombre d'agendas
     */
    long count();

    /**
     * Vérifie si un agenda existe
     */
    boolean existsById(Integer id);
}
