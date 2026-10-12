/**
 * Lançada quando ocorre uma falha operacional na esteira
 * (como esteira desligada, capacidade de peso excedida ou tentativa de colocar mais de um item).
 * 
 * @author Laura Póvoas
 */
public class EsteiraOperacaoException extends PlantaException {

    /**
     * Constrói uma nova exceção de operação da esteira.
     *
     * @param mensagem Detalhes do motivo da falha operacional.
     */
    public EsteiraOperacaoException(String mensagem) {
        super(mensagem);
    }
}