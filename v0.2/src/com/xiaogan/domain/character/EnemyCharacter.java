package com.xiaogan.domain.character;

public class EnemyCharacter extends Character {
    private String skillName;
    private double skillRate;
    private int skillHits;
    private boolean isDefenseSkill;
    private boolean defending;

    public EnemyCharacter() {
        super();
        this.defending = false;
    }

    public EnemyCharacter(String name, int HP, int attack, int defense,
                          String skillName, double skillRate, int skillHits, boolean isDefenseSkill) {
        super(name, HP, attack, defense);
        this.skillName = skillName;
        this.skillRate = skillRate;
        this.skillHits = skillHits;
        this.isDefenseSkill = isDefenseSkill;
        this.defending = false;
    }

    public String getSkillName() { return skillName; }
    public double getSkillRate() { return skillRate; }
    public int getSkillHits() { return skillHits; }
    public boolean isDefenseSkill() { return isDefenseSkill; }

    public void setSkillName(String skillName) { this.skillName = skillName; }
    public void setSkillRate(double skillRate) { this.skillRate = skillRate; }
    public void setSkillHits(int skillHits) { this.skillHits = skillHits; }
    public void setDefenseSkill(boolean defenseSkill) { isDefenseSkill = defenseSkill; }

    public boolean isDefending() { return defending; }
    public void setDefending(boolean defending) { this.defending = defending; }

    @Override
    public void takeDamage(int damage) {
        if (isDefending()) {
            damage = Math.max(damage / 2, 1);
            System.out.println(getName() + " 身上拥有防御buff，受到的伤害减半！");
            System.out.println(getName() + " 受到 " + damage + " 点伤害！");
        }
        super.takeDamage(damage);
    }

    public String show() {
        return super.getName() + "[当前生命值：" + super.getHp()
                + "，攻击力：" + super.getAttack()
                + "，防御力：" + super.getDefense() + "]";
    }
}