package br.gov.es.pmo.strategic_planning.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import br.gov.es.pmo.strategic_planning.config.StrategicPlanningAutoConfig;
import br.gov.es.pmo.strategic_planning_interface.model.IStrategicPlanningProvider;
import br.gov.es.pmo.strategic_planning_interface.model.IndicatorDto;
import br.gov.es.pmo.strategic_planning_interface.model.MissionDto;
import br.gov.es.pmo.strategic_planning_interface.model.StrategicObjectiveDto;
import br.gov.es.pmo.strategic_planning_interface.model.TargetDto;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = StrategicPlanningAutoConfig.class)
@EnabledIfEnvironmentVariable(
    named = "RUN_PENTAHO_INTEGRATION",
    matches = "true"
)
class PentahoStrategicPlanningProviderIntegrationTest {

    @Autowired
    private IStrategicPlanningProvider provider;

    @Test
    void consultaObjetivosNoBi() {
        List<StrategicObjectiveDto> objectives = provider.getObjectives();

        assertNotNull(objectives);
        assertFalse(objectives.isEmpty(), "O BI retornou uma lista de objetivos vazia");

        System.out.println("Quantidade de objetivos: " + objectives.size());
        for (StrategicObjectiveDto objective : objectives) {
            System.out.println(objective.getId() + " | " + objective.getCode()
                    + " | " + objective.getTitle());
        }
    }

    @Test
    void consultaMissoesNoBi() {
        List<MissionDto> missions = provider.getMissions();

        assertNotNull(missions);
        assertFalse(missions.isEmpty(), "O BI retornou uma lista de missoes vazia");

        System.out.println("Quantidade de missoes: " + missions.size());
        for (MissionDto mission : missions) {
            System.out.println(mission.getId() + " | " + mission.getCode()
                    + " | " + mission.getTitle() + " | " + mission.getDescription());
        }
    }

    @Test
    void consultaMetasNoBi() {
        List<TargetDto> targets = provider.getTargets();

        assertNotNull(targets);
        assertFalse(targets.isEmpty(), "O BI retornou uma lista de metas vazia");

        System.out.println("Quantidade de metas: " + targets.size());
        for (TargetDto target : targets) {
            System.out.println(target.getId() + " | " + target.getCode() + " | " + target.getTitle());
        }
    }

    @Test
    void consultaIndicadoresNoBi() {
        List<IndicatorDto> indicators = provider.getIndicators();

        assertNotNull(indicators);
        assertFalse(indicators.isEmpty(), "O BI retornou uma lista vazia");

        IndicatorDto first = indicators.get(0);

        System.out.println("Quantidade de indicadores: " + indicators.size());
        System.out.println("Primeiro ID: " + first.getId());
        System.out.println("Primeiro código: " + first.getCode());
        System.out.println("Primeiro título: " + first.getTitle());
    }
}
