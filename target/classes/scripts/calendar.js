
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
            "status": "",
            "calendar": []
        }
    ]
}

function InitApp() {

    /* let db_var = localStorage.getItem('db_psico');

    if (!db_var) {
        alert('Criando localstorage');

        db_cal = dadosIniciais;

        console.log(db_cal.data[0]);

        localStorage.setItem('db_cal', JSON.stringify(db_cal));
    }

    db_ls = localStorage.getItem('db_cal');
    db_ls = JSON.parse(db_ls)
    console.log(db_ls);

    console.log(db_ls)

    db_var = sessionStorage.getItem('db_cal');

    if (!db_var) {
        alert('criando session storage');

        sessionStorage.setItem('db_cal', JSON.stringify(db_ls));
    }

    console.log(db_ls.calendar) */

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

    console.log(user_dados_index);
    console.log(db_psico.data[user_dados_index].calendario);
    console.log(usuarioCorrente.calendario);

    function draw_calendar(data_cal) {
        var calendarEl = document.getElementById('calendar');
        var calendar = new FullCalendar.Calendar(calendarEl, {
            initialView: 'dayGridMonth',
            headerToolbar: {
                left: 'prev,next today',
                center: 'title',
                right: 'dayGridMonth,timeGridWeek,timeGridDay'
            },
            dateClick: function (info) {
                criarData(info.dateStr);
            },
            events: data_cal,
            selectable: true,
            eventClick: function (info) {
                if (confirm(`Desenha excluir a marcação ${info.event.title} ?`)) {
                    txt = "Marcação excluida com sucesso!";
                    info.event.remove();
                    let deletar = db_psico.data[user_dados_index].calendario.findIndex(x => x.title == info.event.title);
                    db_psico.data[user_dados_index].calendario[deletar];
                    db_psico.data[user_dados_index].calendario[deletar].start = "";
                    usuarioCorrente.calendario[deletar].start = "";
                    localStorage.setItem('db_psico', JSON.stringify(db_psico));
                    sessionStorage.setItem('usuarioCorrente', JSON.stringify(usuarioCorrente));
                }
            }


        });
        calendar.render();
    };

    draw_calendar(usuarioCorrente.calendario);

    function criarData(data_select) {
        $("#modal_date").replaceWith(`
        <div class="modal fade" id="modal_date" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
            aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel"></h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div id="login-box" class="col-md-12">
                            <form id="login-form" class="form" method="post">
                                <h3 class="text-center text-info">Adicionar Terefa</h3>
                                <div class="form-group">
                                    <label for="txt_nome" class="text-info">Nome da tarefa </label><br>
                                    <input type="text" name="txt_nome" id="nome_tarefa" class="form-control" required>
                                </div>
                                <div class="form-group">
                                    <label for="txt_sobrenome" class="text-info">Horario Inicial</label><br>
                                    <input type="time" name="txt_sobrenome" id="time_inicial_tarefa" class="form-control" required>
                                    <label for="txt_email" class="text-info">Horario Final</label><br>
                                    <input type="time" name="txt_email" id="time_final_tarefa" class="form-control">
                                </div>
    
                                <div class="form-group">
                                    <label for="birth_date" class="text-info">Data</label>
                                    <input type="date" id="birth_date" name="date_tarefa" class="form-control" value= "${data_select}">
                                </div>
                                <div class="form-group">
                                    <label for="txt_about" class="text-info">Descricao</label><br>
                                    <textarea id="desc_tarefa" name="user_message" rows="3" cols="5" class="form-control"
                                        placeholder=""></textarea>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary" id="btn_save" data-dismiss="modal">Save changes</button>
                    </div>
                </div>
            </div>
        </div>`);

        $("#modal_date").modal();

        $("#btn_save").click(() => {
            obj_salvar = {
                title: $("#nome_tarefa").val(),
                start: `${data_select}T${$("#time_inicial_tarefa").val()}`,
                end: `${data_select}T${$("#time_final_tarefa").val()}`,
                description: $("#desc_tarefa").val(),
                allDay: false
            }
            console.log(obj_salvar);

            /* db_ls.calendar = obj_salvar; */

            console.log(usuarioCorrente.calendario);

            usuarioCorrente.calendario.push(obj_salvar);
            db_psico.data[user_dados_index].calendario.push(obj_salvar);

            localStorage.setItem('db_psico', JSON.stringify(db_psico));
            sessionStorage.setItem('usuarioCorrente', JSON.stringify(usuarioCorrente));

            draw_calendar(usuarioCorrente.calendario);

        })



    }





}

InitApp();