import java.util.List;

//estratégia de tempos de crise, seleciona a demanda de menor volume:
public class EstratégiaDeCrise implements EstrategiaProducao {

    public String getNomeEstrategia() {
        return "Estratégia da crise econômica (use apenas se estiver bem pobre)";
    }

    public Demanda selecionarDemanda(List<Demanda> demandas, double orcamentoDisponivel) {
        Demanda menorDemanda = null;
        for (int i = 0; i < demandas.size(); i++) {
            Demanda atual = demandas.get(i);
            if (atual.getStatus() == StatusDemanda.PENDENTE) {//verifica a menor demanda entre as pendentes em qnt de prodts.
                if (menorDemanda == null || atual.getQuantidadeProdutos() < menorDemanda.getQuantidadeProdutos()) {
                    menorDemanda = atual;
                }
            }
        }
        return menorDemanda;
    }
}
