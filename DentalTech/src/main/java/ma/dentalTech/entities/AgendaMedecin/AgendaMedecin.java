package ma.dentalTech.entities.AgendaMedecin;

import java.sql.Time;
import java.util.Date;

public class AgendaMedecin {
    private int id;
    private int medecinId;
    private Date dateAgenda;
    private Time heureDebut;
    private Time heureFin;
    private String statut;

    public AgendaMedecin() {
    }

    public AgendaMedecin(int id, int medecinId, Date dateAgenda, Time heureDebut, Time heureFin, String statut) {
        this.id = id;
        this.medecinId = medecinId;
        this.dateAgenda = dateAgenda;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.statut = statut;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMedecinId() { return medecinId; }
    public void setMedecinId(int medecinId) { this.medecinId = medecinId; }

    public Date getDateAgenda() { return dateAgenda; }
    public void setDateAgenda(Date dateAgenda) { this.dateAgenda = dateAgenda; }

    public Time getHeureDebut() { return heureDebut; }
    public void setHeureDebut(Time heureDebut) { this.heureDebut = heureDebut; }

    public Time getHeureFin() { return heureFin; }
    public void setHeureFin(Time heureFin) { this.heureFin = heureFin; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}