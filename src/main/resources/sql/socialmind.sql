CREATE TABLE Psicologo (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	cpf VARCHAR(255) NOT NULL,
	apelido VARCHAR(255) NOT NULL,
	nome VARCHAR(255) NOT NULL,
	sobenome VARCHAR(255) NOT NULL,
	email VARCHAR(255) NOT NULL,
	criacao DATE NOT NULL,
	nascimento DATE NOT NULL,
	sobre VARCHAR(255),
	trabalho VARCHAR(255),
	valor INT NOT NULL
);

CREATE TABLE Paciente (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	cpf VARCHAR(255) NOT NULL,
	apelido VARCHAR(255) NOT NULL,
	nome VARCHAR(255) NOT NULL,
	sobenome VARCHAR(255) NOT NULL,
	email VARCHAR(255) NOT NULL,
	criacao DATE NOT NULL,
	nascimento DATE NOT NULL,
	sobre VARCHAR(255),
	anonimo BIT
);

CREATE TABLE Anotacoes (
	psicologo_id INT NOT NULL,
	paciente_id INT,
	criacao DATE NOT NULL,
	titulo VARCHAR(255),
	descricao VARCHAR(255),

	FOREIGN KEY (psicologo_id) REFERENCES Psicologo(id),
	FOREIGN KEY (paciente_id) REFERENCES Paciente(id)
);

CREATE TABLE Consultas (
	psicologo_id INT NOT NULL,
	paciente_id INT NOT NULL,
	criacao DATE NOT NULL,
	titulo VARCHAR(255),
	data DATE,
	descricao VARCHAR(255),

	FOREIGN KEY (psicologo_id) REFERENCES Psicologo(id),
	FOREIGN KEY (paciente_id) REFERENCES Paciente(id)
);

CREATE TABLE TipoContato (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	nome VARCHAR(255) NOT NULL
);

INSERT INTO TipoContato(nome) VALUES ("Email"), ("Telefone"), ("WhatsApp"), ("Facebook"), ("Instagram"), ("Outro");

CREATE TABLE PsicologoContato (
	psicoloco_id INT NOT NULL,
	tipo INT NOT NULL,
	dado VARCHAR(255) NOT NULL,

	FOREIGN KEY (psicologo_id) REFERENCES Psicologo(id),
	FOREIGN KEY (tipo) REFERENCES TipoContato(id)
);

CREATE TABLE PacienteContato (
	paciente_id INT NOT NULL,
	tipo INT NOT NULL,
	dado VARCHAR(255) NOT NULL,

	FOREIGN KEY (paciente_id) REFERENCES Paciente(id),
	FOREIGN KEY (tipo) REFERENCES TipoContato(id)
);

CREATE TABLE Tags (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	nome VARCHAR(255) NOT NULL
);

CREATE TABLE PsicologoTag (
	psicologo_id INT NOT NULL,
	tag INT NOT NULL,
	count INT NOT NULL,

	FOREIGN KEY (psicologo_id) REFERENCES Psicologo(id),
	FOREIGN KEY (tag) REFERENCES Tags(id)
);