var db_chatmsg_inicial=
{
    conversations:
    [
        new Cv("1","4",
            [
                new Msg("1", 1605877993008, "Hello world!"),
                new Msg("4", 1605878006445, "Hello!"),
                new Msg("4", 1605878006565, "Lorem ipsum dolor sit amet consectetur adipisicing elit. Temporibus dicta odio voluptates fugit consequatur animi accusantium sit, vel exercitationem aut?"),
                new Msg("1", 1605878006565, "Lorem ipsum dolor sit amet consectetur adipisicing elit. Temporibus dicta odio voluptates fugit consequatur animi accusantium sit, vel exercitationem aut?"),
                new Msg("1", 1605878006565, "Lorem ipsum dolor sit amet consectetur adipisicing elit. Temporibus dicta odio voluptates fugit consequatur animi accusantium sit, vel exercitationem aut?"),
                new Msg("4", 1605878006565, "Lorem ipsum dolor sit amet consectetur adipisicing elit. Temporibus dicta odio voluptates fugit consequatur animi accusantium sit, vel exercitationem aut?"),
            ]
        ),

        new Cv("5","2",
            [
                new Msg("5", 1605878054980, "Hi!"),
                new Msg("2", 1605878061513, "How are you?")
            ]
        ),
    ]
};

/*
//Conversation constructor
*/
function Cv(id1, id2, msgs)
{
    this.id1= id1;
    this.id2= id2;
    this.msgs= msgs;
}

/*
//Message constructor
*/
function Msg(id, ts, txt)
{
    this.id= id;
    this.timestamp= ts;
    this.text= txt;
}


// objeto do banco de dados
var db
var db_chatmsg= {conversations: new Array()};//Banco de dados das mensagens
var user;//usuário logado
var chating_user;//dados do usuário do chat
var cv;

function getUser()
{
    user= JSON.parse(sessionStorage.getItem('usuarioCorrente'));
}

function getChatingUser()
{
    let id= JSON.parse(localStorage.getItem('idUnico')).data[0].psicologo;
    let dbu= "db_user";
    let dbp= "db_psico";

    chating_user= searchUser(id, dbu);

    if(chating_user== null)
    {
        chating_user= searchUser(id, dbp);
    }

    if(chating_user== null)
    {
        throw new Error("Impossivel carregar o interlocutario");
    }
}

function searchUser(id, str)
{
    let db= JSON.parse(localStorage.getItem(str));
    let usr= null;

    $(db.data).each(
        function(i)
        {
            if(id == this.id)
            {
                usr= this;
                return;
            }
        }
    )
    return usr;
}

function getChatDB()
{
    let dbc= 'db_chatmsg';
    
    if(localStorage.getItem(dbc))
    {
        db_chatmsg= JSON.parse(localStorage.getItem(dbc));
    }
    else
    {
        localStorage.setItem(dbc, JSON.stringify(db_chatmsg));
    }
}

/*
// Metodo auxiliar que limpa o conteudo antigo existente na janela e
// em seguida constroi o conteúdo gráfico dos paineis de acordo com
// os dados salvos no banco de dados.
*/
function setChatWindow()
{
    clearMsgBox();

    if(user.id != chating_user.id)
    {
        setUserPanel();
        assignChatData();
    }
    else
    {
        setUserPanelError();
        setMsgBoxError(1);
    }
}

/*
//  Procura o usuário corrente ou o usuário do chat comparando o id da
//  referência com o id nos vetores-referência. Quando determinado o usuário
//  do chat, constroi o painel.
*/
function setDropsEvent()
{
    let p= document.querySelectorAll('#users_dropdown-menu1> a');
    p.forEach((item, index) =>//index do vetor1
    {
        item.onclick= ()=> 
        {
            current_user= getUser(index);            
            setChatWindow();
        }
    });

    let q= document.querySelectorAll('#users_dropdown-menu2> a');
    q.forEach((item, index) =>//index do vetor2
    {
        item.onclick= ()=> 
        {
            chating_user= getUser(index)
            setChatWindow();
        }
    });
}

function setUserPanel()
{
    document.getElementById('card-friendname').innerHTML= 
    `${chating_user.nome} ${chating_user.sobrenome}`;
    /* document.getElementById('card-photoframe').innerHTML= 
    `<img id="friend-photo" src="${chating_user.photo}" alt="Foto do amigo">`; */
}


function assignChatData()
{
    getCv();

    if(cv!= null)
    setMsgBox(cv);
    else
    setMsgBoxError(1);
}

function getCv()
{
    let id1= user.id;
    let id2= chating_user.id;
    cv= null;

    if(id1 != id2)
    {
        db_chatmsg.conversations.forEach((item) =>
        {
            if((id1 == item.id1 || id1==item.id2) && (id2 == item.id2 || id2== item.id1))
            {
                cv= item;
            }
        });
    

        if(cv== null)//Cria uma nova conversa
        {
            db_chatmsg.conversations.push(new Cv(user.id, chating_user.id,[]));
            cv= db_chatmsg.conversations[db_chatmsg.conversations.length-1];
        }
    }
}

function clearMsgBox()
{
    document.getElementById('message-box').innerHTML= '';
    document.getElementById('message-box').style.backgroundColor=  "#EFEFEF";
}

function setMsgBox()
{
    let id1= user.id;
    let id2= chating_user.id;

    clearMsgBox();

    cv.msgs.forEach((item) =>
    {
        if(item.id == id1)
        {
            document.getElementById('message-box').innerHTML+= 
            ` <div class="usrmsg-wrapper"><p class= "usrmsg">${item.text}</p><p class= time>${setDate(item.timestamp)}</p></div>`
        }
        else if(item.id == id2)
        {
           
            document.getElementById('message-box').innerHTML+= 
            ` <div class="frndmsg-wrapper"><p class= "frndmsg">${item.text}</p><p class= time>${setDate(item.timestamp)}</p></div>`
        }
        else
        {
            setMsgBoxError(2);
        }
    });
}

function setDate(timestamp)
{
    let hour= '';
    let minute= '';

    let date= new Date(timestamp);
    
    if(date.getHours()< 10)
    {
        hour= '0';
    }
    
    hour= hour + date.getHours();


    if(date.getMinutes()< 10)
    {
        minute= '0';
    }
    
    minute= minute+ date.getMinutes();
    

    return `${hour}:${minute}`
}

function setUserPanelError()
{
    document.getElementById('card-photoframe').innerHTML= '<i id="cardicon-photoframe" class="fas fa-heart-broken fa-3x"></i>';
    document.getElementById('card-friendname').innerHTML= 'Erro!';
}

function setMsgBoxError(i)
{
    document.getElementById('message-box').style.backgroundColor= "#FFBEBE";
    document.getElementById('message-box').innerHTML= '';

    if(i == 1)
    {
        let str1= `O usuário não pode ser o mesmo!`;
        document.getElementById('message-box').innerHTML+= str1;
        //throw new Error(str1);
    }
    else if(i == 2)
    {
        let str2= `Arquivo de conversas mal-formatado ou corrompido!`;
        document.getElementById('message-box').innerHTML+= str1;
        //throw new Error(str2);
    }
    else
    {
        let str3= `Erro desconhecido!`;
        document.getElementById('message-box').innerHTML+= str1;
        //throw new Error(str3);
    }
}

document.getElementById('btn-sendmsg').onclick= () =>
{
    let txt= writingbox.value;
    
    if(cv!= null)
    {
        /* console.log(cv); */
        cv.msgs.push(new Msg(user.id, Date.now(), txt));
        localStorage.setItem('db_chatmsg', JSON.stringify(db_chatmsg));
    }

    writingbox.value= '';

    setMsgBox();
}

getUser();
getChatingUser();
getChatDB();

window.onload= function(){
    setUserPanel();
    setChatWindow();
}
