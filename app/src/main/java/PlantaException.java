/**
 * Exceção base do domínio da planta de produção de chocolates.
 * Serve como superclasse para todas as exceções de negócio do sistema.
 * 
 * @author Laura Póvoas
 */
public class PlantaException extends RuntimeException {

    /**
     * Constrói uma nova exceção com a mensagem descritiva informada.
     *
     * @param mensagem Descrição do erro ocorrido na planta.
     */
    public PlantaException(String mensagem) {
        super(mensagem);
    }

    /**
     * Constrói uma nova exceção com mensagem e causa original.
     *
     * @param mensagem Descrição do erro.
     * @param causa Causa original da exceção.
     */
    public PlantaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
