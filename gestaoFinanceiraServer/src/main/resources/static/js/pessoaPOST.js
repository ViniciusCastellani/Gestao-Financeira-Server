// Objeto global para armazenar os dados da pessoa
let pessoa = {
    nome: '',
    email: '',
    senha: '',
    balancoGeral: 0,
    todasFaturas: 0,
    listaMetas: [],
    listaFluxoFinanceiro: []
};

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
    alert('Fluxo financeiro adicionado com sucesso!');
}


function incluirUsuario() {
    // Atribuir os valores dos campos ao objeto pessoa
    pessoa.nome = document.getElementById('nome').value;
    pessoa.email = document.getElementById('email').value;
    pessoa.senha = document.getElementById('senha').value;
    pessoa.balancoGeral = parseFloat(document.getElementById('balancoGeral').value) || 0;
    pessoa.todasFaturas = parseFloat(document.getElementById('todasFaturas').value) || 0;

    // Ajustando a estrutura do JSON
    const pessoaParaEnviar = {
        idPessoa: null, // Isso pode ser gerado pelo banco
        nome: pessoa.nome,
        email: pessoa.email,
        senha: pessoa.senha,
        balancoGeral: pessoa.balancoGeral,
        todasFaturas: pessoa.todasFaturas,
        listaFluxoFinanceiro: pessoa.listaFluxoFinanceiro.map(fluxo => ({
            idFluxo: null, // Isso deve ser gerado pelo banco
            tipo: fluxo.tipo,
            descricao: fluxo.descricao,
            nomeBanco: fluxo.nomeBanco,
            valor: fluxo.valor,
            data: fluxo.data
        })),
        listaMetas: pessoa.listaMetas.map(meta => ({
            idMeta: null, // Isso deve ser gerado pelo banco
            tituloMeta: meta.tituloMeta,
            descricaoMeta: meta.descricaoMeta,
            valorMeta: meta.valorMeta,
            dataPrazo: meta.dataPrazo // Ajuste para 'dataPrazo'
        }))
    };

    console.log('Dados antes de enviar:', pessoaParaEnviar);

    let headers = {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
    };

    fetch("http://localhost:8081/pessoa", {
        headers: headers,
        method: "POST",
        body: JSON.stringify(pessoaParaEnviar)
    })
    .then(res => res.json())
    .then(res => {
        alert("Pessoa incluída com sucesso!");

        // Limpar os dados do objeto pessoa apenas após a inclusão
        pessoa.nome = '';
        pessoa.email = '';
        pessoa.senha = '';
        pessoa.balancoGeral = 0;
        pessoa.todasFaturas = 0;
        pessoa.listaMetas = [];
        pessoa.listaFluxoFinanceiro = [];
    })
    .catch(err => alert("Erro ao inserir no servidor: " + err.message));
}