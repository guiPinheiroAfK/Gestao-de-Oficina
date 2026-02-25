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
            System.out.println("1 - Cadastrar Carro de Teste (R32)");
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
                        Carro c = new Carro("ABC-4321", "Nissan R32 GTS", 1998);
                        dao.salvar(c, "CARRO");
                        sincronizar(patioDinamico, dao); // <- <- recarrega a lista
                        // print de confirmação:
                        System.out.println("\n✅ Veículo Cadastrado com Sucesso:");
                        System.out.println("Modelo: " + c.getModelo() + " | Placa: " + c.getPlaca() + " | Ano: " + c.getAno());
                    } catch (RuntimeException e) {      // ^ pega os dados do carro e printa
                        System.out.println("⚠️ Erro: " + e.getMessage());
                    }                                    // dps corrigir esta mensagem
                                                         // ele envia um "already exists"

                    // o try serve para pegar, tipo, ja tem um carro com a placa "ABC-1234" e ao inves do
                    // programa travar, ele pega o erro, aponta o que foi errado e volta pro menu
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
                    System.out.println("\n--- Simulando Orçamento ---");
                    // Exemplo: Usando o Nissan R32 que você cadastrou no case 1
                    Veiculo r32 = new Carro("ABC-4321", "Nissan R32 GTS", 1998);

                    // Criando uma lista de peças para o serviço
                    List<Peca> pecasParaServico = new java.util.ArrayList<>();
                    pecasParaServico.add(new Peca("Filtro de Óleo", 50.0, 1));
                    pecasParaServico.add(new Peca("Pastilha de Freio", 180.0, 1));

                    ServicoOficina oficina = new ServicoOficina();
                    double valorFinal = oficina.calcularOrcamento(r32, pecasParaServico);

                    System.out.println("Veículo: " + r32.getModelo());
                    System.out.println("Valor da Revisão Base: R$ " + r32.calcularValorRevisao());
                    System.out.println("Total com Peças: R$ " + valorFinal);

                    System.out.println("\nPresione ENTER para voltar ao menu...");
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