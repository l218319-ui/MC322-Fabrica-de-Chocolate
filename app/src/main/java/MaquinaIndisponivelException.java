/**
 * Exceção lançada quando uma máquina da linha de produção se encontra
 * desligada, inoperante, sem saúde (quebrada) ou indisponível.
 * 
 * @author Laura Póvoas
 */
public class MaquinaIndisponivelException extends PlantaException {

    /**
     * Constrói uma nova exceção de máquina indisponível com a mensagem especificada.
     *
     * @param mensagem Descrição do motivo da indisponibilidade da máquina.
     */
    public MaquinaIndisponivelException(String mensagem) {
        super(mensagem);
    }
}