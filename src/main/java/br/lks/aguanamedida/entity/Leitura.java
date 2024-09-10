package br.lks.aguanamedida.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity(name = "leitura")
public class Leitura {
    @Id
    private Long id;
    @Column(name = "data_hora")
    private LocalDateTime dataHoraLeitura;
    @Column(name = "leitura")
    private String leitura;
}