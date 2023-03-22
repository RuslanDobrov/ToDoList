package todolist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import todolist.domain.User;
import todolist.services.interfaces.IUserService;

@Service
public class UserService implements IUserService {

    @Autowired
    public UserService() {

    }

    @Override
    public int createUser(User user) {
        return 0;
    }

    @Override
    public User getUser(long id) {
        return null;
    }

    @Override
    public int updateUser(User updateUser, long id) {
        return 0;
    }

    @Override
    public int deleteUser(long id) {
        return 0;
    }
}
