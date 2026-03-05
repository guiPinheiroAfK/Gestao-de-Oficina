package servico;

import banco.PecaDAO;
import banco.VeiculoDAO;
import modelo.*;

import java.util.List;
import java.util.Scanner;

public class ExtraMain {

    public static void atualizarListaLocal(List<Veiculo> veiculos, VeiculoDAO dao) {
        veiculos.clear();
        veiculos.addAll(dao.buscarVeiculos());
    }

    public static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for (int i = 0; i < 20; i++) {
            System.out.println();
        }
    }

    private static void pausar(Scanner scanner) {
        System.out.print("\nPressione ENTER para continuar...");
        scanner.nextLine();
    }

    private static int lerInteiro(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas numeros.");
            }
        }
    }

    private static double lerDouble(Scanner scanner, String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim().replace(",", ".");
            try {
                return Double.parseDouble(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Valor invalido.");
            }
        }
    }

    private static void exibirListaSimples(List<Veiculo> lista) {
        if (lista.isEmpty()) {
            System.out.println("Nenhum veiculo encontrado.");
            return;
        }

        System.out.println("\n--- Veiculos ---");
        for (Veiculo v : lista) {
            System.out.printf("Tipo: %-5s | Placa: %-8s | Marca: %-15s | Modelo: %-15s | Ano: %d%n",
                    v.getTipo(), v.getPlaca(), v.getMarca(), v.getModelo(), v.getAno());
        }
    }

    private static List<Veiculo> buscarPorEscopo(VeiculoDAO dao, int escopo) {
        if (escopo == 1) {
            return dao.buscarPorTipo("CARRO");
        }
        if (escopo == 2) {
            return dao.buscarPorTipo("MOTO");
        }
        return dao.buscarVeiculos();
    }

    private static boolean tipoCompativel(Veiculo v, int escopo) {
        if (escopo == 1) {
            return "CARRO".equals(v.getTipo());
        }
        if (escopo == 2) {
            return "MOTO".equals(v.getTipo());
        }
        return true;
    }

    private static int selecionarTipoCadastro(Scanner scanner) {
        while (true) {
            limparTela();
            System.out.println("==============================");
            System.out.println("   Cadastro de Veiculo");
            System.out.println("==============================");
            System.out.println("1 - Carro");
            System.out.println("2 - Moto");
            System.out.println("0 - Voltar");
            int opcao = lerInteiro(scanner, "Escolha: ");
            if (opcao == 0 || opcao == 1 || opcao == 2) {
                return opcao;
            }
            System.out.println("Opcao invalida.");
            pausar(scanner);
        }
    }

    private static int selecionarEscopoVeiculo(Scanner scanner, String titulo) {
        while (true) {
            limparTela();
            System.out.println("==============================");
            System.out.println("  " + titulo);
            System.out.println("==============================");
            System.out.println("1 - Carro");
            System.out.println("2 - Moto");
            System.out.println("3 - Ambos");
            System.out.println("0 - Voltar");
            int opcao = lerInteiro(scanner, "Escolha: ");
            if (opcao >= 0 && opcao <= 3) {
                return opcao;
            }
            System.out.println("Opcao invalida.");
            pausar(scanner);
        }
    }

    private static String lerPlaca(Scanner scanner, boolean permitirVoltar) {
        while (true) {
            System.out.print("Digite a placa (ABC1D23)" + (permitirVoltar ? " ou 0 para voltar" : "") + ": ");
            String placa = scanner.nextLine().toUpperCase().trim();

            if (permitirVoltar && "0".equals(placa)) {
                return null;
            }
            if (placa.matches("[A-Z]{3}[0-9][A-Z0-9][0-9]{2}")) {
                return placa;
            }
            System.out.println("Placa invalida.");
        }
    }

    private static String lerMarca(Scanner scanner, boolean permitirVazio, String atual) {
        while (true) {
            String mensagem = permitirVazio
                    ? "Marca (max 15 letras, ENTER para manter '" + atual + "'): "
                    : "Marca (max 15 letras): ";

            System.out.print(mensagem);
            String marca = scanner.nextLine().trim();

            if (permitirVazio && marca.isEmpty()) {
                return atual;
            }
            if (marca.length() > 15) {
                System.out.println("Marca deve ter no maximo 15 letras.");
                continue;
            }
            if (!marca.matches("[\\p{L} ]+")) {
                System.out.println("Marca aceita apenas letras e espacos.");
                continue;
            }
            return marca;
        }
    }

    private static String lerModelo(Scanner scanner, boolean permitirVazio, String atual) {
        while (true) {
            String mensagem = permitirVazio
                    ? "Modelo (max 15 chars, ENTER para manter '" + atual + "'): "
                    : "Modelo (max 15 chars): ";

            System.out.print(mensagem);
            String modelo = scanner.nextLine().trim();

            if (permitirVazio && modelo.isEmpty()) {
                return atual;
            }
            if (modelo.isEmpty()) {
                System.out.println("Modelo nao pode ficar vazio.");
                continue;
            }
            if (modelo.length() > 15) {
                System.out.println("Modelo deve ter no maximo 15 caracteres.");
                continue;
            }
            return modelo;
        }
    }

    private static int lerAno(Scanner scanner, boolean permitirVazio, int atual) {
        while (true) {
            String mensagem = permitirVazio
                    ? "Ano (1891-2026, ENTER para manter " + atual + "): "
                    : "Ano (1891-2026): ";

            System.out.print(mensagem);
            String entrada = scanner.nextLine().trim();

            if (permitirVazio && entrada.isEmpty()) {
                return atual;
            }

            try {
                int ano = Integer.parseInt(entrada);
                if (ano >= 1891 && ano <= 2026) {
                    return ano;
                }
                System.out.println("Ano fora do intervalo permitido.");
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas numeros para o ano.");
            }
        }
    }

    public static void cadastrarVeiculo(Scanner scanner, List<Veiculo> patioDinamico, VeiculoDAO dao) {
        int tipoSelecionado = selecionarTipoCadastro(scanner);
        if (tipoSelecionado == 0) {
            return;
        }

        limparTela();
        System.out.println("--- Cadastro de Veiculo ---");
        String placa;
        while (true) {
            placa = lerPlaca(scanner, true);
            if (placa == null) {
                return;
            }
            if (dao.buscarPorPlaca(placa) != null) {
                System.out.println("Essa placa ja esta cadastrada em outro veiculo.");
                continue;
            }
            break;
        }

        String marca = lerMarca(scanner, false, "");
        String modelo = lerModelo(scanner, false, "");
        int ano = lerAno(scanner, false, 0);

        Veiculo v = (tipoSelecionado == 1)
                ? new Carro(placa, marca, modelo, ano)
                : new Moto(placa, marca, modelo, ano);

        try {
            dao.salvar(v);
            atualizarListaLocal(patioDinamico, dao);
            System.out.println("Veiculo cadastrado com sucesso.");
        } catch (RuntimeException e) {
            System.out.println("Falha no cadastro: " + e.getMessage());
        }
        pausar(scanner);
    }

    public static void removerVeiculoPorPlaca(Scanner scanner, List<Veiculo> patioDinamico, VeiculoDAO dao) {
        int escopo = selecionarEscopoVeiculo(scanner, "Excluir Veiculo");
        if (escopo == 0) {
            return;
        }

        List<Veiculo> lista = buscarPorEscopo(dao, escopo);
        limparTela();
        exibirListaSimples(lista);

        if (lista.isEmpty()) {
            pausar(scanner);
            return;
        }

        String placa = lerPlaca(scanner, true);
        if (placa == null) {
            return;
        }

        Veiculo encontrado = dao.buscarPorPlaca(placa);
        if (encontrado == null) {
            System.out.println("Placa nao encontrada.");
            pausar(scanner);
            return;
        }

        if (!tipoCompativel(encontrado, escopo)) {
            System.out.println("Veiculo encontrado, mas nao corresponde ao tipo escolhido.");
            pausar(scanner);
            return;
        }

        boolean removido = dao.deletar(placa);
        atualizarListaLocal(patioDinamico, dao);

        if (removido) {
            System.out.println("Veiculo excluido com sucesso.");
        } else {
            System.out.println("Nao foi possivel excluir o veiculo.");
        }
        pausar(scanner);
    }

    public static void listarVeiculosNoPatio(Scanner scanner, VeiculoDAO dao) {
        int escopo = selecionarEscopoVeiculo(scanner, "Listar Veiculos");
        if (escopo == 0) {
            return;
        }

        limparTela();
        List<Veiculo> lista = buscarPorEscopo(dao, escopo);
        exibirListaSimples(lista);
        pausar(scanner);
    }

    public static void atualizarDadosVeiculo(VeiculoDAO dao, List<Veiculo> patioDinamico, Scanner scanner) {
        int escopo = selecionarEscopoVeiculo(scanner, "Atualizar Veiculo");
        if (escopo == 0) {
            return;
        }

        List<Veiculo> lista = buscarPorEscopo(dao, escopo);
        limparTela();
        exibirListaSimples(lista);

        if (lista.isEmpty()) {
            pausar(scanner);
            return;
        }

        String placa = lerPlaca(scanner, true);
        if (placa == null) {
            return;
        }

        Veiculo encontrado = dao.buscarPorPlaca(placa);
        if (encontrado == null) {
            System.out.println("Placa nao encontrada.");
            pausar(scanner);
            return;
        }

        if (!tipoCompativel(encontrado, escopo)) {
            System.out.println("Veiculo encontrado, mas nao corresponde ao tipo escolhido.");
            pausar(scanner);
            return;
        }

        String novaMarca = lerMarca(scanner, true, encontrado.getMarca());
        String novoModelo = lerModelo(scanner, true, encontrado.getModelo());
        int novoAno = lerAno(scanner, true, encontrado.getAno());

        encontrado.setMarca(novaMarca);
        encontrado.setModelo(novoModelo);
        encontrado.setAno(novoAno);

        boolean atualizado = dao.atualizar(encontrado);
        atualizarListaLocal(patioDinamico, dao);

        if (atualizado) {
            System.out.println("Veiculo atualizado com sucesso.");
        } else {
            System.out.println("Nao foi possivel atualizar o veiculo.");
        }

        pausar(scanner);
    }

    private static void listarCatalogoPecas() {
        List<Peca> lista = new PecaDAO().buscarPecas();
        if (lista.isEmpty()) {
            System.out.println("Catalogo vazio.");
            return;
        }

        System.out.println("\n--- Catalogo de Pecas ---");
        for (Peca p : lista) {
            System.out.printf("ID: %d | Nome: %-20s | Valor: R$ %.2f | Estoque: %d%n",
                    p.getId(), p.getNome(), p.getValor(), p.getEstoque());
        }
    }

    public static void gerenciarPecas(Scanner scanner) {
        PecaDAO dao = new PecaDAO();

        while (true) {
            limparTela();
            System.out.println("==============================");
            System.out.println("     Gerenciar Pecas");
            System.out.println("==============================");
            System.out.println("1 - Cadastrar peca");
            System.out.println("2 - Atualizar peca");
            System.out.println("3 - Excluir peca");
            System.out.println("4 - Listar pecas");
            System.out.println("0 - Voltar");

            int opcao = lerInteiro(scanner, "Escolha: ");

            if (opcao == 0) {
                return;
            }

            try {
                switch (opcao) {
                    case 1:
                        limparTela();
                        System.out.println("--- Cadastro de Peca ---");
                        System.out.print("Nome da peca: ");
                        String nome = scanner.nextLine().trim();
                        double valor = lerDouble(scanner, "Valor: ");
                        int estoque = lerInteiro(scanner, "Estoque: ");
                        dao.salvar(new Peca(nome, valor, estoque));
                        System.out.println("Peca cadastrada com sucesso.");
                        break;

                    case 2:
                        limparTela();
                        listarCatalogoPecas();
                        int idAtualizar = lerInteiro(scanner, "ID da peca para atualizar (0 para voltar): ");
                        if (idAtualizar == 0) {
                            break;
                        }
                        Peca pecaAtual = dao.buscarPorId(idAtualizar);
                        if (pecaAtual == null) {
                            System.out.println("Peca nao encontrada.");
                            break;
                        }
                        System.out.print("Novo nome (ENTER para manter '" + pecaAtual.getNome() + "'): ");
                        String novoNome = scanner.nextLine().trim();
                        if (!novoNome.isEmpty()) {
                            pecaAtual.setNome(novoNome);
                        }
                        System.out.print("Novo valor (ENTER para manter " + pecaAtual.getValor() + "): ");
                        String valorStr = scanner.nextLine().trim().replace(",", ".");
                        if (!valorStr.isEmpty()) {
                            pecaAtual.setValor(Double.parseDouble(valorStr));
                        }
                        System.out.print("Novo estoque (ENTER para manter " + pecaAtual.getEstoque() + "): ");
                        String estStr = scanner.nextLine().trim();
                        if (!estStr.isEmpty()) {
                            pecaAtual.setEstoque(Integer.parseInt(estStr));
                        }
                        if (dao.atualizar(pecaAtual)) {
                            System.out.println("Peca atualizada com sucesso.");
                        } else {
                            System.out.println("Nao foi possivel atualizar a peca.");
                        }
                        break;

                    case 3:
                        limparTela();
                        listarCatalogoPecas();
                        int idExcluir = lerInteiro(scanner, "ID da peca para excluir (0 para voltar): ");
                        if (idExcluir == 0) {
                            break;
                        }
                        if (dao.deletar(idExcluir)) {
                            System.out.println("Peca excluida com sucesso.");
                        } else {
                            System.out.println("Peca nao encontrada.");
                        }
                        break;

                    case 4:
                        limparTela();
                        listarCatalogoPecas();
                        break;

                    default:
                        System.out.println("Opcao invalida.");
                }
            } catch (RuntimeException e) {
                System.out.println("Erro ao gerenciar pecas: " + e.getMessage());
            }

            pausar(scanner);
        }
    }

    public static void exibirCatalogoDePecas(Scanner scanner) {
        limparTela();
        try {
            listarCatalogoPecas();
        } catch (RuntimeException e) {
            System.out.println("Erro ao carregar catalogo: " + e.getMessage());
        }
        pausar(scanner);
    }

    public static void iniciarFluxoOrcamento(Scanner scanner, VeiculoDAO dao) {
        int escopo = selecionarEscopoVeiculo(scanner, "Simular Orcamento");
        if (escopo == 0) {
            return;
        }

        List<Veiculo> lista = buscarPorEscopo(dao, escopo);
        limparTela();
        exibirListaSimples(lista);

        if (lista.isEmpty()) {
            pausar(scanner);
            return;
        }

        String placa = lerPlaca(scanner, true);
        if (placa == null) {
            return;
        }

        Veiculo v = dao.buscarPorPlaca(placa);
        if (v == null) {
            System.out.println("Veiculo nao encontrado.");
            pausar(scanner);
            return;
        }

        if (!tipoCompativel(v, escopo)) {
            System.out.println("Veiculo encontrado, mas nao corresponde ao tipo escolhido.");
            pausar(scanner);
            return;
        }

        OrdemServico os = new OrdemServico(v, ServicosGerais.PREVENTIVA);
        System.out.println("Veiculo: " + v.getModelo() + " - " + v.getPlaca());
        menuIniciarFluxoOrcamento(scanner, os);
        System.out.println(os.getResumo());
        pausar(scanner);
    }

    public static void menuIniciarFluxoOrcamento(Scanner scanner, OrdemServico os) {
        while (true) {
            System.out.println("1 - Preventiva");
            System.out.println("2 - Corretiva");
            System.out.println("3 - Eletrica");
            System.out.println("4 - Estetica");
            System.out.println("5 - Pneus");
            System.out.println("0 - Voltar");

            int tipo = lerInteiro(scanner, "Escolha: ");
            if (tipo == 0) {
                return;
            }
            if (tipo >= 1 && tipo <= 5) {
                ServicoOrcamento.exibirMenuServicos(scanner, tipo, os);
                return;
            }
            System.out.println("Opcao invalida.");
        }
    }
}


