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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodosService {

    private final TodosRepository todosRepo;
    private final TodoRepository todoRepo;
    private final TodosRepository todosRepository;
    private final CompanyRepository companyRepository;
    private final TodosMapper todosMapper;
    private final TodoMapper todoMapper;


    public List<TodosDto> getAllTodosLists() {
        return todosRepository.findAll()
                .stream()
                .map(todosMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<TodosDto> getTodosListsByTitle(String searchQuery) {
        return todosRepository.findByTitleContainsIgnoreCaseOrderByCreatedAt(searchQuery).stream()
                .map(todosMapper::toDto)
                .collect(Collectors.toList());

    }

    public TodosDto getATodos(String uuid) {
        return todosRepository.findById(UUID.fromString(uuid))
                .stream()
                .map(todosMapper::toDto)
                .toList().getFirst();
    }

    public TodosDto createTodosList(CreateTodoListDto createTodoListDto) {

//        Todos todos = Todos.builder()
//                .createdAt(LocalDate.now()) // Setting createdAt
//                .todos(new ArrayList<Todo>()) // Initializing with an empty list of TodoDto
//                .uuid(UUID.randomUUID()) // Setting UUID
//                .title(createTodoListDto.getTitle()) // Setting title from DTO
//                .description(createTodoListDto.getDescription()) // Setting description from DTO
//                .build();

        Todos todos = new Todos(UUID.randomUUID(), createTodoListDto.getTitle(), createTodoListDto.getDescription(), new ArrayList<Todo>(), LocalDate.now(), null);

        log.debug("todos {}", todos);

        Todos save = todosRepository.save(todos);
        log.debug("save {}", save);
        return todosMapper.toDto(save);
    }

    @Transactional
    public TodosDto updateTodosList(TodosDto updatedTodos) {
        UUID uuid = updatedTodos.getUuid();
        Todos existingTodos = todosRepository.findById(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Todos not found with UUID: " + uuid));

        existingTodos.setTitle(updatedTodos.getTitle());
        existingTodos.setDescription(updatedTodos.getDescription());

        existingTodos.getTodoList().clear();

        if (updatedTodos.getTodoList() != null) {
            List<Todo> updatedTodoList = updatedTodos.getTodoList().stream()
                    .map(todoMapper::toEntity)
                    .peek(todo -> todo.setTodoList(existingTodos))
                    .collect(Collectors.toList());

            existingTodos.setTodoList(updatedTodoList);
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

    public TodosDto patchTodosList(UUID uuid, TodoDto todoDto) throws ChangeSetPersister.NotFoundException {
        return todosRepository.findById(uuid)
                .map(todos -> {
                    todos.getTodoList().replaceAll(todoFromTodos -> {
                        if (Objects.equals(todoFromTodos.getId(), todoDto.getId())) {
                            BeanUtils.copyProperties(todoDto, todoFromTodos, "id");
                            return todoFromTodos;
                        } else {
                            return todoFromTodos;
                        }
                    });
                    return todosMapper.toDto(todos);
                })
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
    }


}
