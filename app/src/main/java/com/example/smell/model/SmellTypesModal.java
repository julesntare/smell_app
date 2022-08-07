package com.example.smell.model;

public class SmellTypesModal {
    int id;
    String smellType, smellDescriptions;
    boolean isGoodOrBad;

    public SmellTypesModal(String smellType, String smellDescriptions, int isGoodOrBad, int id) {
        this.id = id + 1;
        this.smellType = smellType;
        this.smellDescriptions = smellDescriptions;
        this.isGoodOrBad = isGoodOrBad == 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSmellType() {
        return smellType;
    }

    public void setSmellType(String smellType) {
        this.smellType = smellType;
    }

    public String getSmellDescriptions() {
        return smellDescriptions;
    }

    public void setSmellDescriptions(String smellDescriptions) {
        this.smellDescriptions = smellDescriptions;
    }

    public boolean getSense() {
        return isGoodOrBad;
    }

    public void setIsGoodOrBad(boolean isGoodOrBad) {
        this.isGoodOrBad = isGoodOrBad;
    }
}
