package br.edu.poo.gestaoFinanceiraServer.Meta;

import br.edu.poo.gestaoFinanceiraServer.Pessoa.Pessoa;
import br.edu.poo.gestaoFinanceiraServer.Pessoa.PessoaDao;
import br.edu.poo.gestaoFinanceiraServer.Retorno.Retorno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class MetaController {
    @Autowired MetaDao dao;

    @PutMapping("/pessoa/meta/{idPessoa}")
    public Retorno putMeta (@PathVariable int idPessoa, @RequestBody Pessoa p) {
        try {
            return new Retorno(dao.inserirMeta(p, idPessoa));
        } catch(Exception ex){
            return new Retorno(ex.getMessage());
        }
    }

    @DeleteMapping("/deletar/meta/{idMeta}/pessoa/{idPessoa}")
    public Boolean deletarMeta(@PathVariable int idPessoa, @PathVariable int idMeta ) throws Exception{
        return dao.deletarMeta(idPessoa, idMeta);
    }

}
