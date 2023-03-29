package todolist.services.interfaces;

import todolist.domain.PlainObjects.TodoPojo;
import todolist.domain.Todo;

import java.util.List;

public interface ITodoService {
    TodoPojo createTodo(Todo todo, Long userId);
    TodoPojo getTodo(Long id, Long userId);
    TodoPojo updateTodo(Todo updatedTodo, Long todoId, Long userId);
    String deleteTodo(Long id, Long userId);
    List<TodoPojo> getAllTodos(Long userId);
}
