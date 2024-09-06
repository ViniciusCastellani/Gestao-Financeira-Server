package br.edu.poo.gestaoFinanceiraServer;

import lombok.Data;
import java.util.List;

@Data
public class Pessoa {
    private int idPessoa;
    private String nome;
    private String email;
    private String senha;
    private double balancoGeral;
    private double todasFaturas;
    private List<FluxoFinanceiro> listaFluxoFinanceiro;
    private List<Meta> listaMetas;
}
