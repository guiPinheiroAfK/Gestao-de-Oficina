package modelo;

public class Carro extends Veiculo {
    public Carro(String placa, String modelo, int ano) {
        super(placa, modelo, ano);
    }

    @Override
    public double calcularValorRevisao(){
        return 250.00; //  taxa fixa pros vrum vrum
    }
}
