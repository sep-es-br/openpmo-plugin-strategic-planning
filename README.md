# openpmo-plugin-strategic-planning

Plugin de planejamento estratégico do OpenPMO para integração com o Pentaho BI da SEP, no contexto do ES 500 Anos.

## Status do projeto

**Em desenvolvimento — estrutura inicial.**

O provider já declara a implementação de `IStrategicPlanningProvider` e utiliza os DTOs do projeto `openpmo-plugin-strategic-planning-interface`. As consultas ao BI ainda não foram implementadas: os quatro métodos lançam `UnsupportedOperationException` para sinalizar essa pendência. Não há retorno de dados fictícios.

## Contrato

| Método | Retorno | Dados previstos |
| --- | --- | --- |
| `getMissions()` | `List<MissionDto>` | Missões |
| `getObjectives()` | `List<StrategicObjectiveDto>` | Objetivos estratégicos |
| `getTargets()` | `List<TargetDto>` | Metas |
| `getIndicators()` | `List<IndicatorDto>` | Indicadores |

A implementação deverá executar as consultas no Pentaho e converter os dados recebidos para os DTOs definidos no contrato.

## Estrutura

Pacote principal: `br.gov.es.pmo.strategic_planning`.

| Componente | Responsabilidade |
| --- | --- |
| `StrategicPlanningAutoConfig` | Registrar os componentes e carregar as propriedades no Spring |
| `PentahoStrategicPlanningProperties` | Classe reservada para as propriedades de conexão e consulta; campos ainda pendentes |
| `PentahoStrategicPlanningProvider` | Implementar as operações definidas pelo contrato |
| `PentahoQueryResponse` | Representar `metadata` e `resultset` das respostas CDA |
| `PentahoStrategicPlanningProviderTest` | Arquivo de testes herdado da base, ainda pendente de adaptação |

A configuração automática está registrada em `META-INF/spring.factories`. O arquivo de propriedades é `strategic-planning.properties`, com o prefixo `pentaho-strategic-planning`.

## Configuração do BI

O arquivo de propriedades contém o endereço base `https://bi.sep.es.gov.br` e referências às variáveis `PENTAHO_BI_USER_ID` e `PENTAHO_BI_PASSWORD`, com alternativas em `pentahoBI.userId` e `pentahoBI.password`.

Esses valores ainda não são utilizados pelo provider. Os caminhos e identificadores de consulta presentes no arquivo são herdados do plugin de indicadores (ODS e desafios) e precisam ser substituídos pelas consultas de planejamento estratégico.

## Próximas etapas

- Confirmar os caminhos `.cda`, os `dataAccessId` e os parâmetros das consultas reais.
- Definir os campos de configuração em `PentahoStrategicPlanningProperties`.
- Implementar as chamadas HTTP e a autenticação exigida pelo BI.
- Mapear as respostas para os DTOs e tratar falhas nas consultas.
- Adaptar os testes ao contrato de planejamento estratégico e validar o build.

## Build local

O projeto utiliza Java 11 e inclui o Gradle Wrapper.

Com o projeto da interface em uma pasta irmã, o comando para compilar e executar os testes é:

```powershell
.\gradlew.bat build --include-build ..\openpmo-plugin-strategic-planning-interface
```

**Pendência atual:** os testes ainda importam classes de `indicator_interface` e chamam métodos de mapeamento que não existem no provider atual. Eles precisam ser adaptados para que o build completo possa passar. O comando acima não representa uma validação já concluída.

## Artefatos

| Projeto | Coordenadas configuradas |
| --- | --- |
| Contrato | `com.github.sep-es-br:openpmo-plugin-strategic-planning-interface:1.0.0` |
| Implementação | `com.github.sep-es-br:openpmo-plugin-strategic-planning:1.0.0` |

Sem o uso do projeto local da interface, a resolução dessa dependência via JitPack depende da publicação e disponibilidade da versão correspondente.
