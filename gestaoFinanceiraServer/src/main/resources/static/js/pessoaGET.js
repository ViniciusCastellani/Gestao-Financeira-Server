let idUser = null;


// buscar lista de usuários para página LISTAR
function buscarUsuarios() {
    fetch("http://localhost:8081/pessoa/listar", {
       method: "GET",
    })
        .then(res => res.json())
        .then(res => atualizarTabelaLista(res))
        .catch(err => alert(err.message))
}


// buscar usuario para página VISUALIZAR
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
        .then(res => atualizarTabela(res))
        .catch(err => alert(err.message));
}


function deletarUsuario(idPessoa) {
	fetch("http://localhost:8081/pessoa/deletar/" + idPessoa,{
        method: "DELETE",
	})
        .then(res => res.text())
        .then(res => window.location.replace("PessoaListar.html"))
        .catch(err => alert(err.message))
}


function deletarMeta(idPessoa, idMeta) {
    fetch("http://localhost:8081/deletar/meta/" + idMeta + "/pessoa/" + idPessoa, {
        method: "DELETE",
    })
    .then(res => res.text())
    .then(res => window.location.replace("PessoaListar.html"))
    .catch(err => alert(err.message))
}


function deletarFluxo(idPessoa, idFluxo) {
    fetch("http://localhost:8081/deletar/fluxo/" + idFluxo + "/pessoa/" + idPessoa, {
        method: "DELETE",
    })
    .then(res => res.text())
    .then(res => window.location.replace("PessoaListar.html"))
    .catch(err => alert(err.message))
}


// atualizar tabelas da página LISTAR
function atualizarTabelaLista(userList) {
    var todasAsTabelas = "";

    todasAsTabelas += "<br><h2>Informacoes dos Usuarios</h2><br>";
    todasAsTabelas += atualizarTabelaUsuario(userList);
    todasAsTabelas += "<br><hr><h2>Metas dos Usuarios</h2>";
    todasAsTabelas += atualizarTabelaMetas(userList);
    todasAsTabelas += "<br><hr><h2>Fluxo dos Usuarios</h2>";
    todasAsTabelas += atualizarTabelaFluxo(userList);

    document.getElementById("divPrincipal").innerHTML = todasAsTabelas;
}


// atualizar tabela sobre informações do usuário da página LISTAR + VISUALIZAR
function atualizarTabelaUsuario(userList) {
    let tabelaUsuarios = "";

    if (!userList){
        return;
    }

     tabelaUsuarios = "<table>";
     tabelaUsuarios += "<tr class='header'><th>ID</th><th>Nome</th><th>Email</th><th>Senha</th><th>Balanco geral</th><th>Todas faturas</th></tr>";


    for (var i = 0; i < userList.length; i++) {

        var user = userList[i];
        var linha = "<tr>" +
                    "<td>" + user.idPessoa + "</td>" +
                    "<td>" + user.nome + "</td>" +
                    "<td>" + user.email + "</td>" +
                    "<td>" + user.senha + "</td>" +
                    "<td>" + user.balancoGeral + "</td>" +
                    "<td>" + user.todasFaturas + "</td>" +
                    '<td><a href="PessoaVisualizar.html?id=' + user.idPessoa + '">Detalhes</a></td>' +
                    '<td><a href="PessoaAlterar.html?id=' + user.idPessoa + '">Alterar</a></td>' +
                    '<td><button onclick="deletarUsuario(' + user.idPessoa + ')">Excluir</button></td>' +
                    "</tr>";

        tabelaUsuarios += linha;
    }
    tabelaUsuarios += "</table>";
    return tabelaUsuarios;
}


// atualizar tabela sobre informações das metas dos usuários da página LISTAR + VISUALIZAR
function atualizarTabelaMetas(userList) {
    let tabelaMetas = "";

    if (userList && userList.length > 0) {
        for (let i = 0; i < userList.length; i++) {
            let metas = userList[i].listaMetas;

            if (metas && metas.length > 0) {
                tabelaMetas += "<p>Metas do usuario: <strong>" + userList[i].nome + "</strong></p>";
                tabelaMetas += "<table>";
                tabelaMetas += "<tr class='header'><th>ID</th><th>Titulo</th><th>Descricao</th><th>Valor</th><th>Data de Prazo</th></tr>";

                for (let j = 0; j < metas.length; j++) {
                    tabelaMetas += "<tr>" +
                                   "<td>" + metas[j].idMeta + "</td>" +
                                   "<td>" + metas[j].tituloMeta + "</td>" +
                                   "<td>" + metas[j].descricaoMeta + "</td>" +
                                   "<td>" + metas[j].valorMeta.toFixed(2) + "</td>" +
                                   "<td>" + metas[j].dataPrazo + "</td>" +
                                   '<td><button onclick="deletarMeta(' + userList[i].idPessoa + ', ' + metas[j].idMeta + ')">Excluir</button></td>' +
                                   "</tr>";
                }
                tabelaMetas += "</table>";
            }
        }
    }

    return tabelaMetas;
}


// atualizar tabela sobre informações dos fluxos dos usuários da página LISTAR + VISUALIZAR
function atualizarTabelaFluxo(userList) {
    let tabelaFluxo = "";

    if (userList && userList.length > 0) {
        for (let i = 0; i < userList.length; i++) {
            let fluxoFinanceiro = userList[i].listaFluxoFinanceiro;

            if (fluxoFinanceiro && fluxoFinanceiro.length > 0) {
                tabelaFluxo += "<p>Fluxo do usuario: <strong>" + userList[i].nome + "</strong></p>";
                tabelaFluxo += "<table>";
                tabelaFluxo += "<tr class='header'><th>ID</th><th>Tipo</th><th>Descricao</th><th>Nome do Banco</th><th>Valor</th><th>Data</th></tr>";

                for (let j = 0; j < fluxoFinanceiro.length; j++) {
                    tabelaFluxo += "<tr>" +
                                   "<td>" + fluxoFinanceiro[j].idFluxo + "</td>" +
                                   "<td>" + fluxoFinanceiro[j].tipo + "</td>" +
                                   "<td>" + fluxoFinanceiro[j].descricao + "</td>" +
                                   "<td>" + fluxoFinanceiro[j].nomeBanco + "</td>" +
                                   "<td>" + fluxoFinanceiro[j].valor.toFixed(2) + "</td>" +
                                   "<td>" + fluxoFinanceiro[j].data + "</td>" +
                                   '<td><button onclick="deletarFluxo(' + userList[i].idPessoa + ', ' + fluxoFinanceiro[j].idFluxo + ')">Excluir</button></td>' +
                                   "</tr>";
                }
                tabelaFluxo += "</table>";
            }
        }
    }
    return tabelaFluxo;
}


// atualizar tabelas da página VISUALIZAR
function atualizarTabela(user) {
    var todasAsTabelas = "";

    todasAsTabelas += "<br><h2>Informacao do Usuario</h2>";
    todasAsTabelas += atualizarTabelaUsuario([user]); // Passando o usuário em um array
    todasAsTabelas += "<br><hr><h2>Metas do Usuario</h2>";
    todasAsTabelas += atualizarTabelaMetas([user]); // Passando o usuário em um array
    todasAsTabelas += "<br><hr><h2>Fluxos do Usuario</h2>";
    todasAsTabelas += atualizarTabelaFluxo([user]); // Passando o usuário em um array

    document.getElementById("divPrincipal").innerHTML = todasAsTabelas;
}


function redirigirParaInclusaoFluxo() {
    if (idUser) {
        window.location.href = `PessoaIncluirFluxo.html?id=${idUser}`; // Redireciona para a página de inclusão
    } else {
        alert("ID do usuário não encontrado.");
    }
}


function redirigirParaInclusaoMeta() {
    if (idUser) {
        window.location.href = `PessoaIncluirMeta.html?id=${idUser}`; // Redireciona para a página de inclusão
    } else {
        alert("ID do usuário não encontrado.");
    }
}