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
                submenuPneus(scanner, os); // Agora o método aceita 'os'
                break;
            default:
                System.out.println("Não tinha essa opção...");
        }
    }

    public static void submenuPreventiva(Scanner scanner, OrdemServico os) {
        int escolhaSub = -1;
        while (escolhaSub != 0) {
            System.out.println("\nPreventiva");
            System.out.println("1- " + TipoServico.OleoFiltro.getDescricao());
            System.out.println("2- " + TipoServico.RevFreio.getDescricao());
            System.out.println("3- " + TipoServico.Arrefecimento.getDescricao());
            System.out.println("4- " + TipoServico.VelasCorreia.getDescricao());
            System.out.println("5- " + TipoServico.AlinhaBalanca.getDescricao());
            System.out.println("6- " + TipoServico.Bateria.getDescricao());
            System.out.println("0- Voltar");

            System.out.print("Escolha uma sub-opção: ");
            escolhaSub = scanner.nextInt();
            scanner.nextLine();

            switch (escolhaSub) {
                case 1: os.adicionarSubServico(TipoServico.OleoFiltro); break;
                case 2: os.adicionarSubServico(TipoServico.RevFreio); break;
                case 3: os.adicionarSubServico(TipoServico.Arrefecimento); break;
                case 4: os.adicionarSubServico(TipoServico.VelasCorreia); break;
                case 5: os.adicionarSubServico(TipoServico.AlinhaBalanca); break;
                case 6: os.adicionarSubServico(TipoServico.Bateria); break;
                case 0: break;
                default: System.out.println("Opção inválida!");
            }
            if(escolhaSub != 0) System.out.println("✅ Adicionado ao orçamento!");
        }
    }

    public static void submenuCorretiva(Scanner scanner, OrdemServico os) {
        int escolhaSub = -1;
        while (escolhaSub != 0) {
            System.out.println("\nCorretiva");
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
                case 1: os.adicionarSubServico(TipoServico.ReparoMotor); break;
                case 2: os.adicionarSubServico(TipoServico.Suspensao); break;
                case 3: os.adicionarSubServico(TipoServico.Direcao); break;
                case 4: os.adicionarSubServico(TipoServico.TransmissaoEmbreagem); break;
                case 5: os.adicionarSubServico(TipoServico.Escapamento); break;
                case 0: break;
                default: System.out.println("Opção inválida!");
            }
            if(escolhaSub != 0) System.out.println("✅ Adicionado ao orçamento!");
        }
    }

    public static void submenuEletrica(Scanner scanner, OrdemServico os) {
        int escolhaSub = -1;
        while (escolhaSub != 0) {
            System.out.println("\nElétrica");
            System.out.println("1- " + TipoServico.Diagnostico.getDescricao());
            System.out.println("2- " + TipoServico.Iluminacao.getDescricao());
            System.out.println("3- " + TipoServico.MotorArranque.getDescricao());
            System.out.println("4- " + TipoServico.VidrosTravas.getDescricao());
            System.out.println("0- Voltar");
            System.out.print("Escolha: ");

            escolhaSub = scanner.nextInt();
            scanner.nextLine();

            switch (escolhaSub) {
                case 1: os.adicionarSubServico(TipoServico.Diagnostico); break;
                case 2: os.adicionarSubServico(TipoServico.Iluminacao); break;
                case 3: os.adicionarSubServico(TipoServico.MotorArranque); break;
                case 4: os.adicionarSubServico(TipoServico.VidrosTravas); break;
                case 0: break;
                default: System.out.println("Opção inválida!");
            }
            if(escolhaSub != 0) System.out.println("✅ Adicionado ao orçamento!");
        }
    }

    public static void submenuEstetica(Scanner scanner, OrdemServico os) {
        int escolhaSub = -1;
        while (escolhaSub != 0) {
            System.out.println("\nEstética");
            System.out.println("1- " + TipoServico.Lavagem.getDescricao());
            System.out.println("2- " + TipoServico.Funilaria.getDescricao());
            System.out.println("3- " + TipoServico.Polimento.getDescricao());
            System.out.println("4- " + TipoServico.MartelinhoOuro.getDescricao());
            System.out.println("0- Voltar");
            System.out.print("Escolha: ");

            escolhaSub = scanner.nextInt();
            scanner.nextLine();

            switch (escolhaSub) {
                case 1: os.adicionarSubServico(TipoServico.Lavagem); break;
                case 2: os.adicionarSubServico(TipoServico.Funilaria); break;
                case 3: os.adicionarSubServico(TipoServico.Polimento); break;
                case 4: os.adicionarSubServico(TipoServico.MartelinhoOuro); break;
                case 0: break;
                default: System.out.println("Opção inválida!");
            }
            if(escolhaSub != 0) System.out.println("✅ Adicionado ao orçamento!");
        }
    }

    // CORRIGIDO: Adicionado o parâmetro 'OrdemServico os' para resolver o erro da imagem
    public static void submenuPneus(Scanner scanner, OrdemServico os) {
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
                case 1: os.adicionarSubServico(TipoServico.TrocaPneus); break;
                case 2: os.adicionarSubServico(TipoServico.Remendo); break;
                case 0: break;
                default: System.out.println("Opção inválida!");
            }
            if(escolhaSub != 0) System.out.println("✅ Adicionado ao orçamento!");
        }
    }
}