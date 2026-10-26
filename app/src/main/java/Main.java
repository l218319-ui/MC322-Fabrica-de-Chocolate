import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Classe principal da aplicação da Fábrica de Chocolates.
 * Responsável por inicializar os componentes do sistema e gerenciar a interface do usuário via terminal.
 * 
 * @author Laura Póvoas 
 */
public class Main {

    /** Instância de matéria-prima. */
    static MateriaPrima mp1;

    /** Produto 1: Chocolate Premium. */
    static Produto p1;

    /** Produto 2: Chocolate Meia Boca. */
    static Produto p2;

    /** Produto 3: Chocolate Seboso.*/
    static Produto p3;

    /** Esteira transportadora da planta.*/
    static Esteira e1;

    /** Gerenciador central de produção.*/
    static GerenciadorProducao gp;

    /** Setor de Processamento (Agrupador). */
    static SetorDeProcessamento setorProcessamento;

    /** Lista de demandas de chocolates. */
    static ArrayList<Demanda> demandas;

    /** Leitor de entrada via teclado. */
    static Scanner teclado = new Scanner(System.in);

    /** Cenário de operação selecionado. */
    static Cenario cenarioEsc;

    /**
     * Ponto de entrada do programa. Inicializa o cenário e executa o loop principal com tratamento de exceções.
     *
     * @param args Argumentos de linha de comando.
     */
    public static void main(String[] args) {
        System.out.println(
                    "===========================================================\n" + 
                    "|          A NÃO-FANTÁSTICA FÁBRICA DE CHOCOLATES         |\n" + 
                    "|              Chocolate além da imaginação               |\n" + 
                    "===========================================================\n" + 
                    "Bem-vindos à nossa fábrica de chocolates!\n" + 
                    "Uma fábrica feita para transformar chocolate em experiências mágicas.\n" + 
                    "Descubra, experimente e deixe a imaginação te levar!\n" + 
                    "Desenvolvido por: Laura Póvoas\n" + 
                    "===========================================================\n"+
                    "SELECIONE O CENÁRIO DE OPERAÇÃO:\n" + 
                    "1 - Cenário Ideal \n" +
                    "2 - Cenário Apocalíptico \n" +
                    "Escolha: ");

        try {
            int opCen = teclado.nextInt();
            if (opCen == 1) {
                cenarioEsc = Cenario.IDEAL;
            } else if (opCen == 2) {
                cenarioEsc = Cenario.APOCALIPTICO;
            } else {
                throw new PlantaException("Opção de cenário inválida!");
            }
        } catch (InputMismatchException e) {
            teclado.nextLine();
            exibirMensagemErro("Entrada inválida! Valor padrão (Cenário Ideal) foi selecionado.");
            cenarioEsc = Cenario.IDEAL;
        } catch (PlantaException e) {
            exibirMensagemErro(e.getMessage() + " Valor padrão (Cenário Ideal) foi selecionado.");
            cenarioEsc = Cenario.IDEAL;
        }

        System.out.println(
                "===========================================================\n" +
                "|                     PLANTA INDUSTRIAL                   |\n" +
                "===========================================================\n");
        inicializa(cenarioEsc);

        while (true) {
            try {
                menu();
            } catch (PlantaException e) {
                exibirMensagemErro(e.getMessage());
            } catch (InputMismatchException e) {
                teclado.nextLine();
                exibirMensagemErro("Entrada inválida! Digite apenas números inteiros no menu.");
            } catch (Exception e) {
                exibirMensagemErro("Ocorreu um erro inesperado: " + e.getMessage());
            }
        }
    }

    /**
     * Instancia os objetos fundamentais da fábrica (matéria-prima, produtos, máquinas e demandas).
     *
     * @param cenario Cenário de simulação escolhido.
     */
    private static void inicializa(Cenario cenario) {
        mp1 = new MateriaPrima(1, "Manteiga de Cacau", 900, "kg", 3.0);
        System.out.println("Matéria-prima: " + mp1.getId() + " - " + mp1.getNome());

        System.out.println("\nProdutos disponíveis:");
        p1 = new ChocolatePremium(1, "Barra de chocolate", "AQ1");
        System.out.println(p1.getId() + " - " + p1.getNome() + " (Matéria-prima necessária: " + p1.getQuantidadeMateriaPrimaNecessaria() + " kg)");
        p2 = new ChocolateMeiaBoca(2, "Ovo de Páscoa", "MQ1");
        System.out.println(p2.getId() + " - " + p2.getNome() + " (Matéria-prima necessária: " + p2.getQuantidadeMateriaPrimaNecessaria() + " kg)");
        p3 = new ChocolateSeboso(3, "Bombom de guarda-chuva", "BQ1");
        System.out.println(p3.getId() + " - " + p3.getNome() + " (Matéria-prima necessária: " + p3.getQuantidadeMateriaPrimaNecessaria() + " kg)");

        // Instanciação da máquina Todo e Agrupador
        setorProcessamento = new SetorDeProcessamento("Setor Principal de Mistura");
        MaquinaDeProcessamento m1 = new MaquinaDeProcessamento("Batedeira industrial", 2.0f, 1000, 3);
        setorProcessamento.adicionarMaquina(m1);

        ArrayList<Maquina> maquinas = new ArrayList<>();
        Maquina m2 = new MaquinaEmbaladora("Embaladora", 1, 1000);
        Maquina m3 = new MaquinaInspecaoQualidade("Vigilância Sanitária", 3);

        maquinas.add(m1);
        maquinas.add(m2);
        maquinas.add(m3);

        e1 = new Esteira("", 5000);

        gp = new GerenciadorProducao(new ArrayList<Demanda>(), new ArrayList<Produto>(), maquinas, mp1, cenario, e1); 
        Demanda d1 = new Demanda(p1, 10);
        Demanda d2 = new Demanda(p2, 20);
        Demanda d3 = new Demanda(p3, 30);

        gp.registrarDemanda(d1);
        gp.registrarDemanda(d2);
        gp.registrarDemanda(d3);
    }

    /**
     * Exibe o menu principal e executa as opções.
     *
     * @throws PlantaException se a opção selecionada for inválida.
     */
    private static void menu() {
        String nomeEstr = "";
        String nomeCen = "";
        if (gp.getEstrategiaAtual() != null) {
            nomeEstr = gp.getEstrategiaAtual().getNomeEstrategia();
        } else {
            nomeEstr = "Nenhuma selecionada";
        }
        if (gp.getCenarioAtual() != null) {
            nomeCen = gp.getCenarioAtual().getNome();
        } else {
            nomeCen = "Não definido";
        }
        System.out.println(
              "\n===========================================================\n" +
                "|                       MENU PRINCIPAL                    |\n" +
                "===========================================================\n" +
                "ESTRATÉGIA ATUAL: [" + nomeEstr + "]\n" +
                "CENÁRIO ATIVO:    [" + nomeCen + "]\n" +
                "ORÇAMENTO ATUAL:  R$ "+ gp.getOrçamento() +"\n"+
                "===========================================================\n" +
                "1 - ATUALIZAR DEMANDAS\n"+
                "2 - FABRICAR\n"+
                "3 - CONSULTAR\n"+
                "4 - GERENCIAMENTO DE ESTRATEGIA\n"+
                "5 - AUDITORIA\n"+
                "6 - COMPRAR MATÉRIA-PRIMA\n"+
                "7 - REPARAR MÁQUINAS\n" +
                "0 - SAIR\n"+
                "Escolha:");
        int op = teclado.nextInt();
        if (op == 1) {
            submenuAtualizarDemandas();
        } else if (op == 2) {
            submenuFabricar();
        } else if (op == 3) {
            submenuConsultar();
        } else if (op == 4) {
            escolherEstratégia();
        } else if (op == 5) {
            System.out.println("\n--- AUDITORIA DE SISTEMA ---");
            gp.gerarAuditoriaGeral();
            System.out.println(setorProcessamento.gerarRelatorioDiagnostico());
        } else if (op == 6) {
            System.out.println("Entre com a quantidade de matéria-prima a ser comprada:");
            double qtd1 = teclado.nextDouble();
            gp.comprarMateriaPrima(qtd1);
        } else if (op == 7) {
            gp.repararMaquinas();
            setorProcessamento.repararTodas();
        } else if (op == 0) {
            System.out.println("Saindo...");
            System.exit(0);
        } else {
            throw new PlantaException("Opção inválida! Tente novamente.");
        }
    }

    /** Submenu de atualização de demandas. */
    private static void submenuAtualizarDemandas() {
        while (true) {
            try {
                System.out.println(
                      "\n===========================================================\n" +
                        "|                     ATUALIZAR DEMANDAS                  |\n" +
                        "===========================================================\n" +
                        "1 - Atualizar demanda de Barra de chocolate\n" +
                        "2 - Atualizar demanda de Ovo de Páscoa\n" +
                        "3 - Atualizar demanda de Bombom de guarda-chuva\n" +
                        "0 - Voltar ao Menu Principal\n" +
                        "===========================================================\n" +
                        "Escolha: ");
                int op = teclado.nextInt();
                if (op == 0) {
                    break;
                } else if (op == 1) {
                    System.out.println("Entre com a nova demanda:");
                    int dem = teclado.nextInt();
                    gp.atualizarDemanda(0, dem);
                } else if (op == 2) {
                    System.out.println("Entre com a nova demanda:");
                    int dem = teclado.nextInt();
                    gp.atualizarDemanda(1, dem);
                } else if (op == 3) {
                    System.out.println("Entre com a nova demanda:");
                    int dem = teclado.nextInt();
                    gp.atualizarDemanda(2, dem);
                } else {
                    throw new PlantaException("Opção inválida!");
                }
            } catch (PlantaException e) {
                exibirMensagemErro(e.getMessage());
            } catch (InputMismatchException e) {
                teclado.nextLine();
                exibirMensagemErro("Entrada inválida! Digite apenas valores numéricos.");
            }
        }
    }

    /** Submenu de fabricação de demandas. */
    private static void submenuFabricar() {
        while (true) {
            try {
                System.out.println(
                      "\n===========================================================\n" +
                        "|                     FABRICAR DEMANDAS                   |\n" +
                        "===========================================================\n" +
                        "1 - Fabricar Barra de chocolate\n"+
                        "2 - Fabricar Ovo de Páscoa\n"+
                        "3 - Fabricar Bombom de guarda-chuva\n"+
                        "4 - Executar Próxima Produção (Via Estratégia)\n"+
                        "0 - Voltar ao Menu Principal\n" +
                        "===========================================================\n" +
                        "Escolha: ");
                int op = teclado.nextInt();
                if (op == 0) {
                    break;
                } else if (op == 1) {
                    gp.fabricarDemanda(0);
                } else if (op == 2) {
                    gp.fabricarDemanda(1);
                } else if (op == 3) {
                    gp.fabricarDemanda(2);
                } else if (op == 4) {
                    gp.executarProximaProducao();
                } else {
                    throw new PlantaException("Opção inválida!");
                }
            } catch (PlantaException e) {
                exibirMensagemErro(e.getMessage());
            } catch (InputMismatchException e) {
                teclado.nextLine();
                exibirMensagemErro("Entrada inválida! Digite apenas valores numéricos.");
            }
        }
    }

    /** Submenu de consultas. */
    private static void submenuConsultar() {
        while (true) {
            try {
                System.out.println(
                      "\n===========================================================\n" +
                        "|                          CONSULTAR                      |\n" +
                        "===========================================================\n" +
                        "1 - Ver armazém\n"+
                        "2 - Ver estoque de matéria-prima\n"+
                        "0 - Voltar ao Menu Principal\n" +
                        "===========================================================\n" +
                        "Escolha: ");
                int op = teclado.nextInt();
                if (op == 0) {
                    break;
                } else if (op == 1) {
                    gp.exibirArmazem();
                } else if (op == 2) {
                    System.out.println("Quantidade de matéria-prima em estoque: " + mp1.getQuantidade() + " " + mp1.getUnidade());
                } else {
                    throw new PlantaException("Opção inválida!");
                }
            } catch (PlantaException e) {
                exibirMensagemErro(e.getMessage());
            } catch (InputMismatchException e) {
                teclado.nextLine();
                exibirMensagemErro("Entrada inválida! Digite apenas valores numéricos.");
            }
        }
    } 

    /** Menu de seleção de estratégia de produção. */
    private static void escolherEstratégia() {
        try {
            System.out.println("\nEscolha a Estratégia:\n"+
                                "1 - Ordem de Chegada\n" +
                                "2 - Maior Demanda\n" +
                                "3 - Maximizar Produção\n" +
                                "Escolha: " );
            int op = teclado.nextInt();
            if (op == 1) { 
                gp.setEstrategia(new EstrategiaChocolento());
            } else if (op == 2) {
                gp.setEstrategia(new EstrategiaChocolumosa());
            } else if (op == 3) { 
                gp.setEstrategia(new EstrategiaMegalochocolatuda());
            } else {
                throw new PlantaException("Estratégia inválida!");
            }
        } catch (PlantaException e) {
            exibirMensagemErro(e.getMessage());
        } catch (InputMismatchException e) {
            teclado.nextLine();
            exibirMensagemErro("Entrada inválida! Digite apenas valores numéricos.");
        }
    }

    /** Apresenta a caixa de alerta de erro. */
    private static void exibirMensagemErro(String mensagem) {
        System.out.println("\n========================================");
        System.out.println("ERRO");
        System.out.println("========================================");
        System.out.println(mensagem);
        System.out.println("Nenhuma alteração foi realizada.");
        System.out.println("Pressione ENTER para continuar...");
        teclado.nextLine();
        teclado.nextLine();
    }
}