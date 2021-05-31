package service;

import dao.PacienteDAO;
import model.Paciente;

import spark.Request;
import spark.Response;
import java.util.Date;
import java.text.SimpleDateFormat;

public class PacienteService {

    private PacienteDAO PacienteDAO;

    public PacienteService() {
        try {
            PacienteDAO = new PacienteDAO();
            PacienteDAO.conectar();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Object addPaciente(Request request, Response response) throws Exception {
    	SimpleDateFormat br_format = new SimpleDateFormat("dd/MM/yyyy");
    	
    	
        String Nome = request.queryParams("txt_nome");
        String Sobrenome = request.queryParams("txt_sobrenome");
        int Anonimo = Integer.parseInt(request.queryParams("select_anonimo"));
        String Sexo = request.queryParams("select_genre");
        Date Criacao = new Date();
        Date nascimento = br_format.parse(request.queryParams("user_birth"));
        String Telefone = request.queryParams("txt_phone");
        String Cep = request.queryParams("txt_cep");
        String Valor = request.queryParams("select_valor");
        String Sobre = request.queryParams("user_message");
        String Senha = request.queryParams("txt_senha");
        String Email = request.queryParams("txt_email");
        String Cpf = request.queryParams("txt_cpf");

        Paciente Paciente = new Paciente(Nome, Sobrenome, Anonimo, Sexo, Telefone, Cep, Valor, Senha, Cpf,
                Email, Sobre, Criacao, nascimento);

        PacienteDAO.inserirPaciente(Paciente);

        response.status(201);
        
        String redirect = "<script>window.location.href = \"http://127.0.0.1:5500/template/index.html\"</script>";
        return (redirect);
    }
    
    public Object logarPaciente(Request request, Response response) {
    	String certo = "<script>window.location.href = \"http://127.0.0.1:5501/html/Dash_paciente.html\"</script>";
    	String erro = "<script>window.location.href = \"http://127.0.0.1:5500/template/index.html\"</script>";
    	
    	String email = request.queryParams("email");
    	String senha = request.queryParams("senha");
    	
    	boolean emailcerto = PacienteDAO.emailcerto(email);
    	boolean senhacerta = PacienteDAO.senhacerta(senha);
    	
    	if(emailcerto && senhacerta) {
    		response.status(201);
    		return certo;
    	} 
    	
    	return erro;
    }


    public Object getPaciente(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        
        String html = "<script src=\"teste.js\"></script>";

        Paciente Paciente = (Paciente) PacienteDAO.procurarPaciente(id);

        if (Paciente != null) {
            response.header("Content-Type", "application/xml");
            response.header("Content-Encoding", "UTF-8");
            return "<Paciente>\n" + "\t<id>" + Paciente.getId() + "</id>\n" + "\t<nome>" + Paciente.getNome()
                    + "</nome>\n" + "\t<sobrenome>" + Paciente.getSobrenome() + "</sobrenome>\n" + "\t<Anonimo>"
                    + Paciente.getAnonimo() + "</Anonimo>\n" + "\t<sexo>" + Paciente.getSexo() + "</sexo>\n"
                   + "\t<Telefone>" + Paciente.getTelefone() + "</Telefone>\n" + "\t<CEP>" + Paciente.getCEP()
                    + "</CEP>\n" + "\t<Valor>" + Paciente.getValor() + "</Valor>\n" + "\t<Senha>" + Paciente.getSenha()
                    + "</Senha>\n" + "\t<Cpf>" + Paciente.getCpf()
                    + "</Cpf>\n" + "\t<Email>" + Paciente.getEmail() + "</Email>\n" + "\t<Sobre>" + Paciente.getSobre()
                    + "</Sobre>\n" + "\t<Criacao>" + Paciente.getCriacao() + "</Criacao>\n" + "\t<Nascimento>"
                    + Paciente.getNascimento() + "</Nascimento>\n" + "</Paciente>\n";

        } else {
            response.status(404); // 404 erro
            return html;
        }
    }

    public Object updatePaciente(Request request, Response response) throws Exception {
        int id = Integer.parseInt(request.params(":Id"));
        SimpleDateFormat br_format = new SimpleDateFormat("dd/MM/yyyy");
        Paciente Paciente = (Paciente) PacienteDAO.procurarPaciente(id);

        if (Paciente != null) {
            Paciente.setAnonimo(Integer.parseInt(request.queryParams("select_anonimo")));
            Paciente.setCpf(request.queryParams("txt_cpf"));
            Paciente.setNome(request.queryParams("txt_nome"));
            Paciente.setSobenome(request.queryParams("txt_sobrenome"));
            Paciente.setEmail(request.queryParams("txt_email"));
            Paciente.setSobre(request.queryParams("user_message"));
            Paciente.setNascimento(br_format.parse(request.queryParams("user_birth")));
            Paciente.setSexo(request.queryParams("select_genre"));
            Paciente.setTelefone(request.queryParams("txt_phone"));
            Paciente.setCEP(request.queryParams("txt_cep"));
            Paciente.setSenha(request.queryParams("txt_senha"));
            Paciente.setValor(request.queryParams("select_valor"));

            PacienteDAO.atualizarPaciente(Paciente);

            return id;
        } else {
            response.status(404); // 404 Erro
            return "Paciente com id [" + id + "] nao econtrado";
        }
    }

    public Object removePaciente(Request request, Response response) {
        int id = Integer.parseInt(request.params(":Id"));

        Paciente Paciente = (Paciente) PacienteDAO.procurarPaciente(id);

        if (Paciente != null) {
            PacienteDAO.excluirPaciente(id);
            String redirect = "<script>"
            		+ "let tela = document.getElementById('tela');"
            		+"strHtml = String strHtml = '';"
            		+ "`<p> Nome: ${Paciente.getNome()}\r\n"
            		+ "         <br> CPF: ${Paciente.getCPF()} \r\n"
            		+"tela.innerHTML = strHtml;"
            		+ "window.location.href = \"http://127.0.0.1:5500/src/main/resources/formulario.html\""
            		+ "</script>";


            response.status(200); // Sucesso
            return ("removido com sucesso");
        } else {
            response.status(404); // 404 Erro
            return "Paciente com id [" + id + "] nao econtrado";
        }
    }

    public Object getAllPaciente(Request request, Response response) {
        StringBuffer returnValue = new StringBuffer("<Pacientes type=\"array\">");

        if (PacienteDAO.getPacientes() != null) {
            for (Paciente Paciente : PacienteDAO.getPacientes()) {
                returnValue.append("<Paciente>\n" + "\t<id>" + Paciente.getId() + "</id>\n" + "\t<nome>"
                        + Paciente.getNome() + "</nome>\n" + "\t<sobrenome>" + Paciente.getSobrenome()
                        + "</sobrenome>\n" + "\t<Anonimo>" + Paciente.getAnonimo() + "</Anonimo>\n" + "\t<sexo>"
                        + Paciente.getSexo() + "</sexo>\n" + "\t<Telefone>" + Paciente.getTelefone() + "</Telefone>\n"
                        + "\t<CEP>" + Paciente.getCEP() + "</CEP>\n" + "\t<Valor>" + Paciente.getValor() + "</Valor>\n"
                        + "\t<Senha>" + Paciente.getSenha() + "</Senha>\n" + "\t<Cpf>" + Paciente.getCpf() + "</Cpf>\n" + "\t<Email>" + Paciente.getEmail()
                        + "</Email>\n" + "\t<Sobre>" + Paciente.getSobre() + "</Sobre>\n" + "\t<Criacao>"
                        + Paciente.getCriacao() + "</Criacao>\n" + "\t<Nascimento>" + Paciente.getNascimento()
                        + "</Nascimento>\n" + "</Paciente>\n");
            }
        } else {
            response.status(404); // 404 Erro
            System.out.println("Lista de Pacientes vazia");
        }

        returnValue.append("</Pacientes>");
        response.header("Content-Type", "application/xml");
        response.header("Content-Encoding", "UTF-8");

        return returnValue.toString();
    }
}
