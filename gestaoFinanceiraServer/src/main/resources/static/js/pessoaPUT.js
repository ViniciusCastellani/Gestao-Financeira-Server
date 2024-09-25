// armazenar id pessoa
let idUsuario = null;

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


function buscarPessoaAlterar(){
    var url = new URL(window.location.href);
        var id = url.searchParams.get("id");
        if (id == null) {
            alert("ID não encontrado.");
            return;
        }

        idUsuario = id;

        fetch("http://localhost:8081/pessoa/obter/" + id, {
            method: "GET",
        })
            .then(res => res.json())
            .then(res => atualizarInformacoesTela(res))
            .catch(err => alert(err.message));
}


function atualizarInformacoesTela(res){
    usuarioRESP = res;

    atualizarInfoUserTela();
    atualizarMetasUserTela();
    atualizarFluxoUserTela();
}


function atualizarInfoUserTela(){
    document.getElementById('idPessoa').value = usuarioRESP.idPessoa;
    document.getElementById('nome').value = usuarioRESP.nome;
    document.getElementById('email').value = usuarioRESP.email;
    document.getElementById('senha').value = usuarioRESP.senha;
    document.getElementById('balancoGeral').value = usuarioRESP.balancoGeral;
    document.getElementById('todasFaturas').value = usuarioRESP.todasFaturas;
}


function atualizarMetasUserTela() {
    var htmlMetas = '';

    let metasUser = usuarioRESP.listaMetas;

    if (!metasUser || !metasUser.length) {
        return;
    }

    for (let i = 0; i < metasUser.length; i++) {
        htmlMetas += "<p>Meta: <strong>" + metasUser[i].tituloMeta + "</strong></p>";
        htmlMetas += 'id:<input id="idMeta' + i + '" type="text" readonly value="' + metasUser[i].idMeta + '"><br>'; // Use idMeta value
        htmlMetas += 'Titulo:<input id="tituloMeta' + i + '" type="text" value="' + metasUser[i].tituloMeta + '"><br>'; // Set value
        htmlMetas += 'Descricao:<input id="descricaoMeta' + i + '" type="text" value="' + metasUser[i].descricaoMeta + '"><br>'; // Set value
        htmlMetas += 'Valor:<input id="valorMeta' + i + '" type="number" value="' + metasUser[i].valorMeta + '"><br>'; // Set value
        htmlMetas += 'Prazo da Meta:<input id="dataPrazo' + i + '" type="text" value="' + metasUser[i].dataPrazo + '"><br>'; // Set value
        htmlMetas += '<br><br>';
    }

    document.getElementById("divMeta").innerHTML = htmlMetas;
}


function atualizarFluxoUserTela() {
    var htmlFluxo = '';

    let fluxoFinanceiro = usuarioRESP.listaFluxoFinanceiro;

    // Check if fluxoFinanceiro has items
    if (!fluxoFinanceiro || !fluxoFinanceiro.length) {
        return; // Exit if there are no fluxos
    }

    for (let i = 0; i < fluxoFinanceiro.length; i++) {
        htmlFluxo += "<p>Fluxo: <strong>" + fluxoFinanceiro[i].descricao + "</strong></p>";
        htmlFluxo += 'id:<input id="idFluxo' + i + '" type="text" readonly value="' + fluxoFinanceiro[i].idFluxo + '"><br>'; // Use idFluxo value

        htmlFluxo += 'Tipo:<select id="tipoFluxo' + i + '"> <option value="Despesa" ' + (fluxoFinanceiro[i].tipo === "Despesa" ? 'selected' : '') + '>Despesa</option>' +
            '<option value="Receita" ' + (fluxoFinanceiro[i].tipo === "Receita" ? 'selected' : '') + '>Receita</option>' +
            '</select><br>';

        htmlFluxo += 'Descricao:<input id="descricaoFluxo' + i + '" type="text" value="' + fluxoFinanceiro[i].descricao + '"><br>'; // Set value
        htmlFluxo += 'Nome Banco:<input id="nomeBanco' + i + '" type="text" value="' + fluxoFinanceiro[i].nomeBanco + '"><br>'; // Set value
        htmlFluxo += 'Valor fluxo:<input id="valorFluxo' + i + '" type="number" value="' + fluxoFinanceiro[i].valor + '"><br>'; // Set value
        htmlFluxo += 'Data:<input id="dataFluxo' + i + '" type="text" value="' + fluxoFinanceiro[i].data + '"><br>'; // Set value
        htmlFluxo += '<br><br>';
    }

    document.getElementById("divFluxo").innerHTML = htmlFluxo;
}


function atualizarInputMetas() {
    pessoa.listaMetas = []; // Reinicia a lista

    const metasUser = usuarioRESP.listaMetas;

    metasUser.forEach((metaItem, index) => {
        const meta = {
            idMeta: document.getElementById(`idMeta${index}`).value,
            tituloMeta: document.getElementById(`tituloMeta${index}`).value,
            descricaoMeta: document.getElementById(`descricaoMeta${index}`).value,
            valorMeta: parseFloat(document.getElementById(`valorMeta${index}`).value) || 0,
            dataPrazo: document.getElementById(`dataPrazo${index}`).value
        };
        pessoa.listaMetas.push(meta);
    });
}

function atualizarInputFluxos() {
    pessoa.listaFluxoFinanceiro = []; // Reinicia a lista

    const fluxosUser = usuarioRESP.listaFluxoFinanceiro;

    fluxosUser.forEach((fluxoItem, index) => {
        const fluxo = {
            idFluxo: document.getElementById(`idFluxo${index}`).value,
            tipo: document.getElementById(`tipoFluxo${index}`).value,
            descricao: document.getElementById(`descricaoFluxo${index}`).value,
            nomeBanco: document.getElementById(`nomeBanco${index}`).value,
            valor: parseFloat(document.getElementById(`valorFluxo${index}`).value) || 0,
            data: document.getElementById(`dataFluxo${index}`).value
        };
        pessoa.listaFluxoFinanceiro.push(fluxo);
    });
}



function atualizarInputInfo(){
    pessoa.idPessoa = document.getElementById('idPessoa').value;
    pessoa.nome = document.getElementById('nome').value;
    pessoa.email = document.getElementById('email').value;
    pessoa.senha = document.getElementById('senha').value;
    pessoa.balancoGeral = parseFloat(document.getElementById('balancoGeral').value) || 0;
    pessoa.todasFaturas = parseFloat(document.getElementById('todasFaturas').value) || 0;

}


function alterarPessoa() {
    atualizarInputInfo();
    atualizarInputMetas();
    atualizarInputFluxos();

    // Ajustando a estrutura do JSON
    const pessoaParaEnviar = {
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
            dataPrazo: meta.dataPrazo // Ajuste para 'dataPrazo'
        }))
    };

    console.log('Dados antes de enviar:', pessoaParaEnviar);

	let headers = {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        };
        fetch("http://localhost:8081/pessoa/atualizarTodasInfo/" + idUsuario, {
            headers: headers,
            method: "PUT",
            body: JSON.stringify(pessoaParaEnviar)
        })
        .then(res => res.json())
        .then(res =>  alert("Pessoa alterada"))
        .catch(err => alert("Erro ao inserir no servidor" + err.message))
}