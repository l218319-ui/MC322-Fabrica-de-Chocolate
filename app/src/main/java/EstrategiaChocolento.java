import java.util.List;

// Seleciona a primeira demanda cadastrada com status PENDENTE (Fila First-In, First-Out).
public class EstrategiaChocolento implements EstrategiaProducao {

    public String getNomeEstrategia() {
        return "Estratégia do Chocolate lento (fila de chocolates)";
    }

    public Demanda selecionarDemanda(List<Demanda> demandas, double orcamentoDisponivel) {
        int i;
        for (i = 0; i < demandas.size(); i++) {
            if (demandas.get(i).getStatus() == StatusDemanda.PENDENTE) {
                return demandas.get(i); //retorna a primeira pendente
            }
        }
        return null;
    }
}
