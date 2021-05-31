package service;

import dao.PsicologoDAO;
import model.Psicologo;

import spark.Request;
import spark.Response;
import java.util.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

public class PsicologoService {

    private PsicologoDAO PsicologoDAO;

    public PsicologoService() {
        try {
            PsicologoDAO = new PsicologoDAO();
            PsicologoDAO.conectar();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public Object logarPsicologo(Request request, Response response) {
    	String certo = "<script>window.location.href = \"http://127.0.0.1:5501/html/Dash_psicologo.html\"</script>";
    	String erro = "<script>window.location.href = \"http://127.0.0.1:5500/template/index.html\"</script>";
    	
    	String email = request.queryParams("email");
    	String senha = request.queryParams("senha");
    	
    	boolean emailcerto = PsicologoDAO.emailcerto(email);
    	boolean senhacerta = PsicologoDAO.senhacerta(senha);
    	
    	if(emailcerto && senhacerta) {
    		response.status(201);
    		return certo;
    	} 
    	
    	return erro;
    }

    public Object addPsicologo(Request request, Response response) throws Exception {
        SimpleDateFormat br_format = new SimpleDateFormat("dd/MM/yyyy");

        String Nome = request.queryParams("txt_nome");
        String Sobrenome = request.queryParams("txt_sobrenome");
        String Sexo = request.queryParams("select_genre");
        Date Criacao = new Date();
        Date DataNascimento = br_format.parse(request.queryParams("user_birth"));
        String Telefone = request.queryParams("txt_phone");
        String Cep = request.queryParams("txt_cep");
        String Valor = request.queryParams("select_valor");
        String Sobre = request.queryParams("txt_sobre");
        String Sobre_trabalho = request.queryParams("txt_sobre_trabalho");
        String Senha = request.queryParams("txt_senha");
        String Email = request.queryParams("txt_email");
        String Cpf = request.queryParams("txt_cpf");
        String CRP = request.queryParams("txt_crp");

        Psicologo Psicologo = new Psicologo(Nome, Sobrenome, Sexo, Telefone, Cep, Valor, Senha,
                Cpf, Email, Sobre, Sobre_trabalho, Criacao, DataNascimento, CRP);

        PsicologoDAO.inserirPsicologo(Psicologo);

        response.status(201);
        
        String redirect = "<script>window.location.href = \"http://127.0.0.1:5500/template/index.html\"</script>";

        return redirect;
    }

    public Object getPsicologo(Request request, Response response) {
        int id = Integer.parseInt(request.params(":Id"));

        Psicologo Psicologo = (Psicologo) PsicologoDAO.procurarPsicologo(id);

        if (Psicologo != null) {
            response.header("Content-Type", "application/xml");
            response.header("Content-Encoding", "UTF-8");

            return "<Psicologo>\n" + "\t<id>" + Psicologo.getId() + "</id>\n" + "\t<nome>" + Psicologo.getNome()
                    + "</nome>\n" + "\t<sobrenome>" + Psicologo.getSobrenome() + "</sobrenome>\n" + "</Anonimo>\n" + "\t<sexo>" + Psicologo.getSexo() + "</sexo>\n"
                    + "\t<Telefone>" + Psicologo.getTelefone() + "</Telefone>\n" + "\t<CEP>" + Psicologo.getCEP()
                    + "</CEP>\n" + "\t<Valor>" + Psicologo.getValor() + "</Valor>\n" + "\t<Senha>"
                    + Psicologo.getSenha() + "</Senha>\n" + "\t<Cpf>" + Psicologo.getCpf() + "</Cpf>\n" + "\t<Email>" + Psicologo.getEmail() + "</Email>\n"
                    + "\t<Sobre>" + Psicologo.getSobre() + "</Sobre>\n" + "\t<Sobre_trabalho>" + Psicologo.getSobre_trabalho()
                    + "</Sobre_trabalho>\n" + "\t<Criacao>" + Psicologo.getCriacao() + "</Criacao>\n" + "\t<Nascimento>"
                    + Psicologo.getNascimento() + "</Nascimento>\n" + "\t<CRP>" + Psicologo.getCRP()
                    + "</CRP>\n" + "</Psicologo>\n";

        } else {
            response.status(404); // 404 erro
            return "Psicologo com id [" + id + "] nao encontrado.";
        }
    }

    public Object updatePsicologo(Request request, Response response) throws Exception {
        int id = Integer.parseInt(request.params(":Id"));
        SimpleDateFormat br_format = new SimpleDateFormat("dd/MM/yyyy");
        Psicologo Psicologo = (Psicologo) PsicologoDAO.procurarPsicologo(id);

        if (Psicologo != null) {
            Psicologo.setCpf(request.queryParams("txt_cpf"));
            Psicologo.setNome(request.queryParams("txt_nome"));
            Psicologo.setSobenome(request.queryParams("txt_sobrenome"));
            Psicologo.setEmail(request.queryParams("txt_email"));
            Psicologo.setSobre(request.queryParams("txt_sobre"));
            Psicologo.setSobre_trabalho(request.queryParams("txt_sobre_trabalho"));
            Psicologo.setNascimento(br_format.parse(request.queryParams("user_birth")));
            Psicologo.setSexo(request.queryParams("select_genre"));
            Psicologo.setTelefone(request.queryParams("txt_phone"));
            Psicologo.setCEP(request.queryParams("txt_cep"));
            Psicologo.setSenha(request.queryParams("txt_senha"));
            Psicologo.setValor(request.queryParams("select_valor"));
            Psicologo.setCRP(request.queryParams("txt_crp"));

            PsicologoDAO.atualizarPsicologo(Psicologo);

            return id;
        } else {
            response.status(404); // 404 Erro
            return "Psicologo com id [" + id + "] nao econtrado";
        }
    }

    public Object removePsicologo(Request request, Response response) {
        int id = Integer.parseInt(request.params(":Id"));

        Psicologo Psicologo = (Psicologo) PsicologoDAO.procurarPsicologo(id);

        if (Psicologo != null) {
            PsicologoDAO.excluirPsicologo(id);

            response.status(200); // Sucesso
            return id;
        } else {
            response.status(404); // 404 Erro
            return "Psicologo com id [" + id + "] nao econtrado";
        }
    }

    public Object getAllPsicologo(Request request, Response response) {
        StringBuffer returnValue = new StringBuffer("<Psicologos type=\"array\">");

        if (PsicologoDAO.getPsicologos() != null) {
            for (Psicologo Psicologo : PsicologoDAO.getPsicologos()) {
                returnValue.append("<Psicologo>\n" + "\t<id>" + Psicologo.getId() + "</id>\n" + "\t<nome>"
                        + Psicologo.getNome() + "</nome>\n" + "\t<sobrenome>" + Psicologo.getSobrenome()
                        + "</sobrenome>\n" + "\t<sexo>"
                        + Psicologo.getSexo() + "</sexo>\n" + "\t<Telefone>" + Psicologo.getTelefone() + "</Telefone>\n"
                        + "\t<CEP>" + Psicologo.getCEP() + "</CEP>\n" + "\t<Valor>" + Psicologo.getValor()
                        + "</Valor>\n" + "\t<Senha>" + Psicologo.getSenha() + "</Senha>\n" + "</Senha2>\n" + "\t<Cpf>" + Psicologo.getCpf() + "</Cpf>\n"
                        + "\t<Email>" + Psicologo.getEmail() + "</Email>\n" + "\t<Sobre>" + Psicologo.getSobre()
                        + "</Sobre>\n" + "\t<Sobre_trabalho>" + Psicologo.getSobre_trabalho()
                        + "</Sobre_trabalho>\n" + "\t<Criacao>" + Psicologo.getCriacao() + "</Criacao>\n" + "\t<Nascimento>"
                        + Psicologo.getNascimento() + "</Nascimento>\n" + "\t<CRP>" + Psicologo.getCRP()
                        + "</CRP>\n" + "</Psicologo>\n");
            }
        } else {
            response.status(404); // 404 Erro
            System.out.println("Lista de Psicologos vazia");
        }

        returnValue.append("</Psicologos>");
        response.header("Content-Type", "application/xml");
        response.header("Content-Encoding", "UTF-8");

        return returnValue.toString();
    }
}
