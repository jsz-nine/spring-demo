package dk.nine.demo.service;


import dk.nine.demo.dto.todo.CreateTodoListDto;
import dk.nine.demo.dto.todo.TaskDto;
import dk.nine.demo.dto.todo.TodoListDto;
import dk.nine.demo.model.Task;
import dk.nine.demo.model.TodoList;
import dk.nine.demo.repository.CompanyRepository;
import dk.nine.demo.repository.todos.TodoRepository;
import dk.nine.demo.repository.todos.TodosRepository;
import dk.nine.demo.view.TaskMapper;
import dk.nine.demo.view.TodoListMapper;
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
    private final TodoListMapper todoListMapper;
    private final TaskMapper taskMapper;


    public List<TodoListDto> getAllTodosLists() {
        return todosRepository.findAll().stream().map(todoListMapper::toDto).collect(Collectors.toList());
    }

    public List<TodoListDto> getTodosListsByTitle(String searchQuery) {
        return todosRepository.findByTitleContainsIgnoreCaseOrderByCreatedAt(searchQuery).stream().map(todoListMapper::toDto).collect(Collectors.toList());

    }

    public TodoListDto getATodos(String uuid) {
        return todosRepository.findById(UUID.fromString(uuid)).stream().map(todoListMapper::toDto).toList().getFirst();
    }

    public TodoListDto createTodosList(CreateTodoListDto createTodoListDto) {

        TodoList todoList = TodoList.builder().createdAt(LocalDate.now()) // Setting createdAt
                .taskList(new ArrayList<Task>()) // Initializing with an empty list of TodoDto
                .id(UUID.randomUUID()) // Setting UUID
                .title(createTodoListDto.getTitle()) // Setting title from DTO
                .description(createTodoListDto.getDescription()) // Setting description from DTO
                .build();

        log.debug("todos {}", todoList);
        TodoList save = todosRepository.save(todoList);
        log.debug("save {}", save);
        return todoListMapper.toDto(save);
    }

    @Transactional
    public TodoListDto updateTodosList(TodoListDto updatedTodos) {


        UUID uuid = updatedTodos.getId();
        TodoList existingTodoList = todosRepository.findById(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Todos not found with UUID: " + uuid));

        if (updatedTodos.getTitle() != null) {
            existingTodoList.setTitle(updatedTodos.getTitle());
        }
        if (updatedTodos.getDescription() != null) {
            existingTodoList.setDescription(updatedTodos.getDescription());
        }
        if (updatedTodos.getCompletedAt() != null) {
            existingTodoList.setCompletedAt(updatedTodos.getCompletedAt());
        }

        if (updatedTodos.getTodoList() != null) {
            List<Task> existingTaskList = existingTodoList.getTaskList();

            existingTaskList.removeIf(task ->
                    updatedTodos.getTodoList().stream().noneMatch(updatedTodo -> updatedTodo.getId().equals(task.getId()))
            );

            for (TaskDto updatedTaskDto : updatedTodos.getTodoList()) {
                if (updatedTaskDto.getId() != null) {
                    Task updatedTask = taskMapper.toModel(updatedTaskDto);

                    existingTaskList.stream()
                            .filter(task -> task.getId().equals(updatedTask.getId()))
                            .findFirst()
                            .ifPresentOrElse(
                                    existingTask -> {
                                        if (updatedTask.getTitle() != null) {
                                            existingTask.setTitle(updatedTask.getTitle());
                                        }
                                        if (updatedTask.getDescription() != null) {
                                            existingTask.setDescription(updatedTask.getDescription());
                                        }
                                        if (updatedTask.getDueDate() != null) {
                                            existingTask.setDueDate(updatedTask.getDueDate());
                                        }
                                        existingTask.setCompleted(updatedTask.getCompleted()); // Assuming isCompleted()
                                    },
                                    () -> {
                                        // Add new Todo
                                        updatedTask.setTodoList(existingTodoList);
                                        existingTaskList.add(updatedTask);
                                    }
                            );
                }
            }
        }
        return todoListMapper.toDto(todosRepository.save(existingTodoList));
    }


    public Boolean removeTodosList(UUID uuid) {
        try {
            todosRepository.deleteById(uuid);
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getLocalizedMessage());
        }
        return todosRepository.findById(uuid).isEmpty();
    }


    public TodoListDto createTodo(UUID uuid, TaskDto taskDto) {
        TodoList todoList = todosRepository.findById(uuid).orElseThrow(() -> new EntityNotFoundException("Todos not found with id: " + uuid));
        Task task = taskMapper.toModel(taskDto);
        task.setTodoList(todoList);

        todoRepo.save(task);

        return todosRepository.findById(uuid).map(todoListMapper::toDto).orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve created Todo"));

    }

    public TodoListDto patchTodosList(UUID uuid, TaskDto taskDto) throws ChangeSetPersister.NotFoundException {
        return todosRepository.findById(uuid).map(todoList -> {
            todoList.getTaskList().replaceAll(taskFromTodos -> {
                if (Objects.equals(taskFromTodos.getId(), taskDto.getId())) {
                    BeanUtils.copyProperties(taskDto, taskFromTodos, "id");
                    return taskFromTodos;
                } else {
                    return taskFromTodos;
                }
            });
            return todoListMapper.toDto(todoList);
        }).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }


}
