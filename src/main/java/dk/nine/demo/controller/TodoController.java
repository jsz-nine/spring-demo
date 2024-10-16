package dk.nine.demo.controller;

import dk.nine.demo.dto.todo.CreateTodoListDto;
import dk.nine.demo.dto.todo.TodoDto;
import dk.nine.demo.dto.todo.TodosDto;
import dk.nine.demo.service.TodosService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public List<TodosDto> AllTodoLists() {
        return todosService.getAllTodosLists();
    }

    @GetMapping("/todos/{uuid}")
    public ResponseEntity<TodosDto> getTodoListByUuid(@PathVariable UUID uuid) {
        return new ResponseEntity<TodosDto>(todosService.getATodos(uuid.toString()), HttpStatus.OK);
    }

    @PostMapping("/todos")
    public TodosDto createTodoList(@RequestBody CreateTodoListDto createTodosDto) {
        log.debug("creating TodoList {}", createTodosDto);
        TodosDto todosList = todosService.createTodosList(createTodosDto);
        log.debug("Creation complete {}", todosList);
        return todosList;
    }
    @PostMapping("/todos/{uuid}/todo")
    public TodosDto createTodo(@RequestBody TodoDto todoDto, @PathVariable UUID uuid) {
        log.debug("creating Todo {}, for TodoList: {}", todoDto, uuid);
        TodosDto todosList = todosService.createTodo(uuid, todoDto);
        log.debug("Creation complete {}", todosList);
        return todosList;
    }


    @GetMapping("/todos/search/{query}")
    public List<TodosDto> searchForTodoLists(@PathVariable String query) {
        log.debug("searched for {}", query);
        List<TodosDto> todosListsByTitle = todosService.getTodosListsByTitle(query);
        log.debug("todoLists found [{}]", todosListsByTitle.size());
        todosListsByTitle.forEach(element -> log.debug("uuid: {}, title: {}", element.getId(), element.getTitle()));
        return todosListsByTitle;

    }

    @PutMapping("/todos")
    public TodosDto updateTodoList(@RequestBody TodosDto todosDto) {
        log.debug("attempting to update TodoList with uuid: {}", todosDto.getId());
        TodosDto updatedTodosList = todosService.updateTodosList(todosDto);
        log.debug("after update {}", updatedTodosList.toString());
        return updatedTodosList;
    }

    @PatchMapping("/todos/{uuid}")
    public ResponseEntity<?> patchTodoById(@PathVariable String uuid, @RequestBody TodoDto todoDto) throws ChangeSetPersister.NotFoundException {
        try {
            TodosDto updatedTodosDto = todosService.patchTodosList(UUID.fromString(uuid), todoDto);
            log.debug("Successfully updated Todo with UUID: {}. Updated data: {}", uuid, updatedTodosDto);
            return new ResponseEntity<>(updatedTodosDto, HttpStatus.OK);
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
