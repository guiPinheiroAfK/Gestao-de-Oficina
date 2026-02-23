package modelo;

public class Moto extends Veiculo {
    public Moto(String placa, String modelo, int ano) {
        super(placa, modelo, ano);
    }

    @Override
    public double calcularValorRevisao() {
        return 120.00; // taxa fixa pras randandandan
    }
}
