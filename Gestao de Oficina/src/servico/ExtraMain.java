package servico;

import banco.VeiculoDAO;
import modelo.*;
import banco.PecaDAO;
import modelo.Peca;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

// "ai q nome feio"... faltou criatividade :c
public class ExtraMain {

    // atualizarListaLocal, ja tava na main antes >:c
    public static void atualizarListaLocal(List<Veiculo> veiculos, VeiculoDAO dao) {
        veiculos.clear();
        veiculos.addAll(dao.buscarVeiculos());
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

            // validação de placa: aceita padrão antigo (AAA1234) e Mercosul (AAA1A23)
            String placaNovo;
            while (true) {
                System.out.print("Digite a Placa (7 caracteres): ");
                placaNovo = scanner.nextLine().toUpperCase().trim();
                if (placaNovo.matches("[A-Z]{3}[0-9][A-Z0-9][0-9]{2}")) break;
                else System.out.println(" Erro: Padrão de placa inválido!");
            }

            // validação de Modelo: regex [a-zA-Z0-9 ]+ impede caracteres como . , / *
            String modeloNovo;
            while (true) {
                System.out.print("Digite o Modelo: ");
                modeloNovo = scanner.nextLine().trim();
                if (modeloNovo.matches("[a-zA-Z0-9 ]+")) break;
                else System.out.println(" Erro: Não use símbolos como '.' ',' etc no modelo!");
            }

            // validação de ano: trava entre o 1º carro no Brasil (1891) e o ano atual (2026)
            int anoNovo = 0;
            while (true) {
                System.out.print("Digite o Ano (1891 - 2026): ");
                try {
                    // Integer.parseInt para evitar que letras quebrem o Scanner
                    anoNovo = Integer.parseInt(scanner.nextLine());
                    if (anoNovo >= 1891 && anoNovo <= 2026) break;
                    else System.out.println(" Ano inválido! No Brasil, apenas veículos entre 1891 e 2026.");
                } catch (NumberFormatException e) {
                    System.out.println(" Erro: Digite apenas números inteiros.");
                }
            }

            // validação de tipo: só permite os tipos que o sistema conhece
            String tipoNovo;
            while(true) {
                System.out.print("Tipo (CARRO/MOTO): ");
                tipoNovo = scanner.nextLine().toUpperCase().trim();
                if(tipoNovo.equals("CARRO") || tipoNovo.equals("MOTO")) break;
                System.out.println(" Use apenas CARRO ou MOTO.");
            }

            // polimorfismo: decide qual classe instanciar baseada no tipo
            Veiculo v = tipoNovo.equals("CARRO")
                    ? new Carro(placaNovo, modeloNovo, anoNovo, tipoNovo)
                    : new Moto(placaNovo, modeloNovo, anoNovo, tipoNovo);

            dao.salvar(v, tipoNovo); // salva no banco
            atualizarListaLocal(patioDinamico, dao); // atualiza lista local

            System.out.println("\n✅ Veículo " + v.getModelo() + " cadastrado com sucesso!");
        } catch (RuntimeException e) {
            System.out.println("⚠️ Erro ao cadastrar: " + e.getMessage());
        }
    }

    // op. 2
    public static void removerVeiculoPorPlaca(Scanner scanner, List<Veiculo> patioDinamico, VeiculoDAO dao){
        System.out.print("Digite a placa para apagar: ");
        String placa = scanner.nextLine().toUpperCase().trim();
        dao.deletar(placa);
        atualizarListaLocal(patioDinamico, dao); // recarrega a lista
        System.out.println(" Comando de remoção enviado.");
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
            try {
                escopo = scanner.nextInt();
                scanner.nextLine();

                if (escopo == 1) { filtroTipo = "CARRO"; break;}
                if (escopo == 2) { filtroTipo = "MOTO"; break;}
                if (escopo == 3) { filtroTipo = null; break;} // AMBOS
                System.out.println(" Opção inválida! Escolha 1, 2 ou 3. >:c");
            } catch (Exception e){
                System.out.println(" Erro: Digite apenas números! >:c"); }
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

            try {
                opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao){
                    case 1:
                        exibirLista( (filtroTipo == null) ? dao.buscarVeiculos() : dao.buscarPorTipo(filtroTipo) );
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
                }
            } catch (Exception e) { System.out.println("Erro na busca. Opção inválida! >:c"); }
        }

        // todos os salvar/buscar/deletar estao no "banco.VeiculoDAO"
        // e as heranças/polimorfismo estão no "modelo."
    }

    // op. 4
    public static void atualizarDadosVeiculo(VeiculoDAO dao, List<Veiculo> patioDinamico, Scanner scanner) throws InterruptedException {
        // ex: mudar o 370z para um 370z NISMO (18 cv a mais)
        System.out.println("\n--- 📝 Atualizando Dados de Veículo ---");
        System.out.print("Digite a placa do veículo que deseja alterar: ");
        String placaBusca = scanner.nextLine().toUpperCase().trim();

        // 1. Tenta buscar o veículo no banco pela placa
        Veiculo vEncontrado = dao.buscarPorPlaca(placaBusca);


        if (vEncontrado != null) {
            System.out.println("Veículo encontrado: " + vEncontrado.getModelo() + " (" + vEncontrado.getAno() + ")");

            // 2. Coleta os novos dados
            System.out.print("Novo Modelo (Deixe vazio para manter [" + vEncontrado.getModelo() + "]): ");
            String novoModelo = scanner.nextLine().trim();
            if (!novoModelo.isEmpty()) {
                vEncontrado.setModelo(novoModelo);
            }

            System.out.print("Novo Ano (Deixe vazio para manter [" + vEncontrado.getAno() + "]): ");
            String novoAnoStr = scanner.nextLine().trim();
            if (!novoAnoStr.isEmpty()) {
                try {
                    int novoAno = Integer.parseInt(novoAnoStr);
                    vEncontrado.setAno(novoAno);
                } catch (NumberFormatException e) {
                    System.out.println("Ano inválido! Mantendo o ano anterior.");
                }
            }

            // 3. Envia o objeto atualizado para o banco
            dao.atualizar(vEncontrado);

            // 4. Sincroniza a lista da memória (patioDinamico) com o banco
            atualizarListaLocal(patioDinamico, dao);

            System.out.println("✨ Processo de atualização concluído!");
        } else {
            System.out.println("❌ Erro: Veículo com placa " + placaBusca + " não encontrado no sistema.");
        }

        TimeUnit.SECONDS.sleep(1);
    }

    // op. 5
    public static void cadastrarPecaNoCatalogo(Scanner scanner){
        PecaDAO pecaDao = new PecaDAO();
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n--- GESTÃO DE CATÁLOGO DE PEÇAS ---");
            System.out.println("1 - Cadastrar Nova Peça");
            System.out.println("2 - Atualizar Peça (Preço/Estoque/Nome)");
            System.out.println("3 - Apagar Peça do Sistema");
            System.out.println("0 - Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:
                        executarCadastroPeca(scanner, pecaDao);
                        break;
                    case 2:
                        executarAtualizacaoPeca(scanner, pecaDao);
                        break;
                    case 3:
                        executarRemocaoPeca(scanner, pecaDao);
                        break;
                    case 0:
                        System.out.println("Voltando...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("Erro: Digite um número válido.");
            }
        }
    }

    private static void executarCadastroPeca(Scanner scanner, PecaDAO dao) {
        System.out.println("\n--- Novo Cadastro ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine().trim();
        System.out.print("Valor (R$): ");
        double valor = Double.parseDouble(scanner.nextLine().replace(",", "."));
        System.out.print("Estoque Inicial: ");
        int qtd = Integer.parseInt(scanner.nextLine());

        dao.salvar(new Peca(nome, valor, qtd));
        System.out.println("✅ Peça salva no catálogo!");
    }

    private static void executarAtualizacaoPeca(Scanner scanner, PecaDAO dao) {
        // Lista as peças para o usuário ver os IDs antes de escolher
        List<Peca> pecas = dao.buscarPecas();
        if (pecas.isEmpty()) {
            System.out.println("O catálogo está vazio!");
            return;
        }

        System.out.println("\n--- Peças Disponíveis ---");
        for (Peca p : pecas) {
            System.out.printf("ID: %d | Nome: %s | Preço: R$ %.2f | Estoque: %d\n",
                    p.getId(), p.getNome(), p.getValor(), p.getEstoque());
        }

        System.out.print("\nDigite o ID da peça que deseja editar: ");
        int id = Integer.parseInt(scanner.nextLine());

        Peca p = dao.buscarPorId(id);
        if (p != null) {
            System.out.print("Novo Nome [" + p.getNome() + "]: ");
            String nome = scanner.nextLine();
            if (!nome.isEmpty()) p.setNome(nome);

            System.out.print("Novo Valor [" + p.getValor() + "]: ");
            String valorStr = scanner.nextLine().replace(",", ".");
            if (!valorStr.isEmpty()) p.setValor(Double.parseDouble(valorStr));

            System.out.print("Novo Estoque [" + p.getEstoque() + "]: ");
            String estoqueStr = scanner.nextLine();
            if (!estoqueStr.isEmpty()) p.setEstoque(Integer.parseInt(estoqueStr));

            dao.atualizar(p);
            System.out.println("Peça atualizada!");
        } else {
            System.out.println("Peça com ID " + id + " não encontrada.");
        }
    }

    private static void executarRemocaoPeca(Scanner scanner, PecaDAO dao) {
        System.out.print("Digite o ID da peça para APAGAR: ");
        int id = Integer.parseInt(scanner.nextLine());

        Peca p = dao.buscarPorId(id);
        if (p != null) {
            System.out.print("Confirmar exclusão de '" + p.getNome() + "'? (S/N): ");
            if (scanner.nextLine().equalsIgnoreCase("S")) {
                dao.deletar(id);
                System.out.println("🗑️ Peça removida com sucesso.");
            } else {
                System.out.println("Remoção cancelada.");
            }
        } else {
            System.out.println("❌ ID não encontrado.");
        }
    }

    // op. 6
    public static void exibirCatalogoDePecas(Scanner scanner){
        System.out.println("\n--- Catálogo de Peças Cadastradas ---");
        List<Peca> listaPecas = new PecaDAO().buscarPecas(); // Chama o banco

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

    // op. 7 - corrigido: removido o 'os' do parâmetro, pois ele é criado aqui dentro
    public static void iniciarFluxoOrcamento(Scanner scanner, VeiculoDAO dao) {
        System.out.println("\n--- Gerando Orçamento Real ---");
        System.out.print("Digite a placa do veículo cadastrado: ");
        String placaBusca = scanner.nextLine().toUpperCase().trim();

        Veiculo vEncontrado = dao.buscarPorPlaca(placaBusca);

        if (vEncontrado == null) {
            System.out.println("Mentiroso! Não tem veículo.");
            iniciarFluxoOrcamento(scanner, dao);
        }

        // criando a OrdemServico aqui
        OrdemServico novaOS = new OrdemServico(vEncontrado, ServicosGerais.PREVENTIVA);

        System.out.println("Veículo: " + vEncontrado.getModelo());

        // novaOs existe agr!
        menuIniciarFluxoOrcamento(scanner, novaOS);

        // e no final de tudo, mostramos o "resultado"
        System.out.println("\n" + novaOS.getResumo());
    }

    // menu op. 7 - ADICIONADO 'OrdemServico os' nos parâmetros
    public static void menuIniciarFluxoOrcamento(Scanner scanner, OrdemServico os) {
        System.out.println("\n=== TIPO DE SERVIÇOS ===");
        System.out.println("1. " + ServicosGerais.PREVENTIVA.getDescricao());
        System.out.println("2. " + ServicosGerais.CORRETIVA.getDescricao());
        System.out.println("3. " + ServicosGerais.ELETRICA.getDescricao());
        System.out.println("4. " + ServicosGerais.ESTETICA.getDescricao());
        System.out.println("5. " + ServicosGerais.PNEUS.getDescricao());
        System.out.print("Escolha uma opção: ");

        try {
            int tipo = Integer.parseInt(scanner.nextLine());
            ServicoOrcamento.exibirMenuServicos(scanner, tipo, os);
        } catch (Exception e) {
            System.out.println(" Erro no orçamento.");
        }
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

            try {
                opcao = Integer.parseInt(scanner.nextLine());

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
                        atualizarDadosVeiculo(dao, patioDinamico, scanner);
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
                }
            } catch (Exception e) {
                // se o usuário digitar uma letra no menu, cai aqui e não fecha o programa
                System.out.println(" Digite apenas números!");
            }
        }
        scanner.close();
    }
}