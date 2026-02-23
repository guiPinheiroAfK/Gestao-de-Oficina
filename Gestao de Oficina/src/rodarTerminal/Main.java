package rodarTerminal;

import banco.ConnectionFactory;
import banco.VeiculoDAO;
import modelo.Carro;
import modelo.Veiculo;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        VeiculoDAO dao = new VeiculoDAO();
        int opcao = -1;

        while (opcao != 0) {
            // menu bem basicao
            System.out.println("\n--- Oficina do Gui ---");
            System.out.println("1 - Cadastrar Carro de Teste (R32)");
            System.out.println("2 - Apagar Veículo por Placa");
            System.out.println("3 - Listar Veículos no Pátio");
            System.out.println("3 - Atualizar Veículos no Pátio");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    try {
                        Carro c = new Carro("ABC-4321", "Nissan R32 GTS", 1998);
                        dao.salvar(c, "CARRO");
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
                    break;

                case 3:
                    System.out.println("\n--- Lista de Veículos no Pátio ---");
                    List<Veiculo> lista = dao.buscarTodos();
                    if (lista.isEmpty()) {
                        System.out.println("O pátio está vazio!");
                    } else {
                        for (Veiculo v : lista) {
                            System.out.println("Modelo: " + v.getModelo() + " | Placa: " + v.getPlaca() + " | Ano: " + v.getAno());
                        }
                    }

                    // todos os salvar/buscar/deletar estao no "banco.VeiculoDAO"
                    // e as heranças/polimorfismo estão no "modelo."
                    break;

                case 4:
                    // ex: mudar o 370z para um 370z NISMO (18 cv a mais)
                    System.out.println("\n--- Atualizando Veículo ---");
                    Carro atualizado = new Carro("ABC-1234", "Nissan 370z NISMO", 2025);
                    dao.atualizar(atualizado);
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