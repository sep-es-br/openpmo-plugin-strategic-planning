package br.gov.es.pmo.strategic_planning.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.gov.es.pmo.strategic_planning.model.PentahoQueryResponse;
import br.gov.es.pmo.strategic_planning_interface.model.TargetDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.Test;

class TargetMappingTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void mapsTargetsUsingMetadataOrder() throws Exception {
        String json = "{\"metadata\":["
                + "{\"colName\":\"titulo\",\"colIndex\":0},"
                + "{\"colName\":\"id\",\"colIndex\":1},"
                + "{\"colName\":\"codigo\",\"colIndex\":2}],"
                + "\"resultset\":[[\"Reduzir a taxa de pobreza para menos de 10%\",2,\"M1.GM2\"]]}";

        List<TargetDto> targets = PentahoStrategicPlanningProvider.mapTargets(
                mapper.readValue(json, PentahoQueryResponse.class));

        assertEquals(1, targets.size());
        assertEquals(2, targets.get(0).getId());
        assertEquals("M1.GM2", targets.get(0).getCode());
        assertEquals("Reduzir a taxa de pobreza para menos de 10%", targets.get(0).getTitle());
    }

    @Test
    void rejectsMissingTargetColumn() throws Exception {
        PentahoQueryResponse response = mapper.readValue(
                "{\"metadata\":[{\"colName\":\"id\",\"colIndex\":0}],\"resultset\":[[1]]}",
                PentahoQueryResponse.class);

        assertThrows(IllegalStateException.class,
                () -> PentahoStrategicPlanningProvider.mapTargets(response));
    }
}
