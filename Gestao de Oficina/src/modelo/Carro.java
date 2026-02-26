package modelo;

public class Carro extends Veiculo {
    public Carro(String placa, String modelo, int ano, String tipo) {
        super(placa, modelo, ano, tipo);
    }

    @Override
    public double calcularValorRevisao(){
        return 250.00; //  taxa fixa pros vrum vrum
    }
}
