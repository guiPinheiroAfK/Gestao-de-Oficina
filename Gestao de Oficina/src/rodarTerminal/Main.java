package rodarTerminal;

import banco.VeiculoDAO;
import modelo.Veiculo;
import servico.ExtraMain; // Importa a classe de "package servico"
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // 1. Inicializa o que é necessário
        Scanner scanner = new Scanner(System.in);
        VeiculoDAO dao = new VeiculoDAO();

        // 2. Joga tudo para uma lista antes de rodar o menu
        List<Veiculo> patioDinamico = dao.buscarTodos();
        System.out.println("✅ " + patioDinamico.size() + " veículos carregados do banco!");
        // Faz o mesmo que o "sleep() " em C
        TimeUnit.SECONDS.sleep(2);

        //3. Chama o menu da ExtraMain
        ExtraMain.menuPrincipal(scanner, patioDinamico, dao);

        // 4. Fecha o scanner ao sair do loop do menu
        scanner.close();
    }
}