public class ChocolateMediaQualidade extends Produto {

    public ChocolateMediaQualidade(long id, String nome, boolean status, long quantidadeMateriaPrimaNecessaria) {
        this.id = id;
        this.nome = nome;
        this.status = status;
        this.quantidadeMateriaPrimaNecessaria = quantidadeMateriaPrimaNecessaria;
        this.qualidade = 0.7f;
        this.probabilidadeFalhaAcumulada = 0.07f;
    }
    public void processar(){
        this.status = true;
    } 

    public int calcularTempoProducao(){
        //retorna tempo em minutos
        return 2;
    }

    public String getTipo() {
        return "Média Qualidade";
    } 
}
