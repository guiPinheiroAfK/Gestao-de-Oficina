package servico;

import banco.VeiculoDAO;
import modelo.*;
import banco.PecaDAO;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ExtraMain {

    // Mantém a lista da memória (ArrayList) sincronizada com o Banco de Dados
    public static void atualizarListaLocal(List<Veiculo> veiculos, VeiculoDAO dao) {
        veiculos.clear();
        veiculos.addAll(dao.buscarTodos());
    }

    // Método auxiliar para imprimir listas de veículos com um pequeno delay para leitura
    private static void exibirLista(List<Veiculo> lista) throws InterruptedException {
        if (lista.isEmpty()) {
            System.out.println("Nada encontrado! :c");
        } else {
            for (Veiculo v : lista) {
                System.out.println("Modelo: " + v.getModelo() + " | Placa: " + v.getPlaca() + " | Ano: " + v.getAno());
            }
            // Faz o programa esperar 2 segundos para o usuário conseguir ler antes do menu voltar
            TimeUnit.SECONDS.sleep(2);
        }
    }

    // OPÇÃO 1: Cadastro de Veículo com tratamento contra inputs inválidos
    public static void cadastrarVeiculo(Scanner scanner, List<Veiculo> patioDinamico, VeiculoDAO dao){
        try {
            System.out.println("\n--- Cadastro de Veículo ---");

            // Validação de Placa: Aceita padrão antigo (AAA1234) e Mercosul (AAA1A23)
            String placaNovo;
            while (true) {
                System.out.print("Digite a Placa (7 caracteres): ");
                placaNovo = scanner.nextLine().toUpperCase().trim();
                if (placaNovo.matches("[A-Z]{3}[0-9][A-Z0-9][0-9]{2}")) break;
                else System.out.println("❌ Erro: Padrão de placa inválido!");
            }

            // Validação de Modelo: Regex [a-zA-Z0-9 ]+ impede caracteres como . , / *
            String modeloNovo;
            while (true) {
                System.out.print("Digite o Modelo: ");
                modeloNovo = scanner.nextLine().trim();
                if (modeloNovo.matches("[a-zA-Z0-9 ]+")) break;
                else System.out.println("❌ Erro: Não use símbolos como '.' ',' etc no modelo!");
            }

            // Validação de Ano: Trava entre o 1º carro no Brasil (1891) e o ano atual (2026)
            int anoNovo = 0;
            while (true) {
                System.out.print("Digite o Ano (1891 - 2026): ");
                try {
                    // Usamos Integer.parseInt para evitar que letras quebrem o Scanner
                    anoNovo = Integer.parseInt(scanner.nextLine());
                    if (anoNovo >= 1891 && anoNovo <= 2026) break;
                    else System.out.println("❌ Ano inválido! No Brasil, apenas veículos entre 1891 e 2026.");
                } catch (NumberFormatException e) {
                    System.out.println("❌ Erro: Digite apenas números inteiros.");
                }
            }

            // Validação de Tipo: Só permite os tipos que o sistema conhece
            String tipoNovo;
            while(true) {
                System.out.print("Tipo (CARRO/MOTO): ");
                tipoNovo = scanner.nextLine().toUpperCase().trim();
                if(tipoNovo.equals("CARRO") || tipoNovo.equals("MOTO")) break;
                System.out.println("❌ Use apenas CARRO ou MOTO.");
            }

            // Polimorfismo: Decide qual classe instanciar baseada no tipo
            Veiculo v = tipoNovo.equals("CARRO")
                    ? new Carro(placaNovo, modeloNovo, anoNovo, tipoNovo)
                    : new Moto(placaNovo, modeloNovo, anoNovo, tipoNovo);

            dao.salvar(v, tipoNovo); // Salva no banco
            atualizarListaLocal(patioDinamico, dao); // Atualiza lista local
            System.out.println("\n✅ Veículo cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("⚠️ Erro: " + e.getMessage());
        }
    }

    // OPÇÃO 2: Deleta um veículo por placa e limpa a entrada
    public static void removerVeiculoPorPlaca(Scanner scanner, List<Veiculo> patioDinamico, VeiculoDAO dao){
        System.out.print("Digite a placa para apagar: ");
        String placa = scanner.nextLine().toUpperCase().trim();
        dao.deletar(placa);
        atualizarListaLocal(patioDinamico, dao);
        System.out.println("✅ Comando de remoção enviado.");
    }

    // OPÇÃO 3: Menus de busca protegidos contra erros de digitação
    public static void listarVeiculosNoPatio(Scanner scanner, VeiculoDAO dao) throws InterruptedException {
        String filtroTipo = null;
        while (true) {
            System.out.println("\n1-Carros | 2-Motos | 3-Ambos");
            try {
                int esc = Integer.parseInt(scanner.nextLine());
                if (esc == 1) { filtroTipo = "CARRO"; break; }
                if (esc == 2) { filtroTipo = "MOTO"; break; }
                if (esc == 3) { filtroTipo = null; break; }
                System.out.println("Opção inválida.");
            } catch (Exception e) { System.out.println("Digite apenas números."); }
        }

        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n1-Tudo | 2-Por Ano | 3-Por Modelo | 0-Voltar");
            try {
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao){
                    case 1: exibirLista((filtroTipo == null) ? dao.buscarTodos() : dao.buscarPorTipo(filtroTipo)); break;
                    case 2:
                        System.out.print("Ano: ");
                        int ano = Integer.parseInt(scanner.nextLine());
                        exibirLista(dao.buscarPorAnoETipo(ano, filtroTipo));
                        break;
                    case 3:
                        System.out.print("Modelo: ");
                        exibirLista(dao.buscarPorModeloETipo(scanner.nextLine(), filtroTipo));
                        break;
                }
            } catch (Exception e) { System.out.println("Erro na busca."); }
        }
    }

    // OPÇÃO 4: Cadastro de Peças com troca automática de vírgula por ponto
    public static void cadastrarPecaNoCatalogo(Scanner scanner){
        try {
            System.out.println("\n--- Cadastro de Peça ---");
            System.out.print("Nome: ");
            String nome = scanner.nextLine().trim();

            System.out.print("Valor (R$): ");
            // replace(",", ".") permite que o usuário digite "10,50" sem quebrar o programa
            double valor = Double.parseDouble(scanner.nextLine().replace(",", "."));

            System.out.print("Estoque: ");
            int qtd = Integer.parseInt(scanner.nextLine());

            new PecaDAO().salvar(new Peca(nome, valor, qtd));
            System.out.println("✅ Peça salva!");
        } catch (Exception e) {
            System.out.println("❌ Erro: Valores numéricos inválidos.");
        }
    }

    // OPÇÃO 5: Inicia a Ordem de Serviço vinculada a um veículo existente
    public static void iniciarFluxoOrcamento(Scanner scanner, VeiculoDAO dao) {
        System.out.print("Placa do veículo: ");
        String placa = scanner.nextLine().toUpperCase().trim();
        Veiculo v = dao.buscarPorPlaca(placa);

        if (v == null) {
            System.out.println("❌ Veículo não encontrado.");
            return;
        }

        // Instancia a OS e abre o menu de serviços
        OrdemServico novaOS = new OrdemServico(v, ServicosGerais.PREVENTIVA);
        System.out.println("\n--- Selecione o Setor ---");
        System.out.println("1.Preventiva | 2.Corretiva | 3.Elétrica | 4.Estética | 5.Pneus");

        try {
            int tipo = Integer.parseInt(scanner.nextLine());
            ServicoOrcamento.exibirMenuServicos(scanner, tipo, novaOS);
            System.out.println("\n" + novaOS.getResumo());
        } catch (Exception e) {
            System.out.println("❌ Erro no orçamento.");
        }
    }

    // LOOP PRINCIPAL: Gerencia as opções do menu com tratamento de erro global
    public static void menuPrincipal(Scanner scanner, List<Veiculo> patioDinamico ,VeiculoDAO dao) throws InterruptedException {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- Oficina do Gui ---");
            System.out.println("1-Cadastrar | 2-Apagar | 3-Listar | 4-Peças | 5-Orçamento | 0-Sair");
            System.out.print("Escolha: ");
            try {
                // Lê a opção como String e converte para evitar o bug do buffer do Scanner
                opcao = Integer.parseInt(scanner.nextLine());
                switch (opcao) {
                    case 1: cadastrarVeiculo(scanner, patioDinamico, dao); break;
                    case 2: removerVeiculoPorPlaca(scanner, patioDinamico, dao); break;
                    case 3: listarVeiculosNoPatio(scanner, dao); break;
                    case 4: cadastrarPecaNoCatalogo(scanner); break;
                    case 5: iniciarFluxoOrcamento(scanner, dao); break;
                    case 0: System.out.println("Saindo..."); break;
                    default: System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                // Se o usuário digitar uma letra no menu, cai aqui e não fecha o programa
                System.out.println("⚠️ Digite apenas números!");
            }
        }
    }
}