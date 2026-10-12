/**
 * Interface que estabelece o contrato para componentes auditáveis do sistema.
 * Permite a verificação do estado de saúde, qualidade e diagnósticos do componente,
 * além de identificar se necessita de intervenções técnicas ou manutenção.
 * 
 * @author Laura Póvoas
 */
public interface Auditavel {

    /**
     * Gera um relatório detalhado referente ao diagnóstico do estado de saúde
     * e funcionamento do componente.
     *
     * @return {@link String} contendo o relatório de diagnóstico do componente.
     */
    String gerarRelatorioDiagnostico();

    /**
     * Avalia o estado atual do componente para determinar se há necessidade de intervenção ou manutenção.
     *
     * @return {@code true} se o componente precisar de manutenção; {@code false} caso contrário.
     */
    boolean precisaManutencao();
}