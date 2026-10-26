import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Classe agrupadora que reúne múltiplas unidades produtivas (Máquinas de Processamento).
 * Representa um setor na fábrica, implementando a relação de agregação (1:N) e auditoria em cascata.
 * 
 * @author Laura Póvoas
 */
public class SetorDeProcessamento implements Auditavel {

    /** Nome do setor.*/
    private String nome;

    /** Coleção de máquinas que pertencem ao setor (relação 1:N). */
    private List<MaquinaDeProcessamento> maquinas;

    /** Constrói o Setor de Processamento.
     *
     * @param nome Nome do setor.
     */
    public SetorDeProcessamento(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new PlantaException("O nome do setor de processamento não pode ser nulo nem vazio!");
        }
        this.nome = nome;
        this.maquinas = new ArrayList<>();
    }

    /** Adiciona uma máquina de processamento ao setor.
     *
     * @param maquina Instância de MaquinaDeProcessamento.
     */
    public void adicionarMaquina(MaquinaDeProcessamento maquina) {
        if (maquina == null) {
            throw new PlantaException("Não é possível adicionar uma máquina nula ao setor!");
        }
        this.maquinas.add(maquina);
    }

    /** Remove uma máquina de processamento do setor.
     *
     * @param maquina Máquina a ser removida.
     */
    public void removerMaquina(MaquinaDeProcessamento maquina) {
        this.maquinas.remove(maquina);
    }

    /** Retorna a lista de máquinas associadas com proteção de coleção.
     *
     * @return Coleção de Máquinas de Processamento.
     */
    public List<MaquinaDeProcessamento> getMaquinas() {
        return Collections.unmodifiableList(maquinas);
    }

    /** Retorna o nome do setor.
     *
     * @return Nome do setor.
     */
    public String getNome() {
        return nome;
    }

    /** Gera relatório de diagnóstico  em cascata (Setor -> Máquina -> Partes).
     *
     * @return Relatório completo em formato String indentada.
     */
    @Override
    public String gerarRelatorioDiagnostico() {
        String relatorio = "===========================================================\n"
                        + "[SETOR DE PROCESSAMENTO]: " + nome + "\n"
                        + "===========================================================\n";

        if (maquinas.isEmpty()) {
            relatorio += "  (Nenhuma máquina cadastrada neste setor)\n";
        } else {
            for (int i = 0; i < maquinas.size(); i++) {
                relatorio += " ► Máquina #" + (i + 1) + ":\n";
                String relatorioMaquina = maquinas.get(i).gerarRelatorioDiagnostico();
                
                // Adiciona recuo na primeira linha e substitui as quebras de linha adicionando 4 espaços
                relatorio += "    " + relatorioMaquina.replace("\n", "\n    ") + "\n";
            }
        }

        return relatorio;
    }

    /** Verifica se alguma máquina do setor necessita de manutenção.
     *
     * @return {@code true} se ao menos uma máquina precisar de manutenção.
     */
    @Override
    public boolean precisaManutencao() {
        for (MaquinaDeProcessamento maquina : maquinas) {
            if (maquina.precisaManutencao()) {
                return true;
            }
        }
        return false;
    }

    /** Executa o reparo em cascata em todas as máquinas do setor.*/
    public void repararTodas() {
        for (MaquinaDeProcessamento maquina : maquinas) {
            maquina.reparar();
        }
    }
}
