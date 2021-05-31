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
            "status": 0
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
            "status": 0
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
            "status": 0
        },
    ]
}

const usertest = '{"id": 1,"tipo": 1, "nome": "Igor","sobrenome": "de Castro","foto": "null","cep": "51346587","anonimato": "Sim","email": "Sincere@april.biz","celular": "31 992221287","sobre": "Usuário de testes","valor": "gratuito","senha": "teste123","status": 2,"recado": "Lorem ipsum dolor sit amet consectetur adipisicing elit. Ipsa nam, nulla animi consectetur?"}';

// objeto do banco de dados de users em json
var user
/* var db_userreg = {};
var db_userdata= {};
var drop_reference= []; */

initLoginApp();

// Inicializa o usuarioCorrente e banco de dados de usuários da aplicação de Login

function initLoginApp() {
    
    /* sessionStorage.removeItem('usuarioCorrente'); */
    let aux = sessionStorage.getItem('usuarioCorrente');
    
    if (aux)
    {
        user = JSON.parse(aux);
        /* console.log("get"); */
    }
    else
    {
        user = JSON.parse(usertest);
        sessionStorage.setItem('usuarioCorrente', usertest);
        /* console.log("set"); */
    }
};

function saveUser()
{
    sessionStorage.setItem('usuarioCorrente', JSON.stringify(user));
}

function setSidebar()
{
    setName();
    setStatus();
    toggleControlOnResize();
    //setContent();
}

function setName()
{
    $("#sidebar_username").html(user.nome+' '+user.sobrenome);
    resizeUsername();
    $("#sidebar_message").html("\""+user.recado+"\"");
}

function resizeUsername()
{
    let fontsize= 22;
    let aux= fontsize;

    if($(document).width()<= 400)
    {
        fontsize= 18;
    }

    while($("#sidebar_username").height()> fontsize)
    {
        aux-= 1;
        $("#sidebar_username").css("font-size", aux);
    }

    while($("#sidebar_username").height()< fontsize)
    {
        aux+= 1;
        $("#sidebar_username").css("font-size", aux);
    }
}

function setStatus()
{
    if(user.status== 0)
    {
        $("#sidebar_statusinfo").html(
            `<i id="sidebar_statusicon" class="fas fa-check-circle"></i>
            <p id="sidebar_statustext">Online</p>`
        );
        $("#sidebar_statusinfo>i").css("color","rgb(0, 200, 0)");
    }
    else if(user.status== 1)
    {
        $("#sidebar_statusinfo").html(
            `<i id="sidebar_statusicon" class="fas fa-minus-circle"></i>
            <p id="sidebar_statustext">Ocupado</p>`
        );
        $("#sidebar_statusinfo>i").css("color","rgb(230, 0, 0)");
    }
    else if(user.status== 2)
    {
        $("#sidebar_statusinfo").html(
            `<i id="sidebar_statusicon" class="fas fa-times-circle"></i>
            <p id="sidebar_statustext">Offline</p>`
        );
        $("#sidebar_statusinfo>i").css("color","rgb(80, 80, 80)");
    }
    else
    {
        throw new Exception("Impossível determinar status!");
    }
}

function toggleControlOnResize()
{
    if($(document).width()<767)
    {
        $("#sidebar_options").html(`<i class="fas fa-cog"></i>`);
        $("#sidebar_logout").html(`<i class="fas fa-sign-out-alt"></i>`);
    }
    else
    {
        $("#sidebar_options").html(`<p>Configurar Conta</p>`);
        $("#sidebar_logout").html(`<p>Sair</p>`);
    }
}


/*function setContent()
{
    if(user.tipo= 0)
    {
        $("#content-context").html(
            `<button id="searchpsy" class="menu-btn">
            <i class="fas fa-search"></i>
            <p>Procurar indicações</p>
        </button>

        <button id="mypsy" class="menu-btn">
            <i class="fas fa-hand-holding-medical"></i>
            <p>Meus Psicólogos</p>
        </button>`
        )
    }
    else if(user.tipo= 1)
    {
        $("#content-context").html(
            `<button id="s" class="menu-btn">
            <i class="fas fa-calendar-check"></i>
            <p>Verificar solicitações</p>
        </button>

        <button id="mypat" class="menu-btn">
            <i class="fas fa-users"></i>
            <p>Meus Pacientes</p>
        </button>`
        )
    }
    else
    {
        throw new Exception("Impossível determinar o usuário!");
    }
}*/

$("#sidebar_statusbar").on("click",
    function()
    {
        $("#sidebar_statusmenu").css("display","block");
        $("#sidebar_statusmenu").focus();
    }
)

$("#sidebar_statusmenu>ul li").each(
    function(i)
    {
        $(this).click(
            function()
            {
                user.status= i;
                console.log(i);
                setStatus();
                saveUser();
            }
        )
    }
)

$(window).resize(resizeUsername)

$(window).resize(toggleControlOnResize)

$(document).click(
    function(evt)
    {
        if(!(evt.target.id == "sidebar_userstatus" || evt.target.id == "sidebar_statusbar" ||
            evt.target.id == "sidebar_statusinfo" || evt.target.id == "sidebar_statusicon" ||
                evt.target.id == "sidebar_statustext" || evt.target.id == "sidebar_selectarrow"))
        $("#sidebar_statusmenu").css("display","none");
    }
)

$(document).ready(
    function()
    {
        if(user)
        setSidebar();
    }    
)