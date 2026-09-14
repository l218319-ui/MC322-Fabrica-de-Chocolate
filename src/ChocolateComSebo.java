public class ChocolateComSebo extends Produto {

    public ChocolateComSebo(long id, String nome, boolean status, long quantidadeMateriaPrimaNecessaria) {
        this.id = id;
        this.nome = nome;
        this.status = status;
        this.quantidadeMateriaPrimaNecessaria = quantidadeMateriaPrimaNecessaria;
        this.qualidade = 0.5f;
        this.probabilidadeFalhaAcumulada = 0.05f;
    }
    public void processar(){
        this.status = true;
    } 

    public int calcularTempoProducao(){
        //retorna tempo em minutos
        return 1;
    }

    public String getTipo() {
        return "Sebo Puro";
    } 
}
