package modelo;

public abstract class Veiculo {
    private String placa;
    private String modelo;
    private int ano;
    private String tipo;

    public Veiculo(String placa, String modelo, int ano, String tipo){
        this.placa = placa;
        this.modelo = modelo;
        this.ano = ano;
        this.tipo = tipo;
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

    public String getTipo(){
        return tipo;
    }
    public void setTipo(){}
}
