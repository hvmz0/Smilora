package ma.dentalTech.entities.Certificat;

import lombok.*;
import ma.dentalTech.entities.Consultation.Consultation;

import java.time.LocalDate;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Certificat {
    private Long idCertif;
    private LocalDate dateCertif;
    private Long duree;
    private String noteMedecin;
    private Consultation consultation;
}