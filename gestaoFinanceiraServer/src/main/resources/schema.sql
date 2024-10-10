CREATE TABLE Pessoa (
    idPessoa INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    senha VARCHAR(100) NOT NULL,
    balancoGeral DOUBLE NULL,
    todasFaturas DOUBLE NULL
);

CREATE TABLE Meta (
    idMeta INT AUTO_INCREMENT PRIMARY KEY,
    codPessoa INT NOT NULL,
    tituloMeta VARCHAR(100) NOT NULL,
    descricaoMeta VARCHAR(100) NOT NULL,
    valorMeta DOUBLE NOT NULL,
    dataPrazo VARCHAR(100) NOT NULL,
    FOREIGN KEY (codPessoa) REFERENCES Pessoa(idPessoa) ON DELETE CASCADE
);

CREATE TABLE FluxoFinanceiro (
    idFluxo INT AUTO_INCREMENT PRIMARY KEY,
    codPessoa INT NOT NULL,
    tipo VARCHAR(100) NOT NULL,
    descricao VARCHAR(100) NOT NULL,
    nomeBanco VARCHAR(100) NOT NULL,
    valor DOUBLE NOT NULL,
    data VARCHAR(100) NOT NULL,
    FOREIGN KEY (codPessoa) REFERENCES Pessoa(idPessoa) ON DELETE CASCADE
);