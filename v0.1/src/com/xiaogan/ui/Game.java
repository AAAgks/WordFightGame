package com.xiaogan.ui;

import com.xiaogan.bean.Enemy;
import com.xiaogan.bean.GameCharacter;
import com.xiaogan.bean.Player;
import com.xiaogan.bean.UseSkills;

import java.util.Random;
import java.util.Scanner;

public class Game {
    static Scanner scanner = new Scanner(System.in);
    static Random r = new Random();
    private Game() {
    }

    public static void startGame(String username){
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("        🎮 欢迎来到文字格斗游戏 🎮   ");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("创建你的角色:");
        Player player = Game.createPlayer(username);
        new Game().fightBegin(player, null);

    }

    public static Player createPlayer(String username){
        int hpPoints = 0, atkPoints = 0, defPoints = 0;
        int hp=100,maxHP=100,attack=10,defense=0;
        int attributePoints=20;

        System.out.println("请分配你的属性点");
        while(true){
            System.out.println("你有 " + attributePoints + " 点去分配");
            System.out.println("1.生命值 (每点+10 HP)");
            System.out.println("2.攻击力 (每点+2 ATK)");
            System.out.println("3.防御力 (每点+1 DEF)");
            System.out.println("分配生命值点数");
            hpPoints = scanner.nextInt();
            System.out.println("分配攻击力点数");
            atkPoints = scanner.nextInt();
            System.out.println("分配防御力点数");
            defPoints = scanner.nextInt();
            if (hpPoints + atkPoints + defPoints == 20) break;
            System.out.println("点数分配失败，请重新输入：");
        }
        hp = hpPoints * 10;
        maxHP = hpPoints * 10;
        attack = atkPoints * 2;
        defense = defPoints * 1;
        System.out.println("角色创建完成:");
        Player player = new Player(username, hp, maxHP, attack, defense);
        System.out.println("初始属性: " + player.getName() + " [HP: " + player.getHP() + "/" + player.getMaxHP() + ", ATK: " + player.getATK() + ", DEF: " + player.getDEF() + "]");
        for (int i = 0; i < player.getSkills().size(); i++) {
            if(i > 0) System.out.print(", ");
            System.out.print("拥有技能: " + player.getSkills().get(i).getSkill_Name());
        }
        System.out.println("\n");
        return player;
    }

    public void fightBegin(Player player,Enemy enemy){

        int passCount=0;
        int roundCount=0;
        while(true){
            if(player.getHP() <= 0){
                player = Game.createPlayer(player.getName());
            }
            int index = r.nextInt(4)+1;
            switch(index){
                case 1: {
                    enemy = new Enemy("初级战士", 80, 80, 15, 10, 0);
                    break;
                }
                case 2: {
                    enemy = new Enemy("敏捷刺客", 60, 60, 20, 5, 1);
                    break;
                }
                case 3: {
                    enemy = new Enemy("重装坦克", 120, 120, 10, 20, 2);
                    break;
                }
                case 4: {
                    enemy = new Enemy("神秘法师", 70, 70, 25, 8, 3);
                    break;
                }
            }
            enemy.setMaxHP(enemy.getMaxHP()+passCount*10);
            enemy.setHP(enemy.getMaxHP());
            enemy.setATK(enemy.getATK()+passCount*3);
            enemy.setDEF(enemy.getDEF()+passCount*2);
            System.out.println("敌方属性: " + enemy.getName() + " [HP: " + enemy.getHP() + "/" + enemy.getMaxHP() + ", ATK: " + enemy.getATK() + ", DEF: " + enemy.getDEF() + "]");
            System.out.println("————————————————————————————————————————————————————————");
            System.out.println("第" + (passCount+1) + "轮战斗开始!对手:" + enemy.getName());
            while(true) {
                System.out.println("————————————————————————————————————————————————————————");
                System.out.println("第" + (roundCount+1) + "回合");
                System.out.println(player.getName() + ": [" + ShowHp.showHp(player) + "] " + player.getHP() + "/" + player.getMaxHP() + " HP");
                System.out.println(enemy.getName() + ": [" + ShowHp.showHp(enemy) + "] " + enemy.getHP() + "/" + enemy.getMaxHP() + " HP");
                System.out.println("请选择你的行动:");
                System.out.println("1. 普通攻击");
                System.out.println("2. 强力一击");
                System.out.println("3. 生命汲取");
                player.useSkills(player, enemy);
                if (enemy.getHP() <= 0){
                    System.out.println("————————————————————————战斗胜利—————————————————————————");
                    int healing = r.nextInt(20,41);
                    player.setHP(player.getHP() + healing);
                    System.out.println("\uD83C\uDF89你击败了" + enemy.getName() + "!" + "并恢复了\uD83D\uDC9A" + healing + " HP"    );
                    roundCount=0;
                    break;
                }
                enemy.useSkills(enemy, player);
                if (player.getHP() <= 0) {
                    System.out.println("————————————————————————战斗失败—————————————————————————");
                    System.out.println("你被" + enemy.getName() + "击败了!");
                    passCount=0;
                    roundCount=0;
                    break;
                }
                roundCount++;
            }
            passCount++;
            if(passCount%3==0){
                player.setMaxHP(player.getMaxHP()+30);
                player.setHP(player.getHP()+30);
                player.setATK(player.getATK()+3);
                player.setDEF(player.getDEF()+2);
                System.out.println("你已经通过了3轮战斗，属性提升！");
                System.out.println("当前属性: " + player.getName() + " [HP: " + player.getHP() + "/" + player.getMaxHP() + ", ATK: " + player.getATK() + ", DEF: " + player.getDEF() + "]");
            }
            System.out.println("是否继续战斗? (y/n)");
            char continueBattle = scanner.next().charAt(0);
            if (continueBattle != 'y' && continueBattle != 'Y') {
                System.out.println("游戏结束，感谢游玩！");
                break;
            }
        }
    }
}

