- Trabalho de MC322 2026-2S
- Tema: Fábrica de Chocolates
- Autora: Laura Póvoas

SEÇÃO 1 - Escopo e Limitações:
|-> Descreve a estratégia de persistência adotada para a planta, além de detalhar a estrutura da suíte de testes automatizados com JUnit 5.

+ Dados Persistidos vs. Não Persistidos:

++ Dados Persistidos (Salvos em XML):

- Estoque de Matérias-Primas: salva o identificador, nome, quantidade disponível em estoque, unidade de medida e o custo por lote de cada insumo.
  |-> Justificativa: evita que o estoque seja resetado ao reiniciar o programa, mantendo a consistência da linha de produção.
  
- Estado e Configuração das Máquinas e Subcomponentes: salva nome, classe concreta da máquina, capacidade máxima, custo por operação, probabilidade de falha, saúde e status. Para instâncias da MaquinaDeProcessamento, salva também a composição dos seus subcomponentes:
  |-> Motor: potência, saúde e estado de funcionamento.
  |-> Bico Injetor: vazão, saúde e estado de entupimento.
  |-> Braços Batedores: identificador, velocidade de rotação e saúde individual.
  |-> Justificativa: preserva o nível de desgaste acumulado nos equipamentos e em suas partes, mantendo a consistência da linha de produção.
  
- Demandas de Produção e seus Estados: salva o produto associado, a quantidade de produtos a fabricar e o status atual da demanda.
  |-> Justificativa: permite retomar ordens de produção e manter o histórico das demandas.
  
- Orçamento da Fábrica: salva o saldo disponível.
  |-> Justificativa: mantém o ciclo econômico da planta.

++ Dados Não Persistidos (Mantidos Apenas em Memória):

- Estratégia de Produção Ativa:
  |-> Justificativa: selecionado dinamicamente.
  
- Cenário:
  |-> Justificativa: define parâmetros globais de inicialização. Ao carregar do XML, os dados persistidos sobrescrevem os parâmetros padrão.
  
- Estado da Esteira Transportadora:
  |-> Justificativa: o transporte de itens ocorre em tempo real, não exigindo a persistência dos dados.
  
- Leitores de Entrada de Terminal.

 + Explicação da Suíte de Testes Automatizados:

A suíte de testes foi modelada aplicando princípios de isolamento, repetibilidade e execução independente, sem exigir entrada via teclado.

++ Estratégia de Isolamento de Dados:
|-> Arquivos Temporários: os testes de persistência utilizam o recurso `@TempDir`, que cria e remove diretórios temporários isolados para cada execução de teste. Evitando contaminação entre testes e garantindo que o arquivo real da aplicação não seja alterado ou utilizado durante a suíte automatizada.

++ Organização e Escopo das Classes de Teste:
+++ Classe de Teste - Escopo - Casos Testados:
|-> MateriaPrimaTest - Regras de Negócio e Invariantes de Domínio - Criação de matéria-prima com atributos válidos; Lançamento de PlantaException ao tentar instanciar com quantidade ou custo negativos; Consumo de estoque com margem suficiente; Lançamento de RecursoInsuficienteException ao tentar consumir acima do disponível (verificando a manutenção do estoque intacto); Adição de novas remessas e validação de valores de entrada inválidos (menores ou iguais a zero). 
|-> DemandaTest - Regras de Transição de Estado e Negócio - Inicialização correta no status PENDENTE; Lançamento de DemandaInvalidaException para parâmetros nulos ou quantidades; Transições válidas de status e conclusão de atendimento; Lançamento de EstadoInvalidoException ao tentar concluir uma demanda com status CANCELADA; Cálculo exato da quantidade total de matéria-prima necessária. 
|-> MaquinaDeProcessamentoTest - Composição Estrita e Comportamento Operacional - Instanciação e integridade das partes compostas; Bloqueio de processamento e lançamento de MaquinaIndisponivelException quando a máquina está desligada; Validação de entradas nulas DemandaInvalidaException; Execução do reparo em cascata (verificando se o comando de reparo no Todo restaura a saúde de todas as partes para 100%).
|-> PersistenciaPlantaTest - Serialização XML, Deserialização e Exceções - Salvamento e recarregamento completo da planta com asserção dos valores restaurados (orçamento, coleções e estados); Lançamento de PersistenciaException ao tentar carregar arquivo inexistente; Lançamento de PersistenciaException ao tentar ler arquivo totalmente vazio; Lançamento de PersistenciaException diante de XML corrompido ou malformatado; Validação de regras de negócio durante o carregamento (ex.: rejeição de arquivo XML contendo saldo negativo). 
