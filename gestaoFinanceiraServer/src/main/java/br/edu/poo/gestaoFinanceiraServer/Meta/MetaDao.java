package br.edu.poo.gestaoFinanceiraServer.Meta;

import br.edu.poo.gestaoFinanceiraServer.Pessoa.Pessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MetaDao {
    private @Autowired JdbcTemplate jdbcTemplate;

    public Pessoa inserirMeta(Pessoa pessoa, int idPessoa) throws SQLException {
        String sqlInsertMeta = "INSERT INTO Meta (codPessoa, tituloMeta, descricaoMeta, valorMeta, dataPrazo) " +
                "VALUES (?,?,?,?,?)";

        try (Connection con = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement psMeta = con.prepareStatement(sqlInsertMeta, Statement.RETURN_GENERATED_KEYS);) {

            con.setAutoCommit(false);

            List<Meta> listaMetas = new ArrayList<>();
            listaMetas = pessoa.getListaMetas();

            Meta meta = listaMetas.get(listaMetas.size() - 1);

            psMeta.setInt(1, idPessoa);
            psMeta.setString(2,meta.getTituloMeta());
            psMeta.setString(3, meta.getDescricaoMeta());
            psMeta.setDouble(4, meta.getValorMeta());
            psMeta.setString(5, meta.getDataPrazo());
            psMeta.executeUpdate();

            ResultSet tableKeys = psMeta.getGeneratedKeys();
            tableKeys.next();
            meta.setIdMeta(tableKeys.getInt(1));

            con.commit();
            System.out.println("Meta inserida com êxito: " + pessoa.getIdPessoa());
            return pessoa;
        }
    }


    public boolean deletarMeta(int idPessoa, int idMeta) throws Exception {
        String sqlDeleteMeta = "DELETE FROM META WHERE codPessoa = ? AND idMeta = ?";

        try (Connection con = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement psMeta = con.prepareStatement(sqlDeleteMeta);) {

            psMeta.setInt(1,idPessoa);
            psMeta.setInt(2,idMeta);
            int result = psMeta.executeUpdate();

            if (result == 1){
                return true;
            }

            throw new Exception("Id não encontrado na tabela");
        }
    }
}
