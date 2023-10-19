package main.model;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//Интерфейс для получения данных из БД
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Iterable<User> findAll(Sort name);
}
