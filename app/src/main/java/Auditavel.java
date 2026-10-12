public interface Auditavel {
    // Métodos:
    
    // Retorna uma String com o estado de saúde/qualidade do componente:
    String gerarRelatorioDiagnostico(); // assume-se público, ou seja, opcional colocar public na interface?

    // Retorna um boolean indicando se o componente requer intervenção:
    boolean precisaManutencao();
}
