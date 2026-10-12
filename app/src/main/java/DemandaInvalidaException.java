/**
 * Lançada quando ocorre uma tentativa de operar sobre uma demanda inexistente,
 * nula, com parâmetros inválidos (como quantidade menor ou igual a zero) ou em lista nula.
 * 
 * @author Laura Póvoas
 */
public class DemandaInvalidaException extends PlantaException {

    /**
     * Constrói uma nova exceção de demanda inválida.
     *
     * @param mensagem Descrição do problema na demanda.
     */
    public DemandaInvalidaException(String mensagem) {
        super(mensagem);
    }
}