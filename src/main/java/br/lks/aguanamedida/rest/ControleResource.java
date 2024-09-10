package br.lks.aguanamedida.rest;

import br.lks.aguanamedida.scheduler.AgendamentoIrrigacao;
import br.lks.aguanamedida.scheduler.IrrigacaoJob;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.quartz.*;

@Path("controle")
public class ControleResource {

    @Inject
    Scheduler scheduler;

    @GET
    @Path("status")
    public String getIrrigacaoStatus() {
        return "status";
    }

    @POST
    @Path("ligar")
    public void postLigarIrrigacao() {
        System.out.println("ligado");
    }

    @POST
    @Path("desligar")
    public void postDesligarIrrigacao() {
        System.out.println("desligado");
    }

    @POST
    @Path("/agendar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response agendarIrrigacao(AgendamentoIrrigacao agendamento) {
        // Validação dos dados (implementar)

        try {
            JobDetail job = JobBuilder.newJob(IrrigacaoJob.class)
                    .withIdentity("irrigacao-" + agendamento.getHorario())
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger-" + agendamento.getHorario())
                    .withSchedule(CronScheduleBuilder.cronSchedule(agendamento.getCronExpression()))
                    .build();

            scheduler.scheduleJob(job, trigger);

            // Opcional: armazenar o agendamento no banco de dados

            return Response.status(201).build();
        } catch (SchedulerException e) {
            return Response.status(500).entity("Erro ao agendar a irrigação").build();
        }
    }

}
