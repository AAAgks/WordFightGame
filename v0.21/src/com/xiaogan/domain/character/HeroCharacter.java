package com.xiaogan.domain.character;

import com.xiaogan.domain.item.Item;
import com.xiaogan.domain.skill.Skill;

import java.util.ArrayList;

public class HeroCharacter extends Character {
    private ArrayList<Skill> skills;
    private ArrayList<Item> bag;
    private int mp;
    private int maxMp;

    public HeroCharacter() {
        super();
        skills = new ArrayList<>();
        bag = new ArrayList<>();
    }

    public HeroCharacter(String name, int HP,int MP, int attack, int defense) {
        super(name, HP, attack, defense);
        this.mp = MP;
        this.maxMp = MP;
        skills = new ArrayList<>();
        bag = new ArrayList<>();
    }


    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public void addSkill(Skill skill) {
        skills.add(skill);
    }
    public void setSkills(ArrayList<Skill> skills) {
        this.skills = skills;
    }

    public ArrayList<Item> getBag() {
        return bag;
    }
    public void setBag(ArrayList<Item> bag) {
        this.bag = bag;
    }
    public void addItem(Item item) { bag.add(item); }
    public void removeItem(int index) {
        if (index >= 0 && index < bag.size()) bag.remove(index);
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = Math.max(0, Math.min(mp, maxMp));
    }

    public int getMaxMp() {
        return maxMp;
    }

    public void setMaxMp(int maxMp) {
        this.maxMp = maxMp;
    }

    public void addHp(int num) {
        super.setHp(Math.min(super.getHp() + num, super.getMaxHp())); // 不超过上限
    }
    public void addMp(int num) {
        mp = Math.min(mp + num, maxMp); // 不超过上限
    }
    public void addAtk(int num) { super.setAttack(super.getAttack() + num); }
    public void addDef(int num) { super.setDefense(super.getDefense() + num); }

    private boolean defending = false;
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


    public String showSkills(){
        StringBuilder sb=new StringBuilder();
        for (int i = 0; i < skills.size(); i++) {
            sb.append(skills.get(i).getSkillName());
            if(i!=skills.size()-1){
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public void consumeMp(int MP){
        mp -= MP;
        if(mp<0){
            mp = 0;
        }
    }

    public void recoverMp(int amount){
        setMp(mp + amount);
    }

    public String show(){
        return super.getName()+"[生命值："+super.getHp()+"/"+super.getMaxHp()+
                "，蓝量："+mp+"/"+maxMp+
                "，攻击力："+super.getAttack()+
                "，防御力："+super.getDefense()+
                "]";
    }
}
