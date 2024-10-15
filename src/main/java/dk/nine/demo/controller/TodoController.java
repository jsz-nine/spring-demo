package dk.nine.demo.controller;

import dk.nine.demo.dto.lomboks.todo.TodoDto;
import dk.nine.demo.service.TodosService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
public class TodoController {

    @Autowired
    private final TodosService todosService;


    @Autowired
    public TodoController(TodosService todosService) {
        this.todosService = todosService;
    }

    @GetMapping("/todos")
    public List<TodosDto> AllTodos() {
        return todosService.getAllTodosLists();
    }

    @GetMapping("/todos/{uuid}")
    public ResponseEntity<TodosDto> getTodoById(@PathVariable UUID uuid) {
        return new ResponseEntity<TodosDto>(todosService.getATodos(uuid.toString()), HttpStatus.OK);
    }

    @PostMapping("/todos")
    public TodosDto createTodo(@RequestBody CreateTodosDto createTodosDto) {
        TodosDto todosDto = new TodosDtoBuilder()
                .title(createTodosDto.title())
                .uuid(UUID.randomUUID())
                .createdAt(LocalDate.now())
                .description(createTodosDto.description())
                .build();
        return todosService.createTodosList(todosDto);
    }

    @PutMapping("/todos")
    public TodosDto updateTodoById(@RequestBody TodosDto todosDto) {
        return todosService.updateTodosList(todosDto);
    }

    @PatchMapping("/todos/{UUID}")
    public ResponseEntity<?> patchTodoById(@PathVariable String uuid, @RequestBody TodoDto todoDto) throws ChangeSetPersister.NotFoundException {
        try {
            return new ResponseEntity<TodosDto>(todosService.patchTodosList(UUID.fromString(uuid), todoDto), HttpStatus.OK);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error updating todo with UUID: {}", uuid, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }

    @DeleteMapping("/todos/{uuid}")
    public Boolean deleteTodoById(@PathVariable String uuid) {
        return todosService.removeTodosList(UUID.fromString(uuid));
    }
}
