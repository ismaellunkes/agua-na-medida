package br.lks.aguanamedida.repository;

import br.lks.aguanamedida.entity.Leitura;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class LeituraRepository implements PanacheRepository<Leitura> {

    public Optional<Leitura> buscarUltimaLeitura() {
        return find("order by dataHoraLeitura desc").firstResultOptional();
    }

    public List<Leitura> buscarHistorico(int limite) {
        return find("order by dataHoraLeitura desc").page(0, limite).list();
    }
}
