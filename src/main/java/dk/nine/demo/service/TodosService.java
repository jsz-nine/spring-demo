package dk.nine.demo.service;

import dk.nine.demo.dto.lomboks.todo.TodoDto;
import dk.nine.demo.dto.lomboks.todo.TodosDto;
import dk.nine.demo.model.Todos;
import dk.nine.demo.repository.CompanyRepository;
import dk.nine.demo.repository.todos.TodoRepository;
import dk.nine.demo.repository.todos.TodosRepository;
import dk.nine.demo.view.TodoMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TodosService {

    private final TodosRepository todosRepo;
    private final TodoRepository todoRepo;
    private final TodosRepository todosRepository;
    private final CompanyRepository companyRepository;
    private final TodoMapper todoMapper;

    @Autowired
    public TodosService(TodosRepository todosRepo, TodoRepository todoRepo, TodosRepository todosRepository, CompanyRepository companyRepository, TodoMapper todoMapper) {
        this.todosRepo = todosRepo;
        this.todoRepo = todoRepo;
        this.todosRepository = todosRepository;
        this.companyRepository = companyRepository;
        this.todoMapper = todoMapper;
    }


    public List<TodosDto> getAllTodosLists() {
        return todosRepository.findAll()
                .stream()
                .map(todoMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<TodosDto> getTodosListsByTitle(String searchQuery) {
        return todosRepository.findByTitleContainsIgnoreCaseOrderByCreatedAt(searchQuery).stream()
                .map(todoMapper::toDto)
                .collect(Collectors.toList());

    }

    public TodosDto getATodos(String uuid) {
        return todosRepository.findById(UUID.fromString(uuid))
                .stream()
                .map(todoMapper::toDto)
                .toList().getFirst();
    }

    public TodosDto createTodosList(TodosDto todosDto) {
        log.debug("created TodosDto {} ", todosDto.toString());
        Todos createdTodos = todosRepository.save(todoMapper.toEntity(todosDto));

    }

    @Transactional
    public TodosDto updateTodosList(TodosDto updatedTodos) {
//        return mapperUtil.map(todosRepository.updateByUuidMatches(todosDto.uuid(), mapperUtil.map(todosDto, Todos.class)), TodosDto.class);
        Optional<Todos> existingTodos = todosRepository.findById(updatedTodos.uuid());
        if (existingTodos.isPresent()) {
            Todos todosToUpdate = existingTodos.get();
            todosToUpdate.setTitle(updatedTodos.title());
            todosToUpdate.setCompletedAt(updatedTodos.completedAt());
            todosToUpdate.setDescription(updatedTodos.description());
            todosRepository.deleteById(existingTodos.get().getUuid());
            return mapperUtil.map(todosRepository.save(todosToUpdate), TodosDto.class);
        } else {
            // Handle the case where no Todo with the given UUID is found
            throw new IllegalArgumentException("Todo not found with UUID: " + updatedTodos.uuid().toString());
        }

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
                    todos.getTodos().replaceAll(todoFromTodos -> {
                        if (Objects.equals(todoFromTodos.getId(), todoDto.getId())) {
                            BeanUtils.copyProperties(todoDto, todoFromTodos, "id");
                            return todoFromTodos;
                        } else {
                            return todoFromTodos;
                        }
                    });
                    return mapperUtil.map(todos, TodosDto.class);
                })
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
    }


}
