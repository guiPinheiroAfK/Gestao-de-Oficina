package servico;

import banco.VeiculoDAO;
import modelo.*;
import banco.PecaDAO;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

// "ai q nome feio"... faltou criatividade :c
public class ExtraMain {

    // atualizarListaLocal, ja tava na main antes >:c
    public static void atualizarListaLocal(List<Veiculo> veiculos, VeiculoDAO dao) {
        veiculos.clear();
        veiculos.addAll(dao.buscarTodos());
    }

    // evita que tenhamos que repetir loops de impressão em cada 'case' do menu.
    private static void exibirLista(List<Veiculo> lista) throws InterruptedException {
        if (lista.isEmpty()) {
            System.out.println("Nada encontrado! :c");
        } else {
            for (Veiculo v : lista) {
                System.out.println("Modelo: " + v.getModelo() + " | Placa: " + v.getPlaca() + " | Ano: " + v.getAno());
            }
            TimeUnit.SECONDS.sleep(2);
        }
    }

    // op. 1
    public static void cadastrarVeiculo(Scanner scanner, List<Veiculo> patioDinamico, VeiculoDAO dao){
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
            atualizarListaLocal(patioDinamico, dao);

            System.out.println("\n✅ Veículo " + v.getModelo() + " cadastrado com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("⚠️ Erro ao cadastrar: " + e.getMessage());
        }
    }

    // op. 2
    public static void removerVeiculoPorPlaca(Scanner scanner, List<Veiculo> patioDinamico, VeiculoDAO dao){
        System.out.print("Digite a placa para apagar: ");
        String placa = scanner.nextLine();
        dao.deletar(placa);
        atualizarListaLocal(patioDinamico, dao); // <- recarrega a lista
    }

    // op. 3
    public static void listarVeiculosNoPatio(Scanner scanner, VeiculoDAO dao) throws InterruptedException {
        int escopo = -1;
        String filtroTipo = null;

        // para validar a escolha inicial (CARRO/MOTO/AMBOS)
        while (escopo < 1 || escopo > 3) {
            System.out.println("\n--- O que deseja visualizar? ---");
            System.out.println("1 - Apenas CARROS");
            System.out.println("2 - Apenas MOTOS");
            System.out.println("3 - AMBOS (Ver pátio completo)");
            System.out.print("Escolha uma opção: ");

            // verifica se o que foi digitado é um número para evitar erro de letra
            if (scanner.hasNextInt()) {
                escopo = scanner.nextInt();
                scanner.nextLine();

                if (escopo == 1) {
                    filtroTipo = "CARRO";
                } else if (escopo == 2) {
                    filtroTipo = "MOTO";
                } else if (escopo == 3) {
                    filtroTipo = null; // AMBOS
                } else {
                    System.out.println(" Opção inválida! Escolha 1, 2 ou 3. >:c");
                }
            } else {
                System.out.println(" Erro: Digite apenas números! >:c");
                scanner.nextLine(); // Limpa o buffer do erro
            }
        }

        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n------------------------------");
            System.out.println("Listando: " + (filtroTipo == null ? "TODOS" : filtroTipo + "S"));
            System.out.println("1 - Listar Todos");
            System.out.println("2 - Listar por Ano");
            System.out.println("3 - Listar por Modelo");
            System.out.println("0 - Voltar ao Menu Principal");
            System.out.println("------------------------------");
            System.out.println("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao){
                case 1:
                    exibirLista( (filtroTipo == null) ? dao.buscarTodos() : dao.buscarPorTipo(filtroTipo) );
                    break;

                case 2:
                    System.out.println("\n--- Filtrar por Ano ---");
                    System.out.println("1 - Ver todos em ordem crescente");
                    System.out.println("2 - Buscar um ano específico");
                    System.out.print("Escolha uma opção: ");
                    int subOpcaoAno = scanner.nextInt();
                    scanner.nextLine();

                    if (subOpcaoAno == 1) {
                        exibirLista(dao.buscarOrdenadoPorAno(filtroTipo));
                    } else if (subOpcaoAno == 2) {
                        System.out.print("Digite o ano desejado: ");
                        int anoBusca = scanner.nextInt();
                        scanner.nextLine();
                        exibirLista(dao.buscarPorAnoETipo(anoBusca, filtroTipo));
                    } else {
                        System.out.println("Opção inválida!");
                    }
                    break;

                case 3:
                    System.out.print("Digite o modelo: ");
                    String modelo = scanner.nextLine();
                    exibirLista( dao.buscarPorModeloETipo(modelo, filtroTipo));
                    break;

                case 0:
                    System.out.println("Voltando...");
                    break;

                default:
                    System.out.println("Opção inválida! >:c");

            }

        }

        // todos os salvar/buscar/deletar estao no "banco.VeiculoDAO"
        // e as heranças/polimorfismo estão no "modelo."
    }

    // op. 4
    public static void atualizarDadosVeiculo(VeiculoDAO dao, List<Veiculo> patioDinamico){
        // ex: mudar o 370z para um 370z NISMO (18 cv a mais)
        System.out.println("\n--- Atualizando Veículo ---");
        Carro atualizado = new Carro("ABC-1234", "Nissan 370z NISMO", 2025, "Carro");
        dao.atualizar(atualizado);
        atualizarListaLocal(patioDinamico, dao); // <- <- recarrega a lista
    }

    // op. 5
    public static void cadastrarPecaNoCatalogo(Scanner scanner){
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
    public static void exibirCatalogoDePecas(Scanner scanner){
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
    public static void iniciarFluxoOrcamento(Scanner scanner, VeiculoDAO dao){
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
            menuIniciarFluxoOrcamento(scanner);
        } else{
            System.out.println("vc definitivamente tem uma moto!");
            menuIniciarFluxoOrcamento(scanner);
        }



            /*ServicoOficina oficinaService = new ServicoOficina();
            double valorFinal = oficinaService.calcularOrcamento(vEncontrado, pecasParaServico);

            System.out.println("\n--- RESUMO DO ORÇAMENTO ---");
            System.out.println("Veículo: " + vEncontrado.getModelo());
            System.out.println("Total: R$ " + valorFinal);*/
        }

    // menu op. 7
    public static void menuIniciarFluxoOrcamento(Scanner scanner){
        List<Peca> pecasParaServico = new java.util.ArrayList<>();
        System.out.println("\n===TIPO DE SERVIÇOS===");
        System.out.println("1. "+ ServicosGerais.PREVENTIVA.getDescricao());
        System.out.println("2. "+ ServicosGerais.CORRETIVA.getDescricao());
        System.out.println("3- "+ ServicosGerais.ELETRICA.getDescricao());
        System.out.println("4. "+ ServicosGerais.ESTETICA.getDescricao());
        System.out.println("5. "+ ServicosGerais.PNEUS.getDescricao());
        System.out.println("Escolha uma opção: ");
        int tipo = scanner.nextInt();
            ServicoOrcamento.exibirMenuServicos(scanner, tipo);
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
                    cadastrarVeiculo(scanner, patioDinamico, dao);
                    break;

                case 2:
                    removerVeiculoPorPlaca(scanner, patioDinamico, dao);
                    break;

                case 3:
                    listarVeiculosNoPatio(scanner, dao);
                    break;

                case 4:
                    atualizarDadosVeiculo(dao, patioDinamico);
                    break;

                case 5:
                    cadastrarPecaNoCatalogo(scanner);
                    break;

                case 6:
                    exibirCatalogoDePecas(scanner);
                    break;

                case 7:
                    iniciarFluxoOrcamento(scanner, dao);
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


}