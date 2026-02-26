package servico;

import java.util.Scanner;

public class ServicoOrcamento {
    // Menu do orçamento
    public static void exibirMenuServicos(Scanner scanner, int tipo){
        switch (tipo){
            case 1:
                System.out.println("1- Troca de Óleo e Filtros:");
                System.out.println("2- Revisão de Freios:");
                System.out.println("3- Sistema de Arrefecimento:");
                System.out.println("4- Verificação de Velas e Correias:");
                System.out.println("5- Alinhamento e Balanceamento:");
                System.out.println("6- Verificação da Bateria:");
                scanner.nextInt();
                submenuPreventiva(scanner);
                break;
            case 2:
                System.out.println("1- Reparo de Motor:");
                System.out.println("2- Serviços de Suspensão: Troca de amortecedores, molas, buchas e bandejas.");
                System.out.println("3- Sistema de Direção: Reparo em direção hidráulica ou elétrica.");
                System.out.println("4- Transmissão e Embreagem: Troca de kit de embreagem ou manutenção do câmbio automático.");
                System.out.println("5- Reparo no Sistema de Escapamento:");
                scanner.nextInt();
                submenuCorretiva(scanner);
                break;
            case 3:
                System.out.println("1- Diagnóstico Computadorizado:");
                System.out.println("2- Sistema de Iluminação:");
                System.out.println("3- Motor de Arranque e Alternador:");
                System.out.println("4- Vidros e Travas Elétricas:");
                scanner.nextInt();
                submenuEletrica(scanner);
                break;
            case 4:
                System.out.println("1- Lavagem Detalhada e Higienização:");
                System.out.println("2- Funilaria e Pintura:");
                System.out.println("3- Polimento e Cristalização/Vitrificação:");
                System.out.println("4- Martelinho de Ouro:");
                scanner.nextInt();
                submenuEstetica(scanner);
                break;
            case 5:
                System.out.println("1- Troca de Pneus:");
                System.out.println("2- Reparo de Pneus:");
                scanner.nextInt();
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
            System.out.println("\n--- Manutenção Preventiva ---");
            System.out.println("1- Troca de Óleo e Filtros");
            System.out.println("2- Revisão de Freios");
            System.out.println("3- Sistema de Arrefecimento");
            System.out.println("4- Verificação de Velas e Correias");
            System.out.println("5- Alinhamento e Balanceamento");
            System.out.println("6- Verificação da Bateria");
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
            System.out.println("\n--- Manutenção Corretiva ---");
            System.out.println("1- Reparo de Motor");
            System.out.println("2- Serviços de Suspensão");
            System.out.println("3- Sistema de Direção");
            System.out.println("4- Transmissão e Embreagem");
            System.out.println("5- Reparo no Sistema de Escapamento");
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
            System.out.println("\n--- Diagnóstico e Elétrica ---");
            System.out.println("1- Diagnóstico Computadorizado");
            System.out.println("2- Sistema de Iluminação");
            System.out.println("3- Motor de Arranque e Alternador");
            System.out.println("4- Vidros e Travas Elétricas");
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
            System.out.println("\n--- Estética e Limpeza ---");
            System.out.println("1- Lavagem Detalhada e Higienização");
            System.out.println("2- Funilaria e Pintura");
            System.out.println("3- Polimento e Cristalização");
            System.out.println("4- Martelinho de Ouro");
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
            System.out.println("1- Troca de Pneus");
            System.out.println("2- Reparo de Pneus");
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
