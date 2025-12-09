package ma.dentalTech.service.modules.Patient.Api;

import ma.dentalTech.entities.Enums.RisqueEnum;
import ma.dentalTech.entities.Patient.Antecedent;
import ma.dentalTech.entities.Patient.Patient;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;

import java.util.List;
import java.util.Optional;

public interface AntecedentService {

     // CRUD Operations
     List<Antecedent> getAllAntecedents() throws ServiceException;
     Optional<Antecedent> getAntecedentById(Long id) throws ServiceException;
     Antecedent createAntecedent(Antecedent antecedent) throws ServiceException, ValidationException;
     Antecedent updateAntecedent(Antecedent antecedent) throws ServiceException, ValidationException;
     void deleteAntecedent(Long id) throws ServiceException;

     // Business Operations
     List<Antecedent> getAntecedentsByCategorie(String categorie) throws ServiceException;
     List<Antecedent> getAntecedentsByNiveauRisque(RisqueEnum niveau) throws ServiceException;
     List<Antecedent> searchAntecedents(String keyword) throws ServiceException;
     long getTotalAntecedents() throws ServiceException;

     // Patient Relations
     List<Patient> getPatientsWithAntecedent(Long antecedentId) throws ServiceException;
}