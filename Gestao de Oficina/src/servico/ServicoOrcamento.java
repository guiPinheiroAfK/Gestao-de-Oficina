package servico;

import modelo.TipoServico;

import java.util.Scanner;

public class ServicoOrcamento {
    // Menu do orçamento
    public static void exibirMenuServicos(Scanner scanner, int tipo){
        switch (tipo){
            case 1:
                submenuPreventiva(scanner);
                break;
            case 2:
                submenuCorretiva(scanner);
                break;
            case 3:
                submenuEletrica(scanner);
                break;
            case 4:
                submenuEstetica(scanner);
                break;
            case 5:
                submenuPneus(scanner);
                break;
            default:
                System.out.println("Não tinha essa opção...");
        }
    }

    // menus base para orçamento
    public static void submenuPreventiva(Scanner scanner) {
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
                case 1: System.out.println(">> Selecionado: Troca de Óleo"); break; // Simplesmente para
                case 2: System.out.println(">> Selecionado: Revisão de Freios"); break; // deixar mais curto
                case 3: System.out.println(">> Selecionado: Arrefecimento"); break;
                case 4: System.out.println(">> Selecionado: Velas e Correias"); break;
                case 5: System.out.println(">> Selecionado: Alinhamento"); break;
                case 6: System.out.println(">> Selecionado: Bateria"); break;
                case 0: System.out.println("Voltando..."); break;
                default: System.out.println("Opção inválida! >:c");
            }
        }
    }

    public static void submenuCorretiva(Scanner scanner) {
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
                case 1: System.out.println(">> Selecionado: Reparo de Motor"); break;
                case 2: System.out.println(">> Selecionado: Suspensão"); break;
                case 3: System.out.println(">> Selecionado: Direção"); break;
                case 4: System.out.println(">> Selecionado: Transmissão"); break;
                case 5: System.out.println(">> Selecionado: Escapamento"); break;
                case 0: System.out.println("Voltando..."); break;
                default: System.out.println("Opção inválida! >:c");
            }
        }
    }

    public static void submenuEletrica(Scanner scanner) {
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
                case 1: System.out.println(">> Selecionado: Diagnóstico"); break;
                case 2: System.out.println(">> Selecionado: Iluminação"); break;
                case 3: System.out.println(">> Selecionado: Motor de Arranque"); break;
                case 4: System.out.println(">> Selecionado: Vidros/Travas"); break;
                case 0: System.out.println("Voltando..."); break;
                default: System.out.println("Opção inválida! >:c");
            }
        }
    }

    public static void submenuEstetica(Scanner scanner) {
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
