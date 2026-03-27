package br.lks.aguanamedida.rest;

import br.lks.aguanamedida.entity.Leitura;
import br.lks.aguanamedida.repository.LeituraRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Path("/umidade")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UmidadeResource {

    @Inject
    LeituraRepository leituraRepository;

    @GET
    public Response getUmidade() {
        return leituraRepository.buscarUltimaLeitura()
                .map(leitura -> Response.ok(leitura).build())
                .orElse(Response.ok(Map.of("mensagem", "Sem leituras registradas")).build());
    }

    @POST
    @Transactional
    public Response postUmidade(RegistroUmidadeRequest request) {
        if (request == null || request.umidade() == null || request.umidade().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("erro", "Campo 'umidade' é obrigatório"))
                    .build();
        }

        Leitura leitura = new Leitura();
        leitura.setLeitura(request.umidade());
        leitura.setDataHoraLeitura(LocalDateTime.now());
        leituraRepository.persist(leitura);

        return Response.status(Response.Status.CREATED).entity(leitura).build();
    }

    @GET
    @Path("historico")
    public List<Leitura> getUmidadehistorico() {
        return leituraRepository.buscarHistorico(100);
    }

    public record RegistroUmidadeRequest(String umidade) {
    }
}
