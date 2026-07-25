package com.xiaogan.bean;

public class GameCharacter {
    private String name;
    private double HP;
    private double maxHP;
    private int ATK;
    private int DEF;

    public GameCharacter() {
    }

    public GameCharacter(String name, int HP, int maxHP, int attackPower, int defensePower) {
        this.name = name;
        this.HP = HP;
        this.maxHP = maxHP;
        this.ATK = attackPower;
        this.DEF = defensePower;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHP() {
        return HP;
    }

    public void setHP(double HP) {
        this.HP = HP;
    }

    public double getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(double maxHP) {
        this.maxHP = maxHP;
    }

    public int getATK() {
        return ATK;
    }

    public void setATK(int ATK) {
        this.ATK = ATK;
    }

    public int getDEF() {
        return DEF;
    }

    public void setDEF(int DEF) {
        this.DEF = DEF;
    }


}
