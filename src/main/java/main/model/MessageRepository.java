package main.model;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//Интерфейс для получения данных из БД
@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {

    Iterable<Message> findAll(Sort dateTime);
}
