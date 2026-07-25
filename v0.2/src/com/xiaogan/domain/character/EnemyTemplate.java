package com.xiaogan.domain.character;

public class EnemyTemplate {
    private final String name;
    private final int hp;
    private final int attack;
    private final int defense;
    private final String skillName;       // 显示用
    private final double skillRate;       // 攻击倍率
    private final int skillHits;          // 攻击次数（1=单次，2=两次）
    private final boolean isDefenseSkill; // 是否是防御技

    public EnemyTemplate(String name, int hp, int attack, int defense, String skillName, double skillRate, int skillHits, boolean isDefenseSkill) {
        this.name = name;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.skillName = skillName;
        this.skillRate = skillRate;
        this.skillHits = skillHits;
        this.isDefenseSkill = isDefenseSkill;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public String getSkillName() {
        return skillName;
    }

    public double getSkillRate() {
        return skillRate;
    }

    public int getSkillHits() {
        return skillHits;
    }

    public boolean isDefenseSkill() {
        return isDefenseSkill;
    }

    public EnemyCharacter create() {
        return new EnemyCharacter(name, hp, attack, defense, skillName, skillRate, skillHits, isDefenseSkill);
    }
}