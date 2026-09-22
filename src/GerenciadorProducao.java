import java.util.ArrayList;

public class GerenciadorProducao {

    private ArrayList<Demanda> demandas; // ArrayList de demandas;
    private ArrayList<Produto> produtosFabricados; // ArrayList do armazém;
    private ArrayList<Maquina> maquinas; // ArrayList de máquinas;
    private MateriaPrima materiaPrima; // Matéria-prima;
    private Esteira esteira; // Esteira de transporte;
    private double orçamento; // Orçamento disponível.
    EstrategiaProducao estrategiaAtual;

    // Construtor:
    public GerenciadorProducao(ArrayList<Demanda> demandas, ArrayList<Produto> produtosFabricados,
            ArrayList<Maquina> maquinas, MateriaPrima materiaPrima, double orçamento, Esteira esteira) {
        this.demandas = demandas;
        this.produtosFabricados = produtosFabricados;
        this.maquinas = maquinas;
        this.materiaPrima = materiaPrima;
        this.orçamento = orçamento;
        this.esteira = esteira;
    }

    // Métodos:

    public void registrarDemanda(Demanda demanda) {
        demandas.add(demanda);
    }

    public void atualizarDemanda(int indice, long quantidade) {
        if (indice >= 0 && indice < demandas.size()) {// verifica os indices
            demandas.get(indice).atualizarQuantidade(quantidade);
            System.out.println("[OK]  Demanda atualizada com sucesso!");
        } else {
            System.out.println("[ERRO]  Não foi possível atualizar a demanda!");
        }

    }

    public void fabricarDemanda(int indice) {
        if (indice < 0 || indice >= demandas.size()) {// verifica se o indice é válido
            System.out.println("[ERRO] Índice de demanda inválido!");
            return;
        }
        Demanda demanda = demandas.get(indice);

        long qtd = demanda.getQuantidadeProdutos();

        Produto produtoDem = demanda.getTipoProduto();

        double mpNecessaria = produtoDem.getQuantidadeMateriaPrimaNecessaria() * qtd;

        if (!materiaPrima.verificarDisponibilidade(mpNecessaria) || orçamento < calcularCustoProducao(indice)) {// verifica se tem dindin ou mat. prim. suf.
            System.out.println("[ERRO]  Matéria-prima ou orçamento insuficiente!");
            return;
        }

        this.orçamento -= calcularCustoProducao(indice);// tira do orçamento o custo da prod.
        materiaPrima.consumir(mpNecessaria);// tira da mat.prim. o custo da prod.

        if (esteira != null) {// integração da esteira na produção
            esteira.ligar();
            esteira.adicionarItem(materiaPrima.getNome(), mpNecessaria);
            esteira.removerItem();
            esteira.desligar();
        }
        // processamento:
        for (int i = 1; i <= qtd; i++) {
            for (Maquina m : maquinas) {
                m.ligar();
                m.processar(produtoDem);
                m.desligar();
            }
            produtosFabricados.add(produtoDem);
        }
        if (esteira != null) {// integração da esteira na produção
            esteira.ligar();
            esteira.adicionarItem(produtoDem.getNome(), mpNecessaria);
            esteira.removerItem();
            esteira.desligar();
        }

        demanda.atender();
        System.out.println("[OK]  Fabricação concluída com sucesso!");
    }

    // Compra matéria-prima para a produção:
    public void comprarMateriaPrima(double quantidade) {
        if (this.orçamento < (materiaPrima.getCustoPorLote() * quantidade)) {// se nn tem dinheiro p/ transação
            System.out.println("[ERRO]  Não há dinheiro suficiente para essa transação!");
        } else {
            materiaPrima.adicionarEstoque(quantidade);// add o q foi comprado
            this.orçamento -= materiaPrima.getCustoPorLote() * quantidade;
            System.out.println("[OK]  Compra realizada com sucesso!");
        }
    }

    //Lista todos os produtos acabados em estoque, com quantidade, qualidade e lote:
    public void exibirArmazem() {
        int i;
        System.out.println("==================================================\n" +
                            "       ARMAZÉM DE PRODUTOS FABRICADOS  \n" +
                            "==================================================\n");
        if (produtosFabricados.isEmpty()) {
            System.out.println("[ERRO]  Nenhum produto no estoque!");// erro se nn tem produtos no armazem ainda
            return;
        } else {
            for (i = 0; i < produtosFabricados.size(); i++) {
                System.out.println(
                        "- [" + produtosFabricados.get(i).getNome() + "] ID: " + produtosFabricados.get(i).getId()
                                + " | Qualidade: " + produtosFabricados.get(i).getQualidade());
            }
        }
    }

    // custo total = custo de funcionamento das máquinas + matéria necessaria pra
    // cada demanda * custo da mp
    private double calcularCustoProducao(int indice) { // calcula o custo de produção de uma demanda
        int i;
        double custo = 0.0;
        for (i = 0; i < maquinas.size(); i++) {
            custo += maquinas.get(i).getCustoOperacao();
        }
        custo += demandas.get(indice).calcularMateriaPrimaNecessaria() * materiaPrima.getCustoPorLote();
        return custo;
    }

    // Percorre coleções de objetos que implementam Auditavel e exibe um relatório
    // consolidado da planta:
    public void gerarAuditoriaGeral() {
        System.out.println("==================================================\n" +
                            "              RELATÓRIO DE AUDITORIA             \n" +
                            "==================================================\n");

        System.out.println("--- AUDITORIA DE MÁQUINAS ---");
            for (Maquina m : maquinas) {
                System.out.println(m.gerarRelatorioDiagnostico());
            }

        System.out.println("\n--- AUDITORIA DE PRODUTOS EM ESTOQUE ---");
        if (produtosFabricados.isEmpty()) {
            System.out.println("Nenhum produto em estoque.");
        } else {
            for (Produto p : produtosFabricados) {
                System.out.println(p.gerarRelatorioDiagnostico());
            }
        }
        System.out.println("==================================================");
    }

    // Permite a alteração dinâmica da estratégia em tempo de execução:
    public void setEstrategia(EstrategiaProducao novaEstrategia){

    }

    /* public void executarProximaProducao(){}  //Utiliza estrategiaAtual.selecionarDemanda(...) para identificar a demanda correta e inicia a fabricação;*/

    public double getOrçamento() {
        return this.orçamento;
    }

    public void exibirOrçamento() {
        System.out.println("O orçamento disponível é " + this.orçamento);
    }
}
