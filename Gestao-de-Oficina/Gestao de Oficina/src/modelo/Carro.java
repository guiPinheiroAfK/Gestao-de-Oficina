package modelo;

public class Carro extends Veiculo {

    public Carro(String placa, String marca, String modelo, int ano) {
        super(placa, marca, modelo, ano, "CARRO");
    }

    @Override
    public double calcularValorRevisao() {
        return 300.0; // valor padrão de revisão de carro
    }
}