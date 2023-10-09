package com.janfer.estoque.domain.enums;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TipoEmpresaTest {

    @Test
    public void testToEnumValid() {
        // Teste de conversão para enumeração válida
        assertEquals(TipoEmpresa.FORNECEDOR, TipoEmpresa.toEnum(0));
        assertEquals(TipoEmpresa.CLIENTE, TipoEmpresa.toEnum(1));
    }

    @Test
    public void testToEnumInvalid() {
        // Teste de conversão para enumeração inválida (código inexistente)
        assertThrows(IllegalArgumentException.class, () -> {
            TipoEmpresa.toEnum(99);
        });
    }

    @Test
    public void testToEnumNull() {
        // Teste de conversão para enumeração com código nulo
        assertNull(TipoEmpresa.toEnum(null));
    }
}
