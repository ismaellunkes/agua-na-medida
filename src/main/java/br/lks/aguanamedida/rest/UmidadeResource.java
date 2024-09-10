package br.lks.aguanamedida.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/umidade")
public class UmidadeResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getUmidade() {
        return "{umidade}";
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public void postUmidade(@QueryParam("umidade") String umidade) {
        System.out.println(umidade);
    }

    @GET
    @Path("historico")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUmidadehistorico() {
        return "{historico}";
    }

}
