package modelo;

public class Carro extends Veiculo {
    public Carro(String placa, String marca, String modelo, int ano) {
        super(placa, marca, modelo, ano, "CARRO");
    }

    @Override
    public double calcularValorRevisao(){
        return 250.00; //  taxa fixa pros vrum vrum
    }
}
