package com.storage.stockflow.domain.enums;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ContextConfiguration
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class SetorTest {

    @Test
    public void testToEnumValid() {
        // Teste de conversão para enumeração válida
        assertEquals(Setor.MARKETING, Setor.toEnum(0));
        assertEquals(Setor.ESCRITORIO, Setor.toEnum(1));
        assertEquals(Setor.USO_INTERNO, Setor.toEnum(2));
        assertEquals(Setor.SERVICOS_GERAIS, Setor.toEnum(3));
    }

    @Test
    public void testToEnumInvalid() {
        // Teste de conversão para enumeração inválida (código inexistente)
        assertThrows(IllegalArgumentException.class, () -> {
            Setor.toEnum(99);
        });
    }
}
