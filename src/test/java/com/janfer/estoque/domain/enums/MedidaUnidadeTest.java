package com.janfer.estoque.domain.enums;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ContextConfiguration
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class MedidaUnidadeTest {

    @Test
    public void testToEnumValid() {
        // Teste de conversão para enumeração válida
        assertEquals(MedidaUnidade.UNIDADE, MedidaUnidade.toEnum(0));
        assertEquals(MedidaUnidade.PACOTE, MedidaUnidade.toEnum(1));
        assertEquals(MedidaUnidade.KILO, MedidaUnidade.toEnum(2));
    }


    @Test
    public void testToEnumInvalid() {
        // Teste de conversão para enumeração inválida (código inexistente)
        assertThrows(IllegalArgumentException.class, () -> {
            MedidaUnidade.toEnum(99);
        });
    }

    @Test
    public void testToEnumNull() {
        // Teste de conversão com código nulo
        assertNull(Resuprimento.toEnum(null));
    }
}
