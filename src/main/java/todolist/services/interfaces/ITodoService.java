package todolist.services.interfaces;

import todolist.domain.PlainObjects.TodoPojo;
import todolist.domain.Todo;

import java.util.List;

public interface ITodoService {
    TodoPojo createTodo(Todo todo, Long userId);
    TodoPojo getTodo(Long id);
    TodoPojo updateTodo(Todo updatedTodo, Long todoId);
    String deleteTodo(Long id);
    List<TodoPojo> getAllTodos(Long userId);
}
