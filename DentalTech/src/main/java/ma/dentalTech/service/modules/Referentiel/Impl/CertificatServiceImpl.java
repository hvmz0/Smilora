package ma.dentalTech.service.modules.Referentiel.Impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import ma.dentalTech.common.validation.Validators;
import ma.dentalTech.entities.Referentiel.Certificat;
import ma.dentalTech.repository.modules.Referentiel.Api.CertificatRepository;
import ma.dentalTech.service.modules.Referentiel.Api.CertificatService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class CertificatServiceImpl implements CertificatService {

    private final CertificatRepository certificatRepository;

    @Override
    public List<Certificat> getAllCertificats() throws ServiceException {
        try {
            return certificatRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des certificats", e);
        }
    }

    @Override
    public Optional<Certificat> getCertificatById(Long id) throws ServiceException {
        try {
            Certificat certificat = certificatRepository.findById(id);
            return Optional.ofNullable(certificat);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération du certificat avec l'ID: " + id, e);
        }
    }

    @Override
    public Certificat createCertificat(Certificat certificat) throws ServiceException, ValidationException {
        try {
            validateCertificat(certificat);
            certificatRepository.create(user);
            return certificat;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création du certificat", e);
        }
    }

    @Override
    public Certificat updateCertificat(Certificat certificat) throws ServiceException, ValidationException {
        try {
            if (certificat.getId() == null) {
                throw new ValidationException("L'ID du certificat est requis pour la mise à jour");
            }
            validateCertificat(certificat);
            certificatRepository.update(certificat);
            return certificat;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour du certificat", e);
        }
    }

    @Override
    public void deleteCertificat(Long id) throws ServiceException {
        try {
            Certificat certificat = certificatRepository.findById(id);
            if (certificat == null) {
                throw new ServiceException("Certificat non trouvé avec l'ID: " + id);
            }
            certificatRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression du certificat", e);
        }
    }

    @Override
    public List<Certificat> searchCertificats(String keyword) throws ServiceException {
        try {
            if (keyword == null || keyword.trim().isEmpty()) {
                return getAllCertificats();
            }
            return certificatRepository.searchById(keyword);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche de certificats", e);
        }
    }

    @Override
    public long getTotalCertificats() throws ServiceException {
        try {
            return certificatRepository.countByPatientId();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du comptage des certificats", e);
        }
    }

    private void validateCertificat(Certificat certificat) throws ValidationException {
        Validators.notBlank(String.valueOf(certificat.getId()), "Le libellé");
    }
}
