import java.util.List;

/**
 * Interface que define o contrato para estratégias de seleção de demandas de produção.
 * Permite a implementação do padrão de projeto Strategy para alternar dinamicamente
 * a lógica de escolha da próxima demanda a ser fabricada.
 * 
 * @author Laura Póvoas
 */
public interface EstrategiaProducao {

    /**
     * Examina a lista de demandas disponíveis e seleciona a melhor demanda a ser
     * processada de acordo com o critério da estratégia e o orçamento disponível.
     *
     * @param demandas Lista com as demandas cadastradas na planta.
     * @param orcamentoDisponivel Orçamento financeiro atual da fábrica.
     * @return A {@link Demanda} elegível selecionada, ou {@code null} caso nenhuma atenda aos critérios.
     * @throws DemandaInvalidaException se a lista de demandas for nula ou inválida.
     */
    Demanda selecionarDemanda(List<Demanda> demandas, double orcamentoDisponivel);

    /**
     * Retorna o nome da estratégia para exibição na interface do usuário.
     *
     * @return {@link String} com o nome/descrição da estratégia.
     */
    String getNomeEstrategia();
}