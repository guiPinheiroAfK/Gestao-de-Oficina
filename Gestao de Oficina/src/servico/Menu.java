package servico;

import banco.VeiculoDAO;
import modelo.Veiculo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Menu {

    public void iniciar() throws InterruptedException {

        Scanner scanner = new Scanner(System.in);
        VeiculoDAO dao = new VeiculoDAO();
        List<Veiculo> patioDinamico = new ArrayList<>();


        try {
            ExtraMain.atualizarListaLocal(patioDinamico, dao);
        } catch (RuntimeException e) {
            System.out.println("Aviso ao carregar patio: " + e.getMessage());
        }

        int opcao = -1;

        while (opcao != 0) {
            System.out.println("✅ " + patioDinamico.size() + " veículos carregados do banco!");
            // Faz o mesmo que o "sleep() " em C
            TimeUnit.SECONDS.sleep(2);


            ExtraMain.limparTela();
            System.out.println("========================================");
            System.out.println("            Oficina do Gui ");
            System.out.println("========================================");
            System.out.println("1 - Cadastrar Veiculo");
            System.out.println("2 - Excluir Veiculo");
            System.out.println("3 - Listar Veiculos");
            System.out.println("4 - Atualizar Veiculos");
            System.out.println("5 - Gerenciar Pecas");
            System.out.println("6 - Visualizar Catalogo de Pecas");
            System.out.println("7 - Simular Orcamento");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            String entrada = scanner.nextLine().trim();
            try {
                opcao = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas numeros no menu principal.");
                System.out.print("Pressione ENTER para continuar...");
                scanner.nextLine();
                continue;
            }

            try {
                switch (opcao) {
                    case 1:
                        ExtraMain.cadastrarVeiculo(scanner, patioDinamico, dao);
                        break;

                    case 2:
                        ExtraMain.removerVeiculoPorPlaca(scanner, patioDinamico, dao);
                        break;

                    case 3:
                        ExtraMain.listarVeiculosNoPatio(scanner, dao);
                        break;

                    case 4:
                        ExtraMain.atualizarDadosVeiculo(dao, patioDinamico, scanner);
                        break;

                    case 5:
                        ExtraMain.gerenciarPecas(scanner);
                        break;

                    case 6:
                        ExtraMain.exibirCatalogoDePecas(scanner);
                        break;

                    case 7:
                        ExtraMain.iniciarFluxoOrcamento(scanner, dao);
                        break;

                    case 0:
                        ExtraMain.limparTela();
                        System.out.println("Sistema encerrado.");
                        break;

                    default:
                        System.out.println("Opcao invalida.");
                        System.out.print("Pressione ENTER para continuar...");
                        scanner.nextLine();
                }
            } catch (RuntimeException e) {
                System.out.println("Erro da operacao: " + e.getMessage());
                System.out.print("Pressione ENTER para continuar...");
                scanner.nextLine();
            }
        }

        scanner.close();
    }
}
