-- Table Patients
CREATE TABLE IF NOT EXISTS Patients (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        nom VARCHAR(80)      NOT NULL,
                                        prenom VARCHAR(80)   NOT NULL,
                                        adresse VARCHAR(150),
                                        telephone VARCHAR(30),
                                        email VARCHAR(120),
                                        dateNaissance DATE,
                                        dateCreation DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                        sexe ENUM('Homme','Femme') NOT NULL,
                                        assurance ENUM('CNOPS','CNSS','Autre','Aucune') NOT NULL,
                                        UNIQUE KEY uk_patients_email (email),
                                        KEY idx_patients_nom (nom, prenom)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Table Antecedents
CREATE TABLE Antecedents (
                             id BIGINT AUTO_INCREMENT PRIMARY KEY,
                             nom VARCHAR(100) NOT NULL,
                             categorie ENUM(
                                 'ALLERGIE',
                                 'MALADIE_CHRONIQUE',
                                 'CONTRE_INDICATION',
                                 'TRAITEMENT_EN_COURS',
                                 'ANTECEDENT_CHIRURGICAL',
                                 'ANTECEDENT_INFECTIEUX',
                                 'ANTECEDENT_DENTAIRE',
                                 'HABITUDE_DE_VIE',
                                 'AUTRE'
                                 ) NOT NULL,
                             niveauRisque ENUM('FAIBLE', 'MODERE', 'ELEVE', 'CRITIQUE') NOT NULL
);

-- Table Association Patient-Antecedent
CREATE TABLE Patient_Antecedents (
                                     patient_id BIGINT NOT NULL,
                                     antecedent_id BIGINT NOT NULL,
                                     PRIMARY KEY (patient_id, antecedent_id),
                                     FOREIGN KEY (patient_id) REFERENCES Patients(id) ON DELETE CASCADE,
                                     FOREIGN KEY (antecedent_id) REFERENCES Antecedents(id) ON DELETE CASCADE
);

