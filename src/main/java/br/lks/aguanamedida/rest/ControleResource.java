package br.lks.aguanamedida.rest;

import br.lks.aguanamedida.scheduler.AgendamentoIrrigacao;
import br.lks.aguanamedida.scheduler.IrrigacaoJob;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@Path("/controle")
@Produces(MediaType.APPLICATION_JSON)
public class ControleResource {

    private static final AtomicBoolean IRRIGACAO_ATIVA = new AtomicBoolean(false);

    @Inject
    Scheduler scheduler;

    @GET
    @Path("/status")
    public Map<String, Boolean> getIrrigacaoStatus() {
        return Map.of("irrigacaoAtiva", IRRIGACAO_ATIVA.get());
    }

    @POST
    @Path("/ligar")
    public Response postLigarIrrigacao() {
        IRRIGACAO_ATIVA.set(true);
        return Response.ok(Map.of("mensagem", "Irrigação ligada")).build();
    }

    @POST
    @Path("/desligar")
    public Response postDesligarIrrigacao() {
        IRRIGACAO_ATIVA.set(false);
        return Response.ok(Map.of("mensagem", "Irrigação desligada")).build();
    }

    @POST
    @Path("/agendar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response agendarIrrigacao(AgendamentoIrrigacao agendamento) {
        if (agendamento == null || !agendamento.cronValido()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("erro", "Cron inválido para o agendamento"))
                    .build();
        }

        try {
            JobDetail job = JobBuilder.newJob(IrrigacaoJob.class)
                    .withIdentity("irrigacao-" + UUID.randomUUID())
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger-" + UUID.randomUUID())
                    .withSchedule(CronScheduleBuilder.cronSchedule(agendamento.getCronExpression()))
                    .build();

            scheduler.scheduleJob(job, trigger);
            return Response.status(Response.Status.CREATED)
                    .entity(Map.of("mensagem", "Agendamento criado"))
                    .build();
        } catch (SchedulerException e) {
            return Response.serverError().entity(Map.of("erro", "Erro ao agendar a irrigação")).build();
        }
    }
}
