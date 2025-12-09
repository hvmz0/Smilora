-- ==========================================
-- DENTALTECH DATABASE SCHEMA
-- ==========================================

-- ==========================================
-- USERS MODULE TABLES
-- ==========================================

CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    adresse VARCHAR(255),
    cin VARCHAR(20) UNIQUE,
    tel VARCHAR(20),
    prenom VARCHAR(100),
    login VARCHAR(50) UNIQUE NOT NULL,
    mot_de_pass VARCHAR(255) NOT NULL,
    date_inscription DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE roles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    libelle VARCHAR(100) NOT NULL UNIQUE,
    active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

CREATE TABLE staff (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    salaire DOUBLE,
    prime DOUBLE,
    date_recrutement DATETIME,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE secretaire (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    num_cnss VARCHAR(50),
    commission DOUBLE,
    agenda_med VARCHAR(255),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE super_admin (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE logs (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    session_start DATETIME,
    session_end DATETIME,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE notification (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    titre VARCHAR(255),
    message VARCHAR(1000),
    date_notification DATETIME DEFAULT CURRENT_TIMESTAMP,
    priorite VARCHAR(50),
    lue BOOLEAN DEFAULT FALSE,
    type VARCHAR(50),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- ==========================================
-- MEDECIN MODULE TABLES
-- ==========================================

CREATE TABLE medecin (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    specialite VARCHAR(100),
    agenda VARCHAR(255),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE agenda_medecin (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    medecin_id BIGINT NOT NULL,
    date_creation DATETIME DEFAULT CURRENT_TIMESTAMP,
    disponibilites JSON,
    jours_disponibles JSON,
    horaires_travail VARCHAR(255),
    conflits JSON,
    date_derniere_maj DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (medecin_id) REFERENCES medecin(id) ON DELETE CASCADE
);

-- ==========================================
-- PATIENT MODULE TABLES
-- ==========================================

CREATE TABLE patient (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100),
    date_naissance DATE,
    adresse VARCHAR(255),
    tel VARCHAR(20),
    email VARCHAR(100),
    sexe ENUM('M', 'F', 'AUTRE') DEFAULT 'M',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE antecedent (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patient_id BIGINT NOT NULL,
    nom VARCHAR(100),
    categorie VARCHAR(100),
    description VARCHAR(1000),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patient(id) ON DELETE CASCADE
);

-- ==========================================
-- RDV MODULE TABLES
-- ==========================================

CREATE TABLE rdv (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patient_id BIGINT NOT NULL,
    medecin_id BIGINT NOT NULL,
    heure DATETIME NOT NULL,
    motif VARCHAR(500),
    note_medecin VARCHAR(1000),
    statut ENUM('CONFIRMÉ', 'ANNULÉ', 'REPORTÉ', 'EN_ATTENTE') DEFAULT 'EN_ATTENTE',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patient(id) ON DELETE CASCADE,
    FOREIGN KEY (medecin_id) REFERENCES medecin(id) ON DELETE CASCADE
);

-- ==========================================
-- DOSSIER MEDICALE MODULE TABLES
-- ==========================================

CREATE TABLE dossier_medicale (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patient_id VARCHAR(255) NOT NULL,
    date_creation DATETIME DEFAULT CURRENT_TIMESTAMP,
    statut ENUM('ACTIF', 'INACTIF', 'ARCHIVÉ') DEFAULT 'ACTIF',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE historique_medicale (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    dossier_medicale_id BIGINT NOT NULL,
    libelle VARCHAR(500),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (dossier_medicale_id) REFERENCES dossier_medicale(id) ON DELETE CASCADE
);

-- ==========================================
-- CONSULTATION MODULE TABLES
-- ==========================================

CREATE TABLE consultation (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patient_id BIGINT NOT NULL,
    medecin_id BIGINT NOT NULL,
    rdv_id BIGINT,
    dossier_medicale_id BIGINT,
    observation_med VARCHAR(1000),
    statut ENUM('EN_COURS', 'TERMINÉE', 'ANNULÉE') DEFAULT 'EN_COURS',
    date_consultation DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patient(id) ON DELETE CASCADE,
    FOREIGN KEY (medecin_id) REFERENCES medecin(id) ON DELETE CASCADE,
    FOREIGN KEY (rdv_id) REFERENCES rdv(id) ON DELETE SET NULL,
    FOREIGN KEY (dossier_medicale_id) REFERENCES dossier_medicale(id) ON DELETE SET NULL
);

CREATE TABLE intervention_medecin (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    consultation_id BIGINT NOT NULL,
    acte_id BIGINT NOT NULL,
    prix_de_patient DOUBLE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (consultation_id) REFERENCES consultation(id) ON DELETE CASCADE,
    FOREIGN KEY (acte_id) REFERENCES actes(id) ON DELETE RESTRICT
);

-- ==========================================
-- ORDONNANCE MODULE TABLES
-- ==========================================

CREATE TABLE ordonnance (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patient_id BIGINT NOT NULL,
    consultation_id BIGINT NOT NULL,
    medecin_id BIGINT NOT NULL,
    date_emission DATETIME DEFAULT CURRENT_TIMESTAMP,
    statut ENUM('ACTIVE', 'EXPIRÉE', 'ANNULÉE') DEFAULT 'ACTIVE',
    notes VARCHAR(1000),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patient(id) ON DELETE CASCADE,
    FOREIGN KEY (consultation_id) REFERENCES consultation(id) ON DELETE CASCADE,
    FOREIGN KEY (medecin_id) REFERENCES medecin(id) ON DELETE CASCADE
);

CREATE TABLE prescription_des_medicaments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    ordonnance_id BIGINT NOT NULL,
    medicament_id BIGINT NOT NULL,
    frequence VARCHAR(100),
    dosage VARCHAR(100),
    duree VARCHAR(100),
    notes VARCHAR(500),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (ordonnance_id) REFERENCES ordonnance(id) ON DELETE CASCADE,
    FOREIGN KEY (medicament_id) REFERENCES medicaments(id) ON DELETE RESTRICT
);

-- ==========================================
-- REFERENTIEL MODULE TABLES
-- ==========================================

CREATE TABLE actes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    libelle VARCHAR(255) NOT NULL,
    categorie VARCHAR(100),
    prix_de_base DOUBLE NOT NULL,
    description VARCHAR(1000),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE medicaments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(255) NOT NULL,
    prix DOUBLE NOT NULL,
    description VARCHAR(1000),
    dosage VARCHAR(100),
    forme VARCHAR(100),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE certificat (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patient_id BIGINT NOT NULL,
    consultation_id BIGINT,
    note_medecin VARCHAR(1000),
    type_certificat VARCHAR(100),
    date_emission DATETIME DEFAULT CURRENT_TIMESTAMP,
    statut ENUM('VALIDE', 'INVALIDE', 'EXPIRÉ') DEFAULT 'VALIDE',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patient(id) ON DELETE CASCADE,
    FOREIGN KEY (consultation_id) REFERENCES consultation(id) ON DELETE SET NULL
);

CREATE TABLE cabinet_medicale (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nom VARCHAR(255) NOT NULL,
    email VARCHAR(100),
    logo VARCHAR(255),
    adresse VARCHAR(255),
    tel VARCHAR(20),
    tel2 VARCHAR(20),
    site_web VARCHAR(255),
    instagram VARCHAR(100),
    facebook VARCHAR(100),
    description VARCHAR(1000),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- ==========================================
-- FINANCE MODULE TABLES
-- ==========================================

CREATE TABLE statistiques (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    total_charges DOUBLE DEFAULT 0,
    total_revenus DOUBLE DEFAULT 0,
    date_stat DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE charges (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    statistique_id BIGINT,
    titre VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    montant DOUBLE NOT NULL,
    date DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (statistique_id) REFERENCES statistiques(id) ON DELETE SET NULL
);

CREATE TABLE revenues (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    statistique_id BIGINT,
    titre VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    montant DOUBLE NOT NULL,
    date DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (statistique_id) REFERENCES statistiques(id) ON DELETE SET NULL
);

CREATE TABLE facture (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patient_id BIGINT NOT NULL,
    consultation_id BIGINT,
    total_facture DOUBLE NOT NULL,
    total_paye DOUBLE DEFAULT 0,
    reste DOUBLE,
    date_facture DATETIME DEFAULT CURRENT_TIMESTAMP,
    statut ENUM('PAYÉE', 'PARTIELLEMENT_PAYÉE', 'NON_PAYÉE') DEFAULT 'NON_PAYÉE',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patient(id) ON DELETE CASCADE,
    FOREIGN KEY (consultation_id) REFERENCES consultation(id) ON DELETE SET NULL
);

CREATE TABLE situation_financiere (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    patient_id BIGINT NOT NULL,
    total_des_achats DOUBLE DEFAULT 0,
    total_paye DOUBLE DEFAULT 0,
    credit DOUBLE DEFAULT 0,
    statut ENUM('À_JOUR', 'DETTEUR', 'CRÉDITEUR') DEFAULT 'À_JOUR',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patient(id) ON DELETE CASCADE
);

-- ==========================================
-- INDEXES
-- ==========================================

-- Users
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_login ON users(login);

-- Patient
CREATE INDEX idx_patient_user_id ON patient(user_id);
CREATE INDEX idx_patient_email ON patient(email);

-- Medecin
CREATE INDEX idx_medecin_user_id ON medecin(user_id);

-- RDV
CREATE INDEX idx_rdv_patient_id ON rdv(patient_id);
CREATE INDEX idx_rdv_medecin_id ON rdv(medecin_id);
CREATE INDEX idx_rdv_heure ON rdv(heure);

-- Consultation
CREATE INDEX idx_consultation_patient_id ON consultation(patient_id);
CREATE INDEX idx_consultation_medecin_id ON consultation(medecin_id);
CREATE INDEX idx_consultation_rdv_id ON consultation(rdv_id);

-- Ordonnance
CREATE INDEX idx_ordonnance_patient_id ON ordonnance(patient_id);
CREATE INDEX idx_ordonnance_consultation_id ON ordonnance(consultation_id);
CREATE INDEX idx_ordonnance_medecin_id ON ordonnance(medecin_id);

-- Finance
CREATE INDEX idx_charges_statistique_id ON charges(statistique_id);
CREATE INDEX idx_revenues_statistique_id ON revenues(statistique_id);
CREATE INDEX idx_facture_patient_id ON facture(patient_id);
CREATE INDEX idx_situation_patient_id ON situation_financiere(patient_id);

-- Logs
CREATE INDEX idx_logs_user_id ON logs(user_id);
CREATE INDEX idx_logs_session_start ON logs(session_start);

-- Notification
CREATE INDEX idx_notification_user_id ON notification(user_id);
