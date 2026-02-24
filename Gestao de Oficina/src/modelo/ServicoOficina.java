package modelo;

import java.util.List;

public class ServicoOficina {

    /**
     * Calcula o valor total do orçamento.
     * Soma a taxa fixa de revisão do veículo (Polimorfismo) com o valor total das peças.
     */
    public double calcularOrcamento(Veiculo veiculo, List<Peca> pecas) {
        // Pega o valor base dependendo se é Carro (250.0) ou Moto (120.0)
        double total = veiculo.calcularValorRevisao();

        // Soma o valor de cada peça utilizada
        for (Peca p : pecas) {
            total += p.getValor();
        }

        return total;
    }

    /**
     * Regra de Negócio: Valida se a Ordem de Serviço pode ser fechada.
     * Exemplo: Não fecha sem peças ou se o veículo for muito antigo (regra hipotética).
     */
    public boolean validarFechamentoOS(Veiculo veiculo, List<Peca> pecas) {
        if (pecas.isEmpty()) {
            System.out.println("⚠️ Alerta: Nenhuma peça foi adicionada ao serviço.");
            return false;
        }
        if (veiculo.getAno() < 1900) {
            System.out.println("⚠️ Erro: Ano do veículo inválido.");
            return false;
        }
        return true;
    }
}