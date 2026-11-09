package br.unicamp.ic.MC322.exception;
/**
 * Exceção lançada durante falhas no processo de salvamento, leitura ou validação de arquivos XML.
 * 
 * @author Laura Póvoas
 */
public class PersistenciaException extends PlantaException {
    
    public PersistenciaException(String mensagem) {
        super(mensagem);
    }

    public PersistenciaException(String mensagem, Throwable causa) {
        super(mensagem + (causa != null ? " | Causa: " + causa.getMessage() : ""));
    }
}
