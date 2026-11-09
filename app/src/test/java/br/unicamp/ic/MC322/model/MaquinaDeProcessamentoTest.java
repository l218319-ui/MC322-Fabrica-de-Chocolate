package br.unicamp.ic.MC322.model;

import br.unicamp.ic.MC322.exception.DemandaInvalidaException;
import br.unicamp.ic.MC322.exception.MaquinaIndisponivelException;
import br.unicamp.ic.MC322.service.Cenario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários avaliando a composição estrita e comportamento da Máquina de Processamento.
 */
public class MaquinaDeProcessamentoTest {

    private MaquinaDeProcessamento maquina;
    private Produto produto;

    @BeforeEach
    void setUp() {
        maquina = new MaquinaDeProcessamento("Batedeira Teste", 5.0f, 500.0, 2);
        produto = new ChocolateMeiaBoca(1, "Ovo de Páscoa", "MQ-01");
    }

    @Test
    @DisplayName("Deve inicializar as partes compostas (Motor, Bico, Braços) integradamente")
    void testInicializacaoComposta() {
        assertNotNull(maquina.getMotor());
        assertNotNull(maquina.getBicoInjetor());
        assertEquals(2, maquina.getBracosBatedores().size());
    }

    @Test
    @DisplayName("Deve lançar MaquinaIndisponivelException ao tentar processar com a máquina desligada")
    void testProcessarDesligada() {
        assertFalse(maquina.estaLigada());
        assertThrows(MaquinaIndisponivelException.class, () -> maquina.processar(produto, Cenario.IDEAL));
    }

    @Test
    @DisplayName("Deve lançar DemandaInvalidaException ao tentar processar produto nulo")
    void testProcessarProdutoNulo() {
        maquina.ligar();
        assertThrows(DemandaInvalidaException.class, () -> maquina.processar(null, Cenario.IDEAL));
    }

    @Test
    @DisplayName("Deve executar o reparo em cascata restaurando a saúde do Todo e de todas as Partes")
    void testReparoEmCascata() {
        maquina.setSaude(10);
        maquina.getMotor().setSaude(15);
        maquina.getBicoInjetor().setSaude(15);
        maquina.getBracosBatedores().get(0).setSaude(10);

        assertTrue(maquina.precisaManutencao());

        maquina.reparar();

        assertEquals(100, maquina.getSaude());
        assertEquals(100, maquina.getMotor().getSaude());
        assertEquals(100, maquina.getBicoInjetor().getSaude());
        assertEquals(100, maquina.getBracosBatedores().get(0).getSaude());
        assertFalse(maquina.precisaManutencao());
    }
}
