import java.util.ArrayList;

public class GerenciadorProducao {

    private ArrayList<Demanda> demandas; // ArrayList de demandas;
    private ArrayList<Produto> produtosFabricados; // ArrayList do armazém;
    private ArrayList<Maquina> maquinas; // ArrayList de máquinas;
    private MateriaPrima materiaPrima; // Matéria-prima;
    private double orçamento; // Orçamento disponível.

    // Construtor:
    public GerenciadorProducao(ArrayList<Demanda> demandas, ArrayList<Produto> produtosFabricados,
            ArrayList<Maquina> maquinas, MateriaPrima materiaPrima, double orçamento) {
        this.demandas = demandas;
        this.produtosFabricados = produtosFabricados;
        this.maquinas = maquinas;
        this.materiaPrima = materiaPrima;
        this.orçamento = orçamento;
    }

    // Métodos:
    public void registrarDemanda(Demanda demanda) {
        demandas.add(demanda);
    }

    public void atualizarDemanda(int indice, long quantidade) {
        if (indice >= 0 && indice < demandas.size()) {//verifica os indices
            demandas.get(indice).atualizarQuantidade(quantidade);
            System.out.println("Demanda atualizada com sucesso!");
        } else {
            System.out.println("[ERRO]  Não foi possível atualizar a demanda!");
        }

    }

    //falta essa merda aqui 
    public void fabricarDemanda(int indice) {
        if (indice < 0 || indice >= demandas.size()) {//verifica se o indice é válido 
            System.out.println("[ERRO] Índice de demanda inválido!");
            return;
        }
    }

    public void comprarMateriaPrima(double quantidade){
        if(this.orçamento < (materiaPrima.getCustoPorLote()*quantidade)){//se nn tem dinheiro p/ transação
            System.out.println("[ERRO]  Não há dinheiro suficiente para essa transação!");
        } else {
            materiaPrima.adicionarEstoque(quantidade);
            System.out.println("Compra realizada com sucesso!");
        }
    }

    public void exibirArmazem() {
        int i;
        System.out.println("------- ARMAZÉM DE PRODUTOS FABRICADOS -------");
        if (produtosFabricados.isEmpty()) {
            System.out.println("[ERRO]  Nenhum produto no estoque!");
            return;
        } else {
            for (i = 0; i < produtosFabricados.size(); i++) {
                System.out.println("- [" + produtosFabricados.get(i).getNome() + "] ID: " + produtosFabricados.get(i).getId() + " | Qualidade: " + produtosFabricados.get(i).getQualidade()
                        + " | Status: " + produtosFabricados.get(i).getStatus());
            }
        }
    }

    // custo total = custo de funcionamento das máquinas + matéria necessaria pra
    // cada demanda * custo da mp
    private double calcularCustoProducao(Produto produto, Demanda demanda) { // calcula o custo de produção de uma demanda
        int i;
        double custo = 0.0;
        for (i = 0; i < maquinas.size(); i++) {
            custo += maquinas.get(i).getCustoOperacao();
        }
        custo += produto.getQuantidadeMateriaPrimaNecessaria() * demanda.getQuantidadeProdutos() * materiaPrima.getCustoPorLote();
        return custo;
    }

    public double getOrçamento() {
        return this.orçamento;
    }

    public void exibirOrçamento(){
        System.out.println("O orçamento disponível é " + this.orçamento);
    }
}
