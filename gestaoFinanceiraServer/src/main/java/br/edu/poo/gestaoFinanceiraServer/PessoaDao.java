package br.edu.poo.gestaoFinanceiraServer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PessoaDao {
    private @Autowired JdbcTemplate jdbcTemplate;

    public Pessoa inserirPessoa(Pessoa pessoa) throws Exception {
        String sqlInsertPessoa = "INSERT INTO Pessoa(nome, email, senha) VALUES (?,?,?)";

        String sqlUpdateBalancoGeral = "UPDATE Pessoa SET balancoGeral = ? WHERE idPessoa = ?";

        String sqlUpdateTodasFaturas = "UPDATE Pessoa SET todasFaturas = ? WHERE idPessoa = ?";

        String sqlInsertFluxoFinanceiro = "INSERT INTO FluxoFinanceiro (codPessoa, tipo, descricao, nomeBanco, valor, data) " +
                "VALUES (?,?,?,?,?,?)";

        String sqlInsertMeta = "INSERT INTO Meta (codPessoa, tituloMeta, descricaoMeta, valorMeta, dataPrazo) " +
                "VALUES (?,?,?,?,?)";

        try (Connection con = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement psPessoa = con.prepareStatement(sqlInsertPessoa, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement psFluxoFinanceiro = con.prepareStatement(sqlInsertFluxoFinanceiro, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement psMeta = con.prepareStatement(sqlInsertMeta, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement psBalancoGeral = con.prepareStatement(sqlUpdateBalancoGeral);
             PreparedStatement psTodasFaturas = con.prepareStatement(sqlUpdateTodasFaturas);) {

            con.setAutoCommit(false);

            psPessoa.setString(1, pessoa.getNome());
            psPessoa.setString(2, pessoa.getEmail());
            psPessoa.setString(3, pessoa.getSenha());
            psPessoa.executeUpdate();

            ResultSet tableKeys = psPessoa.getGeneratedKeys();
            tableKeys.next();
            pessoa.setIdPessoa(tableKeys.getInt(1));

            String balancoGeralST = String.valueOf(pessoa.getBalancoGeral());
            String todasFaturasST = String.valueOf(pessoa.getTodasFaturas());

            if (!balancoGeralST.isEmpty()) {
                psBalancoGeral.setDouble(1, pessoa.getBalancoGeral());
                psBalancoGeral.setInt(2, pessoa.getIdPessoa());
                psBalancoGeral.executeUpdate();
            }

            if (!todasFaturasST.isEmpty()) {
                psTodasFaturas.setDouble(1, pessoa.getTodasFaturas());
                psTodasFaturas.setInt(2, pessoa.getIdPessoa());
                psTodasFaturas.executeUpdate();
            }

            if (!pessoa.getListaFluxoFinanceiro().isEmpty()) {
                for (FluxoFinanceiro fluxo : pessoa.getListaFluxoFinanceiro()) {
                    psFluxoFinanceiro.setInt(1, pessoa.getIdPessoa());
                    psFluxoFinanceiro.setString(2, fluxo.getTipo());
                    psFluxoFinanceiro.setString(3, fluxo.getDescricao());
                    psFluxoFinanceiro.setString(4, fluxo.getNomeBanco());
                    psFluxoFinanceiro.setDouble(5, fluxo.getValor());
                    psFluxoFinanceiro.setString(6, fluxo.getData());

                    psFluxoFinanceiro.executeUpdate();
                    tableKeys = psFluxoFinanceiro.getGeneratedKeys();
                    tableKeys.next();
                    fluxo.setIdFluxo(tableKeys.getInt(1));
                }
            }

            if (!pessoa.getListaMetas().isEmpty()) {
                for (Meta meta : pessoa.getListaMetas()) {
                    psMeta.setInt(1, pessoa.getIdPessoa());
                    psMeta.setString(2,meta.getTituloMeta());
                    psMeta.setString(3, meta.getDescricaoMeta());
                    psMeta.setDouble(4, meta.getValorMeta());
                    psMeta.setString(5, meta.getDataPrazo());

                    psMeta.executeUpdate();
                    tableKeys = psMeta.getGeneratedKeys();
                    tableKeys.next();
                    meta.setIdMeta(tableKeys.getInt(1));
                }
            }

            con.commit();
            System.out.println("Pessoa inserida com sucesso: " + pessoa.getIdPessoa());
            return pessoa;
        }
    }


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

    public Pessoa atualizarPessoa(Pessoa pessoa, int idPessoa) throws SQLException {
        String sqlUpdatePessoa = "UPDATE Pessoa SET nome = ?, email = ?, senha = ? WHERE idPessoa = ?";

        String sqlUpdateBalancoGeral = "UPDATE Pessoa SET balancoGeral = ? WHERE idPessoa = ?";

        String sqlUpdateTodasFaturas = "UPDATE Pessoa SET todasFaturas = ? WHERE idPessoa = ?";


        try (Connection con = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement psPessoa = con.prepareStatement(sqlUpdatePessoa);
             PreparedStatement psBalancoGeral = con.prepareStatement(sqlUpdateBalancoGeral);
             PreparedStatement psTodasFaturas = con.prepareStatement(sqlUpdateTodasFaturas);) {

            con.setAutoCommit(false);

            psPessoa.setString(1, pessoa.getNome());
            psPessoa.setString(2, pessoa.getEmail());
            psPessoa.setString(3, pessoa.getSenha());
            psPessoa.setInt(4, idPessoa);
            psPessoa.executeUpdate();

            String balancoGeralST = String.valueOf(pessoa.getBalancoGeral());
            String todasFaturasST = String.valueOf(pessoa.getTodasFaturas());

            if (!balancoGeralST.isEmpty()) {
                psBalancoGeral.setDouble(1, pessoa.getBalancoGeral());
                psBalancoGeral.setInt(2, pessoa.getIdPessoa());
                psBalancoGeral.executeUpdate();
            }

            if (!todasFaturasST.isEmpty()) {
                psTodasFaturas.setDouble(1, pessoa.getTodasFaturas());
                psTodasFaturas.setInt(2, pessoa.getIdPessoa());
                psTodasFaturas.executeUpdate();
            }

            con.commit();
            System.out.println("Pessoa modificada com êxito: " + pessoa.getIdPessoa());
            return pessoa;
        }
    }


    public Pessoa atualizarTodasInfoPessoa(Pessoa pessoa, int idPessoa) throws SQLException {
        String sqlUpdatePessoa = "UPDATE Pessoa SET nome = ?, email = ?, senha = ? WHERE idPessoa = ?";
        String sqlUpdateBalancoGeral = "UPDATE Pessoa SET balancoGeral = ? WHERE idPessoa = ?";
        String sqlUpdateTodasFaturas = "UPDATE Pessoa SET todasFaturas = ? WHERE idPessoa = ?";
        String sqlUpdateMeta = "UPDATE Meta SET tituloMeta = ?, descricaoMeta = ?, valorMeta = ?, dataPrazo = ? WHERE idMeta = ? AND codPessoa = ?";
        String sqlUpdateFluxo = "UPDATE FluxoFinanceiro SET tipo = ?, descricao = ?, nomeBanco = ?, valor = ?, data = ? WHERE idFluxo = ? AND codPessoa = ?";

        try (Connection con = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement psPessoa = con.prepareStatement(sqlUpdatePessoa);
             PreparedStatement psBalancoGeral = con.prepareStatement(sqlUpdateBalancoGeral);
             PreparedStatement psTodasFaturas = con.prepareStatement(sqlUpdateTodasFaturas);
             PreparedStatement psUpdateMeta = con.prepareStatement(sqlUpdateMeta);
             PreparedStatement psUpdateFluxo = con.prepareStatement(sqlUpdateFluxo)) {

            con.setAutoCommit(false);

            // Atualizando informações da pessoa
            psPessoa.setString(1, pessoa.getNome());
            psPessoa.setString(2, pessoa.getEmail());
            psPessoa.setString(3, pessoa.getSenha());
            psPessoa.setInt(4, idPessoa);
            psPessoa.executeUpdate();

            String balancoGeralST = String.valueOf(pessoa.getBalancoGeral());
            String todasFaturasST = String.valueOf(pessoa.getTodasFaturas());

            if (!balancoGeralST.isEmpty()) {
                psBalancoGeral.setDouble(1, pessoa.getBalancoGeral());
                psBalancoGeral.setInt(2, idPessoa);
                psBalancoGeral.executeUpdate();
            }

            // Atualizando todasFaturas
            if (!todasFaturasST.isEmpty()) {
                psTodasFaturas.setDouble(1, pessoa.getTodasFaturas());
                psTodasFaturas.setInt(2, idPessoa);
                psTodasFaturas.executeUpdate();
            }

            // Atualizando metas
            if (pessoa.getListaMetas() != null) {
                for (Meta meta : pessoa.getListaMetas()) {
                    psUpdateMeta.setString(1, meta.getTituloMeta());
                    psUpdateMeta.setString(2, meta.getDescricaoMeta());
                    psUpdateMeta.setDouble(3, meta.getValorMeta());
                    psUpdateMeta.setString(4, meta.getDataPrazo());
                    psUpdateMeta.setInt(5, meta.getIdMeta()); // ID da meta
                    psUpdateMeta.setInt(6, idPessoa); // ID da pessoa
                    psUpdateMeta.executeUpdate();
                }
            }

            // Atualizando fluxos
            if (pessoa.getListaFluxoFinanceiro() != null) {
                for (FluxoFinanceiro fluxo : pessoa.getListaFluxoFinanceiro()) {
                    psUpdateFluxo.setString(1, fluxo.getTipo());
                    psUpdateFluxo.setString(2, fluxo.getDescricao());
                    psUpdateFluxo.setString(3, fluxo.getNomeBanco());
                    psUpdateFluxo.setDouble(4, fluxo.getValor());
                    psUpdateFluxo.setString(5, fluxo.getData());
                    psUpdateFluxo.setInt(6, fluxo.getIdFluxo()); // ID do fluxo
                    psUpdateFluxo.setInt(7, idPessoa); // ID da pessoa
                    psUpdateFluxo.executeUpdate();
                }
            }

            con.commit();
            System.out.println("Pessoa, suas metas e fluxos modificados com êxito: " + idPessoa);
            return pessoa;
        }
    }


    public Pessoa consultarPessoa(int idPessoa) throws SQLException {
        Pessoa pessoa = new Pessoa();

        String sqlConsultaPessoa = "SELECT * FROM Pessoa WHERE idPessoa = ?";

        String sqlConsultaFluxoFinanceiro = "SELECT * FROM FluxoFinanceiro " +
                "WHERE codPessoa = ?";

        String sqlConsultaMeta = "SELECT * FROM Meta WHERE codPessoa = ?";

        try (Connection con = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement psPessoa = con.prepareStatement(sqlConsultaPessoa);
             PreparedStatement psFluxoFinanceiro = con.prepareStatement(sqlConsultaFluxoFinanceiro);
             PreparedStatement psMeta = con.prepareStatement(sqlConsultaMeta);) {

            con.setAutoCommit(false);

            psPessoa.setInt(1, idPessoa);
            ResultSet rsPessoa = psPessoa.executeQuery();

            if (rsPessoa.next()) {
                pessoa.setIdPessoa(rsPessoa.getInt("idPessoa"));
                pessoa.setNome(rsPessoa.getString("nome"));
                pessoa.setEmail(rsPessoa.getString("email"));
                pessoa.setSenha(rsPessoa.getString("senha"));
                pessoa.setBalancoGeral(rsPessoa.getDouble("balancoGeral"));
                pessoa.setTodasFaturas(rsPessoa.getDouble("todasFaturas"));

                psFluxoFinanceiro.setInt(1, idPessoa);
                ResultSet rsFluxoFinanceiro = psFluxoFinanceiro.executeQuery();

                List<FluxoFinanceiro> listaFluxoFinanceiro = new ArrayList<>();
                while (rsFluxoFinanceiro.next()) {
                    FluxoFinanceiro fluxo = new FluxoFinanceiro();
                    fluxo.setIdFluxo(rsFluxoFinanceiro.getInt("idFluxo"));
                    fluxo.setTipo(rsFluxoFinanceiro.getString("tipo"));
                    fluxo.setDescricao(rsFluxoFinanceiro.getString("descricao"));
                    fluxo.setNomeBanco(rsFluxoFinanceiro.getString("nomeBanco"));
                    fluxo.setValor(rsFluxoFinanceiro.getDouble("valor"));
                    fluxo.setData(rsFluxoFinanceiro.getString("data"));
                    listaFluxoFinanceiro.add(fluxo);
                }
                pessoa.setListaFluxoFinanceiro(listaFluxoFinanceiro);


                psMeta.setInt(1, idPessoa);
                ResultSet rsMeta = psMeta.executeQuery();

                List<Meta> listaMetas = new ArrayList<>();
                while (rsMeta.next()) {
                    Meta meta = new Meta();
                    meta.setIdMeta(rsMeta.getInt("idMeta"));
                    meta.setTituloMeta(rsMeta.getString("tituloMeta"));
                    meta.setDescricaoMeta(rsMeta.getString("descricaoMeta"));
                    meta.setValorMeta(rsMeta.getDouble("valorMeta"));
                    meta.setDataPrazo(rsMeta.getString("dataPrazo"));
                    listaMetas.add(meta);
                }
                pessoa.setListaMetas(listaMetas);
                con.commit();
                return pessoa;
            }
        }
        return null;
    }


    public List<Pessoa> listarPessoas() throws SQLException {
        List<Pessoa> listaPessoas = new ArrayList<>();

        String sqlQueryPessoa = "SELECT * FROM Pessoa";

        String sqlQueryFluxoFinanceiro = "SELECT * FROM FluxoFinanceiro " +
                "WHERE codPessoa = ?";

        String sqlQueryMeta = "SELECT * FROM Meta WHERE codPessoa = ?";

        try (Connection con = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement psPessoa = con.prepareStatement(sqlQueryPessoa);
             PreparedStatement psFluxoFinanceiro = con.prepareStatement(sqlQueryFluxoFinanceiro);
             PreparedStatement psMeta = con.prepareStatement(sqlQueryMeta);) {

            ResultSet rsPessoa = psPessoa.executeQuery();
            while (rsPessoa.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setIdPessoa(rsPessoa.getInt("idPessoa"));
                pessoa.setNome(rsPessoa.getString("nome"));
                pessoa.setEmail(rsPessoa.getString("email"));
                pessoa.setSenha(rsPessoa.getString("senha"));
                pessoa.setBalancoGeral(rsPessoa.getDouble("balancoGeral"));
                pessoa.setTodasFaturas(rsPessoa.getDouble("todasFaturas"));

                psFluxoFinanceiro.setInt(1, pessoa.getIdPessoa());
                ResultSet rsFluxoFinanceiro = psFluxoFinanceiro.executeQuery();

                List<FluxoFinanceiro> listaFluxoFinanceiro = new ArrayList<>();
                while (rsFluxoFinanceiro.next()) {
                    FluxoFinanceiro fluxo = new FluxoFinanceiro();
                    fluxo.setIdFluxo(rsFluxoFinanceiro.getInt("idFluxo"));
                    fluxo.setTipo(rsFluxoFinanceiro.getString("tipo"));
                    fluxo.setDescricao(rsFluxoFinanceiro.getString("descricao"));
                    fluxo.setNomeBanco(rsFluxoFinanceiro.getString("nomeBanco"));
                    fluxo.setValor(rsFluxoFinanceiro.getDouble("valor"));
                    fluxo.setData(rsFluxoFinanceiro.getString("data"));
                    listaFluxoFinanceiro.add(fluxo);
                }
                pessoa.setListaFluxoFinanceiro(listaFluxoFinanceiro);

                psMeta.setInt(1, pessoa.getIdPessoa());
                ResultSet rsMeta = psMeta.executeQuery();

                List<Meta> listaMetas = new ArrayList<>();
                while (rsMeta.next()) {
                    Meta meta = new Meta();
                    meta.setIdMeta(rsMeta.getInt("idMeta"));
                    meta.setTituloMeta(rsMeta.getString("tituloMeta"));
                    meta.setDescricaoMeta(rsMeta.getString("descricaoMeta"));
                    meta.setValorMeta(rsMeta.getDouble("valorMeta"));
                    meta.setDataPrazo(rsMeta.getString("dataPrazo"));
                    listaMetas.add(meta);
                }
                pessoa.setListaMetas(listaMetas);

                listaPessoas.add(pessoa);
            }
            return listaPessoas;
        }
    }

    public boolean deletarPessoa(int id) throws Exception {
        String sqlDeletePessoa = "DELETE FROM Pessoa WHERE idPessoa = ?";

        try (Connection con = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement psPessoa = con.prepareStatement(sqlDeletePessoa);) {

            psPessoa.setInt(1,id);
            int result = psPessoa.executeUpdate();

            if (result == 1){
                return true;
            }

            throw new Exception("Id não encontrado na tabela");
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

