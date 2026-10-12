/**
 * Gerencia as ordens de pedido e demanda de produção de chocolates.
 * Regras de Negócio e Consistência de Estado:
 *   Cada demanda é associada a um tipo de produto válido e uma quantidade maior que zero.
 *   Inicia sempre com o status {@link StatusDemanda#PENDENTE}.
 *   Lança {@link EstadoInvalidoException} ao tentar alterar para um status incompatível,
 *   como tentar concluir uma demanda cancelada.
 *   Valida todas as entradas antes de efetuar qualquer alteração no estado interno do objeto.
 * 
 * @author Laura Póvoas
 */
public class Demanda {

    /** Produto associado à ordem de produção. */
    private Produto tipoProduto;

    /** Quantidade de produtos a serem fabricados. */
    private long quantidadeProdutos;

    /** Situação/status atual da ordem de demanda. */
    private StatusDemanda status;

    /**
     * Cria uma nova demanda de produção com a quantidade solicitada do produto.
     *
     * @param tipoProduto Tipo de produto a ser fabricado nesta demanda.
     * @param quantidadeProdutos Quantidade total de itens necessários.
     * @throws DemandaInvalidaException se {@code tipoProduto} for nulo ou se {@code quantidadeProdutos <= 0}.
     */
    public Demanda(Produto tipoProduto, long quantidadeProdutos) {
        if (tipoProduto == null) {
            throw new DemandaInvalidaException("O tipo de produto da demanda não pode ser nulo.");
        }
        if (quantidadeProdutos <= 0) {
            throw new DemandaInvalidaException("A quantidade de produtos da demanda deve ser maior que zero.");
        }
        this.tipoProduto = tipoProduto;
        this.quantidadeProdutos = quantidadeProdutos;
        this.status = StatusDemanda.PENDENTE;
    }

    /**
     * Atualiza a quantidade de produtos a ser produzida.
     *
     * @param quantidade Nova quantidade total de itens.
     * @throws DemandaInvalidaException se {@code quantidade <= 0}.
     */
    public void atualizarQuantidade(long quantidade) {
        if (quantidade <= 0) {
            throw new DemandaInvalidaException("A quantidade atualizada deve ser estritamente maior que zero.");
        }
        this.quantidadeProdutos = quantidade;
    }

    /**
     * Calcula o total de matéria-prima necessário para atender a quantidade desta demanda.
     *
     * @return A quantidade total de matéria-prima requerida.
     */
    public double calcularMateriaPrimaNecessaria() {
        return tipoProduto.getQuantidadeMateriaPrimaNecessaria() * this.quantidadeProdutos;
    }

    /**
     * Atende e conclui a demanda atual.
     *
     * @throws EstadoInvalidoException se a demanda estiver com status {@link StatusDemanda#CANCELADA}.
     */
    public void atender() {
        if (this.status == StatusDemanda.CANCELADA) {
            throw new EstadoInvalidoException("Não é possível atender uma demanda que está CANCELADA!");
        }
        this.status = StatusDemanda.CONCLUIDA;
    }

    /**
     * Altera o status da demanda respeitando as regras de transição.
     *
     * @param novoStatus O novo {@link StatusDemanda} a ser aplicado.
     * @throws DemandaInvalidaException se {@code novoStatus} for nulo.
     * @throws EstadoInvalidoException se houver tentativa de concluir uma demanda CANCELADA.
     */
    public void setStatus(StatusDemanda novoStatus) {
        if (novoStatus == null) {
            throw new DemandaInvalidaException("O novo status fornecido não pode ser nulo.");
        }
        if (this.status == StatusDemanda.CANCELADA && novoStatus == StatusDemanda.CONCLUIDA) {
            throw new EstadoInvalidoException("Não é possível concluir uma demanda que está CANCELADA!");
        }
        this.status = novoStatus;
    }

    /**
     * Retorna o tipo de produto configurado na demanda.
     *
     * @return O objeto {@link Produto}.
     */
    public Produto getTipoProduto() {
        return tipoProduto;
    }

    /**
     * Retorna a quantidade de produtos vinculados a esta demanda.
     *
     * @return O número total de itens.
     */
    public long getQuantidadeProdutos() {
        return quantidadeProdutos;
    }

    /**
     * Informa se a demanda foi devidamente finalizada.
     *
     * @return {@code true} se o status for {@link StatusDemanda#CONCLUIDA}; {@code false} caso contrário.
     */
    public boolean isAtendida() {
        return status == StatusDemanda.CONCLUIDA;
    }

    /**
     * Obtém o status operacional atual da demanda.
     *
     * @return O {@link StatusDemanda} atual.
     */
    public StatusDemanda getStatus() {
        return status;
    }

    /**
     * Calcula uma estimativa do custo total financeiro exigido para atender a demanda,
     * incluindo custos com matéria-prima e custos de operação das máquinas.
     *
     * @return O custo total estimado para atendimento.
     */
    public double custoDem() {
        double custoMáqEstimado = 6.0;
        double custoMateriaPrima = 3.0;
        return calcularMateriaPrimaNecessaria() + custoMateriaPrima + custoMáqEstimado;
    }
}