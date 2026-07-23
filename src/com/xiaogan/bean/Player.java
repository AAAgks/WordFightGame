package com.xiaogan.bean;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Player extends GameCharacter implements UseSkills{
    Scanner scanner = new Scanner(System.in);
    Random random = new Random();
    private ArrayList<Skill> skills = new ArrayList<Skill>();

    public Player() {
        initSkill();
    }


    public Player(String name, int HP, int maxHP, int attackPower, int defensePower) {
        super(name, HP, maxHP, attackPower, defensePower);
        initSkill();
    }


    public Player(String name, int HP, int maxHP, int attackPower, int defensePower, ArrayList<Skill> skills) {
        super(name, HP, maxHP, attackPower, defensePower);
        initSkill();
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Skill> skills) {
        this.skills = skills;
    }

    public void initSkill() {
        skills.add(new Skill("普通攻击", "无", "造成基础伤害", "标准攻击方式"));
        skills.add(new Skill("强力一击", "10 HP", "造成180%攻击伤害", "高伤害但消耗生命"));
        skills.add(new Skill("生命汲取", "10HP", "恢复0-20点生命值", "风险回报型恢复技能"));
    }

    @Override
    public void useSkills(GameCharacter caster, GameCharacter target) {

        if(caster instanceof Player){
            Player player = (Player) caster;
            Enemy enemy = (Enemy) target;
            int skillChoice = scanner.nextInt();
            switch (skillChoice) {
                case 1:{
                    System.out.println(player.getName() + " 使用了 [" + player.getSkills().get(0).getSkill_Name()+"]");
                    double damage = Math.max(1,player.getATK() - enemy.getDEF());
                    if(enemy.isDefensiveStance()) {

                        damage = Math.max(1, damage * 0.5);
                    }
                    System.out.println("造成了 " + damage + " 点伤害");
                    enemy.setHP(enemy.getHP() - damage);
                    break;
                }
                case 2:{
                    System.out.println("\uD83D\uDCA5消耗了10HP,你对" + enemy.getName() + " 使用了 [" + player.getSkills().get(1).getSkill_Name()+"]");
                    player.setHP(player.getHP() - 10);
                    double damage = Math.max(1,player.getATK() * 1.8 - enemy.getDEF());
                    if(enemy.isDefensiveStance()) damage = Math.max(01, damage * 0.5);
                    System.out.println("造成了 " + damage + " 点伤害");
                    enemy.setHP(enemy.getHP() - damage);
                    break;
                }
                case 3:{
                    System.out.println("\uD83D\uDCA5消耗了10HP,你使用了 [" + player.getSkills().get(2).getSkill_Name()+"]");
                    player.setHP(player.getHP() - 10);
                    int healing = random.nextInt(21);
                    player.setHP(player.getHP() + healing);
                    System.out.println("恢复了 " + healing + " 点生命值");
                    break;
                }
                default:{
                    System.out.println("无效的技能选择");
                    break;
                }
            }
        }
    }
}