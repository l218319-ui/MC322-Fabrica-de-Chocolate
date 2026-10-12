import java.util.List;

/**
 * Estratégia de seleção de produção baseada em ordem de chegada (FIFO - First-In, First-Out).
 * Regra de Negócio: Seleciona a primeira demanda cadastrada que possua status {@link StatusDemanda#PENDENTE}.
 * 
 * @author Laura Póvoas
 */
public class EstrategiaChocolento implements EstrategiaProducao {

    /**
     * Retorna o nome da estratégia de produção em ordem de fila.
     *
     * @return Nome descritivo da estratégia.
     */
    @Override
    public String getNomeEstrategia() {
        return "Estratégia do Chocolate lento (fila de chocolates)";
    }

    /**
     * Seleciona a primeira demanda pendente na ordem cronológica em que foi adicionada à lista.
     *
     * @param demandas Lista de demandas registradas na fábrica.
     * @param orcamentoDisponivel Valor do orçamento corrente disponível para produção.
     * @return A primeira {@link Demanda} com status {@code PENDENTE}, ou {@code null} caso não haja nenhuma.
     * @throws DemandaInvalidaException se a lista de demandas fornecida for nula.
     */
    @Override
    public Demanda selecionarDemanda(List<Demanda> demandas, double orcamentoDisponivel) {
        if (demandas == null) {
            throw new DemandaInvalidaException("A lista de demandas não pode ser nula.");
        }
        for (int i = 0; i < demandas.size(); i++) {
            if (demandas.get(i) != null && demandas.get(i).getStatus() == StatusDemanda.PENDENTE) {
                return demandas.get(i);
            }
        }
        return null;
    }
}