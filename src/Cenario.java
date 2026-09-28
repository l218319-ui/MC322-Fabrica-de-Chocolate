public enum Cenario {
    // Constantes:
    IDEAL("Cenário ideal e pacífico", 1000000.0, 0.2f, 0.2f),
    APOCALIPTICO("Cenário Apocalíptico (à beira da falência)", 1000.0, 2.0f, 2.0f);

    // Atributos:
    private final double orçamentoInicial;
    private final float fatorDesgaste;
    private final float fatorFalha;
    private final String nome;

    // Construtor:
    private Cenario(String nome, double orçamentoInicial, float fatorDesgaste, float fatorFalha) {
        this.nome = nome;
        this.orçamentoInicial = orçamentoInicial;
        this.fatorDesgaste = fatorDesgaste;
        this.fatorFalha = fatorFalha;
    }

    // Métodos:
    public double getOrçamentoInicial() {
        return orçamentoInicial;
    }

    public float getFatorDesgaste() {
        return fatorDesgaste;
    }

    public float getFatorFalha() {
        return fatorFalha;
    }

    public String getNome() {
        return nome;
    }

}
