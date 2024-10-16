package dk.nine.demo.service;


import dk.nine.demo.dto.todo.CreateTodoListDto;
import dk.nine.demo.dto.todo.TodoDto;
import dk.nine.demo.dto.todo.TodosDto;
import dk.nine.demo.model.Todo;
import dk.nine.demo.model.Todos;
import dk.nine.demo.repository.CompanyRepository;
import dk.nine.demo.repository.todos.TodoRepository;
import dk.nine.demo.repository.todos.TodosRepository;
import dk.nine.demo.view.TodoMapper;
import dk.nine.demo.view.TodosMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class TodosService {

    private final TodosRepository todosRepo;
    private final TodoRepository todoRepo;
    private final TodosRepository todosRepository;
    private final CompanyRepository companyRepository;
    private final TodosMapper todosMapper;
    private final TodoMapper todoMapper;


    public List<TodosDto> getAllTodosLists() {
        return todosRepository.findAll().stream().map(todosMapper::toDto).collect(Collectors.toList());
    }

    public List<TodosDto> getTodosListsByTitle(String searchQuery) {
        return todosRepository.findByTitleContainsIgnoreCaseOrderByCreatedAt(searchQuery).stream().map(todosMapper::toDto).collect(Collectors.toList());

    }

    public TodosDto getATodos(String uuid) {
        return todosRepository.findById(UUID.fromString(uuid)).stream().map(todosMapper::toDto).toList().getFirst();
    }

    public TodosDto createTodosList(CreateTodoListDto createTodoListDto) {

        Todos todos = Todos.builder().createdAt(LocalDate.now()) // Setting createdAt
                .todoList(new ArrayList<Todo>()) // Initializing with an empty list of TodoDto
                .uuid(UUID.randomUUID()) // Setting UUID
                .title(createTodoListDto.getTitle()) // Setting title from DTO
                .description(createTodoListDto.getDescription()) // Setting description from DTO
                .build();

        log.debug("todos {}", todos);
        Todos save = todosRepository.save(todos);
        log.debug("save {}", save);
        return todosMapper.toDto(save);
    }

    @Transactional
    public TodosDto updateTodosList(TodosDto updatedTodos) {
        UUID uuid = updatedTodos.getUuid();
        Todos existingTodos = todosRepository.findById(uuid).orElseThrow(() -> new IllegalArgumentException("Todos not found with UUID: " + uuid));

        existingTodos.setTitle(updatedTodos.getTitle());
        existingTodos.setDescription(updatedTodos.getDescription());

        List<Todo> existingTodoList = existingTodos.getTodoList();

        existingTodoList.removeIf(todo -> updatedTodos.getTodoList().stream().noneMatch(updatedTodo -> updatedTodo.getId().equals(todo.getId())));

        for (TodoDto updatedTodoDto : updatedTodos.getTodoList()) {
            Todo updatedTodo = todoMapper.toEntity(updatedTodoDto);

            // Check if the Todo exists or needs to be added
            existingTodoList.stream().filter(todo -> todo.getId().equals(updatedTodo.getId())).findFirst().ifPresentOrElse(existingTodo -> {
                existingTodo.setTitle(updatedTodo.getTitle());
                existingTodo.setDescription(updatedTodo.getDescription());
                existingTodo.setDueDate(updatedTodo.getDueDate());
                existingTodo.setCompleted(updatedTodo.getCompleted());
            }, () -> {
                updatedTodo.setTodoList(existingTodos);
                existingTodoList.add(updatedTodo);
            });
        }

        return todosMapper.toDto(todosRepository.save(existingTodos));
    }

    public Boolean removeTodosList(UUID uuid) {
        try {
            todosRepository.deleteById(uuid);
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getLocalizedMessage());
        }
        return todosRepository.findById(uuid).isEmpty();
    }


    /*
    public Todo createTodo(Todo todo, Long todoListId) {
        // 1. Retrieve the Todos entity from the database using the todoListId
        Todos todos = todosRepository.findById(todoListId)
                .orElseThrow(() -> new EntityNotFoundException("Todos not found with id: " + todoListId));

        // 2. Set the retrieved Todos entity to the 'todoList' field of the new Todo
        todo.setTodoList(todos);

        // 3. Save the new Todo entity
        return todoRepository.save(todo);
    }

     */
    public TodosDto createTodo(UUID uuid, TodoDto todoDto) {
        Todos todos = todosRepository.findById(uuid).orElseThrow(() -> new EntityNotFoundException("Todos not found with id: " + uuid));
        Todo todo = todoMapper.toEntity(todoDto);
        todo.setTodoList(todos);

        todoRepo.save(todo);

        return todosRepository.findById(uuid).map(todosMapper::toDto).orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve updated Todos"));

    }

    public TodosDto patchTodosList(UUID uuid, TodoDto todoDto) throws ChangeSetPersister.NotFoundException {
        return todosRepository.findById(uuid).map(todos -> {
            todos.getTodoList().replaceAll(todoFromTodos -> {
                if (Objects.equals(todoFromTodos.getId(), todoDto.getId())) {
                    BeanUtils.copyProperties(todoDto, todoFromTodos, "id");
                    return todoFromTodos;
                } else {
                    return todoFromTodos;
                }
            });
            return todosMapper.toDto(todos);
        }).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }


}
