import java.util.ArrayList;

/**
 * Classe responsável por gerenciar a produção, orçamento, estoque, máquinas, esteiras
 * e execução de estratégias na planta industrial.
 * Regras de Negócio e Consistência do Estado:
 *   Verifica previamente a disponibilidade de recursos (matéria-prima e orçamento) antes de iniciar qualquer fabricação ou compra.
 *   Lança exceções de domínio como {@link RecursoInsuficienteException}, {@link DemandaInvalidaException} e {@link EstadoInvalidoException} em caso de falhas operacionais.
 *   Garante a consistência atômica do estado: nenhuma alteração de orçamento ou estoque é realizada se a operação falhar na validação inicial.
 * 
 * @author Laura Póvoas
 */
public class GerenciadorProducao {

    /** Lista de demandas cadastradas. */
    private ArrayList<Demanda> demandas;

    /** Lista de produtos já fabricados armazenados no armazém. */
    private ArrayList<Produto> produtosFabricados;

    /** Lista de máquinas disponíveis na linha de produção. */
    private ArrayList<Maquina> maquinas;

    /** Insumo de matéria-prima utilizado na fabricação. */
    private MateriaPrima materiaPrima;

    /** Esteira transportadora da fábrica. */
    private Esteira esteira;

    /** Montante financeiro atualmente disponível. */
    private double orçamento;

    /** Estratégia de produção selecionada para execução automática. */
    private EstrategiaProducao estrategiaAtual;

    /** Cenário de operação ativo no simulador. */
    private Cenario cenarioAtual;

    /**
     * Constrói e inicializa o gerenciador de produção da fábrica.
     *
     * @param demandas Lista de demandas de produção.
     * @param produtosFabricados Lista inicial do armazém de produtos.
     * @param maquinas Lista de máquinas da linha de montagem.
     * @param materiaPrima Objeto de matéria-prima.
     * @param cenarioAtual Cenário de simulação ativo.
     * @param esteira Esteira transportadora.
     * @throws PlantaException se algum dos parâmetros essenciais for nulo.
     */
    public GerenciadorProducao(ArrayList<Demanda> demandas, ArrayList<Produto> produtosFabricados,
            ArrayList<Maquina> maquinas, MateriaPrima materiaPrima, Cenario cenarioAtual, Esteira esteira) {
        if (demandas == null || produtosFabricados == null || maquinas == null || materiaPrima == null || cenarioAtual == null) {
            throw new PlantaException("Componentes essenciais do Gerenciador de Produção não podem ser nulos.");
        }
        this.demandas = demandas;
        this.produtosFabricados = produtosFabricados;
        this.maquinas = maquinas;
        this.materiaPrima = materiaPrima;
        this.cenarioAtual = cenarioAtual;
        this.orçamento = cenarioAtual.getOrçamentoInicial();
        this.esteira = esteira;
        this.estrategiaAtual = null;
    }

    /**
     * Registra uma nova demanda na lista do gerenciador.
     *
     * @param demanda Objeto {@link Demanda} a ser inserido.
     * @throws DemandaInvalidaException se a demanda fornecida for nula.
     */
    public void registrarDemanda(Demanda demanda) {
        if (demanda == null) {
            throw new DemandaInvalidaException("Não é possível registrar uma demanda nula.");
        }
        demandas.add(demanda);
    }

    /**
     * Atualiza a quantidade requisitada de uma demanda específica pelo seu índice.
     *
     * @param indice Posição da demanda na lista.
     * @param quantidade Nova quantidade desejada.
     * @throws DemandaInvalidaException se o índice for inválido ou a quantidade for menor/igual a zero.
     */
    public void atualizarDemanda(int indice, long quantidade) {
        if (indice < 0 || indice >= demandas.size()) {
            throw new DemandaInvalidaException("Não foi possível atualizar: índice de demanda (" + indice + ") é inválido.");
        }
        demandas.get(indice).atualizarQuantidade(quantidade);
        System.out.println("[OK]  Demanda atualizada com sucesso!");
    }

    /**
     * Fabricação de uma demanda por índice, validando orçamento e estoque antes de alterar o estado.
     *
     * @param indice Posição da demanda na lista.
     * @throws DemandaInvalidaException se o índice for inválido.
     * @throws EstadoInvalidoException se a demanda já estiver concluída ou cancelada.
     * @throws RecursoInsuficienteException se houver falta de orçamento ou matéria-prima no estoque.
     */
    public void fabricarDemanda(int indice) {
        if (indice < 0 || indice >= demandas.size()) {
            throw new DemandaInvalidaException("Índice de demanda inválido: " + indice);
        }
        Demanda demanda = demandas.get(indice);

        if (demanda.isAtendida()) {
            throw new EstadoInvalidoException("A demanda selecionada já se encontra CONCLUÍDA.");
        }
        if (demanda.getStatus() == StatusDemanda.CANCELADA) {
            throw new EstadoInvalidoException("A demanda selecionada está CANCELADA e não pode ser fabricada.");
        }

        long qtd = demanda.getQuantidadeProdutos();
        Produto produtoDem = demanda.getTipoProduto();
        double mpNecessaria = produtoDem.getQuantidadeMateriaPrimaNecessaria() * qtd;
        double custoTotal = calcularCustoProducao(indice);

        // Validação prévia dos recursos (Garantia de consistência do estado - item 3.3.3)
        if (!materiaPrima.verificarDisponibilidade(mpNecessaria)) {
            throw new RecursoInsuficienteException("Matéria-prima insuficiente no estoque! "
                    + "Necessário: " + mpNecessaria + " " + materiaPrima.getUnidade()
                    + ", Disponível: " + materiaPrima.getQuantidade() + " " + materiaPrima.getUnidade());
        }

        if (this.orçamento < custoTotal) {
            throw new RecursoInsuficienteException("Orçamento insuficiente para fabricar o lote! "
                    + "Necessário: R$ " + custoTotal + ", Disponível: R$ " + this.orçamento);
        }

        // Alterações de estado iniciam apenas após todas as validações passarem
        this.orçamento -= custoTotal;
        materiaPrima.consumir(mpNecessaria);

        if (esteira != null) {
            esteira.ligar();
            esteira.adicionarItem(materiaPrima.getNome(), mpNecessaria);
            esteira.removerItem();
            esteira.desligar();
            System.out.println("[OK]  Matérias-primas adicionadas às máquinas.");
        }

        // Processamento individual das unidades
        for (int i = 1; i <= qtd; i++) {
            Produto unidade = produtoDem.clonar();
            produtoDem.resetarProbabilidadeFalha();
            for (Maquina m : maquinas) {
                m.ligar();
                m.processar(unidade, this.cenarioAtual);
                m.desligar();
            }
            produtosFabricados.add(unidade);
        }

        if (esteira != null) {
            esteira.ligar();
            esteira.adicionarItem(produtoDem.getNome(), mpNecessaria);
            esteira.removerItem();
            esteira.desligar();
            System.out.println("[OK]  Produtos adicionados ao Armazém.");
        }

        demanda.atender();
        System.out.println("===========================================================\n" +
                           "|           ~ PRODUÇÃO CONCLUÍDA COM SUCESSO! :)          |\n" +
                           "===========================================================\n" +
                           "••••••••••••••••••••••█████████•••••••••\n" + 
                           "••███████••••••••••███........███•••••••\n" + 
                           "••█......█•••••••███.............███••••\n" + 
                           "•••█......█••••██...................██••\n" + 
                           "••••█.....█•••██.....██......██.....███•\n" + 
                           "•••••█...█•••█......████....████......██\n" + 
                           "•••█████████████......................██\n" + 
                           "•••█............█.........█...........██\n" + 
                           "•██.............█...██..........██....██\n" + 
                           "██...███████████.....██........██.....██\n" + 
                           "█...............█......████████.......██\n" + 
                           "██..............█....................██•\n" + 
                           "•█...███████████...................██•••\n" + 
                           "•██..........████.................█•••••\n" + 
                           "••████████████•••█████████████████••••••\n");
    }

    /**
     * Realiza a compra de lotes adicionais de matéria-prima.
     *
     * @param quantidade Quantidade de lotes a serem adquiridos.
     * @throws PlantaException se a quantidade for menor ou igual a zero.
     * @throws RecursoInsuficienteException se o orçamento não for suficiente para a transação.
     */
    public void comprarMateriaPrima(double quantidade) {
        if (quantidade <= 0) {
            throw new PlantaException("A quantidade de matéria-prima para compra deve ser maior que zero.");
        }
        double custoTotal = materiaPrima.getCustoPorLote() * quantidade;
        if (this.orçamento < custoTotal) {
            throw new RecursoInsuficienteException("Não há orçamento suficiente para essa transação! "
                    + "Custo: R$ " + custoTotal + ", Orçamento Disponível: R$ " + this.orçamento);
        }

        materiaPrima.adicionarEstoque(quantidade);
        this.orçamento -= custoTotal;
        System.out.println("[OK]  Compra realizada com sucesso!");
    }

    /**
     * Exibe no terminal a listagem de produtos presentes no armazém.
     */
    public void exibirArmazem() {
        System.out.println("===========================================================\n" +
                           "|           ~ ARMAZÉM DE PRODUTOS FABRICADOS ~            |\n" +
                           "===========================================================\n");
        if (produtosFabricados.isEmpty()) {
            System.out.println("[INFO] Nenhum produto no armazém momentaneamente.");
            return;
        }
        for (int i = 0; i < produtosFabricados.size(); i++) {
            System.out.println(
                    "[" + produtosFabricados.get(i).getNome() + "], ID: " + produtosFabricados.get(i).getId()
                            + ",  Qualidade: " + produtosFabricados.get(i).getQualidade()
                            + ",  Lote: " + produtosFabricados.get(i).getLote() 
                            + ",  Status: " + produtosFabricados.get(i).getStatus());
        }
    }

    /**
     * Calcula o custo total financeiro estimado para fabricar uma demanda por seu índice.
     *
     * @param indice Posição da demanda.
     * @return O custo monetário em Reais (R$).
     */
    private double calcularCustoProducao(int indice) {
        double custo = 0.0;
        for (int i = 0; i < maquinas.size(); i++) {
            custo += maquinas.get(i).getCustoOperacao();
        }
        custo += demandas.get(indice).calcularMateriaPrimaNecessaria() * materiaPrima.getCustoPorLote();
        return custo;
    }

    /**
     * Gera e imprime o relatório completo de auditoria das máquinas e produtos da fábrica.
     */
    public void gerarAuditoriaGeral() {
        System.out.println("===========================================================\n" +
                           "|                ~ RELATÓRIO DE AUDITORIA ~               |\n" +
                           "===========================================================\n");

        System.out.println("===========================================================\n" +
                           "|                       ~ MÁQUINAS ~                      |\n" +
                           "===========================================================\n");
        for (int i = 0; i < maquinas.size(); i++) {
            System.out.println(maquinas.get(i).gerarRelatorioDiagnostico());
        }

        System.out.println("===========================================================\n" +
                           "|                     ~ PRODUTOS FÁB. ~                   |\n" +
                           "===========================================================\n");
        if (produtosFabricados.isEmpty()) {
            System.out.println("[INFO] Nenhum produto em estoque!");
        } else {
            for (int i = 0; i < produtosFabricados.size(); i++) {
                System.out.println(produtosFabricados.get(i).gerarRelatorioDiagnostico());
            }
        }
        System.out.println("===========================================================");
    }

    /**
     * Configura a estratégia dinâmica de produção para execução automatizada.
     *
     * @param novaEstrategia Instância que implementa {@link EstrategiaProducao}.
     * @throws PlantaException se a estratégia informada for nula.
     */
    public void setEstrategia(EstrategiaProducao novaEstrategia) {
        if (novaEstrategia == null) {
            throw new PlantaException("A estratégia de produção não pode ser nula.");
        }
        this.estrategiaAtual = novaEstrategia;
        System.out.println("[OK]  Nova estratégia definida: " + novaEstrategia.getNomeEstrategia());
    }

    /**
     * Identifica e executa a próxima demanda viável com base na estratégia ativa.
     *
     * @throws EstadoInvalidoException se nenhuma estratégia estiver definida.
     * @throws DemandaInvalidaException se nenhuma demanda puder ser produzida.
     */
    public void executarProximaProducao() {
        if (this.estrategiaAtual == null) {
            throw new EstadoInvalidoException("Nenhuma estratégia de produção foi selecionada! Escolha uma estratégia primeiro.");
        }
        Demanda demandaSelecionada = estrategiaAtual.selecionarDemanda(this.demandas, this.orçamento);

        if (demandaSelecionada == null) {
            throw new DemandaInvalidaException("Nenhuma demanda elegível/viável encontrada para produção com o orçamento atual!");
        }

        int indice = this.demandas.indexOf(demandaSelecionada);
        System.out.println("[OK]  A demanda selecionada é: " + demandaSelecionada.getTipoProduto().getNome());
        fabricarDemanda(indice);
    }

    /**
     * Obtém o montante do orçamento corrente.
     *
     * @return O saldo em Reais (R$).
     */
    public double getOrçamento() {
        return this.orçamento;
    }

    /**
     * Imprime no terminal o valor do orçamento atual.
     */
    public void exibirOrçamento() {
        System.out.println("O orçamento disponível é R$ " + this.orçamento);
    }

    /**
     * Altera o cenário de simulação atual.
     *
     * @param cenarioAtual O novo {@link Cenario}.
     */
    public void setCenarioAtual(Cenario cenarioAtual) {
        if (cenarioAtual == null) {
            throw new PlantaException("O cenário de simulação não pode ser nulo.");
        }
        this.cenarioAtual = cenarioAtual;
    }

    /**
     * Obtém o cenário de simulação ativo.
     *
     * @return O {@link Cenario} atual.
     */
    public Cenario getCenarioAtual() {
        return cenarioAtual;
    }

    /**
     * Obtém a estratégia de produção configurada no momento.
     *
     * @return A {@link EstrategiaProducao} atual.
     */
    public EstrategiaProducao getEstrategiaAtual() {
        return estrategiaAtual;
    }

    /**
     * Restaura a saúde de todas as máquinas cadastradas para o nível máximo (100).
     */
    public void repararMaquinas() {
        for (int i = 0; i < maquinas.size(); i++) {
            maquinas.get(i).reparar();
            System.out.println("[OK] Máquina " + maquinas.get(i).getNome() + " reparada com sucesso.");
        }
    }
}