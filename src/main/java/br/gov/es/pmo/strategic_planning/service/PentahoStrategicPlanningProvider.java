package br.gov.es.pmo.strategic_planning.service;

import br.gov.es.pmo.strategic_planning.model.PentahoQueryResponse;
import br.gov.es.pmo.strategic_planning.properties.PentahoStrategicPlanningProperties;
import br.gov.es.pmo.strategic_planning_interface.model.IStrategicPlanningProvider;
import br.gov.es.pmo.strategic_planning_interface.model.IndicatorDto;
import br.gov.es.pmo.strategic_planning_interface.model.MissionDto;
import br.gov.es.pmo.strategic_planning_interface.model.StrategicObjectiveDto;
import br.gov.es.pmo.strategic_planning_interface.model.TargetDto;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class PentahoStrategicPlanningProvider implements IStrategicPlanningProvider {

    private static final String CDA_ENDPOINT = "/pentaho/plugin/cda/api/doQuery";

    private final PentahoStrategicPlanningProperties properties;
    private final Environment environment;
    private final WebClient webClient;

    public PentahoStrategicPlanningProvider(
            PentahoStrategicPlanningProperties properties, Environment environment) {
        this.properties = properties;
        this.environment = environment;
        this.webClient = WebClient.builder().baseUrl(properties.getBaseUrl()).build();
    }

    @Override
    public List<MissionDto> getMissions() {
        return mapMissions(query(
                properties.getMissionsPath(),
                properties.getMissionsDataAccessId(),
                Map.of()));
    }

    @Override
    public List<StrategicObjectiveDto> getObjectives() {
        return mapObjectives(query(
                properties.getObjectivesPath(),
                properties.getObjectivesDataAccessId(),
                Map.of("paramp_missao", -1)));
    }

    @Override
    public List<TargetDto> getTargets() {
        return mapTargets(query(
                properties.getTargetsPath(),
                properties.getTargetsDataAccessId(),
                Map.of("paramp_missao", -1)));
    }

    @Override
    public List<IndicatorDto> getIndicators() {
        return mapIndicators(query(
                properties.getIndicatorsPath(),
                properties.getIndicatorsDataAccessId(),
                Map.of(
                        "paramp_missao", -1,
                        "paramp_meta", -1,
                        "paramp_objetivo", -1,
                        "paramp_buscaTxt", "")));
    }

    private PentahoQueryResponse query(
            String path,
            String dataAccessId,
            Map<String, Object> parameters) {

        if (!StringUtils.hasText(path) || !StringUtils.hasText(dataAccessId)) {
            throw new IllegalStateException(
                    "Configure o caminho e o dataAccessId da consulta");
        }

        String userId = StringUtils.hasText(properties.getUserId())
                ? properties.getUserId()
                : environment.getProperty("pentahoBI.userId");

        String password = StringUtils.hasText(properties.getPassword())
                ? properties.getPassword()
                : environment.getProperty("pentahoBI.password");

        if (StringUtils.hasText(userId) != StringUtils.hasText(password)) {
            throw new IllegalStateException(
                    "Configure usuario e senha do Pentaho juntos");
        }

        PentahoQueryResponse response = webClient.get()
                .uri(builder -> {
                    builder.path(CDA_ENDPOINT)
                            .queryParam("path", path)
                            .queryParam("dataAccessId", dataAccessId);

                    parameters.forEach((name, value) -> builder.queryParam(name, value));

                    return builder.build();
                })
                .headers(headers -> setBasicAuthIfConfigured(headers, userId, password))
                .retrieve()
                .bodyToMono(PentahoQueryResponse.class)
                .block(Duration.ofSeconds(30));

        if (response == null) {
            throw new IllegalStateException(
                    "Resposta vazia do Pentaho para " + dataAccessId);
        }

        return response;
    }

    static List<MissionDto> mapMissions(PentahoQueryResponse response) {
        int id = columnIndex(response, "id");
        int code = columnIndex(response, "codigo");
        int title = columnIndex(response, "titulo");
        int description = columnIndex(response, "descricao");

        return response.getResultset().stream().map(row -> {
            MissionDto mission = new MissionDto();
            mission.setId(integer(row, id));
            mission.setCode(string(row, code));
            mission.setTitle(string(row, title));
            mission.setDescription(string(row, description));
            return mission;
        }).collect(Collectors.toList());
    }

    static List<StrategicObjectiveDto> mapObjectives(PentahoQueryResponse response) {
        int id = columnIndex(response, "id");
        int code = columnIndex(response, "codigo");
        int title = columnIndex(response, "titulo");

        return response.getResultset().stream().map(row -> {
            StrategicObjectiveDto objective = new StrategicObjectiveDto();
            objective.setId(integer(row, id));
            objective.setCode(string(row, code));
            objective.setTitle(string(row, title));
            return objective;
        }).collect(Collectors.toList());
    }

    static List<TargetDto> mapTargets(PentahoQueryResponse response) {
        int id = columnIndex(response, "id");
        int code = columnIndex(response, "codigo");
        int title = columnIndex(response, "titulo");

        return response.getResultset().stream().map(row -> {
            TargetDto target = new TargetDto();
            target.setId(integer(row, id));
            target.setCode(string(row, code));
            target.setTitle(string(row, title));
            return target;
        }).collect(Collectors.toList());
    }

    static List<IndicatorDto> mapIndicators(PentahoQueryResponse response) {
        int id = columnIndex(response, "id");
        int code = columnIndex(response, "codigo");
        int objectiveCode = columnIndex(response, "codObjetivo");
        int targetCode = columnIndex(response, "codMeta");
        int appId = columnIndex(response, "appId");
        int title = columnIndex(response, "titulo");
        int description = columnIndex(response, "descricao");
        int targetValue = columnIndex(response, "strValorMeta");
        int unit = columnIndex(response, "unidade");
        int polarity = columnIndex(response, "polaridade");
        int source = columnIndex(response, "fonte");

        return response.getResultset().stream().map(row -> {
            IndicatorDto indicator = new IndicatorDto();
            indicator.setId(integer(row, id));
            indicator.setCode(string(row, code));
            indicator.setStrategicObjectiveCode(string(row, objectiveCode));
            indicator.setTargetCode(string(row, targetCode));
            indicator.setAppId(integer(row, appId));
            indicator.setTitle(string(row, title));
            indicator.setDescription(string(row, description));
            indicator.setTargetValue(string(row, targetValue));
            indicator.setUnit(string(row, unit));
            indicator.setPolarity(string(row, polarity));
            indicator.setSource(string(row, source));
            return indicator;
        }).collect(Collectors.toList());
    }

    private static int columnIndex(PentahoQueryResponse response, String name) {
        return response.getMetadata().stream()
                .filter(column -> column != null && name.equalsIgnoreCase(column.getColName()))
                .map(PentahoQueryResponse.Column::getColIndex)
                .filter(index -> index != null && index >= 0)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Coluna ausente no retorno do Pentaho: " + name));
    }

    private static Integer integer(List<Object> row, int index) {
        Object value = cell(row, index);
        if (value == null) {
            return null;
        }
        return value instanceof Number ? ((Number) value).intValue()
                : Integer.valueOf(String.valueOf(value));
    }

    private static String string(List<Object> row, int index) {
        Object value = cell(row, index);
        return value == null ? null : String.valueOf(value);
    }

    private static Object cell(List<Object> row, int index) {
        if (row == null || index < 0 || index >= row.size()) {
            throw new IllegalStateException("Linha incompleta no retorno do Pentaho");
        }
        return row.get(index);
    }

    private static void setBasicAuthIfConfigured(HttpHeaders headers, String userId, String password) {
        if (StringUtils.hasText(userId)) {
            headers.setBasicAuth(userId, password);
        }
    }

}
