package modelo;

public enum ServicosGerais{

    // O querido "Enum"
    // foi utilizado para padronizar as categorias
    // de serviços e centralizar os valores de mão de obra
    // (facilitou mt, é só olhar nos últimos commits antes desse).

    PREVENTIVA("Manutenção Preventiva", 100.0),
    CORRETIVA("Manutenção Corretiva", 150.0),
    ELETRICA("Diagnóstico e Elétrica", 200.0),
    ESTETICA("Estética e Limpeza", 80.0),
    PNEUS("Serviços de Pneus", 50.0);

    private final String descricao;
    private final double valorMaoDeObra;

    ServicosGerais(String descricao, double valorMaoDeObra) {
        this.descricao = descricao;
        this.valorMaoDeObra = valorMaoDeObra;
    }

    public String getDescricao() {
        return descricao;
    }
    public double getValorMaoDeObra() {
        return valorMaoDeObra;
    }
}
