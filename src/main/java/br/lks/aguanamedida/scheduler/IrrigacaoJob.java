package br.lks.aguanamedida.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class IrrigacaoJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // Lógica para ligar a irrigação pelo tempo especificado no agendamento
    }
}
