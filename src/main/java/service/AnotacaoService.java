package service;

import dao.AnotacaoDAO;
import dao.PsicologoDAO;
import model.Anotacao;

import spark.Request;
import spark.Response;
import java.util.Date;
import java.text.SimpleDateFormat;

public class AnotacaoService {

    private static AnotacaoDAO AnotacaoDAO;
    private static PsicologoDAO psicologoDAO;

    public AnotacaoService() {
        try {
            AnotacaoDAO = new AnotacaoDAO();
            AnotacaoDAO.conectar();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Object addAnotacao(Request request, Response response) throws Exception {
        SimpleDateFormat br_format = new SimpleDateFormat("dd/MM/yyyy");

        String Nome_anotacao = request.queryParams("campoNome");
        String Motivo = request.queryParams("campoMotivos");
        String Obs = request.queryParams("campoObservacoes");
        Date Data_consulta = br_format.parse(request.queryParams("campoData"));
        Date Criacao = new Date();

        Anotacao Anotacao = new Anotacao(Nome_anotacao, Motivo, Criacao, Data_consulta, Obs);
        int id_logado = psicologoDAO.getId_logado();

        AnotacaoDAO.inserirAnotacao(Anotacao, id_logado);

        response.status(201);

        return ("sucesso");
    }

//    public Object getAnotacao(Request request, Response response) {
//        int id = Integer.parseInt(request.params(":Id"));
//
//        Anotacao Anotacao = (Anotacao) AnotacaoDAO.procurarAnotacao(id);
//
//        if (Anotacao != null) {
//            response.header("Content-Type", "application/xml");
//            response.header("Content-Encoding", "UTF-8");
//
//            return "<Anotacao>\n" + "\t<Anotacao_ID>" + Anotacao.getAnotacaoId() + "</Anotacao_ID>\n"
//                    + "\t<Nome_anotacao>" + Anotacao.getNome_anotacao() + "</Nome_anotacao>\n" + "\t<Motivo>"
//                    + Anotacao.getMotivo() + "</Motivo>\n" + "\t<Criacao>" + Anotacao.getCriacao() + "</Criacao>\n"
//                    + "\t<DataConsulta>" + Anotacao.getDataConsulta() + "</DataConsulta>\n" + "\t<Obs>"
//                    + Anotacao.getObs() + "</Obs>\n" + "</Anotacao>\n";
//
//        } else {
//            response.status(404); // 404 erro
//            return "Anotacao com id [" + id + "] nao encontrado.";
//        }
//    }

    /* public Object updateAnotacao(Request request, Response response) throws Exception {
        int id = Integer.parseInt(request.params(":Id"));
        SimpleDateFormat br_format = new SimpleDateFormat("dd/MM/yyyy");
        Anotacao Anotacao = (Anotacao) AnotacaoDAO.procurarAnotacao(id);

        if (Anotacao != null) {
            Anotacao.setAnonimo(Integer.parseInt(request.queryParams("select_anonimo")));
            Anotacao.setCpf(request.queryParams("txt_cpf"));
            Anotacao.setNome(request.queryParams("txt_nome"));
            Anotacao.setSobenome(request.queryParams("txt_sobrenome"));
            Anotacao.setEmail(request.queryParams("txt_email"));
            Anotacao.setSobre(request.queryParams("user_message"));
            Anotacao.setNascimento(br_format.parse(request.queryParams("user_birth")));
            Anotacao.setSexo(request.queryParams("select_genre"));
            Anotacao.setTelefone(request.queryParams("txt_phone"));
            Anotacao.setCEP(request.queryParams("txt_cep"));
            Anotacao.setSenha(request.queryParams("txt_senha"));
            Anotacao.setSenha2(request.queryParams("txt_senha2"));
            Anotacao.setValor(request.queryParams("select_valor"));

            Anotacao.setNome_anotacao(request.queryParams("campoNome"));
            Anotacao.setMotivo(request.queryParams("campoMotivos"));
            Anotacao.setObs(request.queryParams("campoObservacoes"));
            Anotacao.setData_consulta(br_format.parse(request.queryParams("campoData")));
            Anotacao.setCriacao(new Date());

            AnotacaoDAO.atualizarAnotacao(Anotacao);

            return id;
        } else {
            response.status(404); // 404 Erro
            return "Anotacao com id [" + id + "] nao econtrado";
        }
    } */

    public static Object removeAnotacao(Request request, Response response) {
        int id = Integer.parseInt(request.params(":Id"));

        Anotacao Anotacao = (Anotacao) AnotacaoDAO.procurarAnotacao(id);

        if (Anotacao != null) {
            AnotacaoDAO.excluirAnotacao(id);

            response.status(200); // Sucesso
            return id;
        } else {
            response.status(404); // 404 Erro
            return "Anotacao com id [" + id + "] nao econtrado";
        }
    }

    public static Object getAllAnotacao(Request request, Response response) {
        StringBuffer returnValue = new StringBuffer("<Anotacaos type=\"array\">");

        if (AnotacaoDAO.getAnotacoes() != null) {
            for (Anotacao Anotacao : AnotacaoDAO.getAnotacoes()) {
                returnValue.append("<Anotacao>\n" + "\t<Anotacao_ID>" + Anotacao.getAnotacaoId() + "</Anotacao_ID>\n"
                        + "\t<Nome_anotacao>" + Anotacao.getNome_anotacao() + "</Nome_anotacao>\n" + "\t<Motivo>"
                        + Anotacao.getMotivo() + "</Motivo>\n" + "\t<Criacao>" + Anotacao.getCriacao() + "</Criacao>\n"
                        + "\t<DataConsulta>" + Anotacao.getDataConsulta() + "</DataConsulta>\n" + "\t<Obs>"
                        + Anotacao.getObs() + "</Obs>\n" + "</Anotacao>\n");
            }
        } else {
            response.status(404); // 404 Erro
            System.out.println("Lista de Anotacaos vazia");
        }

        returnValue.append("</Anotacaos>");
        response.header("Content-Type", "application/xml");
        response.header("Content-Encoding", "UTF-8");

        return returnValue.toString();
    }
}
