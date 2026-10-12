import java.util.List;

/**
 * Estratégia de priorização voltada a altos volumes de produção.
 * Regra de Negócio: Percorre todas as solicitações e seleciona a demanda pendente com a
 * maior quantidade total de itens/produtos a serem fabricados.
 * 
 * @author Laura Póvoas
 */
public class EstrategiaChocolumosa implements EstrategiaProducao {

    /**
     * Retorna o nome da estratégia volumosa.
     *
     * @return Nome descritivo da estratégia.
     */
    @Override
    public String getNomeEstrategia() {
        return "Estratégia Volumosa (quanto mais chocolates, melhor!)";
    }

    /**
     * Busca e seleciona a demanda com a maior quantidade de produtos pendentes de fabricação.
     *
     * @param demandas Lista de demandas registradas na fábrica.
     * @param orcamentoDisponivel Valor do orçamento restante para uso na produção.
     * @return A {@link Demanda} pendente de maior volume, ou {@code null} caso nenhuma esteja pendente.
     * @throws DemandaInvalidaException se a lista de demandas fornecida for nula.
     */
    @Override
    public Demanda selecionarDemanda(List<Demanda> demandas, double orcamentoDisponivel) {
        if (demandas == null) {
            throw new DemandaInvalidaException("A lista de demandas não pode ser nula.");
        }
        Demanda melhorDemanda = null;
        for (int i = 0; i < demandas.size(); i++) {
            Demanda atual = demandas.get(i);
            if (atual != null && atual.getStatus() == StatusDemanda.PENDENTE) {
                if (melhorDemanda == null || atual.getQuantidadeProdutos() > melhorDemanda.getQuantidadeProdutos()) {
                    melhorDemanda = atual;
                }
            }
        }
        return melhorDemanda;
    }
}