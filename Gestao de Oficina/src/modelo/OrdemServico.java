package modelo;

import java.util.List;
import java.util.ArrayList;

public class OrdemServico {
    private int id;
    private Veiculo veiculo;
    private List<Peca> pecas = new ArrayList<>();
    private double valorTotal;
    private String status; // "ABERTA", "FINALIZADA"

    public OrdemServico(Veiculo veiculo) {
        this.veiculo = veiculo;
        this.status = "ABERTA";
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Veiculo getVeiculo() { return veiculo; }
    public List<Peca> getPecas() { return pecas; }
    public double getValorTotal() { return valorTotal; }
    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public void adicionarPeca(Peca peca) {
        this.pecas.add(peca);
    }
}