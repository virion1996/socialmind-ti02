package dao;

import java.sql.*;
import model.Anotacao;

public class AnotacaoDAO {
    private Connection conexao;
    private int MaxAnotacaoID;

    public AnotacaoDAO() {
        conexao = null;
    }

    public int getMaxAnotacaoID() {
        return this.MaxAnotacaoID;
    }

    public void setMaxAnotacaoID(int value) {
        this.MaxAnotacaoID = value;
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

    public boolean inserirAnotacao(Anotacao Anotacao, int id_logado) {
        boolean status = false;

        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("INSERT INTO anotacoes (psicologo_id, nome, motivo, criacao, obs, data_consulta)" + "VALUES ("
                    + id_logado + "', '" + Anotacao.getNome_anotacao() + "', '" + Anotacao.getMotivo() + "', '" + Anotacao.getCriacao()
                    + "', '" + Anotacao.getObs() + "', '" + Anotacao.getDataConsulta() + "');");
            st.close();
            status = true;

            // Somar mais um ao MaxAnotacaoID
            this.MaxAnotacaoID++;

            System.out.println("Insercao do Anotacao com id [" + Anotacao.getAnotacaoId() + "] efetuada com sucesso.");

        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

        return status;
    }

    /*
     * public boolean atualizarAnotacao(Anotacao Anotacao) { boolean status = false;
     * 
     * try { Statement st = conexao.createStatement(); String sql =
     * "UPDATE Anotacao SET Nome = '" + Anotacao.getNome() + "', Sobrenome = '" +
     * Anotacao.getSobrenome() + "', DataNascimento = '" + Anotacao.getNascimento()
     * + "', Senha = '" + Anotacao.getSenha() + "', Email = '" + Anotacao.getEmail()
     * + "', Sexo = '" + Anotacao.getSexo() + "', Anonimo = '" +
     * Anotacao.getAnonimo() + "', Telefone = '" + Anotacao.getTelefone() +
     * "', CEP = '" + Anotacao.getCEP() + "', Valor = '" + Anotacao.getValor() +
     * "', Senha2 = '" + Anotacao.getSenha2() + "', CPF = '" + Anotacao.getCpf() +
     * "', Sobre = '" + Anotacao.getSobre() + "', Criacao = '" +
     * Anotacao.getCriacao() + "'" + " WHERE Id = " + Anotacao.getId();
     * st.executeUpdate(sql); st.close(); status = true;
     * 
     * System.out.println("Atualizacao do Anotacao com codigo [" + Anotacao.getId()
     * + "] efetuada com sucesso."); } catch (SQLException u) { throw new
     * RuntimeException(u); }
     * 
     * return status; }
     */

    public boolean excluirAnotacao(int id) {
        boolean status = false;

        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("DELETE FROM anotacoes WHERE Anotacao_ID = " + id);
            st.close();
            status = true;

            // subtrair um ao
            if (this.getMaxAnotacaoID() > 0) {
                this.setMaxAnotacaoID(getMaxAnotacaoID() - 1);
            }

            System.out.println("Remocao do Anotacao com id [" + id + "] efetuada com sucesso.");
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

        return status;
    }

    public Anotacao[] getAnotacoes() {
        Anotacao[] Anotacoes = null;

        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM anotacoes");
            if (rs.next()) {
                rs.last();
                Anotacoes = new Anotacao[rs.getRow()];
                rs.beforeFirst();

                for (int i = 0; rs.next(); i++) {
                    Anotacoes[i] = new Anotacao(rs.getString("nome_anotacao"), rs.getString("motivo"), rs.getDate("criacao"),
                            rs.getDate("data_consulta"), rs.getString("obs"));
                }
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return Anotacoes;
    }

    public Anotacao procurarAnotacao(int id) {
        Anotacao Anotacao = null;

        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM Anotacao WHERE Anotacao.ID = " + id);

            if (rs.next()) {
                Anotacao = new Anotacao(rs.getString("nome_anotacao"), rs.getString("motivo"), rs.getDate("criacao"),
                        rs.getDate("data_consulta"), rs.getString("obs"));
            }

            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return Anotacao;
    } 
}
