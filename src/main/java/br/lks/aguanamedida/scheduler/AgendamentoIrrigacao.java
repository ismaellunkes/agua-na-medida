package br.lks.aguanamedida.scheduler;

import lombok.Data;
import org.quartz.CronExpression;

@Data
public class AgendamentoIrrigacao {
    private String minutos = "0";
    private String hora = "0";
    private String diaDoMes = "*";
    private String mes = "*";
    private String horario = "padrao";
    private int duracao = 60;

    public String getCronExpression() {
        return String.format("0 %s %s %s %s ?",
                valorOuPadrao(minutos, "0"),
                valorOuPadrao(hora, "0"),
                valorOuPadrao(diaDoMes, "*"),
                valorOuPadrao(mes, "*"));
    }

    public boolean cronValido() {
        return CronExpression.isValidExpression(getCronExpression());
    }

    private String valorOuPadrao(String valor, String padrao) {
        return valor == null || valor.isBlank() ? padrao : valor;
    }
}
