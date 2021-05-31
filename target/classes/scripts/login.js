var db_user = {};

var db_psico = {};

var usuarioCorrente = {};

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
    ]
}

function initLoginApp () {
    // PARTE 1 - INICIALIZA USUARIOCORRENTE A PARTIR DE DADOS NO LOCAL STORAGE, CASO EXISTA
    usuarioCorrenteJSON = sessionStorage.getItem('usuarioCorrente');
    if (usuarioCorrenteJSON) {
        usuarioCorrente = JSON.parse (usuarioCorrenteJSON);
    }
    
    // PARTE 2 - INICIALIZA BANCO DE DADOS DE USUÁRIOS
    // Obtem a string JSON com os dados de usuários a partir do localStorage
    var usuariosJSON = localStorage.getItem('db_user');
    let usuario_psico_json = localStorage.getItem('db_psico');

    // Verifica se existem dados já armazenados no localStorage
    if (!usuariosJSON) {  // Se NÃO há dados no localStorage
        
        // Informa sobre localStorage vazio e e que serão carregados os dados iniciais
        alert('Dados de usuários não encontrados no localStorage. \n -----> Fazendo carga inicial.');

        // Copia os dados iniciais para o banco de dados 
        db_user = dadosIniciais;

        // Salva os dados iniciais no local Storage convertendo-os para string antes
        localStorage.setItem('db_user', JSON.stringify (dadosIniciais));
    }
    else  {  // Se há dados no localStorage
        
        // Converte a string JSON em objeto colocando no banco de dados baseado em JSON
        db_user = JSON.parse(usuariosJSON);    
    }

    if (!usuario_psico_json) {  // Se NÃO há dados no localStorage
        
        // Informa sobre localStorage vazio e e que serão carregados os dados iniciais
        alert('Dados de usuários não encontrados no localStorage. \n -----> Fazendo carga inicial.');

        // Copia os dados iniciais para o banco de dados 
        db_psico = dadosIniciais;

        // Salva os dados iniciais no local Storage convertendo-os para string antes
        localStorage.setItem('db_psico', JSON.stringify (dadosIniciais));
    }
    else  {  // Se há dados no localStorage
        
        // Converte a string JSON em objeto colocando no banco de dados baseado em JSON
        db_psico = JSON.parse(usuario_psico_json);    
    }
};

function loginUser (email, senha) {
    
    // Verifica todos os itens do banco de dados de usuarios 
    // para localizar o usuário informado no formulario de login
    for (let i = 0; i < db_user.data.length; i++) {
        let usuario = db_user.data[i];
        
        // Se encontrou login, carrega usuário corrente e salva no Session Storage
        if (email == usuario.email && senha == usuario.senha) {
            usuarioCorrente.id = usuario.id;
            usuarioCorrente.nome = usuario.nome;
            usuarioCorrente.sobrenome = usuario.sobrenome;
            usuarioCorrente.cep = usuario.cep;
            usuarioCorrente.anonimato = usuario.anonimato;
            usuarioCorrente.email = usuario.email;
            usuarioCorrente.celular = usuario.celular;
            usuarioCorrente.sobre = usuario.sobre;
            usuarioCorrente.valor = usuario.valor;
            usuarioCorrente.senha = usuario.senha;
            usuarioCorrente.status = usuario.status;
            
            // Salva os dados do usuário corrente no Session Storage, mas antes converte para string
            sessionStorage.setItem ('usuarioCorrente', JSON.stringify (usuarioCorrente));

            // Retorna true para usuário encontrado
            return true;
        }
    }

    // Se chegou até aqui é por que não encontrou o usuário e retorna falso
    return false;
}

function loginPsico (email, senha) {
    
    // Verifica todos os itens do banco de dados de usuarios 
    // para localizar o usuário informado no formulario de login

    for (let i = 0; i < db_psico.data.length; i++) {
        let usuario = db_psico.data[i];
        
        // Se encontrou login, carrega usuário corrente e salva no Session Storage
        if (email == usuario.email && senha == usuario.senha) {

            usuarioCorrente.id = usuario.id;
            usuarioCorrente.nome = usuario.nome;
            usuarioCorrente.sobrenome = usuario.sobrenome;
            usuarioCorrente.cep = usuario.cep;
            usuarioCorrente.email = usuario.email;
            usuarioCorrente.celular = usuario.celular;
            usuarioCorrente.sobre = usuario.sobre;
            usuarioCorrente.sobre_job = usuario.sobre_job;
            usuarioCorrente.valor = usuario.valor;
            usuarioCorrente.senha = usuario.senha;
            usuarioCorrente.status = usuario.status;
            usuarioCorrente.calendario = usuario.calendario;
            usuarioCorrente.anotacao = usuario.anotacao;
            // Salva os dados do usuário corrente no Session Storage, mas antes converte para string
            sessionStorage.setItem ('usuarioCorrente', JSON.stringify (usuarioCorrente));
            console.log(usuarioCorrente);
            console.log(usuarioCorrente.anotacao);
            console.log(usuario.anotacao);

            // Retorna true para usuário encontrado
            return true;
        }
    }

    // Se chegou até aqui é por que não encontrou o usuário e retorna falso
    return false;
}


initLoginApp ();