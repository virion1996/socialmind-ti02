var db_user={};
var usuarioCorrente = {};
var ids={};

var db_solic=
{
    "data" : []
};
var conex=
{
    "data":[]
};

var idUnico=
{
    "data":[]
};
//Carregar local storage
usuarioCorrente = JSON.parse(sessionStorage.getItem('usuarioCorrente'));
db_user = JSON.parse(localStorage.getItem('db_user'));
ids = JSON.parse(localStorage.getItem('db_solic'));

function Usuario()
{
    if(JSON.parse(sessionStorage.getItem('usuarioCorrente'))!=null)
    {
        return usuarioCorrente.nome;
    }
    return "Você pulou algumas etapas, tipo fazer o login"
}

function gerarSol(psico)
{
    var solicit = 
    {
        "solicitante" : usuarioCorrente.id,
        "solicitado" : psico
    };

    db_solic.data.push(solicit);
    localStorage.setItem('db_solic',JSON.stringify(db_solic));
}

function procID(ind)
{   
    let idTemp;
    for(let x=0; x<db_user.data.length; x++)
    {
        if(db_user.data[x].id == ind)
        {
            idTemp=x;
        }
    }

    return idTemp;
}

function aceitarCox(indice)
{
    var aceite = 
    {
        "psicologo" : usuarioCorrente.id,
        "paciente" : ids.data[indice].solicitante
    };

    conex.data.push(aceite);
    localStorage.setItem('conex',JSON.stringify(conex));
    localStorage.removeItem('db_solic');

}

function userSolic()
{
    if(JSON.parse(localStorage.getItem('db_solic')!=null))
    {
     return db_user.data[procID(ids.data[0].solicitante)].nome;
    }
    return "Sem Solicitações no momento.";
}

function userSobreSolic()
{
    if(JSON.parse(localStorage.getItem('db_solic')!=null))
    {
        return db_user.data[procID(ids.data[0].solicitante)].sobre;
    }
    return " ";
}

function conexoes()
{ 
    let conect;
    if(JSON.parse(localStorage.getItem('conex')) != null)
    {
        conect = JSON.parse(localStorage.getItem('conex')) ; 
        document.getElementById('conx1').innerHTML = `<a href="Chat.html">${db_user.data[procID(conect.data[0].paciente)].nome}</a>`;
        //document.getElementById('conx1').innerText = db_user.data[procID(conect.data[0].paciente)].nome;;
    }

    if(JSON.parse(localStorage.getItem('db_solic')) == null)
    document.getElementById('bot1').innerHTML = ``;
    
}

function IgorTeAmoS2(botao)
{
    let conect;
    if(JSON.parse(localStorage.getItem('conex')) != null)
    {
        conect = JSON.parse(localStorage.getItem('conex')) ; 
            var id={
                      "paciente":conect.data[0].paciente
                     };
            if(idUnico.data!=null)
                     idUnico.data.pop();

            idUnico.data.push(id);
            localStorage.setItem('idUnico',JSON.stringify(idUnico));

    }
}

