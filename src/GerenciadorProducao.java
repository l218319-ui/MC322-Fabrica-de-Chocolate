import java.util.ArrayList;

//obs:verificar arraylist.
public class GerenciadorProducao {
    private ArrayList demandas; // ArrayList de demandas;
    private ArrayList produtosFabricados; // ArrayList do armazém;
    private ArrayList maquinas; // ArrayList de máquinas;
    private MateriaPrima materiaPrima; // Matéria-prima;
    private long budget; // Orçamento disponível.

    public GerenciadorProducao(ArrayList demandas, ArrayList produtosFabricados, ArrayList maquinas,
            MateriaPrima materiaPrima, long budget) {
        this.demandas = demandas;
        this.produtosFabricados = produtosFabricados;
        this.maquinas = maquinas;
        this.materiaPrima = materiaPrima;
        this.budget = budget;
    }

    // Métodos:
    // registrarDemanda, atualizarDemanda;
    // fabricarDemanda;
    // comprarMateriaPrima;
    //exibirArmazem;
    

    //private long calcularCustoProducao() {}

    public long getBudget() {
        System.out.println("O orçamento disponível é "+this.budget);
        return this.budget;
    }

}
