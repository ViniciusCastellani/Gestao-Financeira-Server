package br.edu.poo.gestaoFinanceiraServer.Pessoa;

import br.edu.poo.gestaoFinanceiraServer.Retorno.Retorno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class PessoaController {
    @Autowired PessoaDao dao;

    @GetMapping("/pessoa/obter/{idPessoa}")
    public Pessoa getPessoa(@PathVariable int idPessoa) throws Exception {
        return dao.consultarPessoa(idPessoa);
    }


    @GetMapping("/pessoa/listar")
    public List<Pessoa> listarPessoas() throws Exception {
        return dao.listarPessoas();
    }


    @PostMapping("/pessoa")
    public Retorno postPessoa(@RequestBody Pessoa p) {
        try {
            return new Retorno(dao.inserirPessoa(p));
        } catch(Exception ex){
            return new Retorno(ex.getMessage());
        }

    }

    @PutMapping("/pessoa/atualizar/{idPessoa}")
    public Retorno putPessoa (@PathVariable int idPessoa, @RequestBody Pessoa p) {
        try {
            return new Retorno(dao.atualizarPessoa(p, idPessoa));
        } catch(Exception ex){
            return new Retorno(ex.getMessage());
        }
    }


    @PutMapping("/pessoa/atualizarTodasInfo/{idPessoa}")
    public Retorno putTodaInfoPessoa (@PathVariable int idPessoa, @RequestBody Pessoa p) {
        try {
            return new Retorno(dao.atualizarTodasInfoPessoa(p, idPessoa));
        } catch(Exception ex){
            return new Retorno(ex.getMessage());
        }
    }


    @DeleteMapping("/pessoa/deletar/{idPessoa}")
    public Boolean deletarPessoa(@PathVariable int idPessoa) throws Exception{
        return dao.deletarPessoa(idPessoa);
    }

}