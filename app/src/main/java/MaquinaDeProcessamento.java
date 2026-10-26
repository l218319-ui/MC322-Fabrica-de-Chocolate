import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Máquina dedicada ao processamento e transformação da matéria-prima em chocolate.
 * Atua como o "Todo" em relação de composição estrita com suas partes (Motor, BicoInjetor e BraçosBatedores).
 * Implementa Lei de Deméter para gerenciar o ciclo de vida e operações das partes.
 * 
 * @author Laura Póvoas
 */
public class MaquinaDeProcessamento extends Maquina {

    /** Parte de composição 1:1 - Motor da máquina. */
    private Motor motor;

    /** Parte de composição 1:1 - Bico injetor de matéria-prima. */
    private BicoInjetor bicoInjetor;

    /** Parte de composição 1:N - Lista de braços batedores da máquina. */
    private List<BracoBatedor> bracosBatedores;

    /** Construtor principal da Máquina de Processamento.
     * Controla estritamente o ciclo de vida de suas partes.
     *
     * @param nome Nome da máquina.
     * @param custoOperacao Custo por operação.
     * @param capacidadeMaxima Capacidade produtiva por ciclo.
     * @param quantidadeBracos Quantidade de braços batedores a serem instanciados.
     */
    public MaquinaDeProcessamento(String nome, float custoOperacao, double capacidadeMaxima, int quantidadeBracos) {
        super(nome, capacidadeMaxima, 0.0f, custoOperacao);

        if (quantidadeBracos <= 0) {
            throw new PlantaException("A quantidade de braços batedores deve ser pelo menos 1!");
        }

        this.motor = new Motor(150.0);
        this.bicoInjetor = new BicoInjetor(50.0);
        this.bracosBatedores = new ArrayList<>();

        for (int i = 1; i <= quantidadeBracos; i++) {
            this.bracosBatedores.add(new BracoBatedor(i, 300.0));
        }
    }

    /** Construtor que define por padrão 2 braços batedores (evita que o código quebre no main).
     *
     * @param nome Nome da máquina.
     * @param custoOperacao Custo por operação.
     * @param capacidadeMaxima Capacidade produtiva por ciclo.
     */
    public MaquinaDeProcessamento(String nome, float custoOperacao, double capacidadeMaxima) {
        this(nome, custoOperacao, capacidadeMaxima, 2);
    }

    /** Retorna o tipo da máquina.    (non-Javadoc)
     * 
     * @see Maquina#getTipo()
     */
    @Override
    public String getTipo() {
        return "Máquina de processamento";
    }

    /**
     * Processa delegando comportamentos para suas Partes (Lei de Deméter),
     * aplicando desgaste no Todo e propagando para as Partes.
     *
     * @param produto Produto a ser processado.
     * @param cenario Cenário de operação.
     */
    @Override
    public void processar(Produto produto, Cenario cenario) {
        if (produto == null) {
            throw new DemandaInvalidaException("Não é possível processar um produto nulo.");
        }
        if (!this.estaLigada()) {
            throw new MaquinaIndisponivelException("Não é possível processar: a máquina '" + getNome() + "' está desligada!");
        }
        if (this.getSaude() <= 0) {
            throw new MaquinaIndisponivelException("Não é possível processar: a máquina '" + getNome() + "' está quebrada!");
        }

        motor.acionar();
        bicoInjetor.injetar();
        for (BracoBatedor braco : bracosBatedores) {
            braco.bater();
        }

        produto.processar();

        int dano = random.nextInt(1, 4);

        // Aplica desgaste no Todo e propaga para as Partes
        aplicaDesgaste(dano, cenario);
        motor.aplicarDesgaste(dano);
        bicoInjetor.aplicarDesgaste(dano);
        for (BracoBatedor braco : bracosBatedores) {
            braco.aplicarDesgaste(dano);
        }

        motor.desligar();

        double chanceFalha = ((100.0 - this.getSaude()) / 100.0) * (cenario != null ? cenario.getFalha() : 1.0f);
        if (random.nextDouble() < chanceFalha) {
            produto.aumentarProbabilidadeFalha(0.05f);
        }
    }

    /** Restaura a saúde do Todo e propaga o reparo em cascata para todas as suas Partes. */
    @Override
    public void reparar() {
        super.reparar();
        motor.reparar();
        bicoInjetor.desentupirEReparar();
        for (BracoBatedor braco : bracosBatedores) {
            braco.reparar();
        }
    }

    /**
     * Avalia se a máquina ou qualquer uma de suas partes precisa de manutenção.
     *
     * @return {@code true} se o Todo ou alguma Parte precisar de reparo.
     */
    @Override
    public boolean precisaManutencao() {
        if (super.precisaManutencao() || motor.precisaManutencao() || bicoInjetor.precisaManutencao()) {
            return true;
        }
        for (BracoBatedor braco : bracosBatedores) {
            if (braco.precisaManutencao()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gera relatório de diagnóstico, propagando a auditoria em cascata para as partes.
     *
     * @return String com os diagnósticos do Todo e das Partes.
     */
    @Override
    public String gerarRelatorioDiagnostico() {
        String relatorio = super.gerarRelatorioDiagnostico() + "\n"
                        + "    ├─ " + motor.gerarRelatorioDiagnostico() + "\n"
                        + "    ├─ " + bicoInjetor.gerarRelatorioDiagnostico() + "\n"
                        + "    └─ Braços Batedores (" + bracosBatedores.size() + " un):\n";

        for (int i = 0; i < bracosBatedores.size(); i++) {
            String prefixo = (i == bracosBatedores.size() - 1) ? "       └─ " : "       ├─ ";
            relatorio += prefixo + bracosBatedores.get(i).gerarRelatorioDiagnostico() + "\n";
        }

        return relatorio;
    }

    /** Proteção de coleção.
     *
     * @return Lista imutável dos braços batedores.
     */
    public List<BracoBatedor> getBracosBatedores() {
        return Collections.unmodifiableList(bracosBatedores);
    }
}