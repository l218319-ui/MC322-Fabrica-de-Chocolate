import java.util.List;

public interface EstrategiaProducao {
// Métodos:
/*Examina a lista de demandas disponíveis e retorna a referência da melhor 
Demanda a ser processada, ou null caso nenhuma seja elegível:*/
Demanda selecionarDemanda(List<Demanda> demandas, double orcamentoDisponivel);

//Retorna o nome amigável da estratégia para exibição na interface do usuário:
String getNomeEstrategia(); 
}