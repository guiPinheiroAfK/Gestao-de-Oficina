package modelo;

public enum TipoServico {
    // preventiva
        OleoFiltro("Troca de Óleo e Filtros: ", 350.00),
        RevFreio("Revisão de Freios:", 500.00),
        Arrefecimento("Sistema de Arrefecimento:", 150.00),
        VelasCorreia("Verificação de Velas e Correias:", 1250.00 ),
        AlinhaBalanca("Alinhamento e Balanceamento:", 250.00),
        Bateria("Verificação da Bateria:", 400.00),

    // corretiva
        ReparoMotor("Reparo de Motor", 6500.00),
        Suspensao("Troca de amortecedores, molas, buchas e bandejas", 2000.00),
        Direcao("Reparo em direção hidráulica ou elétrica.", 950.00),
        TransmissaoEmbreagem("Troca de kit de embreagem ou manutenção do câmbio automático", 1700.00),
        Escapamento("Reparo no Sistema de Escapamento", 650.00 ),
    // elétrica
        Diagnostico("Diagnóstico Computadorizado ", 110.00),
        Iluminacao("Sistema de Iluminação", 350.00),
        MotorArranque("Motor de Arranque e Alternador",  600.00),
        VidrosTravas("Vidros e Travas Elétricas", 750.00),
    // estética
        Lavagem("Lavagem Detalhada e Higienização", 300.00),
        Funilaria("Funilaria e Pintura", 3000.00),
        Polimento("Polimento e Cristalização/Vitrificação", 1500.00),
        MartelinhoOuro("Martelinho de Ouro", 400.00),
    // pneus
        TrocaPneus("Troca de Pneu", 1500.00),
        Remendo("Reparo de Pneus", 60.00);

        private final String descricao;
        private final double preco;

        TipoServico(String descricao, double preco){
            this.descricao = descricao;
            this.preco = preco;
        }

    public String getDescricao() {
        return descricao;
    }
    public double getPrecoServico() {
        return preco;
    }

}
