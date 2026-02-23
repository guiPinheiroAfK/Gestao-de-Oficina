package modelo;

public abstract class Veiculo {
    private String placa;
    private String modelo;
    private int ano;

    public Veiculo(String placa, String modelo, int ano){
        this.placa = placa;
        this.modelo = modelo;
        this.ano = ano;
    }

    // polimorfismo
    public abstract double calcularValorRevisao();

    // getters e setters
    public String getPlaca() {
        return placa;
    }
    public void setPlaca() {}

    public String getModelo() {
        return modelo;
    }
    public void setModelo() {}

    public int getAno() {
        return ano;
    }
    public void setAno() {}

}
