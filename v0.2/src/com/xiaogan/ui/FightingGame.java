package com.xiaogan.ui;

import com.xiaogan.domain.character.EnemyCharacter;
import com.xiaogan.domain.character.EnemyFactory;
import com.xiaogan.domain.character.EnemyTemplate;
import com.xiaogan.domain.character.HeroCharacter;
import com.xiaogan.domain.item.Item;
import com.xiaogan.domain.item.ItemEffect;
import com.xiaogan.domain.item.ItemPool;
import com.xiaogan.domain.skill.Skill;
import com.xiaogan.domain.skill.SkillPool;
import com.xiaogan.domain.skill.SkillType;
import com.xiaogan.domain.tips.BattleTip;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class FightingGame {
    public void gameStart(String username){
        System.out.println("╔════════════════════════════════╗");
        System.out.println("    🎮 "+username+"欢迎来到文字格斗游戏 🎮   ");
        System.out.println("╚════════════════════════════════╝");
        HeroCharacter player = createCharacter(username);
        System.out.println("═══════════════════════════════════════");
        System.out.println("角色创建成功");
        System.out.println("🌟初始属性："+player.show());
        System.out.println("🌟拥有技能："+player.showSkills());

        ArrayList<EnemyCharacter> enemies = new ArrayList<>();
        for (EnemyTemplate t : EnemyFactory.getAll()) {
            enemies.add(t.create());
        }

        //开始战斗
        int count=1;
        int wins=0;
        while(player.isAlive()){

            if (count > 1) {
                int preHeal = (int)(player.getMaxHp() * 0.15);
                player.heal(preHeal);
                System.out.println("💚 战前休整，恢复了 " + preHeal + " 点生命值");
            }

            if(count>1){
                for (EnemyCharacter e:enemies) {
                    e.setMaxHp(e.getMaxHp() + 8);
                    e.setHp(e.getMaxHp());
                    e.setAttack(e.getAttack() + 2);
                    e.setDefense(e.getDefense() + 1);
                    e.setDefending(false);
                }
            }

            Random r = new Random();
            EnemyCharacter enemy = enemies.get(r.nextInt(enemies.size()));
            System.out.println("═══════════════════════════════════════");
            System.out.println("⚔️第"+count+"场战斗开始！对手: "+enemy.getName());
            System.out.println("🌟初始属性："+enemy.show());

            int round=1;
            while(player.isAlive()){
                System.out.println("---------------------------------------");
                System.out.println("⚔️第"+round+"回合开始！");
                System.out.println(getResourceBar(player.getName(), player.getHp(), player.getMaxHp(), "HP"));
                System.out.println(getResourceBar(player.getName(), player.getMp(), player.getMaxMp(), "MP"));
                System.out.println(getResourceBar(enemy.getName(), enemy.getHp(), enemy.getMaxHp(), "HP"));

                player.setDefending(false);
                playerTurn(player, enemy);
                if(!enemy.isAlive()){
                    String template= BattleTip.ENEMY_DEATH.getRandomTip();
                    System.out.printf(template,enemy.getName(),player.getName());
                    System.out.println("🎉 你击败了 " + enemy.getName() + "！");
                    getItem(player);
                    wins++;
                    break;
                }
                enemy.setDefending(false);
                enemyTurn(player, enemy);

                double currentHpRate = (double)player.getHp() / player.getMaxHp();
                if(currentHpRate <= 0.2){
                    String template=BattleTip.HP_LACK.getRandomTip();
                    System.out.printf(template,player.getName());
                }
                if(!player.isAlive()){
                    String template=BattleTip.DEATH.getRandomTip();
                    System.out.printf(template,player.getName());
                    System.out.println("游戏结束，你共击败了" + wins + "个敌人");
                    break;
                }
                round++;
            }

            if(player.isAlive()){
                int healing = (int)(player.getMaxHp() * 0.2);
                int recoverMp = (int)(player.getMaxMp() * 0.2);
                player.heal(healing);
                player.recoverMp(recoverMp);
                System.out.println("💚 战斗结束！你恢复了 " + healing + " 点生命值 "+recoverMp+" 点蓝量");
                System.out.println("🏆 当前胜场: " + wins);
                System.out.println("═══════════════════════════════════════");
            }

            if(player.isAlive()&&wins%3==0){
                System.out.println("⭐ 恭喜，你获得了属性提升！");
                player.setMaxHp(player.getMaxHp()+30);
                player.setMaxMp(player.getMaxMp()+20);
                player.setAttack(player.getAttack()+5);
                player.setDefense(player.getDefense()+3);
                System.out.println("最大生命值+30，最大蓝量+20，攻击力+5，防御力+3");
                System.out.println("🌟当前属性："+player.show());
            }

            if(player.isAlive()){
                System.out.println("═══════════════════════════════════════");
                System.out.println("继续下一场战斗？（y/n）");
                Scanner sc=new Scanner(System.in);
                String choose = sc.next();
                if("y".equalsIgnoreCase(choose)){
                    count++;
                    continue;
                }else if ("n".equalsIgnoreCase(choose)){
                    System.out.println("游戏结束，你共击败了" + wins + "个敌人");
                    break;
                }else{
                    System.out.println("无效输入，默认继续下一场战斗！");
                    count++;
                    continue;
                }
            }
        }

        System.out.println("═══════════════════════════════════════");
        System.out.println("游戏结束！");
        System.out.println("总胜场: " + wins);
        System.out.println("感谢游玩！");
        System.exit(0);

    }

    public String getResourceBar(String name,int current, int max,String unit) {
        final int BAR_LENGTH = 20;
        final int NAME_FIX_WIDTH = 8;
        int realCurrent = Math.max(0, Math.min(current, max));
        int filled = (int) ((realCurrent * 1.0 / max) * BAR_LENGTH);
        StringBuilder sb=new StringBuilder();

        if ("HP".equals(unit)) {
            // HP：填充名称，不足宽度补空格
            sb.append(String.format("%-" + NAME_FIX_WIDTH + "s", name));
        } else {
            // MP：直接填充空白占位，不显示名字
            sb.append(" ".repeat(NAME_FIX_WIDTH));
        }
        sb.append(":[");

        for (int i = 0; i < BAR_LENGTH; i++) {
            sb.append(i < filled ? "█" : " ");
        }
        sb.append("] ")
                .append(realCurrent)
                .append("/")
                .append(max)
                .append(" ")
                .append(unit);
        return sb.toString();
    }

    public HeroCharacter createCharacter(String username) {
        Scanner sc=new Scanner(System.in);
        System.out.println("创建你的角色：");
        System.out.println("您的角色名为: " + username);
        System.out.println("请分配属性点 (共20点):");
        System.out.println("1. 生命值 (每点+10 HP)，初始值：100");
        System.out.println("2. 蓝量  (每点+10 HP)，初始值：50");
        System.out.println("3. 攻击力 (每点+2 ATK)，初始值：15");
        System.out.println("4. 防御力 (每点+2 DEF)，初始值：5");
        int[] values=new int[]{0,0,0,0};
        int points = 20;
        while (points > 0) {
            String[] attributes = {"生命值", "蓝量", "攻击力", "防御力"};
            for (int i = 0; i < attributes.length; i++) {
                System.out.println("分配点数到" + attributes[i]+"（剩余点数："+points+"）：");

                if (!sc.hasNextInt()) {
                    System.out.println("输入非法！只能输入数字");
                    i--;
                    sc.next();
                    continue;
                }

                int input = sc.nextInt();
                if(input < 0){
                    System.out.println("无效输入，默认分配0点");
                    continue;
                }

                int assignNum;
                if(input > points) {
                    System.out.println("属性点不足！剩余属性已全部分配到" + attributes[i]);
                    assignNum = points;
                }else{
                    assignNum = input;
                }
                values[i] = assignNum;
                points -= assignNum;

                if(points <= 0&&i!=attributes.length-1){
                    System.out.println("所有属性点已分配完毕！剩余未分配属性默认为0");
                    break;
                }
            }
            if(points > 0) {
                System.out.println("请分配所有属性点！");
                points = 20;
                continue;
            }
            break;
        }
        HeroCharacter player = new HeroCharacter(username, 120+values[0]*10,50+values[1]*10, 15+values[2]*2, 5+values[3]*2);
        for (Skill s : SkillPool.ALL_SKILLS) {
            player.addSkill(s);
        }
        return player;
    }

    public void playerTurn(HeroCharacter player, EnemyCharacter enemy) {
        boolean actionFinish = false;
        Scanner sc=new Scanner(System.in);
        while(!actionFinish) {
            System.out.println("===== 你的回合 =====");
            System.out.println("请选择你的技能：");
            System.out.print("1. 普通攻击\t");
            System.out.print("2. 使用技能\t");
            System.out.print("3. 使用道具\t");
            System.out.print("4. 查看当前属性\t");
            System.out.println("选择行动（1-4）：");

            if(!sc.hasNextInt()){
                System.out.println("输入非法！只能输入数字");
                sc.next();
                continue;
            }

            int choose = sc.nextInt();
            int damage;
            boolean backToMenu;

            switch (choose) {
                default:
                    System.out.println("无效输入，请重新选择！");
                    break;
                case 1:
                    damage=calculateDamage(player.getAttack(), enemy.getDefense());
                    System.out.println("💥你对 " + enemy.getName() + " 使用了[普通攻击]，造成 " + damage + " 点伤害！");
                    enemy.takeDamage(damage);
                    actionFinish = true;
                    break;
                case 2:
                    backToMenu = false;
                    while (!backToMenu) {
                        showSkills(player);
                        System.out.println("请选择使用的技能：");
                        System.out.println("选择 0 返回上一菜单");
                        if(!sc.hasNextInt()){
                            System.out.println("输入非法！只能输入数字");
                            sc.next();
                            continue;
                        }
                        int skillChoose = sc.nextInt();
                        if(skillChoose==0){
                            System.out.println("返回菜单");
                            break;
                        }
                        if(skillChoose<0||skillChoose>player.getSkills().size()){
                            System.out.println("无效的技能选择！");
                        }else{
                            useSkill(player, enemy, skillChoose);
                            backToMenu = true;
                            actionFinish = true;
                        }
                    }
                    break;

                case 3:
                    if(player.getBag().isEmpty()){
                        System.out.println("背包空空如也，无道具！");
                        break;
                    }

                    backToMenu = false;
                    while (!backToMenu) {
                        showBag(player);
                        System.out.println("请选择使用的道具：");
                        System.out.println("选择 0 返回上一菜单");
                        if(!sc.hasNextInt()){
                            System.out.println("输入非法！只能输入数字");
                            sc.next();
                            continue;
                        }

                        int itemChoose = sc.nextInt();
                        if(itemChoose==0){
                            System.out.println("返回菜单");
                            break;
                        }else if(itemChoose<0||itemChoose>player.getBag().size()){
                            System.out.println("无效的道具选择！");
                        }else{
                            useConsumable(player, itemChoose-1);
                            System.out.println("道具使用成功！");
                            backToMenu = true;
                            actionFinish = true;
                        }
                    }
                    break;
                    case 4:
                        backToMenu = false;
                        while (!backToMenu){
                            System.out.println(player.show());
                            System.out.println("选择 0 返回上一菜单");
                            if(!sc.hasNextInt()){
                                System.out.println("输入非法！只能输入数字");
                                sc.next();
                                continue;
                            }
                            choose = sc.nextInt();
                            if(choose==0){
                                System.out.println("返回菜单");
                                backToMenu = true;
                            }else{
                                System.out.println("无效输入，请重新选择！");
                            }
                        }
                        break;
            }
        }
    }

    public void enemyTurn(HeroCharacter player, EnemyCharacter enemy) {
        System.out.println("===== " + enemy.getName() + "的回合 =====");
        Random r = new Random();
        boolean useSkill = r.nextBoolean();

        if (useSkill && enemy.isDefenseSkill()) {
            enemy.setDefending(true);
            System.out.println("🛡️ " + enemy.getName() + " 进入了["
                    + enemy.getSkillName() + "]，下回合受到伤害减半！");
        } else if (useSkill) {
            int totalDamage = 0;
            for (int i = 0; i < enemy.getSkillHits(); i++) {
                totalDamage += calculateDamage(
                        (int)(enemy.getAttack() * enemy.getSkillRate()),
                        player.getDefense());
            }
            player.takeDamage(totalDamage);
            System.out.println("⚔️ " + enemy.getName() + " 对你使用了["
                    + enemy.getSkillName() + "]，造成 " + totalDamage + " 点伤害！");
        } else {
            int damage = calculateDamage(enemy.getAttack(), player.getDefense());
            player.takeDamage(damage);
            System.out.println("⚔️ " + enemy.getName()
                    + " 对你使用了[普通攻击]，造成 " + damage + " 点伤害！");
        }
    }

    public int calculateDamage(int attack, int defense) {
        int damage = attack * 100 / (100 + defense);
        return Math.max(damage, 1);
    }

    public boolean isEnoughMp(HeroCharacter player, int cost) {
        return player.getMp() >= cost;
    }

    public void useItem(HeroCharacter player, Item item) {

        // 遍历道具所有效果
        for (ItemEffect effect : item.getEffectMap().keySet()) {
            int value = item.getEffectValue(effect);
            switch (effect) {
                case ADD_HP:
                    player.setHp(player.getHp() + value);
                    System.out.printf("💚 恢复了 %d 点生命值！%n", value);
                    break;
                case HEAL_HP_PCT:
                    int healAmount = player.getMaxHp() * value / 100;
                    player.setHp(player.getHp() + healAmount);
                    System.out.printf("💚 恢复了 %d 点生命值！%n", healAmount);
                    break;
                case ADD_MP:
                    player.setMp(player.getMp() + value);
                    System.out.printf("💙 恢复了 %d 点魔力！%n", value);
                    break;
                case ADD_ATK:
                    player.setAttack(player.getAttack() + value);
                    System.out.printf("⚔️ 增加了 %d 点攻击力！%n", value);
                    break;
                case ADD_DEF:
                    player.setDefense(player.getDefense() + value);
                    System.out.printf("🛡️ 增加了 %d 点防御力！%n", value);
                    break;
                case HEAL_MP_PCT:
                    int mpAmount = player.getMaxMp() * value / 100;
                    player.setMp(player.getMp() + mpAmount);
                    System.out.printf("💙 恢复了 %d 点魔力！%n", mpAmount);
                    break;
            }
        }
        // 打印道具描述
        System.out.printf("使用道具：%s | %s", item.getName(), item.getDescribe());
    }

    public void showBag(HeroCharacter player) {
        System.out.println("===== 背包 =====");
        List<Item> bag = player.getBag();
        for (int i = 0; i < bag.size(); i++) {
            Item item = bag.get(i);
            System.out.printf("%d. %s ｜ %s", i + 1, item.getName(), item.getDescribe()+"\n");
        }
    }

    public void getItem(HeroCharacter player) {
        Random r=new Random();
        double rate = r.nextInt(10);
        if(rate < 2){
            int index=r.nextInt(ItemPool.ALL_ITEMS.size());
            player.getBag().add(ItemPool.ALL_ITEMS.get(index));
            System.out.println("获得道具：" + ItemPool.ALL_ITEMS.get(index).getName());
        }
    }

    public void showSkills(HeroCharacter player){
        System.out.println("===== 技能 =====");
        ArrayList<Skill> skills = player.getSkills();
        for (int i = 0; i < player.getSkills().size(); i++) {
            Skill skill = skills.get(i);
            System.out.printf("%d. 【%s】：%s\t", i + 1, skill.getSkillName(),skill.getDescription());
            if((i+1)%2==0) System.out.println();
        }

    }

    public boolean useSkill(HeroCharacter player, EnemyCharacter enemy, int skillIndex) {
        Skill skill = player.getSkills().get(skillIndex - 1);
        int cost = skill.getCostMp();

        if (!isEnoughMp(player, cost)) {
            System.out.printf(BattleTip.MP_LACK.getRandomTip(),
                    player.getName(), skill.getSkillName());
            return false;
        }

        player.consumeMp(cost);
        SkillType type = skill.getType();

        switch (type) {
            case ATTACK: {
                int total = 0;
                for (int i = 0; i < skill.getHits(); i++) {
                    total += calculateDamage(
                            (int)(player.getAttack() * skill.getAtkRate()),
                            enemy.getDefense());
                }
                enemy.takeDamage(total);
                System.out.println("💥 消耗" + cost + "MP，你对 " + enemy.getName()
                        + " 使用了【" + skill.getSkillName() + "】，造成 " + total + " 点伤害！");
                break;
            }
            case HEAL_HP: {
                int healing = (int)(player.getMaxHp() * skill.getAtkRate());
                player.heal(healing);
                System.out.println("💚 消耗" + cost + "MP，你使用了【" + skill.getSkillName()
                        + "】，恢复了 " + healing + " 点生命值！");
                break;
            }
            case HEAL_MP: {
                int recover = (int)(player.getMaxMp() * skill.getAtkRate());
                player.recoverMp(recover);
                System.out.println("💙 你使用了【" + skill.getSkillName()
                        + "】，恢复了 " + recover + " 点魔力！");
                break;
            }
            case BUFF: {
                player.setAttack(player.getAttack() + (int)skill.getAtkRate());
                System.out.println("🔥 你使用了【" + skill.getSkillName()
                        + "】，永久提升" + (int)skill.getAtkRate() + "点攻击力！");
                break;
            }
            case DEFENSE: {
                player.setDefending(true);
                System.out.println("🛡️ 你进入了【" + skill.getSkillName() + "】姿态！");
                break;
            }
        }
        return true;
    }

    public void useConsumable(HeroCharacter player, int item) {
        Item useItem = player.getBag().get(item);
        useItem(player, useItem);
        player.getBag().remove(useItem);
    }
}
