package todolist.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todolist.domain.PlainObjects.TodoPojo;
import todolist.domain.Tag;
import todolist.domain.Todo;
import todolist.domain.User;
import todolist.services.interfaces.ITagService;
import todolist.services.interfaces.ITodoService;
import todolist.utils.Converter;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TodoService implements ITodoService {

    @PersistenceContext
    private EntityManager entityManager;

    private final Converter converter;
    private final ITagService tagService;

    @Autowired
    public TodoService(Converter converter, ITagService tagService) {
        this.converter = converter;
        this.tagService = tagService;
    }

    @Override
    @Transactional
    public TodoPojo createTodo(Todo todo, Long userId) {
        User todoUser = entityManager
                .createQuery("SELECT user FROM User user WHERE user.id = :id", User.class)
                .setParameter("id", userId)
                .getSingleResult();

        todo.setUser(todoUser);

        Set<Tag> tags = new HashSet<>();
        tags.addAll(todo.getTagList());

        todo.getTagList().clear();

        entityManager.persist(todo);

        tags.stream().map(tag -> tagService.findOrCreate(tag)).collect(Collectors.toSet()).forEach(todo::addTag);

        return converter.todoToPojo(todo);
    }

    @Override
    @Transactional
    public TodoPojo getTodo(Long id) {
        return null;
    }

    @Override
    @Transactional
    public TodoPojo updateTodo(Todo updatedTodo, Long todoId) {
        return null;
    }

    @Override
    @Transactional
    public TodoPojo deleteTodo(Long id) {
        return null;
    }

    @Override
    @Transactional
    public List<TodoPojo> getAllTodos(Long userId) {
        return null;
    }
}
