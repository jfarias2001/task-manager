package com.example.demo.service;

import com.example.demo.model.Task;
import com.example.demo.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    TaskRepository repository;

    @InjectMocks
    TaskService service;

    @Test
    void deveCriarTarefaComSucesso() {
        Task toSave = new Task("Titulo válido", "Desc");
        Task saved = new Task("Titulo válido", "Desc");
        // simula id gerado
        saved.setCompleted(false);
        saved.setId(1L);

        when(repository.save(toSave)).thenReturn(saved);

        Task result = service.createTask(toSave);

        assertNotNull(result.getId());
        assertEquals("Titulo válido", result.getTitle());
        verify(repository, times(1)).save(toSave);
    }

    @Test
    void deveLancarExcecaoAoCriarTarefaComTituloVazio() {
        Task semTitulo = new Task("", "Desc");

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.createTask(semTitulo)
        );

        assertTrue(ex.getMessage().toLowerCase().contains("título"));
        verify(repository, never()).save(any());
    }
}
