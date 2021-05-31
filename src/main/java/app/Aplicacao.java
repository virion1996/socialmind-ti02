package app;

import static spark.Spark.*;

import service.*;

public class Aplicacao {

    public static PacienteService PacienteService = new PacienteService();
    public static PsicologoService PsicologoService = new PsicologoService();
    public static AnotacaoService AnotacaoSercice = new AnotacaoService();
    public static ConsultaService ConsultaSercice = new ConsultaService();

    public static void main(String[] args) {
        port(6789);

        /* posts e gets de paciente */
        post("/Paciente", (request, response) -> PacienteService.addPaciente(request, response));

        get("/Paciente/:id", (request, response) -> PacienteService.getPaciente(request, response));

        get("/Paciente/update/:id", (request, response) -> PacienteService.updatePaciente(request, response));

        get("/Paciente/delete/:id", (request, response) -> PacienteService.removePaciente(request, response));

        get("/Paciente", (request, response) -> PacienteService.getAllPaciente(request, response));
        
        post("/Paciente/logarPaciente", (request, response) -> PacienteService.logarPaciente(request, response));
        

        /* posts e gets de Psicologo */
         post("/Psicologo", (request, response) -> PsicologoService.addPsicologo(request, response));

        get("/Psicologo/:id", (request, response) -> PsicologoService.getPsicologo(request, response));

        get("/Psicologo/update/:id", (request, response) -> PsicologoService.updatePsicologo(request, response));

        get("/Psicologo/delete/:id", (request, response) -> PsicologoService.removePsicologo(request, response));

        get("/Psicologo", (request, response) -> PsicologoService.getAllPsicologo(request, response));
        
        post("/logarPsicologo", (request, response) -> PsicologoService.logarPsicologo(request, response));


        /* posts e gets de anotacao */
        post("/Anotacao", (request, response) -> AnotacaoService.addAnotacao(request, response));

        get("/Anotacao/delete/:id", (request, response) -> AnotacaoService.removeAnotacao(request, response));

        get("/Anotacao", (request, response) -> AnotacaoService.getAllAnotacao(request, response));


        /* posts e gets de consulta */
        post("/Consulta", (request, response) -> ConsultaService.addConsulta(request, response));

        get("/Consulta/:id", (request, response) -> ConsultaService.getConsulta(request, response));

        get("/Consulta/delete/:id", (request, response) -> ConsultaService.removeConsulta(request, response));
    }
}
