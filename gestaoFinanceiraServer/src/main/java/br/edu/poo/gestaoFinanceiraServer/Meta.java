package br.edu.poo.gestaoFinanceiraServer;

import lombok.Data;

@Data
public class Meta {
    private int idMeta;
    private String descricaoMeta;
    private double valorMeta;
    private String dataPrazo;
    private String tituloMeta;
}
