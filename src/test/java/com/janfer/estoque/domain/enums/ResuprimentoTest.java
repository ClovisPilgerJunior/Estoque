package com.janfer.estoque.domain.enums;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration
@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class ResuprimentoTest {

    @Test
    void testToEnumValid() {
        // Teste de conversão para enumeração válida
        assertEquals(Resuprimento.ESTOQUE_NEGATIVO, Resuprimento.toEnum(0));
        assertEquals(Resuprimento.SALDO_ZERADO, Resuprimento.toEnum(1));
        assertEquals(Resuprimento.COMPRAR_AGORA, Resuprimento.toEnum(2));
        assertEquals(Resuprimento.QUANTIDADE_IDEAL, Resuprimento.toEnum(3));
        assertEquals(Resuprimento.PRODUTO_EXCESSO, Resuprimento.toEnum(4));
    }

    @Test
    void testToEnumInvalid() {
        // Teste de conversão para enumeração inválida (código inexistente)
        assertThrows(IllegalArgumentException.class, () -> {
            Resuprimento.toEnum(99);
        });
    }

    @Test
    void testToEnumNull() {
        // Teste de conversão com código nulo
        assertNull(Resuprimento.toEnum(null));
    }
}
