package br.gov.es.pmo.strategic_planning.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.gov.es.pmo.strategic_planning.model.PentahoQueryResponse;
import br.gov.es.pmo.strategic_planning_interface.model.StrategicObjectiveDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;

class ObjectiveMappingTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void mapsObjectivesUsingMetadataOrder() throws Exception {
        String json = "{\"metadata\":["
                + "{\"colName\":\"titulo\",\"colIndex\":0},"
                + "{\"colName\":\"codigo\",\"colIndex\":1},"
                + "{\"colName\":\"id\",\"colIndex\":2}],"
                + "\"resultset\":[[\"Objetivo de inovação\",\"M1.O1\",1]]}";

        List<StrategicObjectiveDto> objectives = PentahoStrategicPlanningProvider.mapObjectives(
                mapper.readValue(json, PentahoQueryResponse.class));

        assertEquals(1, objectives.size());
        assertEquals(1, objectives.get(0).getId());
        assertEquals("M1.O1", objectives.get(0).getCode());
        assertEquals("Objetivo de inovação", objectives.get(0).getTitle());
    }

    @Test
    void rejectsMissingObjectiveColumn() throws Exception {
        PentahoQueryResponse response = mapper.readValue(
                "{\"metadata\":[{\"colName\":\"id\",\"colIndex\":0}],\"resultset\":[[1]]}",
                PentahoQueryResponse.class);

        assertThrows(IllegalStateException.class,
                () -> PentahoStrategicPlanningProvider.mapObjectives(response));
    }
}
