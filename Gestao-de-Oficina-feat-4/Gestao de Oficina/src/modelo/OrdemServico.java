package modelo;

import modelo.TipoServico;
import modelo.ServicosGerais;
import java.util.List;
import java.util.ArrayList;

public class OrdemServico implements Servico {
    private int id; // Veio da classe de servico
    private Veiculo veiculo;
    private ServicosGerais tipo;
    private List<Peca> pecas = new ArrayList<>();
    private List<TipoServico> subServicos = new ArrayList<>();

    private String status = "ABERTA"; // Veio da classe de servico


    public void adicionarSubServico(TipoServico ts) {
        if (ts != null) {
            this.subServicos.add(ts);
        }
    }

    // Construtor
   public OrdemServico(Veiculo veiculo, ServicosGerais tipo) {
        this.veiculo = veiculo;
        this.tipo = tipo;
    }

    @Override
    public double calcularValorTotal() {
        double total = tipo.getValorMaoDeObra();

        total += veiculo.calcularValorRevisao();

        // sub-serviços
        for (TipoServico ts : subServicos) {
            total += ts.getPrecoServico(); // O seu x += valor
        }

        // peças
        for (Peca peca : pecas) {
            total += peca.getValor();
        }

        return total;
    }


    // textao aqui
    // pq sb.append, n sout?
    // 1. mais rapido, mais facil de entender
    // 2. pode ser transformado direto em arquivo .txt
    // 3. mais familiar com o C, utilizando %
    @Override
    public String getResumo() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n======================================\n");
        sb.append("       ORÇAMENTO DE SERVIÇO\n");
        sb.append("======================================\n");
        sb.append("OS #").append(id).append(" [").append(status).append("]\n");
        sb.append("Veículo: ").append(veiculo.getModelo()).append(" (").append(veiculo.getPlaca()).append(")\n");
        sb.append("Categoria: ").append(tipo.getDescricao()).append("\n");

        sb.append("--------------------------------------\n");
        sb.append("DETALHAMENTO DOS VALORES:\n");
        sb.append(String.format("- Mão de Obra Base: R$ %.2f\n", tipo.getValorMaoDeObra()));
        sb.append(String.format("- Taxa de Oficina (%s): R$ %.2f\n", veiculo.getTipo(), veiculo.calcularValorRevisao()));

        for (TipoServico ts : subServicos) {
            sb.append(String.format("- %-25s: R$ %.2f\n", ts.getDescricao(), ts.getPrecoServico()));
        }

        sb.append("--------------------------------------\n");
        sb.append(String.format("VALOR TOTAL: R$ %.2f\n", calcularValorTotal()));
        sb.append("======================================\n");

        return sb.toString();
    }
}