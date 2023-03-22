package todolist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import todolist.domain.Todo;
import todolist.domain.User;
import todolist.services.interfaces.IUserService;

@Controller
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(path = "/todo")
    public ResponseEntity<Todo> createUser(@RequestBody Todo todo) {
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }
}
