package br.lks.aguanamedida.scheduler;

import lombok.Data;

@Data
public class AgendamentoIrrigacao {
    private String minutos;
    private String hora;
    private String diaDoMes;
    private String mes;
    private String horario;
    private int duracao = 60; // Valor padrão de 60 segundos

    // Getters e setters

    public String getCronExpression() {
        // Converter o horário para uma expressão cron válida para o Quartz

        // Exemplo: "18:30" -> "0 30 18 * * ?"

        return "0 30 18 * * ?";
    }

}
