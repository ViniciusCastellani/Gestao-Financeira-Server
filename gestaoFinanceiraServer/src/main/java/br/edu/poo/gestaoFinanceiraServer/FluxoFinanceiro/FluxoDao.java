package br.edu.poo.gestaoFinanceiraServer.FluxoFinanceiro;

import br.edu.poo.gestaoFinanceiraServer.Pessoa.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FluxoDao {

    private @Autowired JdbcTemplate jdbcTemplate;

    public Pessoa inserirFluxo(Pessoa pessoa, int idPessoa) throws SQLException {
        String sqlInsertFluxo = "INSERT INTO FluxoFinanceiro (codPessoa, tipo, descricao, nomeBanco, valor, data) " +
                "VALUES (?,?,?,?,?,?)";

        try (Connection con = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement psFluxo = con.prepareStatement(sqlInsertFluxo, Statement.RETURN_GENERATED_KEYS);) {

            con.setAutoCommit(false);

            List<FluxoFinanceiro> listaFluxos = new ArrayList<>();
            listaFluxos = pessoa.getListaFluxoFinanceiro();

            FluxoFinanceiro fluxo = listaFluxos.get(listaFluxos.size() - 1);

            psFluxo.setInt(1, idPessoa);
            psFluxo.setString(2, fluxo.getTipo());
            psFluxo.setString(3, fluxo.getDescricao());
            psFluxo.setString(4, fluxo.getNomeBanco());
            psFluxo.setDouble(5, fluxo.getValor());
            psFluxo.setString(6, fluxo.getData());
            psFluxo.executeUpdate();

            ResultSet tableKeys = psFluxo.getGeneratedKeys();
            tableKeys.next();
            fluxo.setIdFluxo(tableKeys.getInt(1));

            con.commit();
            System.out.println("Fluxo inserido com êxito: " + pessoa.getIdPessoa());
            return pessoa;
        }
    }


    public boolean deletarFluxo(int idPessoa, int idFluxo) throws Exception {
        String sqlDeleteMeta = "DELETE FROM FLUXOFINANCEIRO WHERE codPessoa = ? AND idFluxo = ?";

        try (Connection con = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement psMeta = con.prepareStatement(sqlDeleteMeta);) {

            psMeta.setInt(1,idPessoa);
            psMeta.setInt(2,idFluxo);
            int result = psMeta.executeUpdate();

            if (result == 1){
                return true;
            }

            throw new Exception("Id não encontrado na tabela");
        }
    }
}
