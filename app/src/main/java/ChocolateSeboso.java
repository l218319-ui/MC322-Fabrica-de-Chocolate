/**
 * Representa um chocolate de qualidade inferior (popular / econômico).
 * Regras de Negócio / Características:
 *   Possui nota de qualidade básica (5.0).
 *   Consome pouca matéria-prima por unidade (0.5 unidades).
 *   Possui o menor tempo de processamento da fábrica (1.0 minuto).
 * 
 * @author Laura Póvoas
 */
public class ChocolateSeboso extends Produto {

    /**
     * Instancia um novo {@code ChocolateSeboso} com parâmetros padrão para a categoria econômica.
     *
     * @param id Identificador do produto.
     * @param nome Nome do produto.
     * @param lote Lote de produção ao qual o item pertence.
     * @throws DemandaInvalidaException se o parâmetro {@code id} for negativo.
     */
    public ChocolateSeboso(int id, String nome, String lote) {
        super(id, nome, 5.0, 0.5f, lote);
        if (id < 0) {
            throw new DemandaInvalidaException("O ID do produto não pode ser negativo.");
        }
    }

    /**
     * Executa a etapa de processamento do chocolate.
     */
    @Override
    public void processar() {
        setStatus("Em processamento...");
    }

    /**
     * Retorna a duração da linha de produção necessária para esse chocolate.
     *
     * @return Tempo total de produção em minutos (1.0f).
     */
    @Override
    public float calcularTempoProducao() {
        return 1.0f;
    }

    /**
     * Obtém a especificação detalhada da linha econômica deste produto.
     *
     * @return {@link String} com nome e categoria descritiva do produto.
     */
    @Override
    public String getTipo() {
        return getNome() + " Sebo Puro (que nem chocolate de guarda-chuvinha)";
    }

    /**
     * Clona o objeto atual para permitir inserções limpas no armazém sem referências sobrepostas.
     *
     * @return Nova cópia de {@link ChocolateSeboso}.
     */
    @Override
    public Produto clonar() {
        return new ChocolateSeboso((int) getId(), getNome(), getLote());
    }
}