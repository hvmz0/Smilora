

package ma.dentalTech.repository.common;

import ma.dentalTech.entities.Users.User;

import java.util.List;

public interface CrudRepository<T, ID> {

    List<T> findAll();

    T findById(ID id);

    long create(User user);

    void update(T newValuesElement);

    void delete(T patient);

    void deleteById(ID id);
}



