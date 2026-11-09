package br.unicamp.ic.MC322.model;

import br.unicamp.ic.MC322.exception.PlantaException;
import br.unicamp.ic.MC322.exception.RecursoInsuficienteException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a entidade MateriaPrima.
 */
public class MateriaPrimaTest {

    private MateriaPrima materiaPrima;

    @BeforeEach
    void setUp() {
        materiaPrima = new MateriaPrima(1, "Cacau", 100.0, "kg", 10.0);
    }

    @Test
    @DisplayName("Deve criar matéria-prima com atributos válidos")
    void testCriacaoMateriaPrimaValida() {
        assertEquals(1, materiaPrima.getId());
        assertEquals("Cacau", materiaPrima.getNome());
        assertEquals(100.0, materiaPrima.getQuantidade());
        assertEquals("kg", materiaPrima.getUnidade());
        assertEquals(10.0, materiaPrima.getCustoPorLote());
    }

    @Test
    @DisplayName("Deve lançar exceção ao instanciar com quantidade ou custo negativos")
    void testCriacaoMateriaPrimaInvalida() {
        assertThrows(PlantaException.class, () -> 
            new MateriaPrima(2, "Açúcar", -10.0, "kg", 5.0)
        );
        assertThrows(PlantaException.class, () -> 
            new MateriaPrima(3, "Leite", 50.0, "L", -2.0)
        );
    }

    @Test
    @DisplayName("Deve consumir estoque com sucesso quando a quantidade for suficiente")
    void testConsumirEstoqueSuficiente() {
        materiaPrima.consumir(40.0);
        assertEquals(60.0, materiaPrima.getQuantidade());
    }

    @Test
    @DisplayName("Deve lançar RecursoInsuficienteException ao tentar consumir acima do estoque")
    void testConsumirEstoqueInsuficiente() {
        assertThrows(RecursoInsuficienteException.class, () -> materiaPrima.consumir(150.0));
        assertEquals(100.0, materiaPrima.getQuantidade(), "O estoque não deve sofrer alterações após falha no consumo.");
    }

    @Test
    @DisplayName("Deve adicionar novo lote ao estoque existente")
    void testAdicionarEstoqueValido() {
        materiaPrima.adicionarEstoque(50.0);
        assertEquals(150.0, materiaPrima.getQuantidade());
    }

    @Test
    @DisplayName("Deve lançar PlantaException ao adicionar remessa menor ou igual a zero")
    void testAdicionarEstoqueInvalido() {
        assertThrows(PlantaException.class, () -> materiaPrima.adicionarEstoque(0));
        assertThrows(PlantaException.class, () -> materiaPrima.adicionarEstoque(-10));
    }
}