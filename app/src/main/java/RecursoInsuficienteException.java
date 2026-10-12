/**
 * Lançada quando a fábrica não possui recursos suficientes (matéria-prima, orçamento ou insumos)
 * para realizar ou prosseguir com uma determinada operação.
 * 
 * @author Laura Póvoas
 */
public class RecursoInsuficienteException extends PlantaException {

    /**
     * Constrói uma nova exceção de recurso insuficiente.
     *
     * @param mensagem Detalhes do recurso que está insuficiente.
     */
    public RecursoInsuficienteException(String mensagem) {
        super(mensagem);
    }
}