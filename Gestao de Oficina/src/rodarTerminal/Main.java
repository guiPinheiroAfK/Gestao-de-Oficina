package rodarTerminal;

import banco.ConnectionFactory;
import java.sql.Connection;
import banco.VeiculoDAO;
import modelo.*;
import banco.PecaDAO;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void sincronizar(List<Veiculo> veiculos, VeiculoDAO dao) {
        veiculos.clear();
        veiculos.addAll(dao.buscarTodos());
    }

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        VeiculoDAO dao = new VeiculoDAO();
        int opcao = -1;

        // joga tudo para uma lista antes de rodar o menu
        List<Veiculo> patioDinamico = dao.buscarTodos();
        System.out.println("✅ " + patioDinamico.size() + " veículos carregados do banco!");
        // faz o mesmo que o "sleep() " em C
        TimeUnit.SECONDS.sleep(2);

        while (opcao != 0) {
            // menu bem basicao
            System.out.println("\n--- Oficina do Gui ---");
            System.out.println("1 - Cadastrar Carro");
            System.out.println("2 - Apagar Veículo por Placa");
            System.out.println("3 - Listar Veículos no Pátio");
            System.out.println("4 - Atualizar Veículos no Pátio");
            System.out.println("5- Cadastrar peça");
            System.out.println("6- Vizualizar peças cadastradas");
            System.out.println("7- Simular orçamento");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
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

                        // Agora criamos o objeto com os dados que VOCÊ digitou
                        Veiculo v;
                        if (tipoNovo.equals("CARRO")) {
                            v = new Carro(placaNovo, modeloNovo, anoNovo);
                        } else {
                            v = new Moto(placaNovo, modeloNovo, anoNovo);
                        }

                        dao.salvar(v, tipoNovo); // Salva no banco com a nova coluna 'tipo'
                        sincronizar(patioDinamico, dao);

                        System.out.println("\n✅ Veículo " + v.getModelo() + " cadastrado com sucesso!");
                    } catch (RuntimeException e) {
                        System.out.println("⚠️ Erro ao cadastrar: " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.print("Digite a placa para apagar: ");
                    String placa = scanner.nextLine();
                    dao.deletar(placa);
                    sincronizar(patioDinamico, dao); // <- recarrega a lista
                    break;

                case 3:
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
                    break;

                case 4:
                    // ex: mudar o 370z para um 370z NISMO (18 cv a mais)
                    System.out.println("\n--- Atualizando Veículo ---");
                    Carro atualizado = new Carro("ABC-1234", "Nissan 370z NISMO", 2025);
                    dao.atualizar(atualizado);
                    sincronizar(patioDinamico, dao); // <- <- recarrega a lista
                    break;

                case 5: // Novo caso para Peças
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
                    break;

                case 6: // Ou o próximo número livre no seu menu
                    System.out.println("\n--- 📦 Catálogo de Peças Cadastradas ---");
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
                    break;

                case 7:
                    System.out.println("\n--- 🔧 Gerando Orçamento Real ---");
                    System.out.print("Digite a placa do veículo cadastrado: ");
                    String placaBusca = scanner.nextLine();

                    Veiculo vEncontrado = dao.buscarPorPlaca(placaBusca);

                    if (vEncontrado != null) {
                        List<Peca> pecasParaServico = new java.util.ArrayList<>();
                        System.out.println("\n===TIPO DE SERVIÇOS===");
                        System.out.println("1. Manutenção Preventiva (Revisão Periódica)");
                        System.out.println("2. Manutenção Corretiva (Reparos)");
                        System.out.println("3- Diagnóstico e Injeção Eletrônica");
                        System.out.println("4. Serviços de Estética, Limpeza e Funilaria");
                        System.out.println("5. Serviços de Pneus e Rodas");
                        System.out.println("Escolha uma opção: ");
                        int tipo = scanner.nextInt();

                        if(tipo == 1){
                            System.out.println("");
                            System.out.println("Escolha uma opção: ");
                            int tipoCarro = scanner.nextInt();
                        }else if(tipo == 2){
                            System.out.println("===MENU DE SERVIÇOS (MOTO)===");
                        }else{
                            System.out.println("O caractere digitado não corresponde a nenhum dos tipos de veiculos");
                        }


                        ServicoOficina oficinaService = new ServicoOficina();
                        double valorFinal = oficinaService.calcularOrcamento(vEncontrado, pecasParaServico);

                        System.out.println("\n--- RESUMO DO ORÇAMENTO ---");
                        System.out.println("Veículo: " + vEncontrado.getModelo());
                        System.out.println("Total: R$ " + valorFinal);
                    } else {
                        System.out.println("❌ Veículo não encontrado!");
                    }
                    System.out.println("\nPressione ENTER para continuar...");
                    scanner.nextLine();
                    break;
                case 0:
                    System.out.println("Saindo... Até logo!");
                    break;

                default:
                    System.out.println("Opção inválida! >:c");

                    // ele não aceita letras, ent tem q arrumar isso tb
            }
        }
        scanner.close();
    }
}