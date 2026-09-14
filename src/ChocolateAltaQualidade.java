public class ChocolateAltaQualidade extends Produto {

    public ChocolateAltaQualidade(long id, String nome, boolean status, long quantidadeMateriaPrimaNecessaria) {
        this.id = id;
        this.nome = nome;
        this.status = status;
        this.quantidadeMateriaPrimaNecessaria = quantidadeMateriaPrimaNecessaria;
        this.qualidade = 0.9f;
        this.probabilidadeFalhaAcumulada = 0.09f;
    }
    public void processar(){
        this.status = true;
    } 

    public int calcularTempoProducao(){
        //retorna tempo em minutos
        return 3;
    }

    public String getTipo() {
        return "Alta Qualidade";
    } 
}
