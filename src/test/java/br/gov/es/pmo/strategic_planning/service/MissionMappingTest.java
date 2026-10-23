package br.gov.es.pmo.strategic_planning.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.gov.es.pmo.strategic_planning.model.PentahoQueryResponse;
import br.gov.es.pmo.strategic_planning_interface.model.MissionDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;

class MissionMappingTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void mapsMissionsByMetadataAndPreservesNullDescription() throws Exception {
        String json = "{\"metadata\":["
                + "{\"colName\":\"descricao\",\"colIndex\":0},"
                + "{\"colName\":\"titulo\",\"colIndex\":1},"
                + "{\"colName\":\"codigo\",\"colIndex\":2},"
                + "{\"colName\":\"id\",\"colIndex\":3}],"
                + "\"resultset\":[[\"Descrição de exemplo\",\"Missão de exemplo\",\"M1\",1],"
                + "[null,\"Outra missão\",\"M2\",2]]}";

        List<MissionDto> missions = PentahoStrategicPlanningProvider.mapMissions(
                mapper.readValue(json, PentahoQueryResponse.class));

        assertEquals(2, missions.size());
        assertEquals(1, missions.get(0).getId());
        assertEquals("M1", missions.get(0).getCode());
        assertEquals("Missão de exemplo", missions.get(0).getTitle());
        assertEquals("Descrição de exemplo", missions.get(0).getDescription());
        assertNull(missions.get(1).getDescription());
    }

    @Test
    void rejectsMissingMissionColumn() throws Exception {
        PentahoQueryResponse response = mapper.readValue(
                "{\"metadata\":[{\"colName\":\"id\",\"colIndex\":0}],\"resultset\":[[1]]}",
                PentahoQueryResponse.class);

        assertThrows(IllegalStateException.class,
                () -> PentahoStrategicPlanningProvider.mapMissions(response));
    }
}
