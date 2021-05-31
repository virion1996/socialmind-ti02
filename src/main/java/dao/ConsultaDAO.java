package dao;

import java.sql.*;

import model.Consulta;

public class ConsultaDAO {
    private Connection conexao;
    private int MaxConsultaID;

    public ConsultaDAO() {
        conexao = null;
    }

    public int getMaxConsultaID() {
        return this.MaxConsultaID;
    }

    public void setMaxConsultaID(int value) {
        this.MaxConsultaID = value;
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

    public boolean inserirConsulta(Consulta Consulta) {
        boolean status = false;

        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("INSERT INTO consultas (titulo, descricao, criacao, data, tempo_inicial, tempo_final)"
                    + "VALUES (" + Consulta.getTitulo() + "', '" + Consulta.getDescricao() + "', '"
                    + Consulta.getCriacao() + "', '" + Consulta.getData() + "', '" + Consulta.getTempoInicial() + "', '"
                    + Consulta.getTempoFinal() + "');");
            st.close();
            status = true;

            // Somar mais um ao MaxConsultaID
            this.MaxConsultaID++;

            System.out.println("Insercao do Consulta com id [" + Consulta.getConsultaId() + "] efetuada com sucesso.");

        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

        return status;
    }

    public boolean atualizarConsulta(Consulta Consulta) {
        boolean status = false;

        try {
            Statement st = conexao.createStatement();
            String sql = "UPDATE consultas SET titulo = '" + Consulta.getTitulo() + "', descricao = '"
                    + Consulta.getDescricao() + "', criacao = '" + Consulta.getCriacao() + "', data = '"
                    + Consulta.getData() + "', tempo_inicial = '" + Consulta.getTempoInicial() + "', tempo_final = '"
                    + Consulta.getTempoFinal() + " WHERE psicologo_id = " + Consulta.getPsicologoId()
                    + "AND consulta_id = " + Consulta.getConsultaId();
            st.executeUpdate(sql);
            st.close();
            status = true;

            System.out.println(
                    "Atualizacao do Consulta com codigo [" + Consulta.getConsultaId() + "] efetuada com sucesso.");
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

        return status;
    }

    public boolean excluirConsulta(int id) {
        boolean status = false;

        try {
            Statement st = conexao.createStatement();
            st.executeUpdate("DELETE FROM consultas WHERE consulta_id = " + id);
            st.close();
            status = true;

            // subtrair um ao
            if (this.getMaxConsultaID() > 0) {
                this.setMaxConsultaID(getMaxConsultaID() - 1);
            }

            System.out.println("Remocao do Consulta com id [" + id + "] efetuada com sucesso.");
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

        return status;
    }

    /*
     * public Consulta[] getAnotacoes() { Consulta[] Anotacoes = null;
     * 
     * try { Statement st =
     * conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
     * ResultSet.CONCUR_READ_ONLY); ResultSet rs =
     * st.executeQuery("SELECT * FROM Consulta"); if (rs.next()) { rs.last();
     * Anotacoes = new Consulta[rs.getRow()]; rs.beforeFirst();
     * 
     * for (int i = 0; rs.next(); i++) { Anotacoes[i] = new
     * Consulta(rs.getInt("Psicologo"), rs.getInt("Consulta_ID"),
     * rs.getString("Nome_Consulta"), rs.getString("Motivo"), rs.getDate("Craicao"),
     * rs.getString("Obs")); } } st.close(); } catch (Exception e) {
     * System.err.println(e.getMessage()); }
     * 
     * return Anotacoes; }
     */

    
      public Consulta procurarConsulta(int id) { Consulta Consulta = null;
      
      try { Statement st =
      conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
      ResultSet.CONCUR_READ_ONLY); ResultSet rs =
      st.executeQuery("SELECT * FROM Consulta WHERE Consulta.ID = " + id);
      
      if (rs.next()) { Consulta = new Consulta(rs.getString("titulo"),
      rs.getString("descricao"), rs.getDate("criacao"), rs.getDate("data"), rs.getTime("tempo_inicial"), rs.getTime("tempo_final")); }
      
      st.close(); } catch (Exception e) { System.err.println(e.getMessage()); }
      
      return Consulta; }
     
}
