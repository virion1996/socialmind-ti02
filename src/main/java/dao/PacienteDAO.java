package dao;

import java.sql.*;
import model.Paciente;

public class PacienteDAO {
	private Connection conexao;
	private int maxId;

	public PacienteDAO() {
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
            ResultSet rs = st.executeQuery("SELECT email FROM paciente WHERE email = '" + email + "'");

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
            ResultSet rs = st.executeQuery("SELECT senha FROM paciente WHERE senha = '" + senha + "'");

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

	public boolean inserirPaciente(Paciente Paciente) {
		boolean status = false;

		try {
			Statement st = conexao.createStatement();
			st.executeUpdate(
					"INSERT INTO paciente (anonimo, cpf, nome, sobenome, email, sobre, criacao, nascimento, sexo, telefone, cep, valor, senha)"
							+ "VALUES ('"+ Paciente.getAnonimo() + "', '"
							+ Paciente.getCpf() + "', '" + Paciente.getNome() + "', '" + Paciente.getSobrenome()
							+ "', '" + Paciente.getEmail() + "', '" + Paciente.getSobre() + "', '"
							+ Paciente.getCriacao() + "', '" + Paciente.getNascimento() + "', '" + Paciente.getSexo()
							+ "', '" + Paciente.getTelefone() + "', '" + Paciente.getCEP() + "', '" + Paciente.getValor()
							+ "', '" + Paciente.getSenha() +"');");
			st.close();
			status = true;

			// Somar mais um ao maxID
			this.maxId = maxId + 1;
			Paciente.setId(maxId + 1);

			System.out.println("Insercao do paciente com id [" + Paciente.getId() + "] efetuada com sucesso.");

		} catch (SQLException u) {
			throw new RuntimeException(u);
		}

		return status;
	}

	public boolean atualizarPaciente(Paciente Paciente) {
		boolean status = false;

		try {
			Statement st = conexao.createStatement();
			String sql = "UPDATE paciente SET nome = '" + Paciente.getNome() + "', sobrenome = '"
					+ Paciente.getSobrenome() + "', nascimento = '" + Paciente.getNascimento() + "', senha = '"
					+ Paciente.getSenha() + "', email = '" + Paciente.getEmail() + "', sexo = '" + Paciente.getSexo()
					+ "', anonimo = '" + Paciente.getAnonimo() + "', telefone = '" + Paciente.getTelefone()
					+ "', cep = '" + Paciente.getCEP() + "', valor = '" + Paciente.getValor() + "', cpf = '" + Paciente.getCpf() + "', sobre = '" + Paciente.getSobre()
					+ "', criacao = '" + Paciente.getCriacao() + "'" + " WHERE id = " + Paciente.getId();
			st.executeUpdate(sql);
			st.close();
			status = true;

			System.out.println("Atualizacao do Paciente com codigo [" + Paciente.getId() + "] efetuada com sucesso.");
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}

		return status;
	}

	public boolean excluirPaciente(int id) {
		boolean status = false;

		try {
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM paciente WHERE id = " + id);
			st.close();
			status = true;

			// subtrair um ao maxID
			if (this.maxId > 0) {
				this.maxId = this.maxId - 1;
			}

			System.out.println("Remocao do Paciente com id [" + id + "] efetuada com sucesso.");
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}

		return status;
	}

	public Paciente[] getPacientes() {
		Paciente[] Pacientes = null;

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM paciente");
			if (rs.next()) {
				rs.last();
				Pacientes = new Paciente[rs.getRow()];
				rs.beforeFirst();

				for (int i = 0; rs.next(); i++) {
					Pacientes[i] = new Paciente(rs.getString("nome"), rs.getString("sobrenome"),
							rs.getInt("anonimo"), rs.getString("sexo"), rs.getString("telefone"), rs.getString("cep"),
							rs.getString("valor"), rs.getString("senha"), rs.getString("cpf"),
							rs.getString("email"), rs.getString("sobre"), rs.getDate("criacao"),
							rs.getDate("nascimento"));
				}
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return Pacientes;
	}

	public Paciente procurarPaciente(int id) {
		Paciente Pacientes = null;

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = st.executeQuery("SELECT * FROM paciente WHERE paciente.id = " + id);

			if (rs.next()) {
				Pacientes = new Paciente(rs.getString("nome"), rs.getString("sobenome"),
				rs.getInt("anonimo"), rs.getString("sexo"), rs.getString("telefone"), rs.getString("cep"),
				rs.getString("valor"), rs.getString("senha"), rs.getString("cpf"),
				rs.getString("email"), rs.getString("sobre"), rs.getDate("criacao"),
				rs.getDate("nascimento"));
			}

			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return Pacientes;
	}
}
