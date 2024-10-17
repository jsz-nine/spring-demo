package dk.nine.demo.controller;

import dk.nine.demo.dto.todo.CreateTodoListDto;
import dk.nine.demo.dto.todo.TaskDto;
import dk.nine.demo.dto.todo.TodoListDto;
import dk.nine.demo.service.TodosService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
public class TodoListController {

    @Autowired
    private final TodosService todosService;


    @Autowired
    public TodoListController(TodosService todosService) {
        this.todosService = todosService;
    }

    @GetMapping("/todos")
    public List<TodoListDto> AllTodoLists() {
        return todosService.getAllTodosLists();
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<TodoListDto> getTodoListByUuid(@PathVariable UUID id) {
        return new ResponseEntity<TodoListDto>(todosService.getATodos(id.toString()), HttpStatus.OK);
    }

    @PostMapping("/todos")
    public TodoListDto createTodoList(@RequestBody CreateTodoListDto createTodosDto) {
        log.debug("creating TodoList {}", createTodosDto);
        TodoListDto todosList = todosService.createTodosList(createTodosDto);
        log.debug("Creation complete {}", todosList);
        return todosList;
    }

    @PostMapping("/todos/{id}/task")
    public TodoListDto createTodo(@RequestBody TaskDto taskDto, @PathVariable UUID id) {
        log.debug("creating Todo {}, for TodoList: {}", taskDto, id);
        TodoListDto todosList = todosService.createTodo(id, taskDto);
        log.debug("Creation complete {}", todosList);
        return todosList;
    }


    @GetMapping("/todos/search/{query}")
    public List<TodoListDto> searchForTodoLists(@Valid @PathVariable String query) {
        if (query.length() <= 2) {
            return new ArrayList<TodoListDto>();
        }
        log.debug("searched for {}", query);
        List<TodoListDto> todosListsByTitle = todosService.getTodosListsByTitle(query);
        log.debug("todoLists found [{}]", todosListsByTitle.size());
        todosListsByTitle.forEach(element -> log.debug("uuid: {}, title: {}", element.getId(), element.getTitle()));
        return todosListsByTitle;

    }

    @PutMapping("/todos")
    public TodoListDto updateTodoList(@RequestBody TodoListDto todoListDto) {
        log.debug("attempting to update TodoList with uuid: {}", todoListDto.getId());
        TodoListDto updatedTodosList = todosService.updateTodosList(todoListDto);
        log.debug("after update {}", updatedTodosList.toString());
        return updatedTodosList;
    }

    @PatchMapping("/todos/{uuid}")
    public ResponseEntity<?> patchTodoById(@PathVariable String uuid, @RequestBody TaskDto taskDto) throws ChangeSetPersister.NotFoundException {
        try {
            TodoListDto updatedTodoListDto = todosService.patchTodosList(UUID.fromString(uuid), taskDto);
            log.debug("Successfully updated Todo with UUID: {}. Updated data: {}", uuid, updatedTodoListDto);
            return new ResponseEntity<>(updatedTodoListDto, HttpStatus.OK);
        } catch (ChangeSetPersister.NotFoundException e) {
            log.warn("Todo with UUID: {} not found for patch operation.", uuid);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error updating Todo with UUID: {}. Exception: {}", uuid, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/todos/{uuid}")
    public Boolean deleteTodoListByUuid(@PathVariable String uuid) {
        log.debug("Received request to delete Todo with UUID: {}", uuid);

        try {
            boolean isDeleted = todosService.removeTodosList(UUID.fromString(uuid));

            if (isDeleted) {
                log.debug("Successfully deleted Todo with UUID: {}", uuid);
            } else {
                log.warn("No Todo found with UUID: {} to delete.", uuid);
            }

            return isDeleted;
        } catch (Exception e) {
            log.error("Error occurred while trying to delete Todo with UUID: {}. Exception: {}", uuid, e.getMessage(), e);
            return false; // or you might want to throw an exception or handle it differently
        }
    }
}
