package todolist.services;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import todolist.domain.PlainObjects.UserPojo;
import todolist.domain.User;
import todolist.services.interfaces.IUserService;

import java.util.List;

import static org.junit.Assert.*;

public class UserServiceTest {

    private final String EMAIL = "email@mail.com";
    private final String PASSWORD = "somePassword";

    private ApplicationContext applicationContext;
    private IUserService userService;

    @Before
    public void init() {
        this.applicationContext = new ClassPathXmlApplicationContext("/mainTest.xml");
        this.userService = applicationContext.getBean("userService", IUserService.class);
    }

    @After
    public void cleanDB() {
        userService.getAllUsers().forEach(userPojo -> userService.deleteUser(userPojo.getId()));
    }

    @Test
    public void createUserTest() {
        User newUser = new User();
        newUser.setEmail(EMAIL);
        newUser.setPassword(PASSWORD);

        UserPojo actual = userService.createUser(newUser);
        assertEquals(EMAIL, actual.getEmail());
        assertEquals(PASSWORD, actual.getPassword());
        assertNotNull(actual.getId());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testUniqueUserEmail() {
        User newUser = new User();
        newUser.setEmail(EMAIL);
        newUser.setPassword(PASSWORD);
        userService.createUser(newUser);
        User newUser2 = new User();
        newUser2.setEmail(EMAIL);
        newUser2.setPassword(PASSWORD);
        userService.createUser(newUser2);
    }

    @Test
    public void getUserTest() {
        User newUser = new User();
        newUser.setEmail(EMAIL);
        newUser.setPassword(PASSWORD);

        UserPojo actual = userService.createUser(newUser);
        UserPojo current = userService.getUser(actual.getId());

        assertEquals(actual.getId(), current.getId());
        assertEquals(EMAIL, current.getEmail());
        assertEquals(PASSWORD, current.getPassword());
    }

    @Test
    public void findUserByEmailAndPasswordTest() {
        User newUser = new User();
        newUser.setEmail(EMAIL);
        newUser.setPassword(PASSWORD);

        UserPojo actual = userService.createUser(newUser);
        UserPojo current = userService.findUserByEmailAndPassword(EMAIL, PASSWORD);

        assertEquals(actual.getId(), current.getId());
        assertEquals(actual.getEmail(), current.getEmail());
        assertEquals(actual.getPassword(), current.getPassword());
    }

    @Test
    public void updateUserTest() {
        User newUser = new User();
        newUser.setEmail(EMAIL);
        newUser.setPassword(PASSWORD);

        UserPojo actual = userService.createUser(newUser);
        User userForUpdate = new User();
        userForUpdate.setEmail("testmail@test.com");
        userForUpdate.setPassword("testPassword");

        UserPojo updatedUser = userService.updateUser(userForUpdate, actual.getId());
        assertEquals(actual.getId(), updatedUser.getId());
        assertEquals("testmail@test.com", updatedUser.getEmail());
        assertEquals("testPassword", updatedUser.getPassword());
    }

    @Test
    public void deleteUserTest() {
        User newUser = new User();
        newUser.setEmail(EMAIL);
        newUser.setPassword(PASSWORD);

        UserPojo actual = userService.createUser(newUser);
        List<UserPojo> userListAfterCreate = userService.getAllUsers();
        assertEquals(1, userListAfterCreate.size());

        userService.deleteUser(actual.getId());

        List<UserPojo> userListAfterDelete = userService.getAllUsers();
        assertEquals(0, userListAfterDelete.size());
    }
}
