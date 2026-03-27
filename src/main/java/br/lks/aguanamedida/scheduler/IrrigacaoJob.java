package br.lks.aguanamedida.scheduler;

import org.jboss.logging.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class IrrigacaoJob implements Job {

    private static final Logger LOG = Logger.getLogger(IrrigacaoJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOG.info("Execução do job de irrigação iniciada");
    }
}
