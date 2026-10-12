import java.util.List;

/**
 * Estratégia voltada à redução de custos e conservação de recursos em cenários econômicos adversos.
 * Regra de Negócio: Seleciona a demanda pendente que exige o menor volume de produtos
 * a serem fabricados, visando mitigar consumo excessivo de insumos.
 * 
 * @author Laura Póvoas
 */
public class EstratégiaDeCrise implements EstrategiaProducao {

    /**
     * Retorna o nome da estratégia focada em tempos de crise.
     *
     * @return Nome descritivo da estratégia.
     */
    @Override
    public String getNomeEstrategia() {
        return "Estratégia da crise econômica (use apenas se estiver bem pobre)";
    }

    /**
     * Seleciona a demanda pendente que possui a menor quantidade requerida de produtos.
     *
     * @param demandas Lista de demandas disponíveis para avaliação.
     * @param orcamentoDisponivel Valor financeiro disponível.
     * @return A {@link Demanda} pendente de menor volume, ou {@code null} se não houver solicitações pendentes.
     * @throws DemandaInvalidaException se a lista de demandas fornecida for nula.
     */
    @Override
    public Demanda selecionarDemanda(List<Demanda> demandas, double orcamentoDisponivel) {
        if (demandas == null) {
            throw new DemandaInvalidaException("A lista de demandas não pode ser nula.");
        }
        Demanda menorDemanda = null;
        for (int i = 0; i < demandas.size(); i++) {
            Demanda atual = demandas.get(i);
            if (atual != null && atual.getStatus() == StatusDemanda.PENDENTE) {
                if (menorDemanda == null || atual.getQuantidadeProdutos() < menorDemanda.getQuantidadeProdutos()) {
                    menorDemanda = atual;
                }
            }
        }
        return menorDemanda;
    }
}