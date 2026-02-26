package modelo;

import java.util.List;

public class OrdemServico implements Servico{
    private Veiculo veiculo;
    private TipoServico tipo;
    private List<Peca> pecas;


    @Override
    public double calcularValorTotal() {
        double totalPecas = pecas.stream().mapToDouble(Peca::getValor).sum();
        return totalPecas + tipo.getValorMaoDeObra();
    }

    @Override
    public String getResumo() {
        return "Veículo: " + veiculo.getModelo() + "\nServiço: " + tipo.getDescricao();
    }
}
