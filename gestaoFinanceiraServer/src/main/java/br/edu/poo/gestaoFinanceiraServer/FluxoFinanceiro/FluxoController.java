package br.edu.poo.gestaoFinanceiraServer.FluxoFinanceiro;

import br.edu.poo.gestaoFinanceiraServer.Meta.MetaDao;
import br.edu.poo.gestaoFinanceiraServer.Pessoa.Pessoa;
import br.edu.poo.gestaoFinanceiraServer.Retorno.Retorno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class FluxoController {
    @Autowired FluxoDao dao;

    @PutMapping("/pessoa/fluxo/{idPessoa}")
    public Retorno putFluxo (@PathVariable int idPessoa, @RequestBody Pessoa p) {
        try {
            return new Retorno(dao.inserirFluxo(p, idPessoa));
        } catch(Exception ex){
            return new Retorno(ex.getMessage());
        }
    }

    @DeleteMapping("/deletar/fluxo/{idFluxo}/pessoa/{idPessoa}")
    public Boolean deletarFluxo(@PathVariable int idPessoa, @PathVariable int idFluxo) throws Exception{
        return dao.deletarFluxo(idPessoa, idFluxo);
    }

}
