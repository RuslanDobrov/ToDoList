package todolist.services.interfaces;

import todolist.domain.PlainObjects.UserPojo;
import todolist.domain.User;

import java.util.List;

public interface IUserService {
    UserPojo createUser(User user);
    UserPojo findUserByEmailAndPassword(String email, String password);
    UserPojo getUser(long id);
    List<UserPojo> getAllUsers();
    UserPojo updateUser(User user, long id);
    String deleteUser(long id);
}
