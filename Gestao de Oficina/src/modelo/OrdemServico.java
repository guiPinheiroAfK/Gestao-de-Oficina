package modelo;

import java.util.List;
import java.util.ArrayList;

public class OrdemServico implements Servico {
    private int id; // Veio da classe de servico
    private Veiculo veiculo;
    private TipoServico tipo;
    private List<Peca> pecas = new ArrayList<>();
    private String status = "ABERTA"; // Veio da classe de servico

    // Construtor
    public OrdemServico(Veiculo veiculo, TipoServico tipo) {
        this.veiculo = veiculo;
        this.tipo = tipo;
    }

    @Override
    public double calcularValorTotal() {
        double totalPecas = pecas.stream().mapToDouble(Peca::getValor).sum();
        // Soma: Peças + Mão de Obra do Enum + Taxa de Revisão (Polimorfismo do Veículo)
        return totalPecas + tipo.getValorMaoDeObra() + veiculo.calcularValorRevisao();
    }

    @Override
    public String getResumo() {
        return "OS #" + id + " [" + status + "]\n" +
                "Veículo: " + veiculo.getModelo() + " (" + veiculo.getPlaca() + ")\n" +
                "Serviço: " + tipo.getDescricao() + "\n" +
                "Total: R$ " + calcularValorTotal();
    }

    // Getters e Setters necessários para o DAO
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Veiculo getVeiculo() { return veiculo; }
    public List<Peca> getPecas() { return pecas; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}