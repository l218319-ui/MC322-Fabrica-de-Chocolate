import java.util.List;

//Prioriza a demanda que possui a maior quantidade total de itens a serem fabricados.
public class EstrategiaChocolumosa implements EstrategiaProducao {

    public String getNomeEstrategia() {
        return "Estratégia Volumosa (quanto mais chocolates, melhor!)";
    }

    public Demanda selecionarDemanda(List<Demanda> demandas, double orcamentoDisponivel) {
        Demanda melhorDemanda = null;
        for (int i = 0; i < demandas.size(); i++) {
            Demanda atual = demandas.get(i);
            if (atual.getStatus() == StatusDemanda.PENDENTE) {//verifica a maior demanda entre as pendentes
                if (melhorDemanda == null || atual.getQuantidadeProdutos() > melhorDemanda.getQuantidadeProdutos()) {
                    melhorDemanda = atual;
                }
            }
        }
        return melhorDemanda;
    }
}
