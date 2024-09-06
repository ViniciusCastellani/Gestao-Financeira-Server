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
}

