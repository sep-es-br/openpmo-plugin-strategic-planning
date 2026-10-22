package br.gov.es.pmo.strategic_planning.service;

import br.gov.es.pmo.strategic_planning_interface.model.IStrategicPlanningProvider;
import br.gov.es.pmo.strategic_planning_interface.model.IndicatorDto;
import br.gov.es.pmo.strategic_planning_interface.model.MissionDto;
import br.gov.es.pmo.strategic_planning_interface.model.StrategicObjectiveDto;
import br.gov.es.pmo.strategic_planning_interface.model.TargetDto;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PentahoStrategicPlanningProvider implements IStrategicPlanningProvider {

    @Override
    public List<MissionDto> getMissions() {
        // TODO: consultar o BI e mapear o retorno para MissionDto.
        throw new UnsupportedOperationException("Consulta de missoes no BI ainda nao implementada.");
    }

    @Override
    public List<StrategicObjectiveDto> getObjectives() {
        // TODO: consultar o BI e mapear o retorno para StrategicObjectiveDto.
        throw new UnsupportedOperationException("Consulta de objetivos no BI ainda nao implementada.");
    }

    @Override
    public List<TargetDto> getTargets() {
        // TODO: consultar o BI e mapear o retorno para TargetDto.
        throw new UnsupportedOperationException("Consulta de metas no BI ainda nao implementada.");
    }

    @Override
    public List<IndicatorDto> getIndicators() {
        // TODO: consultar o BI e mapear o retorno para IndicatorDto.
        throw new UnsupportedOperationException("Consulta de indicadores no BI ainda nao implementada.");
    }
}
