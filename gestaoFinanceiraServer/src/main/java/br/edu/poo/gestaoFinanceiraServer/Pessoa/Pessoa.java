package br.edu.poo.gestaoFinanceiraServer.Pessoa;

import br.edu.poo.gestaoFinanceiraServer.FluxoFinanceiro.FluxoFinanceiro;
import br.edu.poo.gestaoFinanceiraServer.Meta.Meta;
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
