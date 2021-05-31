const usertest = '{"id": 1,"tipo": 1, "nome": "Igor","sobrenome": "de Castro","foto": "null","cep": "51346587","anonimato": "Sim","email": "Sincere@april.biz","celular": "31 992221287","sobre": "Usuário de testes","valor": "gratuito","senha": "teste123","status": 2,"recado": "Lorem ipsum dolor sit amet consectetur adipisicing elit. Ipsa nam, nulla animi consectetur?"}';

// objeto do banco de dados de users em json
var user

getUser();

// Inicializa o usuarioCorrente e banco de dados de usuários da aplicação de Login

function getUser() {
    
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
    $("#sidebar_message").html("\""+user.sobre+"\"");
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

$("#sidebar_statusbar").click(
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