import java.util.List;

/**
 * Estratégia de seleção de produção que maximiza a quantidade de produtos fabricados.
 * Regra de Negócio: Examina o orçamento disponível e seleciona a demanda viável (pendente e cujo
 * custo de produção não excede o orçamento) que produz o maior número total de itens.
 * 
 * @author Laura Póvoas
 */
public class EstrategiaMegalochocolatuda implements EstrategiaProducao {

    /**
     * Retorna o nome da estratégia megalomaníaca.
     *
     * @return Nome descritivo da estratégia.
     */
    @Override
    public String getNomeEstrategia() {
        return "Estratégia Megalomaníaca (cê tem mania de grandeza, né?)";
    }

    /**
     * Examina a lista de demandas e seleciona a demanda pendente e dentro do orçamento
     * que possui a maior quantidade total de produtos.
     *
     * @param demandas Lista de demandas registradas na fábrica.
     * @param orcamentoDisponivel Valor financeiro atual disponível para produção.
     * @return A {@link Demanda} que maximiza os produtos dentro do orçamento, ou {@code null} se nenhuma for viável.
     * @throws DemandaInvalidaException se a lista de demandas fornecida for nula.
     */
    @Override
    public Demanda selecionarDemanda(List<Demanda> demandas, double orcamentoDisponivel) {
        if (demandas == null) {
            throw new DemandaInvalidaException("A lista de demandas não pode ser nula.");
        }
        long maxProdutos = -1;
        Demanda melhorDemanda = null;
        for (int i = 0; i < demandas.size(); i++) {
            Demanda atual = demandas.get(i);
            if (atual != null && atual.getStatus() == StatusDemanda.PENDENTE) {
                double custo = atual.custoDem();
                if (custo <= orcamentoDisponivel) { // verifica se cabe no orçamento
                    if (atual.getQuantidadeProdutos() > maxProdutos) { // verifica qual demanda possui mais produtos
                        maxProdutos = atual.getQuantidadeProdutos();
                        melhorDemanda = atual;
                    }
                }
            }
        }
        return melhorDemanda;
    }
}