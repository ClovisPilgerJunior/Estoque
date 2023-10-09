package com.janfer.estoque.domain.enums;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TipoProdutoTest {

    @Test
    public void testToEnumValid() {
        // Teste de conversão para enumeração válida
        assertEquals(TipoProduto.MATERIA_PRIMA, TipoProduto.toEnum(0));
        assertEquals(TipoProduto.PRODUTO_PRONTO, TipoProduto.toEnum(1));
        assertEquals(TipoProduto.DIVERSOS, TipoProduto.toEnum(2));
    }

    @Test
    public void testToEnumInvalid() {
        // Teste de conversão para enumeração inválida (código inexistente)
        assertThrows(IllegalArgumentException.class, () -> {
            TipoProduto.toEnum(99);
        });
    }

    @Test
    public void testToEnumNull() {
        // Teste de conversão para enumeração com código nulo
        assertNull(TipoProduto.toEnum(null));
    }
}
