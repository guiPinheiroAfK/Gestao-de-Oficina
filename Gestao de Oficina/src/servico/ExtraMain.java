package servico;

import banco.VeiculoDAO;
import modelo.*;
import banco.PecaDAO;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

// "ai q nome feio"... faltou criatividade :c
public class ExtraMain {

    // sincronizar, ja tava na main antes >:c
    public static void sincronizar(List<Veiculo> veiculos, VeiculoDAO dao) {
        veiculos.clear();
        veiculos.addAll(dao.buscarTodos());
    }

    // preciso dizer algo?
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        VeiculoDAO dao = new VeiculoDAO();


        // joga tudo para uma lista antes de rodar o menu
        List<Veiculo> patioDinamico = dao.buscarTodos();
        System.out.println("✅ " + patioDinamico.size() + " veículos carregados do banco!");
        // faz o mesmo que o "sleep() " em C
        TimeUnit.SECONDS.sleep(2);

        menuPrincipal(scanner, patioDinamico, dao);
    }

    // op. 1
    public static void func1(Scanner scanner, List<Veiculo> patioDinamico, VeiculoDAO dao){
        try {
            System.out.println("\n--- Cadastro de Veículo ---");
            System.out.print("Digite a Placa: ");
            String placaNovo = scanner.nextLine();

            System.out.print("Digite o Modelo: ");
            String modeloNovo = scanner.nextLine();

            System.out.print("Digite o Ano: ");
            int anoNovo = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Tipo (CARRO/MOTO): ");
            String tipoNovo = scanner.nextLine().toUpperCase();

            Veiculo v = null;

            if (tipoNovo.equals("CARRO")) {
                v = new Carro(placaNovo, modeloNovo, anoNovo, tipoNovo);
            } else if (tipoNovo.equals("MOTO")){
                v = new Moto(placaNovo, modeloNovo, anoNovo, tipoNovo);
            } else
                System.out.println("❌ Tipo inválido! Use CARRO ou MOTO.");

            dao.salvar(v, tipoNovo);
            sincronizar(patioDinamico, dao);

            System.out.println("\n✅ Veículo " + v.getModelo() + " cadastrado com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("⚠️ Erro ao cadastrar: " + e.getMessage());
        }
    }

    // op. 2
    public static void func2(Scanner scanner, List<Veiculo> patioDinamico, VeiculoDAO dao){
        System.out.print("Digite a placa para apagar: ");
        String placa = scanner.nextLine();
        dao.deletar(placa);
        sincronizar(patioDinamico, dao); // <- recarrega a lista
    }

    // op. 3
    public static void func3(VeiculoDAO dao) throws InterruptedException {
        System.out.println("\n--- Lista de Veículos no Pátio ---");
        List<Veiculo> lista = dao.buscarTodos();
        if (lista.isEmpty()) {
            System.out.println("O pátio está vazio!");
        } else {
            for (Veiculo v : lista) {
                System.out.println("Modelo: " + v.getModelo() + " | Placa: " + v.getPlaca() + " | Ano: " + v.getAno());
            } TimeUnit.SECONDS.sleep(2);
        }

        // todos os salvar/buscar/deletar estao no "banco.VeiculoDAO"
        // e as heranças/polimorfismo estão no "modelo."
    }

    // op. 4
    public static void func4(VeiculoDAO dao, List<Veiculo> patioDinamico){
        // ex: mudar o 370z para um 370z NISMO (18 cv a mais)
        System.out.println("\n--- Atualizando Veículo ---");
        Carro atualizado = new Carro("ABC-1234", "Nissan 370z NISMO", 2025, "Carro");
        dao.atualizar(atualizado);
        sincronizar(patioDinamico, dao); // <- <- recarrega a lista
    }

    // op. 5
    public static void func5(Scanner scanner){
        System.out.println("\n--- Cadastro de Peça no Catálogo ---");
        System.out.print("Nome da peça: ");
        String nomePeca = scanner.nextLine();

        System.out.print("Valor unitário: ");
        double valorPeca = scanner.nextDouble();

        System.out.print("Quantidade em estoque: ");
        int qtdPeca = scanner.nextInt();

        if (valorPeca < 0 || qtdPeca < 0) {
            System.out.println("⚠️ Erro: Valores não podem ser negativos!");
        } else {
            Peca novaPeca = new Peca(nomePeca, valorPeca, qtdPeca);
            new PecaDAO().salvar(novaPeca);
            System.out.println("✅ Peça adicionada ao catálogo!");
        }
    }

    // op. 6
    public static void func6(Scanner scanner){
        System.out.println("\n--- Catálogo de Peças Cadastradas ---");
        List<Peca> listaPecas = new PecaDAO().buscarTodas(); // Chama o banco

        if (listaPecas.isEmpty()) {
            System.out.println("O catálogo está vazio. Cadastre algo primeiro!");
        } else {
            // Percorre a lista e imprime cada peça
            for (Peca p : listaPecas) {
                System.out.printf("ID: %d | Nome: %-15s | Preço: R$ %8.2f | Estoque: %d unidades%n",
                        p.getId(), p.getNome(), p.getValor(), p.getEstoque());
            }
        }
        System.out.println("\nPresione ENTER para voltar ao menu...");
        scanner.nextLine(); // Este cara "segura" a tela para você conseguir ler
    }

    // op. 7
    public static void func7(Scanner scanner, VeiculoDAO dao){
        System.out.println("\n--- Gerando Orçamento Real ---");
        System.out.print("Digite a placa do veículo cadastrado: ");
        String placaBusca = scanner.nextLine();

        Veiculo vEncontrado = dao.buscarPorPlaca(placaBusca);

        if (vEncontrado == null) {
            System.out.println("Mentiroso! n tem veículo");
            return;
        }

        System.out.println(vEncontrado.getTipo());
        if ("CARRO".equalsIgnoreCase(vEncontrado.getTipo())){
            System.out.println("vc definitivamente tem um carro!");
            menuFunc7(scanner);
        } else{

            System.out.println("vc definitivamente tem uma moto!");
            menuFunc7(scanner);
        }



            /*ServicoOficina oficinaService = new ServicoOficina();
            double valorFinal = oficinaService.calcularOrcamento(vEncontrado, pecasParaServico);

            System.out.println("\n--- RESUMO DO ORÇAMENTO ---");
            System.out.println("Veículo: " + vEncontrado.getModelo());
            System.out.println("Total: R$ " + valorFinal);*/
        }

    // menu op. 7
    public static void menuFunc7 (Scanner scanner){
        List<Peca> pecasParaServico = new java.util.ArrayList<>();
        System.out.println("\n===TIPO DE SERVIÇOS===");
        System.out.println("1. Manutenção Preventiva (Revisão Periódica)");
        System.out.println("2. Manutenção Corretiva (Reparos)");
        System.out.println("3- Diagnóstico e Injeção Eletrônica");
        System.out.println("4. Serviços de Estética, Limpeza e Funilaria");
        System.out.println("5. Serviços de Pneus e Rodas");
        System.out.println("Escolha uma opção: ");
        int tipo = scanner.nextInt();
            innerMenuServices(scanner, tipo);
    }

    // menu principal
    public static void menuPrincipal(Scanner scanner, List<Veiculo> patioDinamico ,VeiculoDAO dao) throws InterruptedException {
        int opcao = -1;
        while (opcao != 0) {
            // menu bem basicao
            System.out.println("\n--- Oficina do Gui ---");
            System.out.println("1 - Cadastrar Veículo");
            System.out.println("2 - Apagar Veículo por Placa");
            System.out.println("3 - Listar Veículos no Pátio");
            System.out.println("4 - Atualizar Veículos no Pátio");
            System.out.println("5 - Cadastrar peça");
            System.out.println("6 - Vizualizar peças cadastradas");
            System.out.println("7 - Simular orçamento");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("oi rs");
                    func1(scanner, patioDinamico, dao);
                    break;

                case 2:
                    func2(scanner, patioDinamico, dao);
                    break;

                case 3:
                    func3(dao);
                    break;

                case 4:
                    func4(dao, patioDinamico);
                    break;

                case 5:
                    func5(scanner);
                    break;

                case 6: // Ou o próximo número livre no seu menu
                    func6(scanner);
                    break;

                case 7:
                    func7(scanner, dao);
                    break;
                case 0:
                    System.out.println("Saindo... Até logo!");
                    break;

                default:
                    System.out.println("Opção inválida! >:c");

                    // ele não aceita letras, ent tem q arrumar isso tb
            }
        }
        // scanner.close();
    }

    // menu dentro do orçamento
    public static void innerMenuServices(Scanner scanner, int tipo){
    switch (tipo){
            case 1:
                System.out.println("1- Troca de Óleo e Filtros:");
                System.out.println("2- Revisão de Freios:");
                System.out.println("3- Sistema de Arrefecimento:");
                System.out.println("4- Verificação de Velas e Correias:");
                System.out.println("5- Alinhamento e Balanceamento:");
                System.out.println("6- Verificação da Bateria:");
                scanner.nextInt();
                    minMenu1(scanner, tipo);
                break;
            case 2:
                System.out.println("1- Reparo de Motor:");
                System.out.println("2- Serviços de Suspensão: Troca de amortecedores, molas, buchas e bandejas.");
                System.out.println("3- Sistema de Direção: Reparo em direção hidráulica ou elétrica.");
                System.out.println("4- Transmissão e Embreagem: Troca de kit de embreagem ou manutenção do câmbio automático.");
                System.out.println("5- Reparo no Sistema de Escapamento:");
                scanner.nextInt();
                    minMenu2(scanner, tipo);
                break;
            case 3:
                System.out.println("1- Diagnóstico Computadorizado:");
                System.out.println("2- Sistema de Iluminação:");
                System.out.println("3- Motor de Arranque e Alternador:");
                System.out.println("4- Vidros e Travas Elétricas:");
                scanner.nextInt();
                    minMenu3(scanner, tipo);
                break;
            case 4:
                System.out.println("1- Lavagem Detalhada e Higienização:");
                System.out.println("2- Funilaria e Pintura:");
                System.out.println("3- Polimento e Cristalização/Vitrificação:");
                System.out.println("4- Martelinho de Ouro:");
                scanner.nextInt();
                    minMenu4(scanner, tipo);
                break;
            case 5:
                System.out.println("1- Troca de Pneus:");
                System.out.println("2- Reparo de Pneus:");
                scanner.nextInt();
                    minMenu5(scanner, tipo);
                break;
            default:
                System.out.println("Não tinha essa opção...");
        }
    }

    // menus base para orçamento
    public static void minMenu1(Scanner scanner, int tipo){
        while (tipo != 0){
            switch(tipo){
                case 1:
                    System.out.println("1- Troca de Óleo e Filtros");
                    scanner.nextInt(tipo);
                    break;
                case 2:
                    System.out.println("2- Revisão de Freios");
                    scanner.nextInt(tipo);
                    break;
                case 3:
                    System.out.println("3- Sistema de Arrefecimento");
                    scanner.nextInt(tipo);
                    break;
                case 4:
                    System.out.println("4- Verificação de Velas e Correias");
                    scanner.nextInt(tipo);
                    break;
                case 5:
                    System.out.println("5- Alinhamento e Balanceamento");
                    scanner.nextInt(tipo);
                    break;
                case 6:
                    System.out.println("6- Verificação da Bateria");
                    scanner.nextInt(tipo);
                    break;
                default:
                    System.out.println("Quer ser especial é?");
            }
        }
    }

    public static void minMenu2(Scanner scanner, int tipo){
        while (tipo != 0) {
            switch (tipo) {
                case 1:
                    System.out.println("1- Reparo de Motor");
                    scanner.nextInt(tipo);
                    break;
                case 2:
                    System.out.println("2- Serviços de Suspensão: Troca de amortecedores, molas, buchas e bandejas");
                    scanner.nextInt(tipo);
                    break;
                case 3:
                    System.out.println("3- Sistema de Direção: Reparo em direção hidráulica ou elétrica");
                    scanner.nextInt(tipo);
                    break;
                case 4:
                    System.out.println("4- Transmissão e Embreagem: Troca de kit de embreagem ou manutenção do câmbio automático");
                    scanner.nextInt(tipo);
                    break;
                case 5:
                    System.out.println("5- Reparo no Sistema de Escapamento");
                    scanner.nextInt(tipo);
                    break;
                default:
                    System.out.println("Quer ser especial é?");
            }
        }
    }

    public static void minMenu3(Scanner scanner, int tipo){
        while (tipo != 0) {
            switch (tipo) {
                case 1:
                    System.out.println("1- Diagnóstico Computadorizado");
                    scanner.nextInt(tipo);
                    break;
                case 2:
                    System.out.println("2- Sistema de Iluminação");
                    scanner.nextInt(tipo);
                    break;
                case 3:
                    System.out.println("3- Motor de Arranque e Alternador");
                    scanner.nextInt(tipo);
                    break;
                case 4:
                    System.out.println("4- Vidros e Travas Elétricas");
                    scanner.nextInt(tipo);
                    break;
                default:
                    System.out.println("Quer ser especial é?");
                }
        }
    }

    public static void minMenu4(Scanner scanner, int tipo){
        while (tipo != 0) {
            switch (tipo) {
                case 1:
                    System.out.println("1- Lavagem Detalhada e Higienização");
                    scanner.nextInt(tipo);
                    break;
                case 2:
                    System.out.println("2- Funilaria e Pintura");
                    scanner.nextInt(tipo);
                    break;
                case 3:
                    System.out.println("3- Polimento e Cristalização/Vitrificação");
                    scanner.nextInt(tipo);
                    break;
                case 4:
                    System.out.println("4- Martelinho de Ouro");
                    scanner.nextInt(tipo);
                    break;
                default:
                    System.out.println("Quer ser especial é?");
                }
        }
    }

    public static void minMenu5(Scanner scanner, int tipo){
        while (tipo != 0) {
            switch (tipo) {
                case 1:
                    System.out.println("1- Troca de Pneus");
                    scanner.nextInt(tipo);
                    break;
                case 2:
                    System.out.println("2- Reparo de Pneus");
                    scanner.nextInt(tipo);
                    break;
                default:
                    System.out.println("Quer ser especial é?");
            }
        }
    }
}