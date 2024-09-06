package br.edu.poo.gestaoFinanceiraServer;

import lombok.Data;

@Data
public class FluxoFinanceiro {
    private int idFluxo;
    private String tipo;
    private String descricao;
    private String nomeBanco;
    private double valor;
    private String data;
}
