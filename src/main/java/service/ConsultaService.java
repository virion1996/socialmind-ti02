package service;

import dao.ConsultaDAO;
import model.Consulta;

import spark.Request;
import spark.Response;
import java.util.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

public class ConsultaService {

    private static ConsultaDAO ConsultaDAO;

    public ConsultaService() {
        try {
            ConsultaDAO = new ConsultaDAO();
            ConsultaDAO.conectar();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Object addConsulta(Request request, Response response) throws Exception {
        SimpleDateFormat br_format = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat time_format = new SimpleDateFormat("HH:mm");

        String titulo = request.queryParams("txt_nome");
        Time time_inicial = (Time) time_format.parse(request.queryParams("time_inicial"));
        Time time_final = (Time) time_format.parse(request.queryParams("time_final"));
        Date data = br_format.parse(request.queryParams("data_consulta"));
        Date Criacao = new Date();
        String descricao = request.queryParams("user_message");

        Consulta Consulta = new Consulta(titulo, descricao, Criacao, data, time_inicial, time_final);

        ConsultaDAO.inserirConsulta(Consulta);

        response.status(201);

        return ("Sucesso");
    }

    public static Object getConsulta(Request request, Response response) {
        int id = Integer.parseInt(request.params(":Id"));

        Consulta Consulta = (Consulta) ConsultaDAO.procurarConsulta(id);

        if (Consulta != null) {
            response.header("Content-Type", "application/xml");
            response.header("Content-Encoding", "UTF-8");

            return "<Consulta>\n" + "\t<Consulta_ID>" + Consulta.getConsultaId() + "</Consulta_ID>\n"
                    + "\t<Titulo>" + Consulta.getTitulo() + "</Titulo>\n" + "\t<Descricao>"
                    + Consulta.getDescricao() + "</Descricao>\n" + "\t<Criacao>" + Consulta.getCriacao() + "</Criacao>\n"
                    + "\t<Data>" + Consulta.getData() + "</Data>\n" + "\t<TempoInicial>"
                    + Consulta.getTempoInicial() + "</TempoInicial>\n" + "\t<TempoFinal>"
                    + Consulta.getTempoFinal() + "</TempoFinal>\n" + "</Consulta>\n";

        } else {
            response.status(404); // 404 erro
            return "Consulta com id [" + id + "] nao encontrado.";
        }
    }

    /* public Object updateConsulta(Request request, Response response) throws Exception {
        int id = Integer.parseInt(request.params(":Id"));
        SimpleDateFormat br_format = new SimpleDateFormat("dd/MM/yyyy");
        Consulta Consulta = (Consulta) ConsultaDAO.procurarConsulta(id);

        if (Consulta != null) {
            Consulta.setAnonimo(Integer.parseInt(request.queryParams("select_anonimo")));
            Consulta.setCpf(request.queryParams("txt_cpf"));
            Consulta.setNome(request.queryParams("txt_nome"));
            Consulta.setSobenome(request.queryParams("txt_sobrenome"));
            Consulta.setEmail(request.queryParams("txt_email"));
            Consulta.setSobre(request.queryParams("user_message"));
            Consulta.setNascimento(br_format.parse(request.queryParams("user_birth")));
            Consulta.setSexo(request.queryParams("select_genre"));
            Consulta.setTelefone(request.queryParams("txt_phone"));
            Consulta.setCEP(request.queryParams("txt_cep"));
            Consulta.setSenha(request.queryParams("txt_senha"));
            Consulta.setSenha2(request.queryParams("txt_senha2"));
            Consulta.setValor(request.queryParams("select_valor"));

            Consulta.setNome_Consulta(request.queryParams("campoNome"));
            Consulta.setMotivo(request.queryParams("campoMotivos"));
            Consulta.setObs(request.queryParams("campoObservacoes"));
            Consulta.setData_consulta(br_format.parse(request.queryParams("campoData")));
            Consulta.setCriacao(new Date());

            ConsultaDAO.atualizarConsulta(Consulta);

            return id;
        } else {
            response.status(404); // 404 Erro
            return "Consulta com id [" + id + "] nao econtrado";
        }
    } */

    public static Object removeConsulta(Request request, Response response) {
        int id = Integer.parseInt(request.params(":Id"));

        Consulta Consulta = (Consulta) ConsultaDAO.procurarConsulta(id);

        if (Consulta != null) {
            ConsultaDAO.excluirConsulta(id);

            response.status(200); // Sucesso
            return id;
        } else {
            response.status(404); // 404 Erro
            return "Consulta com id [" + id + "] nao econtrado";
        }
    }

//    public Object getAllConsulta(Request request, Response response) {
//        StringBuffer returnValue = new StringBuffer("<Consultas type=\"array\">");
//
//        if (ConsultaDAO.getConsultas() != null) {
//            for (Consulta Consulta : ConsultaDAO.getConsultas()) {
//                returnValue.append("<Consulta>\n" + "\t<Consulta_ID>" + Consulta.getConsultaId() + "</Consulta_ID>\n"
//                + "\t<Titulo>" + Consulta.getTitulo() + "</Titulo>\n" + "\t<Descricao>"
//                + Consulta.getDescricao() + "</Descricao>\n" + "\t<Criacao>" + Consulta.getCriacao() + "</Criacao>\n"
//                + "\t<Data>" + Consulta.getData() + "</Data>\n" + "\t<TempoInicial>"
//                + Consulta.getTempoInicial() + "</TempoInicial>\n" + "\t<TempoFinal>"
//                + Consulta.getTempoFinal() + "</TempoFinal>\n" + "</Consulta>\n");
//            }
//        } else {
//            response.status(404); // 404 Erro
//            System.out.println("Lista de Consultas vazia");
//        }
//
//        returnValue.append("</Consultas>");
//        response.header("Content-Type", "application/xml");
//        response.header("Content-Encoding", "UTF-8");
//
//        return returnValue.toString();
//    }
}
