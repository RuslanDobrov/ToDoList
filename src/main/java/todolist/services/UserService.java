package todolist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todolist.domain.PlainObjects.UserPojo;
import todolist.domain.User;
import todolist.exceptions.CustomEmptyDataException;
import todolist.repositories.UserRepository;
import todolist.services.interfaces.IUserService;
import todolist.utils.Converter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private final Converter converter;
    private final UserRepository userRepository;

    @Autowired
    public UserService(Converter converter, UserRepository userRepository) {
        this.converter = converter;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserPojo createUser(User user) {
        userRepository.save(user);
        return converter.userToPojo(user);
    }

    @Override
    @Transactional
    public UserPojo findUserByEmailAndPassword(String email, String password) {
        Optional<User> userOptional = userRepository.findUserByEmailAndPassword(email, password);
        if (userOptional.isPresent()) {
            return converter.userToPojo(userOptional.get());
        } else {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserPojo getUser(long id) {
        Optional<User> foundUserOptional = userRepository.findById(id);
        return converter.userToPojo(foundUserOptional.get());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserPojo> getAllUsers() {
        List<User> listOfUsers = userRepository.findAll();
        return listOfUsers.stream().map(user -> converter.userToPojo(user)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserPojo updateUser(User source, long id) {
        Optional<User> userForUpdateOptional = userRepository.findById(id);
        if (userForUpdateOptional.isPresent()) {
            User target = userForUpdateOptional.get();
            target.setEmail(source.getEmail());
            target.setPassword(source.getPassword());
            userRepository.save(target);
            return converter.userToPojo(target);
        } else {
            throw new CustomEmptyDataException("unable to update user");
        }
    }

    @Override
    @Transactional
    public String deleteUser(long id) {
        Optional<User> userForDeleteOptional = userRepository.findById(id);
        if (userForDeleteOptional.isPresent()) {
            userRepository.delete(userForDeleteOptional.get());
            return "User with id: " + id + " was successfully remover";
        } else {
            throw new CustomEmptyDataException("unable to delete user");
        }
    }
}
