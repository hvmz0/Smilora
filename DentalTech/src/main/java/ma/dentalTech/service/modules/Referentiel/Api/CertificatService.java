package ma.dentalTech.service.modules.Referentiel.Api;

import ma.dentalTech.entities.Referentiel.Certificat;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;

import java.util.List;
import java.util.Optional;

public interface CertificatService {

    // CRUD Operations
    List<Certificat> getAllCertificats() throws ServiceException;
    Optional<Certificat> getCertificatById(Long id) throws ServiceException;
    Certificat createCertificat(Certificat certificat) throws ServiceException, ValidationException;
    Certificat updateCertificat(Certificat certificat) throws ServiceException, ValidationException;
    void deleteCertificat(Long id) throws ServiceException;

    // Recherche
    List<Certificat> searchCertificats(String keyword) throws ServiceException;

    // Comptage
    long getTotalCertificats() throws ServiceException;
}
