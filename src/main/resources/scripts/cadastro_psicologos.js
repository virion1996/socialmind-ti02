//
//
// Disciplina: Trabalho Interdisciplinar - Aplicações Web
// Professor: Rommel Vieira Carneiro (rommelcarneiro@gmail.com)
//
// Código LoginApp utilizado como exemplo para alunos de primeiro período 
// Obtem os dados doo
let login = document.getElementById('txt_login');
let nome = document.getElementById('txt_nome');
let sobrenome = document.getElementById('txt_sobrenome');
let email = document.getElementById('txt_email');
let senha = document.getElementById('txt_senha');
let senha2 = document.getElementById('txt_senha2');
let preco = document.getElementById('select_preco');
let crp = document.getElementById('txt_crp');
let genre = document.getElementById('select_genre');
let date = document.getElementById('birth_date');
let phone = document.getElementById('txt_phone');
let cep = document.getElementById('txt_cep');
let about = document.getElementById('txt_about');
let aboutjob = document.getElementById('txt_aboutjob');


function salvaLogin(event) {
    // Cancela a submissão do formulário para tratar sem fazer refresh da tela
    event.preventDefault();
    
    // Obtem os dados do formulário
    let loginValue = login.value.trim();
    let nomeValue = nome.value.trim();
    let sobrenomeValue = sobrenome.value.trim();
    let emailValue = email.value.trim();
    let senhaValue = senha.value.trim();
    let senha2Value = senha2.value.trim();
    let precoValue = preco.value.trim();
    let crpValue = crp.value.trim();
    let genreValue = genre.value.trim();
    let dateValue = date.value.trim();
    let phoneValue = phone.value.trim();
    let cepValue = cep.value.trim();
    let aboutjobValue = aboutjob.value.trim();
    let aboutValue = about.value.trim();

    if (nomeValue == '') {
        alert("Nome invalido.");
        return
    }

    if (sobrenomeValue == '') {
        alert("Sobrenome invalido.");
        return
    }

    if (crpValue == '') {
        alert("Gentileza digitar o seu número de registro ao CRP.");
        return
    }

    if (genreValue == '') {
        alert("Favor escolher um genero.");
        return
    }

    if (dateValue == '') {
        alert("Favor escolher uma data valida");
        return
    }

    if (phoneValue.length < 11) {
        alert("Celular invalido.");
        return
    }

    if (cepValue < 8) {
        alert("Cep invalido.");
        return
    }

    if (precoValue == '') {
        alert("Favor um escolher um valor de consulta.");
        return
    }

    if (loginValue == '') {
        alert("Login invalido.");
        return
    }

    if (!isEmail(emailValue)) {
        alert("Email invalido.");
        return
    }
    if (aboutValue == '') {
        alert("Gentileza preencher sobre você.");
        return
    }
    if (aboutValue.length < 30) {
        alert("Gentileza preencher mais sobre você.");
        return
    }
    if (aboutjobValue == '') {
        alert("Gentileza preencher sobre seu trabalho.");
        return
    }
    if (aboutjobValue.length < 30) {
        alert("Gentileza preencher mais sobre seu trabalho.");
        return
    }
    else if (!isEmail(emailValue)) {
        alert("Email escrito errado");
        return
    }

    if (senhaValue.length < 5) {
        alert("Senha invalida");
        return
    }

    if (senhaValue != senha2Value) {
        alert('As senhas informadas não conferem.');
        return
    }

    // Adiciona o usuário no banco de dados

    addUser(nomeValue, sobrenomeValue, loginValue, senhaValue, emailValue, precoValue, crpValue, genreValue, dateValue, phoneValue, cepValue, aboutjobValue, aboutValue);
    alert('Usuário salvo com sucesso.');
}

function isEmail(email) {
    return /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i.test(email)
}


// Associar salvamento ao botao
document.getElementById('btn_salvar').addEventListener('click', salvaLogin);

// Página inicial de Login
const LOGIN_URL = "cadastro_psicologos.html";

// Objeto para o banco de dados de usuários baseado em JSON
var db_psico = {};

// Objeto para o usuário corrente
var usuarioCorrente = {};

// função para gerar códigos randômicos a serem utilizados como código de usuário
// Fonte: https://stackoverflow.com/questions/105034/how-to-create-guid-uuid
function generateUUID() { // Public Domain/MIT
    var d = new Date().getTime();//Timestamp
    var d2 = (performance && performance.now && (performance.now() * 1000)) || 0;//Time in microseconds since page-load or 0 if unsupported
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r = Math.random() * 16;//random number between 0 and 16
        if (d > 0) {//Use timestamp until depleted
            r = (d + r) % 16 | 0;
            d = Math.floor(d / 16);
        } else {//Use microseconds since page-load if supported
            r = (d2 + r) % 16 | 0;
            d2 = Math.floor(d2 / 16);
        }
        return (c === 'x' ? r : (r & 0x3 | 0x8)).toString(16);
    });
}


// Dados de usuários para serem utilizados como carga inicial
const dadosIniciais = {
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
            "status": ""
        },
        {
            "id": 2,
            "nome": "Ervin",
            "sobrenome": "Howell",
            "cep": "13468572",
            "anonimato": "Não",
            "email": "Shanna@melissa.tv",
            "celular": "010-692-6593",
            "sobre": "anastasia.net",
            "sobre_job": "anastasia.net",
            "valor": "gratuito",
            "senha": "Ervin",
            "status": ""
        },
        {
            "id": 3,
            "nome": "Clementine",
            "sobrenome": "Bauch",
            "cep": "12345678",
            "anonimato": "Não",
            "email": "Nathan@yesenia.net",
            "celular": "1-463-123-4447",
            "sobre": "ramiro.info",
            "sobre_job": "ramiro.info",
            "valor": "gratuito",
            "senha": "Clementine",
            "status": ""
        },
        {
            "id": 4,
            "nome": "Patricia",
            "sobrenome": "Lebsack",
            "cep": "23451625",
            "anonimato": "Não",
            "email": "Julianne.OConner@kory.org",
            "celular": "493-170-9623 x156",
            "sobre": "kale.biz",
            "sobre_job": "kale.biz",
            "valor": "ate 50 reais",
            "senha": "Patricia",
            "status": ""
        },
        {
            "id": 5,
            "nome": "Chelsey",
            "sobrenome": "Dietrich",
            "cep": "20132054",
            "anonimato": "Sim",
            "email": "Lucio_Hettinger@annie.ca",
            "celular": "(254)954-1289",
            "sobre": "demarco.info",
            "sobre_job": "demarco.info",
            "valor": "ate 50 reais",
            "senha": "Chelsey",
            "status": ""
        },
        {
            "id": 6,
            "nome": "Dennis",
            "sobrenome": "Schulist",
            "cep": "21048750",
            "anonimato": "Sim",
            "email": "Karley_Dach@jasper.info",
            "celular": "1-477-935-8478",
            "sobre": "ola.org",
            "sobre_job": "ola.org",
            "valor": "ate 50 reais",
            "senha": "Dennis",
            "status": ""
        },
        {
            "id": 7,
            "nome": "Kurtis",
            "sobrenome": "Weissnat",
            "cep": "87952102",
            "anonimato": "Não",
            "email": "Telly.Hoeger@billy.biz",
            "celular": "210.067.6132",
            "sobre": "elvis.io",
            "sobre_job": "elvis.io",
            "valor": "ate 100 reais",
            "senha": "Kurtis",
            "status": ""
        },
        {
            "id": 8,
            "nome": "Nicholas",
            "sobrenome": "Runolfsdottir",
            "cep": "12302457",
            "anonimato": "Sim",
            "email": "Sherwood@rosamond.me",
            "celular": "586.493.6943",
            "sobre": "jacynthe.com",
            "sobre_job": "jacynthe.com",
            "valor": "ate 100 reais",
            "senha": "Nicholas",
            "status": ""
        },
        {
            "id": 9,
            "nome": "Glenna",
            "sobrenome": "Reichert",
            "cep": "52085674",
            "anonimato": "Sim",
            "email": "Chaim_McDermott@dana.io",
            "celular": "(775)976-6794",
            "sobre": "conrad.com",
            "sobre_job": "conrad.com",
            "valor": "mais de 100 reais",
            "senha": "Glenna",
            "status": ""
        },
        {
            "id": 10,
            "nome": "Clementina",
            "sobrenome": "DuBuque",
            "cep": "21652012",
            "anonimato": "Sim",
            "email": "Rey.Padberg@karina.biz",
            "celular": "024-648-3804",
            "sobre": "ambrose.net",
            "sobre_job": "ambrose.net",
            "valor": "mais de 100 reais",
            "senha": "Clementina",
            "status": ""
        }
    ]
};


// Inicializa o usuarioCorrente e banco de dados de usuários da aplicação de Login
function initLoginApp() {
    // PARTE 1 - INICIALIZA USUARIOCORRENTE A PARTIR DE DADOS NO LOCAL STORAGE, CASO EXISTA
    usuarioCorrenteJSON = sessionStorage.getItem('usuarioCorrente');
    if (usuarioCorrenteJSON) {
        usuarioCorrente = JSON.parse(usuarioCorrenteJSON);
    }

    // PARTE 2 - INICIALIZA BANCO DE DADOS DE USUÁRIOS
    // Obtem a string JSON com os dados de usuários a partir do localStorage
    var usuariosJSON = localStorage.getItem('db_psico');

    // Verifica se existem dados já armazenados no localStorage
    if (!usuariosJSON) {  // Se NÃO há dados no localStorage

        // Informa sobre localStorage vazio e e que serão carregados os dados iniciais
        alert('Dados de usuários não encontrados no localStorage. \n -----> Fazendo carga inicial.');

        // Copia os dados iniciais para o banco de dados 
        db_psico = dadosIniciais;

        // Salva os dados iniciais no local Storage convertendo-os para string antes
        localStorage.setItem('db_psico', JSON.stringify(dadosIniciais));
    }
    else {  // Se há dados no localStorage

        // Converte a string JSON em objeto colocando no banco de dados baseado em JSON
        db_psico = JSON.parse(usuariosJSON);
    }
};

function addUser(nome, sobrenome, login, senha, email, preco, crp, genre, date, phone, cep, about, aboutjob) {

    // Cria um objeto de usuario para o novo usuario 
    let newId = generateUUID();
    let usuario = {
        "id": newId,
        "login": login,
        "senha": senha,
        "nome": nome,
        "sobrenome": sobrenome,
        "email": email,
        "valor": preco,
        "crp": crp,
        "genero": genre,
        "data": date,
        "celular": phone,
        "cep": cep,
        "sobre": about,
        "status": "",
        "sobre_job": aboutjob,
        "calendario": [ ],
        "anotacao": [ ]
    };

    // Inclui o novo usuario no banco de dados baseado em JSON
    db_psico.data.push(usuario);

    // Salva o novo banco de dados com o novo usuário no localStorage
    localStorage.setItem('db_psico', JSON.stringify(db_psico));
    window.location.href = '../template/index.html';
}

// Inicializa as estruturas utilizadas pelo LoginApp
initLoginApp();

