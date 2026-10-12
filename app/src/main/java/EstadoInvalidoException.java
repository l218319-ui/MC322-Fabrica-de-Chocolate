/**
 * Lançada quando é solicitada uma operação incompatível com o estado atual do objeto
 * (por exemplo, tentar concluir uma demanda cancelada).
 * 
 * @author Laura Póvoas
 */
public class EstadoInvalidoException extends PlantaException {

    /**
     * Constrói uma nova exceção de estado inválido.
     *
     * @param mensagem Detalhes da transição de estado proibida.
     */
    public EstadoInvalidoException(String mensagem) {
        super(mensagem);
    }
}