package servico;

import modelo.OrdemServico;
import modelo.TipoServico;

import java.util.Scanner;

public class ServicoOrcamento {
    // Menu do orçamento
    public static void exibirMenuServicos(Scanner scanner, int tipo, OrdemServico os){
        switch (tipo){
            case 1:
                submenuPreventiva(scanner, os);
                break;
            case 2:
                submenuCorretiva(scanner, os);
                break;
            case 3:
                submenuEletrica(scanner, os);
                break;
            case 4:
                submenuEstetica(scanner, os);
                break;
            case 5:
                submenuPneus(scanner, os);
                break;
            default:
                System.out.println("Não tinha essa opção...");
        }
    }

    // menus base para orçamento
    public static void submenuPreventiva(Scanner scanner, OrdemServico os) {
        int escolhaSub = -1;
        while (escolhaSub != 0) {
            System.out.println("Preventiva");
            System.out.println("1- " + TipoServico.OleoFiltro.getDescricao());
            System.out.println("2- " + TipoServico.RevFreio.getDescricao());
            System.out.println("3- " + TipoServico.Arrefecimento.getDescricao());
            System.out.println("4- " + TipoServico.VelasCorreia.getDescricao());
            System.out.println("5- " + TipoServico.AlinhaBalanca.getDescricao());
            System.out.println("6- " + TipoServico.Bateria.getDescricao());
            System.out.println("0- Voltar");

            System.out.print("Escolha uma sub-opção: ");
            escolhaSub = scanner.nextInt(); // 1o lê para evitar loop infinito
            scanner.nextLine(); // Limpa o buffer

            switch (escolhaSub) {
                case 1: System.out.println(">> Selecionado: \nTroca de Óleo");
                    os.adicionarSubServico(TipoServico.OleoFiltro);
                    System.out.println("Adicionado!");
                    break;

                case 2: System.out.println(">> Selecionado: Revisão de Freios");
                    os.adicionarSubServico(TipoServico.RevFreio);
                    System.out.println("Adicionado!");
                    break;

                case 3: System.out.println(">> Selecionado: Arrefecimento");
                    os.adicionarSubServico(TipoServico.Arrefecimento);
                    System.out.println("Adicionado!");
                    break;

                case 4: System.out.println(">> Selecionado: Velas e Correias");
                    os.adicionarSubServico(TipoServico.VelasCorreia);
                    System.out.println("Adicionado!");
                    break;

                case 5: System.out.println(">> Selecionado: Alinhamento");
                    os.adicionarSubServico(TipoServico.AlinhaBalanca);
                    System.out.println("Adicionado!");
                    break;

                case 6: System.out.println(">> Selecionado: Bateria");
                    os.adicionarSubServico(TipoServico.Bateria);
                    System.out.println("Adicionado!");
                    break;

                case 0: System.out.println("Voltando...");
                    break;

                default: System.out.println("Opção inválida! >:c");
            }
        }
    }

    public static void submenuCorretiva(Scanner scanner, OrdemServico os) {
        int escolhaSub = -1;
        while (escolhaSub != 0) {
            System.out.println("Corretiva");
            System.out.println("1- " + TipoServico.ReparoMotor.getDescricao());
            System.out.println("2- " + TipoServico.Suspensao.getDescricao());
            System.out.println("3- " + TipoServico.Direcao.getDescricao());
            System.out.println("4- " + TipoServico.TransmissaoEmbreagem.getDescricao());
            System.out.println("5- " + TipoServico.Escapamento.getDescricao());
            System.out.println("0- Voltar");
            System.out.print("Escolha: ");

            escolhaSub = scanner.nextInt();
            scanner.nextLine();

            switch (escolhaSub) {
                case 1:
                    System.out.println(">> Selecionado: Reparo de Motor");
                    os.adicionarSubServico(TipoServico.ReparoMotor);
                    System.out.println("Adicionado!");
                    break;

                case 2:
                    System.out.println(">> Selecionado: Suspensão");
                    os.adicionarSubServico(TipoServico.Suspensao);
                    System.out.println("Adicionado!");
                    break;

                    case 3:
                    System.out.println(">> Selecionado: Direção");
                    os.adicionarSubServico(TipoServico.Direcao);
                    System.out.println("Adicionado!");
                    break;

                case 4:
                    System.out.println(">> Selecionado: Transmissão");
                    os.adicionarSubServico(TipoServico.TransmissaoEmbreagem);
                    System.out.println("Adicionado!");
                    break;

                case 5:
                    System.out.println(">> Selecionado: Escapamento");
                    os.adicionarSubServico(TipoServico.Escapamento);
                    System.out.println("Adicionado!");
                    break;

                case 0:
                    System.out.println("Voltando...");
                    break;
                default: System.out.println("Opção inválida! >:c");
            }
        }
    }

    public static void submenuEletrica(Scanner scanner, OrdemServico os) {
        int escolhaSub = -1;
        while (escolhaSub != 0) {
            System.out.println("Elétrica");
            System.out.println("1- " + TipoServico.Diagnostico.getDescricao());
            System.out.println("2- " + TipoServico.Iluminacao.getDescricao());
            System.out.println("3- " + TipoServico.MotorArranque.getDescricao());
            System.out.println("4- " + TipoServico.VidrosTravas.getDescricao());
            System.out.println("0- Voltar");
            System.out.print("Escolha: ");

            escolhaSub = scanner.nextInt();
            scanner.nextLine();

            switch (escolhaSub) {
                case 1:
                    System.out.println(">> Selecionado: Diagnóstico");
                    os.adicionarSubServico(TipoServico.Diagnostico);
                    System.out.println("Adicionado!");
                    break;
                case 2:
                    System.out.println(">> Selecionado: Iluminação");
                    os.adicionarSubServico(TipoServico.Iluminacao);
                    System.out.println("Adicionado!");
                break;
                case 3:
                    System.out.println(">> Selecionado: Motor de Arranque");
                    os.adicionarSubServico(TipoServico.MotorArranque);
                    System.out.println("Adicionado!");
                    break;
                case 4:
                    System.out.println(">> Selecionado: Vidros/Travas");
                    os.adicionarSubServico(TipoServico.VidrosTravas);
                    System.out.println("Adicionado!");
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default: System.out.println("Opção inválida! >:c");
            }
        }
    }

    public static void submenuEstetica(Scanner scanner, OrdemServico os) {
        int escolhaSub = -1;
        while (escolhaSub != 0) {
            System.out.println("Estética");
            System.out.println("1- " + TipoServico.Lavagem.getDescricao());
            System.out.println("2- " + TipoServico.Funilaria.getDescricao());
            System.out.println("3- " + TipoServico.Polimento.getDescricao());
            System.out.println("4- " + TipoServico.MartelinhoOuro.getDescricao());
            System.out.println("0- Voltar");
            System.out.print("Escolha: ");

            escolhaSub = scanner.nextInt();
            scanner.nextLine();

            switch (escolhaSub) {
                case 1: System.out.println(">> Selecionado: Lavagem"); break;
                case 2: System.out.println(">> Selecionado: Funilaria"); break;
                case 3: System.out.println(">> Selecionado: Polimento"); break;
                case 4: System.out.println(">> Selecionado: Martelinho"); break;
                case 0: System.out.println("Voltando..."); break;
                default: System.out.println("Opção inválida! >:c");
            }
        }
    }

    public static void submenuPneus(Scanner scanner) {
        int escolhaSub = -1;
        while (escolhaSub != 0) {
            System.out.println("\n--- Pneus e Rodas ---");
            System.out.println("1- " + TipoServico.TrocaPneus.getDescricao());
            System.out.println("2- " + TipoServico.Remendo.getDescricao());
            System.out.println("0- Voltar");
            System.out.print("Escolha: ");

            escolhaSub = scanner.nextInt();
            scanner.nextLine();

            switch (escolhaSub) {
                case 1: System.out.println(">> Selecionado: Troca de Pneus"); break;
                case 2: System.out.println(">> Selecionado: Reparo de Pneus"); break;
                case 0: System.out.println("Voltando..."); break;
                default: System.out.println("Opção inválida! >:c");
            }
        }
    }
}
