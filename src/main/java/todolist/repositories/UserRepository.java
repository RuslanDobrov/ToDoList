package todolist.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import todolist.domain.User;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findAll();
}
