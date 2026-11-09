package br.unicamp.ic.MC322.persistence;

import br.unicamp.ic.MC322.exception.PersistenciaException;
import br.unicamp.ic.MC322.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Suíte de testes automatizados para a persistência em XML.
 * Utiliza diretório temporário para isolar as verificações do ambiente real.
 */
public class PersistenciaPlantaTest {

    @TempDir
    Path tempDir;

    private File arquivoXml;
    private PersistenciaPlanta persistencia;
    private Planta plantaOriginal;

    @BeforeEach
    void setUp() {
        arquivoXml = tempDir.resolve("teste_planta.xml").toFile();
        persistencia = new PersistenciaPlanta(arquivoXml.getAbsolutePath());

        plantaOriginal = new Planta();
        plantaOriginal.setOrcamento(12500.0);

        MateriaPrima mp = new MateriaPrima(1, "Manteiga de Cacau", 300.0, "kg", 15.0);
        plantaOriginal.adicionarMateriaPrima(mp);

        MaquinaDeProcessamento maq = new MaquinaDeProcessamento("Misturador de Teste", 10.0f, 600.0, 2);
        maq.ligar();
        plantaOriginal.adicionarMaquina(maq);

        Produto prod = new ChocolatePremium(1, "Barra Premium", "LOTE-T1");
        Demanda dem = new Demanda(prod, 25);
        dem.setStatus(StatusDemanda.PRODUZINDO);
        plantaOriginal.adicionarDemanda(dem);
    }

    @Test
    @DisplayName("Deve salvar o estado da planta e recarregá-lo preservando todas as informações")
    void testSalvarECarregarComSucesso() {
        persistencia.salvar(plantaOriginal, arquivoXml.getAbsolutePath());
        assertTrue(arquivoXml.exists() && arquivoXml.length() > 0, "O arquivo XML deve ser gerado no disco.");

        Planta plantaRecuperada = persistencia.carregar(arquivoXml.getAbsolutePath());

        assertNotNull(plantaRecuperada);
        assertEquals(12500.0, plantaRecuperada.getOrcamento(), 0.001);
        assertEquals(1, plantaRecuperada.getMateriasPrimas().size());
        assertEquals("Manteiga de Cacau", plantaRecuperada.getMateriasPrimas().get(0).getNome());

        assertEquals(1, plantaRecuperada.getMaquinas().size());
        assertTrue(plantaRecuperada.getMaquinas().get(0).estaLigada());

        assertEquals(1, plantaRecuperada.getDemandas().size());
        assertEquals(StatusDemanda.PRODUZINDO, plantaRecuperada.getDemandas().get(0).getStatus());
    }

    @Test
    @DisplayName("Deve lançar PersistenciaException ao tentar carregar um arquivo inexistente")
    void testCarregarArquivoInexistente() {
        File arquivoInexistente = tempDir.resolve("arquivo_que_nao_existe.xml").toFile();
        assertThrows(PersistenciaException.class, () -> persistencia.carregar(arquivoInexistente.getAbsolutePath()));
    }

    @Test
    @DisplayName("Deve lançar PersistenciaException ao tentar carregar um arquivo completamente vazio")
    void testCarregarArquivoVazio() throws IOException {
        File arquivoVazio = tempDir.resolve("vazio.xml").toFile();
        assertTrue(arquivoVazio.createNewFile());

        assertThrows(PersistenciaException.class, () -> persistencia.carregar(arquivoVazio.getAbsolutePath()));
    }

    @Test
    @DisplayName("Deve lançar PersistenciaException ao tentar ler arquivo XML corrompido")
    void testCarregarArquivoCorrompido() throws IOException {
        File arquivoCorrompido = tempDir.resolve("corrompido.xml").toFile();
        try (FileWriter writer = new FileWriter(arquivoCorrompido)) {
            writer.write("<planta><dadosIncompletos>sem_fechar");
        }

        assertThrows(PersistenciaException.class, () -> persistencia.carregar(arquivoCorrompido.getAbsolutePath()));
    }

    @Test
    @DisplayName("Deve lançar PersistenciaException se os dados salvos violarem regras de negócio")
    void testValidacaoDadosInvalidos() throws IOException {
        File xmlInvalido = tempDir.resolve("invalido.xml").toFile();
        try (FileWriter writer = new FileWriter(xmlInvalido)) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                         "<planta>" +
                         "  <orcamento>-500.0</orcamento>" + // Orçamento inválido (negativo)
                         "</planta>");
        }

        assertThrows(PersistenciaException.class, () -> persistencia.carregar(xmlInvalido.getAbsolutePath()));
    }
}