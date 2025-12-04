package ma.dentalTech.entities.CabinetMedicale;

import lombok.*;
import ma.dentalTech.entities.Charges.Charges;
import ma.dentalTech.entities.Facture.Facture;
import ma.dentalTech.entities.Revenus.Revenus;
import ma.dentalTech.entities.Statistique.Statistique;
import ma.dentalTech.entities.patient.Patient;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CabinetMedicale {
    private Long idSF;
    private String nom;
    private String email;
    private String logo;
    private String adresse;
    private String tel;
    private Boolean isActif;
    private Boolean isWeb;
    private String facebook;
    private String instagram;
    private String description;

    private List<Patient> patients;
    private List<Facture> factures;
    private List<Charges> charges;
    private List<Revenus> revenus;
    private List<Statistique> statistiques;
}