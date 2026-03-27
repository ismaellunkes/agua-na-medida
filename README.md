# Água na Medida

API backend para monitoramento de umidade do solo e controle básico de irrigação com Quarkus.

## Funcionalidades implementadas

- Registro de leitura de umidade.
- Consulta da última leitura.
- Consulta de histórico (últimas 100 leituras).
- Controle manual de irrigação (ligar/desligar/status).
- Agendamento de irrigação via Quartz (com validação de cron).

## Endpoints principais

### Umidade

- `GET /umidade` → retorna a última leitura registrada.
- `POST /umidade` → registra leitura nova.
  - Body JSON:
    ```json
    {
      "umidade": "42%"
    }
    ```
- `GET /umidade/historico` → retorna histórico de leituras.

### Controle de irrigação

- `GET /controle/status`
- `POST /controle/ligar`
- `POST /controle/desligar`
- `POST /controle/agendar`

Body exemplo para agendamento:

```json
{
  "minutos": "30",
  "hora": "18",
  "diaDoMes": "*",
  "mes": "*",
  "horario": "fim-de-tarde",
  "duracao": 60
}
```

## Rodando o projeto em desenvolvimento

```bash
./mvnw compile quarkus:dev
```

> Se o Maven Wrapper ainda não estiver configurado no repositório, use o Maven instalado localmente:
>
> ```bash
> mvn compile quarkus:dev
> ```

## Testes

```bash
mvn test
```

## Banco de dados

Configuração atual usando H2 em memória (`application.properties`), ideal para desenvolvimento e testes locais.


## Fluxo de contribuição

- Crie um branch para sua feature/correção.
- Execute os testes locais antes de abrir PR.
- Abra um Pull Request com contexto, impacto e passos de validação.
