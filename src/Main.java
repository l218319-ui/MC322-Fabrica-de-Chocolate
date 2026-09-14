import java.util.Scanner;

public class Main {
    // Declarações de objetos:
    static MateriaPrima mp1;
    static Produto p1;
    static Produto p2;
    static Produto p3;
    static Maquina m1;
    static Esteira e1;
    static EstacaoInspecao i1;

    public static void main(String[] args) throws Exception {
        System.out.println(
                    "==============================================\n" + 
                    "A NÃO-FANTÁSTICA FÁBRICA DE CHOCOLATES\n" + 
                    "Chocolate além da imaginação\n" + 
                    "==============================================\n" + 
                    "Bem-vindos à nossa fábrica de chocolates!\n" + 
                    "Uma fábrica feita para transformar chocolate em experiências mágicas.\n" + 
                    "Descubra, experimente e deixe a imaginação te levar!\n" + 
                    "Desenvolvido por: Pietra e Laura\n" + 
                    "==============================================\n");
        System.out.println(
                "==============================================\n" +
                "PLANTA INDUSTRIAL\n" +
                "==============================================\n");
        inicializa();
        while(true) {
            menu();
        }
    }

    // Instanciação dos objetos + informações na telinha:
    private static void inicializa() {
        mp1 = new MateriaPrima(1, "Manteiga de Cacau", 10, "kg", 1);
        System.out.println("Matéria-prima: " + mp1.getId() + " - " + mp1.getNome());
        System.out.println("Quantidade: " + mp1.getQuantidade() + " " + mp1.getUnidade());

        System.out.println("\nProdutos disponíveis:");
        p1 = new ChocolateAltaQualidade(1, "Barra de chocolate", false, 2);
        System.out.println(p1.getId() + " - " + p1.getNome() + " (demanda: " + p1.getQuantidadeMateriaPrimaNecessaria() + " kg)");
        p2 = new ChocolateMediaQualidade(2, "Ovo de Páscoa", false, 3);
        System.out.println(p2.getId() + " - " + p2.getNome() + " (demanda: " + p2.getQuantidadeMateriaPrimaNecessaria() + " kg)");
        p3 = new ChocolateComSebo(3, "Bombom amargo", false, 1);
        System.out.println(p3.getId() + " - " + p3.getNome() + " (demanda: " + p3.getQuantidadeMateriaPrimaNecessaria() + " kg)");

        m1 = new Maquina("Batedeira", false, 50);
        System.out.println("\nMáquina inicializada:" + m1.getNome());

        e1 = new Esteira("",false,50);

        i1 = new EstacaoInspecao(false, 0);
    }

    // Seleção de produtos + processo de produção:
    private static void iniciarProdução() {
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

        System.out.println("[OK] Verificando a disponibilidade de Matéria-prima...");

        if (m1.temEstoqueSuficiente(mp1, p, quantidadeParaProducao)) {
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
            m1.processar(mp1, p, quantidadeParaProducao);

            System.out.println("[OK] Produto " + p.getId() + " - " + p.getNome() + " criado.");

            System.out.println("[OK] Produto "+p.getNome()+" transportado para inspeção.");
            e1.adicionarItem(p.getNome(), quantidadeParaProducao, p);

            i1.ativar();

            System.out.println("[OK] Produto "+p.getNome()+" aprovado na inspeção.");
            i1.inspecionar();

            System.out.println(
                    "============================================\n" +
                    "PRODUÇÃO CONCLUÍDA COM SUCESSO! :)\n" +
                    "============================================\n");
            System.out.println("Estoque restante de "+mp1.getNome()+": "+mp1.getQuantidade()+" "+mp1.getUnidade());
        }

    };

    //consulta o estoque de materias primas(só tem uma no caso)??
    private static void consultarEstoque() {
        System.out.println("O estoque de matéria-prima atual é de "+mp1.getQuantidade()+" "+mp1.getUnidade());
    };

    // Menu com opções númericas:
    private static void menu() {
        System.out.println(
                "\n============================================\n" +
                "MENU PRINCIPAL\n" +
                "============================================\n" +
                "1 - Iniciar produção\n" +
                "2 - Consultar estoque\n" +
                "3 - Sair\n" +
                "Escolha:");

        Scanner teclado = new Scanner(System.in);
        int op = teclado.nextInt();
        if (op == 1) {
            iniciarProdução();
        } else if (op == 2) {
            consultarEstoque();
        } else if (op == 3) {
            System.exit(0);
        } else {
            System.out.println("Opção inválida! Tente novamente.");
        }
    }
}
