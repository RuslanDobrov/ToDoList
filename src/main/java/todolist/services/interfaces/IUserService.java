package todolist.services.interfaces;

import todolist.domain.User;

public interface IUserService {
    int createUser(User user);
    User getUser(long id);
    int updateUser(User user, long id);
    int deleteUser(long id);
    void createTableUser();
}
