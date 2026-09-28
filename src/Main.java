import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    // Declarações de objetos:
    static MateriaPrima mp1;
    static Produto p1;
    static Produto p2;
    static Produto p3;
    static Esteira e1;
    static GerenciadorProducao gp;
    static ArrayList<Demanda> demandas;
    static Scanner teclado = new Scanner(System.in);
    static Cenario cenarioEsc;

    public static void main(String[] args) throws Exception {
        System.out.println(
                    "===========================================================\n" + 
                    "|          A NÃO-FANTÁSTICA FÁBRICA DE CHOCOLATES         |\n" + 
                    "|              Chocolate além da imaginação               |\n" + 
                    "===========================================================\n" + 
                    "Bem-vindos à nossa fábrica de chocolates!\n" + 
                    "Uma fábrica feita para transformar chocolate em experiências mágicas.\n" + 
                    "Descubra, experimente e deixe a imaginação te levar!\n" + 
                    "Desenvolvido por: Laura Póvoas\n" + 
                    "===========================================================\n");
        System.out.println("SELECIONE O CENÁRIO DE OPERAÇÃO:\n"
                        + "1 - Cenário Ideal (Orçamento alto, baixa taxa de falhas)\n"
                        + "2 - Cenário Apocalíptico (Orçamento curto, alta taxa de falhas)\n"
                        + "Escolha: ");
        int opCen = teclado.nextInt();
        if (opCen == 1) {
            Cenario cenarioEsc = Cenario.IDEAL;
        } else if (opCen == 2){
            Cenario cenarioEsc = Cenario.APOCALIPTICO;
        } else {
            System.out.println("[ERRO]  Opção inválida! Tente novamente.");
        }
        System.out.println(
                "===========================================================\n" +
                "|                     PLANTA INDUSTRIAL                   |\n" +
                "===========================================================\n");
        inicializa(cenarioEsc);
        while(true) {
            menu();
        }
    }

    // Instanciação dos objetos + informações na telinha:
    private static void inicializa(Cenario cenario) {
        mp1 = new MateriaPrima(1, "Manteiga de Cacau", 900, "kg", 1);
        System.out.println("Matéria-prima: " + mp1.getId() + " - " + mp1.getNome());
        System.out.println("Quantidade: " + mp1.getQuantidade() + " " + mp1.getUnidade());

        System.out.println("\nProdutos disponíveis:");
        p1 = new ChocolatePremium(1, "Barra de chocolate", "AQ1");
        System.out.println(p1.getId() + " - " + p1.getNome() + " (Matéria-prima necessária: " + p1.getQuantidadeMateriaPrimaNecessaria() + " kg)");
        p2 = new ChocolateMeiaBoca(2, "Ovo de Páscoa", "MQ1");
        System.out.println(p2.getId() + " - " + p2.getNome() + " (Matéria-prima necessária: " + p2.getQuantidadeMateriaPrimaNecessaria() + " kg)");
        p3 = new ChocolateSeboso(3, "Bombom de guarda-chuva", "BQ1");
        System.out.println(p3.getId() + " - " + p3.getNome() + " (Matéria-prima necessária: " + p3.getQuantidadeMateriaPrimaNecessaria() + " kg)");

        ArrayList<Maquina> maquinas = new ArrayList<>();
        Maquina m1 = new MaquinaDeProcessamento("Batedeira", 2, 1000);
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

    // Menu com opções númericas:
    private static void menu() {
        System.out.println(
              "\n===========================================================\n" +
                "|                       MENU PRINCIPAL                    |\n" +
                "===========================================================\n" +
                "ESTRATÉGIA ATUAL: [" + gp.getEstrategiaAtual().getNomeEstrategia() + "]\n" +
                "CENÁRIO ATIVO:    [" + gp.getCenarioAtual().getNome() + "]\n" +
                "ORÇAMENTO ATUAL:  R$ "+ gp.getOrçamento() +"\n"+
                "===========================================================\n" +
                "1 - ATUALIZAR DEMANDAS\n"+
                "2 - FABRICAR\n"+
                "3 - CONSULTAR\n"+
                "4 - GERENCIAMENTO DE ESTRATEGIA\n"+
                "5 - AUDITORIA\n"+
                "6 - COMPRAR MATÉRIA-PRIMA\n"+
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
            gp.gerarAuditoriaGeral();
        } else if (op == 6) {
            System.out.println("Entre com a quantidade de matéria-prima a ser comprada:");
            double qtd1 = teclado.nextDouble();
            gp.comprarMateriaPrima(qtd1);
        } else if (op == 0) {
            System.out.println("Saindo...");
            System.exit(0);
        } else {
            System.out.println("[ERRO]  Opção inválida! Tente novamente.");
        }
    }

    //SUBMENUS: 

    private static void submenuAtualizarDemandas() {
        while (true) {
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
            if (op == 0){
                break;
            } else if (op == 1){
                System.out.println("Entre com a nova demanda:");
                int dem = teclado.nextInt();
                gp.atualizarDemanda(0, dem);
            } else if (op == 2){
                System.out.println("Entre com a nova demanda:");
                int dem = teclado.nextInt();
                gp.atualizarDemanda(1, dem);
            } else if (op == 3){
                System.out.println("Entre com a nova demanda:");
                int dem = teclado.nextInt();
                gp.atualizarDemanda(2, dem);
            } else {
                System.out.println("[ERRO] Opção inválida!");
            }
        }
    }

    private static void submenuFabricar() {
        while (true) {
            System.out.println(
                  "\n===========================================================\n" +
                    "|                     FABRICAR DEMANDAS                   |\n" +
                    "===========================================================\n" +
                    "1 - Fabricar Barra de chocolate\n"+
                    "2 - Fabricar Ovo de Páscoa\n"+
                    "3 - Fabricar Bombom de guarda-chuva\n"+
                    "0 - Voltar ao Menu Principal\n" +
                    "===========================================================\n" +
                    "Escolha: ");
            int op = teclado.nextInt();
            if (op == 0){
                break;
            } else if (op == 1){
                gp.fabricarDemanda(0);
            } else if (op == 2){
                gp.fabricarDemanda(1);
            } else if (op == 3){
                gp.fabricarDemanda(2);
            } else {
                System.out.println("[ERRO] Opção inválida!");
            }
        }
    }

    private static void submenuConsultar() {
        while (true) {
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
            if (op == 0){
                break;
            } else if (op == 1){
                gp.exibirArmazem();
            } else if (op == 2){
                System.out.println("Quantidade de matéria-prima em estoque: " + mp1.getQuantidade());
            } else {
                System.out.println("[ERRO] Opção inválida!");
            }
        }
    } 
    
    private static void escolherEstratégia() {
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
            System.out.println("[ERRO] Estratégia inválida!");
        }
    }
}