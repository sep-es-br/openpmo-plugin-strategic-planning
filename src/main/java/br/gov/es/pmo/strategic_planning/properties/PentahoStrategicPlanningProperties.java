package br.gov.es.pmo.strategic_planning.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "pentaho-strategic-planning")
public class PentahoStrategicPlanningProperties {
    private String baseUrl;
    private String userId;
    private String password;
    private String indicatorsPath;
    private String indicatorsDataAccessId; // dos indicadores

    private String targetsPath;
    private String targetsDataAccessId; // metas por missao

    private String missionsPath;
    private String missionsDataAccessId;  //missoes

    private String objectivesPath;
    private String objectivesDataAccessId;   //objetivos

    public String getObjectivesPath() {
        return objectivesPath;
    }

    public void setObjectivesPath(String objectivesPath) {
        this.objectivesPath = objectivesPath;
    }

    public String getObjectivesDataAccessId() {
        return objectivesDataAccessId;
    }

    public void setObjectivesDataAccessId(String objectivesDataAccessId) {
        this.objectivesDataAccessId = objectivesDataAccessId;
    }

    public String getMissionsPath() {
        return missionsPath;
    }

    public void setMissionsPath(String missionsPath) {
        this.missionsPath = missionsPath;
    }

    public String getMissionsDataAccessId() {
        return missionsDataAccessId;
    }

    public void setMissionsDataAccessId(String missionsDataAccessId) {
        this.missionsDataAccessId = missionsDataAccessId;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIndicatorsPath() {
        return indicatorsPath;
    }

    public void setIndicatorsPath(String indicatorsPath) {
        this.indicatorsPath = indicatorsPath;
    }

    public String getIndicatorsDataAccessId() {
        return indicatorsDataAccessId;
    }

    public void setIndicatorsDataAccessId(String indicatorsDataAccessId) {
        this.indicatorsDataAccessId = indicatorsDataAccessId;
    }

    public String getTargetsPath() {
        return targetsPath;
    }

    public void setTargetsPath(String targetsPath) {
        this.targetsPath = targetsPath;
    }

    public String getTargetsDataAccessId() {
        return targetsDataAccessId;
    }

    public void setTargetsDataAccessId(String targetsDataAccessId) {
        this.targetsDataAccessId = targetsDataAccessId;
    }
}
