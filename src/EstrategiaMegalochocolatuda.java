import java.util.List;

/*Examina o orçamento disponível e seleciona a demanda viável que
maximiza o número total de produtos finais produzidos sem estourar o budget*/
public class EstrategiaMegalochocolatuda implements EstrategiaProducao {

    public String getNomeEstrategia() {
        return "Estratégia Megalomaníaca (cê tem mania de grandeza, né?)";
    }

    public Demanda selecionarDemanda(List<Demanda> demandas, double orcamentoDisponivel) {
        int i;
        long maxProdutos = -1;
        Demanda melhorDemanda = null;
        for (i = 0; i < demandas.size(); i++) {
            if (demandas.get(i).getStatus() == StatusDemanda.PENDENTE) {
                double custo = demandas.get(i).custoDem();
                if (custo < orcamentoDisponivel) { //verifica se cabe no orçamento
                    if (demandas.get(i).getQuantidadeProdutos() > maxProdutos) {//verifica qual demanda 
                        maxProdutos = demandas.get(i).getQuantidadeProdutos(); //possui mais produtos
                        melhorDemanda = demandas.get(i);
                    }
                }
                
            }
        }
        return melhorDemanda;
    }

}
