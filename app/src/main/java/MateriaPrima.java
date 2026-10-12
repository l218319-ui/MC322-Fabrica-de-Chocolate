/**
 * Gerencia o estoque e custos da matéria-prima utilizada na confecção de chocolates.
 * 
 * @author Laura Póvoas
 */
public class MateriaPrima {

    /** Identificador único da matéria-prima. */
    private long id;

    /** Nome do insumo. */
    private String nome;

    /** Quantidade total no estoque. */
    private double quantidade;

    /** Unidade de medida (ex: kg, g). */
    private String unidade;

    /** Custo monetário de cada lote do insumo. */
    private double custoPorLote;

    /**
     * Constrói e cadastra uma nova matéria-prima.
     *
     * @param id Identificador do item.
     * @param nome Nome do insumo.
     * @param quantidade Estoque inicial.
     * @param unidade Unidade de medida.
     * @param custoPorLote Preço de cada lote.
     * @throws PlantaException se a quantidade ou custo forem menores que zero.
     */
    public MateriaPrima(long id, String nome, double quantidade, String unidade, double custoPorLote) {
        if (quantidade < 0 || custoPorLote < 0) {
            throw new PlantaException("Estoque e custo por lote não podem ser valores negativos.");
        }
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.unidade = unidade;
        this.custoPorLote = custoPorLote;
    }

    /**
     * Obtém o ID.
     *
     * @return O id.
     */
    public long getId() {
        return this.id;
    }

    /**
     * Obtém o nome.
     *
     * @return O nome.
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Obtém a quantidade em estoque.
     *
     * @return A quantidade.
     */
    public double getQuantidade() {
        return this.quantidade;
    }

    /**
     * Obtém a unidade de medida.
     *
     * @return A unidade.
     */
    public String getUnidade() {
        return this.unidade;
    }

    /**
     * Obtém o custo por lote.
     *
     * @return Valor em Reais (R$).
     */
    public double getCustoPorLote() {
        return custoPorLote;
    }

    /**
     * Checa se a quantidade em estoque é suficiente para suprir a demanda.
     *
     * @param demanda Quantidade requerida.
     * @return {@code true} se o estoque for maior ou igual à demanda; {@code false} caso contrário.
     */
    public boolean verificarDisponibilidade(double demanda) {
        return this.quantidade >= demanda;
    }

    /**
     * Reduz do estoque a quantidade demandada.
     *
     * @param demanda Quantidade a consumir.
     * @throws RecursoInsuficienteException se o estoque for insuficiente para a demanda.
     */
    public void consumir(double demanda) {
        if (!verificarDisponibilidade(demanda)) {
            throw new RecursoInsuficienteException("Não há quantidade suficiente de matéria-prima no estoque! "
                    + "Necessário: " + demanda + ", Disponível: " + this.quantidade);
        }
        this.quantidade -= demanda;
    }

    /**
     * Adiciona nova remessa ao estoque.
     *
     * @param remessa Quantidade a acrescentar.
     * @throws PlantaException se a remessa for menor ou igual a zero.
     */
    public void adicionarEstoque(double remessa) {
        if (remessa <= 0) {
            throw new PlantaException("Não é possível adicionar uma quantidade menor ou igual a zero no estoque!");
        }
        this.quantidade += remessa;
    }
}