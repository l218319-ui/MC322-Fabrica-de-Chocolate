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

    public static void main(String[] args) throws Exception {
        System.out.println(
                    "==============================================\n" + 
                    "   A NÃO-FANTÁSTICA FÁBRICA DE CHOCOLATES\n" + 
                    "   Chocolate além da imaginação\n" + 
                    "==============================================\n" + 
                    "Bem-vindos à nossa fábrica de chocolates!\n" + 
                    "Uma fábrica feita para transformar chocolate em experiências mágicas.\n" + 
                    "Descubra, experimente e deixe a imaginação te levar!\n" + 
                    "Desenvolvido por: Laura Póvoas\n" + 
                    "==============================================\n");
        System.out.println(
                "==============================================\n" +
                "               PLANTA INDUSTRIAL\n" +
                "==============================================\n");
        inicializa();
        while(true) {
            menu();
        }
    }

    // Instanciação dos objetos + informações na telinha:
    private static void inicializa() {
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

        e1 = new Esteira("", 5000);

        gp = new GerenciadorProducao(new ArrayList<Demanda>(), new ArrayList<Produto>(), maquinas, mp1, 1000.0D, e1, null); 
        Demanda d1 = new Demanda(p1, 10);
        Demanda d2 = new Demanda(p2, 10);
        Demanda d3 = new Demanda(p3, 10);
        gp.registrarDemanda(d1);
        gp.registrarDemanda(d2);
        gp.registrarDemanda(d3);
    }

    // Menu com opções númericas:
    private static void menu() {
        System.out.println(
                "\n============================================\n" +
                "               MENU PRINCIPAL              \n" +
                "============================================\n" +
                "BUDGET ATUAL: "+ gp.getOrçamento() +"\n"+

                "ATUALIZAR DEMANDAS\n"+
                "1 - Atualizar demanda de Barra de chocolate\n"+
                "2 - Atualizar demanda de Ovo de Páscoa\n"+
                "3 - Atualizar demanda de Bombom amargo\n"+
                "FABRICAR\n"+
                "4 - Fabricar Barra de chocolate\n"+
                "5 - Fabricar Ovo de Páscoa\n"+
                "6 - Fabricar Bombom de guarda-chuva\n"+
                "CONSULTAR\n"+
                "7 - Ver armazém\n"+
                "8 - Ver estoque de matéria-prima\n"+
                "COMPRAR MATÉRIA-PRIMA\n"+
                "9 - Comprar matéria-prima\n"+
                "0 - SAIR\n"+
                "Escolha:");

        Scanner teclado = new Scanner(System.in);
        int op = teclado.nextInt();
        if (op == 1) {
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
        } else if (op == 4) {
            gp.fabricarDemanda(0);
        } else if (op == 5) {
            gp.fabricarDemanda(1);
        } else if (op == 6) {
            gp.fabricarDemanda(2);
        } else if (op == 7) {
            gp.exibirArmazem();
        } else if (op == 8) {
            System.out.println("Quantidade de matéria-prima em estoque: " + mp1.getQuantidade());
        } else if (op == 9) {
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
}

    //(sem uso):
    // Seleção de produtos + processo de produção:
    /*private static void iniciarProdução() {
        Scanner teclado = new Scanner(System.in);
        System.out.println("\nSelecione o produto (1-3): ");
        int produtoEscolhido = teclado.nextInt();
        System.out.println("\nSelecione a quantidade a ser produzida: ");
        int quantidadeParaProducao = teclado.nextInt();
        Produto p;
        if (produtoEscolhido == 1) {
            p = p1;
        } else if (produtoEscolhido == 2) {
            p = p2;
        } else if (produtoEscolhido == 3) {
            p = p3;
        } else {
            System.out.println("Esse produto não existe!");
            return;
        }

        System.out.println("[OK] Verificando a disponibilidade de Matéria-prima...");*/

        /*if (m1.temEstoqueSuficiente(mp1, p, quantidadeParaProducao)) {
            System.out.println("[OK] A demanda de " + quantidadeParaProducao + " produtos pode ser atendida.");

            e1.ligar();

            System.out.println("[OK] Matéria-prima "+mp1.getNome()+" colocada na esteira.");
            e1.adicionarItem(mp1.getNome(), quantidadeParaProducao, p);
            
            System.out.println("[OK] Matéria-prima transportada até a máquina.");
            e1.removerItem();

            m1.ligar();
            System.out.println("[OK] Máquina ligada...");

            System.out.println("[OK] Máquina processando " + quantidadeParaProducao * p.getQuantidadeMateriaPrimaNecessaria()
                    + " kg " + mp1.getNome());
            //m1.processar(mp1, p, quantidadeParaProducao);

            System.out.println("[OK] Produto " + p.getId() + " - " + p.getNome() + " criado.");

            System.out.println("[OK] Produto "+p.getNome()+" transportado para inspeção.");
            e1.adicionarItem(p.getNome(), quantidadeParaProducao, p);

            i1.ativar();

            System.out.println("[OK] Produto "+p.getNome()+" aprovado na inspeção.");
            i1.inspecionar();
            System.out.println("Estoque restante de "+mp1.getNome()+": "+mp1.getQuantidade()+" "+mp1.getUnidade());
        }*/
    //};