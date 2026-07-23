package com.xiaogan.bean;

public class Skill {
    private String Skill_Name;
    private String Cost;
    private String Effect;
    private String Description;

    public Skill() {
    }

    public Skill(String skill_Name, String cost, String effect, String description) {
        Skill_Name = skill_Name;
        Cost = cost;
        Effect = effect;
        Description = description;
    }

    public String getSkill_Name() {
        return Skill_Name;
    }

    public void setSkill_Name(String skill_Name) {
        Skill_Name = skill_Name;
    }

    public String getCost() {
        return Cost;
    }

    public void setCost(String cost) {
        Cost = cost;
    }

    public String getEffect() {
        return Effect;
    }

    public void setEffect(String effect) {
        Effect = effect;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
