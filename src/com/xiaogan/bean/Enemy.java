package com.xiaogan.bean;

import java.util.ArrayList;
import java.util.Random;

public class Enemy extends GameCharacter implements UseSkills{
    Random random = new Random();
    private int choice;
    private boolean defensiveStance=false;

    public Enemy(ArrayList<Enemy> enemyList, int choice) {
        this.choice = choice;
    }

    public Enemy(String name, int HP, int maxHP, int attackPower, int defensePower, int choice) {
        super(name, HP, maxHP, attackPower, defensePower);
        this.choice = choice;
    }

    public Enemy(String name, int HP, int maxHP, int attackPower, int defensePower,int choice, boolean defensiveStance) {
        super(name, HP, maxHP, attackPower, defensePower);
        this.choice = choice;
        this.defensiveStance = defensiveStance;
    }

    public Enemy(boolean defensiveStance) {
        this.defensiveStance = defensiveStance;
    }

    public boolean isDefensiveStance() {
        return defensiveStance;
    }

    public void setDefensiveStance(boolean defensiveStance) {
        this.defensiveStance = defensiveStance;
    }

    public int getChoice() {
        return choice;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }

    @Override
    public void useSkills(GameCharacter caster, GameCharacter target) {
        if(caster instanceof Enemy){
            Enemy enemy = (Enemy) caster;
            Player player = (Player) target;
            enemy.setDefensiveStance(false);
            int choice1=enemy.getChoice();
            double damage;
            int choice2 = random.nextInt(2);
            switch (choice1) {
                case 0:
                    switch (choice2) {
                        case 0:
                            damage = Math.max(1,enemy.getATK() - player.getDEF());
                            System.out.println("⚔\uFE0F"+enemy.getName() + " 使用了 " + "[普通攻击],对你造成了" + damage + " 点伤害");
                            player.setHP(player.getHP() - damage);
                            break;
                        case 1:
                            damage = Math.max(1,enemy.getATK() * 1.5 - player.getDEF());
                            System.out.println("⚔\uFE0F"+enemy.getName() + " 使用了 " + "[猛击],对你造成了" + damage + " 点伤害");
                            player.setHP(player.getHP() - damage);
                            break;
                    }
                    break;
                case 1:
                    switch (choice2) {
                        case 0:
                            damage = Math.max(1,enemy.getATK() - player.getDEF());
                            System.out.println("⚔\uFE0F"+enemy.getName() + " 使用了 " + "[普通攻击],对你造成了" + damage + " 点伤害");
                            player.setHP(player.getHP() - damage);
                            break;
                        case 1:
                            //攻击两次
                            damage = 2*(Math.max(1,enemy.getATK() * 0.5 - player.getDEF()));
                            System.out.println("⚔\uFE0F"+enemy.getName() + " 使用了 " + "[快速攻击],对你造成了" + damage + " 点伤害");
                            player.setHP(player.getHP() - damage);
                            break;
                    }
                    break;
                case 2:
                    switch (choice2) {
                        case 0:
                            damage = Math.max(1,enemy.getATK() - player.getDEF());
                            System.out.println("⚔\uFE0F"+enemy.getName() + " 使用了 " + "[普通攻击],对你造成了" + damage + " 点伤害");
                            player.setHP(player.getHP() - damage);
                            break;
                        case 1:
                            System.out.println("⚔\uFE0F"+enemy.getName() + " 使用了 " + "[防御姿态],你的伤害减少50%");
                            enemy.setDefensiveStance(true);
                            break;
                    }
                    break;
                case 3:
                    switch (choice2) {
                        case 0:
                            damage = Math.max(1,enemy.getATK() - player.getDEF());
                            System.out.println("⚔\uFE0F"+enemy.getName() + " 使用了 " + "[普通攻击],对你造成了" + damage + " 点伤害");
                            player.setHP(player.getHP() - damage);
                            break;
                        case 1:
                            damage = Math.max(1,enemy.getATK() * 1.8 - player.getDEF());
                            System.out.println("⚔\uFE0F"+enemy.getName() + " 使用了 " + "[火球术],对你造成了" + damage + " 点伤害");
                            player.setHP(player.getHP() - damage);
                            break;
                    }
                    break;
            }
        }
    }
}
