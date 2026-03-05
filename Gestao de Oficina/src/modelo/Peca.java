package modelo;

public class Peca {
    private int id;
    private String nome;
    private double valor;
    private int estoque;

    public Peca(String nome, double valor, int estoque) {
        this.nome = nome;
        this.valor = valor;
        this.estoque = estoque;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public void setNome(String nome) { this.nome = nome; }
    public String getNome() { return nome; }
    public void setValor(double valor) { this.valor = valor; }
    public double getValor() { return valor; }
    public int getEstoque() { return estoque; }
    public void setEstoque(int estoque) { this.estoque = estoque; }
}