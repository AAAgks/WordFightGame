package com.xiaogan.domain.skill;

public class Skill {
    private String skillName;
    private String description;
    private int costMp;
    private double atkRate;
    private SkillType  type;
    private int hits = 1;



    public Skill() {
    }

    public Skill(String skillName, String description, int costMp, double atkRate, SkillType type ,int hits) {
        this.skillName = skillName;
        this.description = description;
        this.costMp = costMp;
        this.atkRate = atkRate;
        this.hits = hits;
        this.type = type;
    }



    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getDescription() {
        return description;
    }
    public int getHits() {
        return hits;
    }
    public int getCostMp() {
        return costMp;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setHits(int hits) {
        this.hits = hits;
    }
    public void setCostMp(int costMp) {
        this.costMp = costMp;
    }

    public double getAtkRate() {
        return atkRate;
    }

    public void setAtkRate(double atkRate) {
        this.atkRate = atkRate;
    }

    public SkillType getType() {
        return type;
    }

    public void setType(SkillType type) {
        this.type = type;
    }
}
