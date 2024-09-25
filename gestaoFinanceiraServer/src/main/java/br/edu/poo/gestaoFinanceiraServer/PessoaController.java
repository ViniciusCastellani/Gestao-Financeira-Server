package br.edu.poo.gestaoFinanceiraServer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

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

    @PutMapping("/pessoa/meta/{idPessoa}")
    public Retorno putMeta (@PathVariable int idPessoa, @RequestBody Pessoa p) {
        try {
            return new Retorno(dao.inserirMeta(p, idPessoa));
        } catch(Exception ex){
            return new Retorno(ex.getMessage());
        }
    }

    @PutMapping("/pessoa/fluxo/{idPessoa}")
    public Retorno putFluxo (@PathVariable int idPessoa, @RequestBody Pessoa p) {
        try {
            return new Retorno(dao.inserirFluxo(p, idPessoa));
        } catch(Exception ex){
            return new Retorno(ex.getMessage());
        }
    }

    @DeleteMapping("/pessoa/deletar/{idPessoa}")
    public Boolean deletarPessoa(@PathVariable int idPessoa) throws Exception{
        return dao.deletarPessoa(idPessoa);
    }

    @DeleteMapping("/deletar/pessoa/{idPessoa}/meta/{idMeta}")
    public Boolean deletarMeta(@PathVariable int idPessoa, @PathVariable int idMeta ) throws Exception{
        return dao.deletarMeta(idPessoa, idMeta);
    }

    @DeleteMapping("/deletar/pessoa/{idPessoa}/fluxo/{idFluxo}")
    public Boolean deletarFluxo(@PathVariable int idPessoa, @PathVariable int idFluxo) throws Exception{
        return dao.deletarFluxo(idPessoa, idFluxo);
    }
}