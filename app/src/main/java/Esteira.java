/**
 * Controla e simula o funcionamento de uma esteira transportadora de fábrica.
 * Regras de Negócio e Consistência do Estado:
 *   A esteira deve obrigatoriamente estar ligada/em movimento para aceitar itens.
 *   Suporta o transporte de apenas um item por vez.
 *   Não aceita itens cujo peso ultrapasse a sua capacidade máxima cadastrada.
 *   Todas as operações validam as condições antes de realizar qualquer modificação no estado do objeto.
 * 
 * @author Laura Póvoas
 */
public class Esteira {

    /** Nome/descrição do item atualmente presente na esteira. */
    private String item;

    /** Indica se a esteira está ligada e em movimento. */
    private boolean emMovimento;

    /** Limite máximo de peso que a esteira consegue suportar. */
    private long capacidadeMaxima;

    /**
     * Constrói uma nova esteira com capacidade limite e item inicial.
     *
     * @param item Item inicial posicionado na esteira.
     * @param capacidadeMaxima Capacidade máxima suportada de peso.
     * @throws EsteiraOperacaoException se a capacidade máxima for menor ou igual a zero.
     */
    public Esteira(String item, long capacidadeMaxima) {
        if (capacidadeMaxima <= 0) {
            throw new EsteiraOperacaoException("A capacidade máxima da esteira deve ser maior que zero.");
        }
        this.item = item;
        this.emMovimento = false;
        this.capacidadeMaxima = capacidadeMaxima;
    }

    /**
     * Aciona o motor da esteira, colocando-a em movimento para receber ou transportar materiais.
     */
    public void ligar() {
        this.emMovimento = true;
        System.out.println("[OK] Esteira ligada...");
    }

    /**
     * Desliga a esteira, parando imediatamente a movimentação de transporte.
     */
    public void desligar() {
        this.emMovimento = false;
        System.out.println("[OK] Esteira desligada.");
    }

    /**
     * Informa se o equipamento encontra-se ligado e em operação.
     *
     * @return {@code true} se a esteira estiver em movimento; {@code false} se estiver parada.
     */
    public boolean estaEmMovimento() {
        return this.emMovimento;
    }

    /**
     * Adiciona um novo item na esteira, validando o estado operacional e os limites físicos antes da alteração.
     *
     * @param nomeItem Denominação do item a ser transportado.
     * @param peso Peso do item a ser colocado na esteira.
     * @return {@code true} se o item for adicionado com sucesso.
     * @throws EsteiraOperacaoException se a esteira estiver desligada, já ocupada ou o peso exceder a capacidade.
     */
    public boolean adicionarItem(String nomeItem, double peso) {
        if (!this.emMovimento) {
            throw new EsteiraOperacaoException("A esteira está desligada! Ligue-a antes de colocar um item.");
        }
        if (this.item != null && !this.item.isEmpty()) {
            throw new EsteiraOperacaoException("A esteira já contém o item '" + this.item + "'. Remova-o antes.");
        }
        if (!verificarCapacidade(peso)) {
            throw new EsteiraOperacaoException("O peso informado (" + peso + " kg) excede a capacidade máxima (" + this.capacidadeMaxima + " kg)!");
        }

        // Alteração realizada apenas após todas as validações serem bem-sucedidas (Garantia de consistência do estado)
        this.item = nomeItem;
        System.out.println("[OK] Item '" + nomeItem + "' colocado na esteira.");
        return true;
    }

    /**
     * Retira e desocupa o item posicionado na esteira.
     *
     * @return O nome do item retirado.
     * @throws EsteiraOperacaoException se a esteira já se encontrar vazia.
     */
    public String removerItem() {
        if (this.item == null || this.item.isEmpty()) {
            throw new EsteiraOperacaoException("Não há item para ser removido da esteira!");
        }

        String itemRemovido = this.item;
        this.item = "";
        System.out.println("[OK] Item '" + itemRemovido + "' removido da esteira.");
        return itemRemovido;
    }

    /**
     * Avalia se o peso informado é compatível com o limite da esteira.
     *
     * @param peso Valor numérico do peso a ser checado.
     * @return {@code true} se o peso for aceito; {@code false} se ultrapassar a capacidade.
     */
    public boolean verificarCapacidade(double peso) {
        return peso <= this.capacidadeMaxima;
    }

    /**
     * Obtém o nome do item que se encontra no momento sobre a esteira.
     *
     * @return Nome do item, ou String vazia/nula.
     */
    public String getItem() {
        return this.item;
    }

    /**
     * Retorna a capacidade máxima de carga da esteira.
     *
     * @return O limite máximo de peso suportado.
     */
    public double getCapacidadeMaxima() {
        return this.capacidadeMaxima;
    }
}