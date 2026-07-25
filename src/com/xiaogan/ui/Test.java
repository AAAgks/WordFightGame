package com.xiaogan.ui;

public class Test {
//    switch (choose) {
//        default:
//            System.out.println("无效输入，请重新选择！");
//            break;
//        case "1":
//            damage=calculateDamage(player.getAttack(), enemy.getDefense());
//            System.out.println("💥你对 " + enemy.getName() + " 使用了[普通攻击]，造成 " + damage + " 点伤害！");
//            enemy.takeDamage(damage);
//            actionFinish = true;
//            break;
//        case "2":
//            if(player.getMp() < 10){
//                String template= BattleTip.MP_LACK.getRandomTip();
//                System.out.printf(template,player.getName(),"强力一击");
//                return;
//            }
//            player.consumeMp(10);
//            damage=calculateDamage((int)(player.getAttack()*1.8), enemy.getDefense());
//            System.out.println("💥 消耗了10MP，你对 " + enemy.getName() + " 使用了[强力一击]，造成 " + damage + " 点伤害！");
//            enemy.takeDamage(damage);
//            actionFinish = true;
//            break;
//        case "3":
//            if(player.getHp() < 10){
//                String template= BattleTip.MP_LACK.getRandomTip();
//                System.out.printf(template,player.getName(),"生命汲取");
//                return;
//            }
//            player.consumeMp(10);
//            int healing=(int)(player.getMaxHp()*0.3);
//            System.out.println("💚消耗了10MP，你使用了[生命汲取]，恢复了 " + healing + " 点生命值！");
//            player.heal(healing);
//            System.out.println("💚你使用了生命汲取！");
//            actionFinish = true;
//            break;
//        case "4":{
//            int recover=calculateRecoverMp(player.getMaxMp());
//            System.out.println("💙 你使用了[冥想]，恢复了 " + recover + " 点魔力！");
//            player.recoverMp(recover);
//            actionFinish = true;
//            break;
//        }
//
//        case "5":
//            if(player.getBag().isEmpty()){
//                System.out.println("背包空空如也，无道具！");
//                break;
//            }
//
//            boolean backToMenu = false;
//            while (!backToMenu) {
//                System.out.println("请选择使用道具：");
//                System.out.println("选择0返回上一菜单");
//                showBag(player);
//                String itemChoose = sc.next();
//                switch (itemChoose){
//                    case "0":
//                        System.out.println("返回菜单");
//                        backToMenu = true;
//                        break;
//                    default:
//                        try{
//                            int num = Integer.parseInt(itemChoose) - 1;
//                            List<Item> bag = player.getBag();
//                            if(num >=0 && num < bag.size()){
//                                Item useItem = bag.get(num);
//                                useItem(player, useItem);
//                                bag.remove(num);
//                                actionFinish = true;
//                                backToMenu = true;
//                            }else{
//                                System.out.println("编号不存在");
//                            }
//                        }catch (Exception e){
//                            System.out.println("请输入合法数字");
//                        }
//                        break;
//                }
//            }
//            break;
//    }
//}
}
