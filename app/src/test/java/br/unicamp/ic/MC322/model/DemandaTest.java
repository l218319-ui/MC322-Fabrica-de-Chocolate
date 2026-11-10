package br.unicamp.ic.MC322.model;

import br.unicamp.ic.MC322.exception.DemandaInvalidaException;
import br.unicamp.ic.MC322.exception.EstadoInvalidoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe Demanda e transições de StatusDemanda.
 */
public class DemandaTest {

    private Produto produto;
    private Demanda demanda;

    @BeforeEach
    void setUp() {
        produto = new ChocolatePremium(1, "Barra Premium", "AQ-01");
        demanda = new Demanda(produto, 50);
    }

    @Test
    @DisplayName("Deve inicializar a demanda no status PENDENTE")
    void testInicializacaoStatus() {
        assertEquals(StatusDemanda.PENDENTE, demanda.getStatus());
        assertFalse(demanda.isAtendida());
    }

    @Test
    @DisplayName("Deve lançar DemandaInvalidaException para produto nulo ou quantidade <= 0")
    void testValidaEntradasInvalidas() {
        assertThrows(DemandaInvalidaException.class, () -> new Demanda(null, 10));
        assertThrows(DemandaInvalidaException.class, () -> new Demanda(produto, 0));
        assertThrows(DemandaInvalidaException.class, () -> new Demanda(produto, -5));
    }

    @Test
    @DisplayName("Deve alterar status e concluir demanda com sucesso")
    void testAtenderDemanda() {
        demanda.setStatus(StatusDemanda.PRODUZINDO);
        assertEquals(StatusDemanda.PRODUZINDO, demanda.getStatus());

        demanda.atender();
        assertEquals(StatusDemanda.CONCLUIDA, demanda.getStatus());
        assertTrue(demanda.isAtendida());
    }

    @Test
    @DisplayName("Deve proibir alteração para CONCLUIDA quando a demanda estiver CANCELADA")
    void testTransicaoInvalidaDemandaCancelada() {
        demanda.setStatus(StatusDemanda.CANCELADA);
        
        assertThrows(EstadoInvalidoException.class, () -> demanda.atender());
        assertThrows(EstadoInvalidoException.class, () -> demanda.setStatus(StatusDemanda.CONCLUIDA));
    }
}
