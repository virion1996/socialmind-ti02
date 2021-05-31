/* function leDados() {
    let strDados = localStorage.getItem('db');
    let objDados = {};

    if (strDados) {
        objDados = JSON.parse(strDados);
    }
    else {
        objDados = {
            contatos: [
                {
                    "id": 1,
                    "nome": "Leanne",
                    "data": "21/11/2019",
                    "trabalho": "desempregado",
                    "motivos": "Por conta de problemas familiares",
                    "observacoes": "Paciente inquieto",
                },
                {
                    "id": 2,
                    "nome": "Ervin",
                    "data": "18/11/2019",
                    "trabalho": "desempregado",
                    "motivos": "Por conta de problemas familiares",
                    "observacoes": "Paciente inquieto",
                },
                {
                    "id": 3,
                    "nome": "Clementine",
                    "data": "08/11/2019",
                    "trabalho": "desempregado",
                    "motivos": "Por conta de problemas familiares",
                    "observacoes": "Paciente inquieto",
                },
                {
                    "id": 4,
                    "nome": "Patricia",
                    "data": "22/11/2019",
                    "trabalho": "desempregado",
                    "motivos": "Por conta de problemas familiares",
                    "observacoes": "Paciente inquieto",
                },
            ]
        }
    }

    return objDados;
} */
var db_psico = {};

var usuarioCorrente = {};

var db_json = localStorage.getItem('db_psico');
var db_json_ss = sessionStorage.getItem('usuarioCorrente');
if (db_json_ss) {
    usuarioCorrente = JSON.parse(db_json_ss);
}

if (!db_json) {
    db_json = db_contatos_inicial;
    alert("Adicionado ao local Storage vários usuários.");
    localStorage.setItem('db_psico', JSON.stringify(dadosIniciais));
}
else {
    db_psico = JSON.parse(db_json);
}

console.log(db_psico);

//pega o valor do ID do usuario selecionado
var user_id = usuarioCorrente.id;

//acha o index no banco de dados cujo ID é o do usuario selecionado
let user_dados_index = db_psico.data.findIndex(x => x.id == user_id);

/* const dadosIniciais = {
    "data": [
        {
            "id": 1,
            "nome": "Leanne",
            "sobrenome": "Graham",
            "cep": "51346587",
            "anonimato": "Sim",
            "email": "Sincere@april.biz",
            "celular": "1-770-736-8031",
            "sobre": "hildegard.org",
            "sobre_job": "hildegard.org",
            "valor": "gratuito",
            "senha": "Leanne",
            "status": "",
            "anotacoes": []
        }
    ]
} */


function salvaDados() {
    localStorage.setItem('db_psico', JSON.stringify(db_psico));
    sessionStorage.setItem('usuarioCorrente', JSON.stringify(usuarioCorrente));
}

function incluirContato() {
    // Ler os dados do localStorage

    // Incluir um novo contato
    let strNome = document.getElementById('campoNome').value;
    let strData = document.getElementById('campoData').value;
    let strTrabalho = document.getElementById('campoTrabalho').value;
    let strMotivos = document.getElementById('campoMotivos').value;
    let strObservacoes = document.getElementById('campoObservacoes').value;
    let novoContato = {
        nome: strNome,
        data: strData,
        trabalho: strTrabalho,
        motivos: strMotivos,
        observacoes: strObservacoes
    };
    usuarioCorrente.anotacao.push(novoContato);
    db_psico.data[user_dados_index].anotacao.push(novoContato);

    // Salvar os dados no localStorage novamente
    salvaDados();

    // Atualiza os dados da tela
    imprimeDados();
}

function imprimeDados() {
    let tela = document.getElementById('tela');
    let strHtml = '';
    let objDados = usuarioCorrente.anotacao;

    for (i = 0; i < objDados.length; i++) {
        strHtml += `<p> Nome: ${objDados[i].nome}
         <br> Data da consulta: ${objDados[i].data} 
         <br> Trabalho: ${objDados[i].trabalho} 
         <br> Motivos: ${objDados[i].motivos} 
         <br> Observação ${objDados[i].observacoes}</p>`
    }


    tela.innerHTML = strHtml;
}

// Configura os botões
document.getElementById('btnCarregaDados').addEventListener('click', imprimeDados);
document.getElementById('btnIncluirContato').addEventListener('click', incluirContato);

