// armazenar id pessoa
let idUser = null;

// armazenar resposta
let usuarioRESP = null;

// Objeto global para armazenar os dados da pessoa
let pessoa = {
    idPessoa: 0,
    nome: '',
    email: '',
    senha: '',
    balancoGeral: 0,
    todasFaturas: 0,
    listaMetas: [],
    listaFluxoFinanceiro: []
};


function buscarUsuario() {
    var url = new URL(window.location.href);
    var id = url.searchParams.get("id");
    if (id == null) {
        alert("ID não encontrado.");
        return;
    }

    idUser = id;

    fetch("http://localhost:8081/pessoa/obter/" + id, {
        method: "GET",
    })
        .then(res => res.json())
        .then(res => carregarUsuario(res))
        .catch(err => alert(err.message));
}


function carregarUsuario(res){
   usuarioRESP = res;

   pessoa.nome = usuarioRESP.nome;
   pessoa.email = usuarioRESP.email;
   pessoa.senha = usuarioRESP.senha;
   pessoa.balancoGeral = usuarioRESP.balancoGeral;
   pessoa.todasFaturas = usuarioRESP.todasFaturas;
   pessoa.listaMetas = usuarioRESP.listaMetas;
   pessoa.listaFluxoFinanceiro = usuarioRESP.listaFluxoFinanceiro;
}


function adicionarMeta() {
    if (
        document.getElementById('tituloMeta').value === '' ||
        document.getElementById('descricaoMeta').value === '' ||
        document.getElementById('valorMeta').value === ''
    ) {
        alert('Por favor, preencha todos os campos da meta.');
        return;
    }

    let novaMeta = {
        idMeta: pessoa.listaMetas[pessoa.listaMetas.length - 1].idMeta + 1,
        tituloMeta: document.getElementById('tituloMeta').value,
        descricaoMeta: document.getElementById('descricaoMeta').value,
        valorMeta: parseFloat(document.getElementById('valorMeta').value),
        dataPrazo: document.getElementById('dataPrazo').value
    };

    pessoa.listaMetas.push(novaMeta);

    // Limpar campos
    document.getElementById('tituloMeta').value = '';
    document.getElementById('descricaoMeta').value = '';
    document.getElementById('valorMeta').value = '';
    document.getElementById('dataPrazo').value = '';

    console.log('Meta adicionada:', novaMeta);
    console.log('Lista de Metas:', pessoa.listaMetas);
    alert('Meta adicionada com sucesso!');

    incluirMeta();
}


function adicionarFluxoFinanceiro() {
    if (
        document.getElementById('tipoFluxo').value === '' ||
        document.getElementById('descricaoFluxo').value === '' ||
        document.getElementById('nomeBancoFluxo').value === '' ||
        document.getElementById('valorFluxo').value === ''
    ) {
        alert('Por favor, preencha todos os campos do fluxo financeiro.');
        return;
    }

    let novoFluxo = {
        idFluxo: pessoa.listaFluxoFinanceiro[pessoa.listaFluxoFinanceiro.length - 1].idFluxo + 1,
        tipo: document.getElementById('tipoFluxo').value,
        descricao: document.getElementById('descricaoFluxo').value,
        nomeBanco: document.getElementById('nomeBancoFluxo').value,
        valor: parseFloat(document.getElementById('valorFluxo').value),
        data: document.getElementById('dataFluxo').value
    };

    pessoa.listaFluxoFinanceiro.push(novoFluxo);

    // Limpar campos
    document.getElementById('tipoFluxo').value = '';
    document.getElementById('descricaoFluxo').value = '';
    document.getElementById('nomeBancoFluxo').value = '';
    document.getElementById('valorFluxo').value = '';
    document.getElementById('dataFluxo').value = '';

    console.log('Fluxo financeiro adicionado:', novoFluxo);
    console.log('Lista de Fluxo Financeiro:', pessoa.listaFluxoFinanceiro);

    incluirFluxo();
}


function incluirMeta() {
    // estrutura json para ser enviado
    let pessoaParaEnviar = {
            idPessoa: pessoa.idPessoa,
            nome: pessoa.nome,
            email: pessoa.email,
            senha: pessoa.senha,
            balancoGeral: pessoa.balancoGeral,
            todasFaturas: pessoa.todasFaturas,
            listaFluxoFinanceiro: pessoa.listaFluxoFinanceiro.map(fluxo => ({
                idFluxo: fluxo.idFluxo,
                tipo: fluxo.tipo,
                descricao: fluxo.descricao,
                nomeBanco: fluxo.nomeBanco,
                valor: fluxo.valor,
                data: fluxo.data
            })),
            listaMetas: pessoa.listaMetas.map(meta => ({
                idMeta: meta.idMeta,
                tituloMeta: meta.tituloMeta,
                descricaoMeta: meta.descricaoMeta,
                valorMeta: meta.valorMeta,
                dataPrazo: meta.dataPrazo
            }))
        };

    console.log('Dados antes de enviar:', pessoaParaEnviar);

    let headers = {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
    };

    fetch("http://localhost:8081/pessoa/meta/"+ idUser, {
        headers: headers,
        method: "PUT",
        body: JSON.stringify(pessoaParaEnviar)
    })
    .then(res => res.json())
    .then(res => alert("Meta incluida com sucesso!"))
    .catch(err => alert("Erro ao inserir no servidor: " + err.message));
}


function incluirFluxo() {
    // estrutura json para ser enviado
    let pessoaParaEnviar = {
            idPessoa: pessoa.idPessoa,
            nome: pessoa.nome,
            email: pessoa.email,
            senha: pessoa.senha,
            balancoGeral: pessoa.balancoGeral,
            todasFaturas: pessoa.todasFaturas,
            listaFluxoFinanceiro: pessoa.listaFluxoFinanceiro.map(fluxo => ({
                idFluxo: fluxo.idFluxo,
                tipo: fluxo.tipo,
                descricao: fluxo.descricao,
                nomeBanco: fluxo.nomeBanco,
                valor: fluxo.valor,
                data: fluxo.data
            })),
            listaMetas: pessoa.listaMetas.map(meta => ({
                idMeta: meta.idMeta,
                tituloMeta: meta.tituloMeta,
                descricaoMeta: meta.descricaoMeta,
                valorMeta: meta.valorMeta,
                dataPrazo: meta.dataPrazo
            }))
        };

    console.log('Dados antes de enviar:', pessoaParaEnviar);

    let headers = {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
    };

    fetch("http://localhost:8081/pessoa/fluxo/"+ idUser, {
        headers: headers,
        method: "PUT",
        body: JSON.stringify(pessoaParaEnviar)
    })
    .then(res => res.json())
    .then(res => alert("Fluxos incluidos com sucesso!"))
    .catch(err => alert("Erro ao inserir no servidor: " + err.message));
}


function redirigirParaVisualizarUsuario() {
    if (idUser) {
        window.location.href = `PessoaVisualizar.html?id=${idUser}`; // Redireciona para a página de visualização
    } else {
        alert("ID do usuário não encontrado.");
    }
}