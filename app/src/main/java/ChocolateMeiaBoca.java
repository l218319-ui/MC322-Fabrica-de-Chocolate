/**
 * Representa um produto do tipo chocolate de qualidade intermediária.
 * Regras de Negócio / Características:
 *   Possui nota de qualidade fixa em 7.0.
 *   Exige uma quantidade moderada de matéria-prima (0.7 unidades).
 *   Possui tempo de fabricação estimado em 2.0 minutos.
 * @author Laura Póvoas
 */
public class ChocolateMeiaBoca extends Produto {

    /**
     * Constrói uma nova instância do {@code ChocolateMeiaBoca} com parâmetros pré-definidos de qualidade e custo.
     *
     * @param id Identificador único do produto.
     * @param nome Nome comercial do chocolate.
     * @param lote Código do lote ao qual o produto pertence.
     * @throws DemandaInvalidaException se o parâmetro {@code id} for negativo ou se {@code nome}/{@code lote} forem nulos/vazios.
     */
    public ChocolateMeiaBoca(int id, String nome, String lote) {
        super(id, nome, 7.0, 0.7f, lote);
        if (id < 0) {
            throw new DemandaInvalidaException("O ID do produto não pode ser negativo.");
        }
    }

    /**
     * Inicia o processamento do produto na linha de produção, atualizando seu status.
     */
    @Override
    public void processar() {
        setStatus("Em processamento...");
    }

    /**
     * Calcula o tempo de produção necessário para fabricar este chocolate.
     *
     * @return O tempo estimado de produção em minutos (2.0f).
     */
    @Override
    public float calcularTempoProducao() {
        return 2.0f;
    }

    /**
     * Obtém a descrição detalhada referente ao tipo e qualidade do produto.
     *
     * @return {@link String} contendo o nome e especificação do tipo de chocolate.
     */
    @Override
    public String getTipo() {
        return getNome() + " de média Qualidade (alma de sapatênis, uma média entre tênis e sapatilha)";
    }

    /**
     * Realiza a clonagem deste produto para evitar contaminação ou alterações indevidas no armazém.
     *
     * @return Uma nova instância idêntica de {@link ChocolateMeiaBoca}.
     */
    @Override
    public Produto clonar() {
        return new ChocolateMeiaBoca((int) getId(), getNome(), getLote());
    }
}