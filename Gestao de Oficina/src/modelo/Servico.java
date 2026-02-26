package modelo;

public interface Servico {
    // deve obrigar qualquer serviço (troca de óleo, lanternagem, etc)...
    // a dizer quanto ele custa no final.

    double calcularValorTotal();
    String getResumo();
}
