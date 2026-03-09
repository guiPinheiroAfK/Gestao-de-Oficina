package modelo;

public class Moto extends Veiculo {
    public Moto(String placa, String marca, String modelo, int ano) {
        super(placa, marca, modelo, ano, "MOTO");
    }

    @Override
    public double calcularValorRevisao() {
        return 120.00; // taxa fixa pras randandandan
    }
}
