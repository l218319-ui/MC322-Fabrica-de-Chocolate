/**
 * Representa um produto do tipo chocolate de alta qualidade (Gourmet / Artesanal).
 * Regras de Negócio / Características:
 *   Possui nota de qualidade superior (9.0).
 *   Consome maior quantidade de matéria-prima (0.9 unidades).
 *   Demanda um tempo maior de processamento (3.0 minutos).
 * 
 * @author Laura Póvoas
 */
public class ChocolatePremium extends Produto {

    /**
     * Constrói um objeto do tipo {@code ChocolatePremium} com os atributos de qualidade máxima.
     *
     * @param id Identificador único do produto.
     * @param nome Nome comercial do chocolate.
     * @param lote Código do lote de fabricação.
     * @throws DemandaInvalidaException se o parâmetro {@code id} for negativo.
     */
    public ChocolatePremium(int id, String nome, String lote) {
        super(id, nome, 9.0, 0.9f, lote);
        if (id < 0) {
            throw new DemandaInvalidaException("O ID do produto não pode ser negativo.");
        }
    }

    /**
     * Processa a fabricação do chocolate, alterando o estado do seu fluxo de produção.
     */
    @Override
    public void processar() {
        setStatus("Em processamento...");
    }

    /**
     * Retorna o tempo exigido na fabricação deste chocolate especial.
     *
     * @return O tempo em minutos necessário para a produção (3.0f).
     */
    @Override
    public float calcularTempoProducao() {
        return 3.0f;
    }

    /**
     * Obtém a identificação e categoria detalhada do produto.
     *
     * @return {@link String} descrevendo o nome e status premium do chocolate.
     */
    @Override
    public String getTipo() {
        return getNome() + " Premium Gourmet & Artesanal (estão te enganando pra tirar seu dinheiro, nada disso é real)";
    }

    /**
     * Cria uma cópia independente da instância atual para ser inserida individualmente no armazém.
     *
     * @return Nova instância de {@link ChocolatePremium}.
     */
    @Override
    public Produto clonar() {
        return new ChocolatePremium((int) getId(), getNome(), getLote());
    }
}