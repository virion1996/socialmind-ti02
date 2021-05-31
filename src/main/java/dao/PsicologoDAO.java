package dao;

import java.sql.*;
import model.Psicologo;

public class PsicologoDAO {
    private Connection conexao;
    private int maxId;

    public PsicologoDAO() {
        conexao = null;
    }

    public int getMaxCodigo() {
        return this.maxId;
    }

    public boolean conectar() {
        String driverName = "org.postgresql.Driver";
        String serverName = "socialmind-ti02.postgres.database.azure.com"; // Nome da azure que ela vai nos fornecer
        String mydatabase = "projeto"; // Eu tenho que criar na azure
        int porta = 5432; // Vou escolher na azure
        String url = "jdbc:postgresql://" + serverName + ":" + porta + "/" + mydatabase + "?gssEncMode=disable";
        String username = "adm@socialmind-ti02";
        String password = "socialmindti-02";
        boolean status = false;

        try {
            Class.forName(driverName);
            conexao = DriverManager.getConnection(url, username, password);
            status = (conexao == null);
            System.out.println("Conexão efetuada com o postgres!");
        } catch (ClassNotFoundException e) {
            System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
        }

        return status;
    }

    public boolean close() {
        boolean status = false;

        try {
            conexao.close();
            status = true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return status;
    }
    
    public boolean emailcerto(String email) {
    	boolean resp = false;
    	
    	try {
    		Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT email FROM psicologo WHERE email = '" + email + "'");

            if (rs.next()) {
                String frase = rs.getString("email");
                if (frase.equals(email)) {
                    resp = true;
                }
            }

            st.close();

        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
    	return resp;
    }
    
    public boolean senhacerta(String senha) {
    	boolean resp = false;
    	
    	try {
    		Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT senha FROM psicologo WHERE senha = '" + senha + "'");

            if (rs.next()) {
                String frase = rs.getString("senha");
                if (frase.equals(senha)) {
                    resp = true;
                }
            }

            st.close();

        } catch (SQLException u) {
            throw new RuntimeException(u);
        }
    	return resp;
    }

    public boolean inserirPsicologo(Psicologo Psicologo) {
        boolean status = false;

        try {
            Statement st = conexao.createStatement();
            st.executeUpdate(
                    "INSERT INTO psicologo (cpf, nome, sobrenome, email, sobre, sobre_trabalho, criacao, nascimento, sexo, telefone, cep, valor, senha, crp)"
                            + "VALUES (" + Psicologo.getCpf() + "', '" + Psicologo.getNome() + "', '"
                            + Psicologo.getSobrenome() + "', '" + Psicologo.getEmail() + "', '" + Psicologo.getSobre()
                            + "', '" + Psicologo.getSobre_trabalho() + "', '" + Psicologo.getCriacao() + "', '"
                            + Psicologo.getNascimento() + "', '" + Psicologo.getSexo() + "', '"
                            + Psicologo.getTelefone() + "', '" + Psicologo.getCEP() + "', '" + Psicologo.getValor()
                            + "', '" + Psicologo.getSenha() + "', '" + Psicologo.getCRP() + "');");
            st.close();
            status = true;

            // Somar mais um ao maxID
            this.maxId = maxId + 1;
            Psicologo.setId(maxId + 1);

            System.out.println("Insercao do Psicologo com id [" + Psicologo.getId() + "] efetuada com sucesso.");

        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

        return status;
    }

    public boolean atualizarPsicologo(Psicologo Psicologo) {
        boolean status = false;

        try {
            Statement st = conexao.createStatement();
            String sql = "UPDATE psicologo SET nome = '" + Psicologo.getNome() + "', sobrenome = '"
                    + Psicologo.getSobrenome() + "', nascimento = '" + Psicologo.getNascimento() + "', senha = '"
                    + Psicologo.getSenha() + "', email = '" + Psicologo.getEmail() + "', sexo = '" + Psicologo.getSexo()
                    + "', crp = '" + Psicologo.getCRP() + "', telefone = '" + Psicologo.getTelefone() + "', cep = '"
                    + Psicologo.getCEP() + "', valor = '" + Psicologo.getValor() + "', sobre_trabalho = '"
                    + Psicologo.getSobre_trabalho() + "', cpf = '" + Psicologo.getCpf() + "', sobre = '"
                    + Psicologo.getSobre() + "', criacao = '" + Psicologo.getCriacao() + "'" + " WHERE id = "
                    + Psicologo.getId();

            st.executeUpdate(sql);
            st.close();
            status = true;

            System.out.println("Atualizacao do Psicologo com codigo [" + Psicologo.getId() + "] efetuada com sucesso.");
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

        return status;
    }

    public boolean excluirPsicologo(int id) {
        boolean status = false;

        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("DELETE FROM psicologo WHERE id = " + id);
            st.close();
            status = true;

            // subtrair um ao maxID
            if (this.maxId > 0) {
                this.maxId = this.maxId - 1;
            }

            System.out.println("Remocao do Psicologo com id [" + id + "] efetuada com sucesso.");
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

        return status;
    }

    public Psicologo[] getPsicologos() {
        Psicologo[] Psicologos = null;

        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM Psicologo");
            if (rs.next()) {
                rs.last();
                Psicologos = new Psicologo[rs.getRow()];
                rs.beforeFirst();

                for (int i = 0; rs.next(); i++) {
                    Psicologos[i] = new Psicologo(rs.getString("nome"), rs.getString("sobrenome"),
                            rs.getString("sexo"), rs.getString("telefone"), rs.getString("cep"), rs.getString("valor"),
                            rs.getString("senha"), rs.getString("cpf"), rs.getString("email"), rs.getString("sobre"),
                            rs.getString("sobre_trabalho"), rs.getDate("criacao"), rs.getDate("nascimento"),
                            rs.getString("crp"));
                }
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return Psicologos;
    }

    public Psicologo procurarPsicologo(int id) {
        Psicologo Psicologos = null;

        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM Psicologo WHERE Psicologo.Id = " + id);

            if (rs.next()) {
                Psicologos = new Psicologo(rs.getString("nome"), rs.getString("sobrenome"),
                        rs.getString("sexo"), rs.getString("telefone"), rs.getString("cep"), rs.getString("valor"),
                        rs.getString("senha"), rs.getString("cpf"), rs.getString("email"), rs.getString("sobre"),
                        rs.getString("sobre_trabalho"), rs.getDate("criacao"), rs.getDate("nascimento"),
                        rs.getString("crp"));
            }

            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return Psicologos;
    }
}
