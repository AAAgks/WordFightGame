package com.xiaogan.ui;

import java.util.Scanner;

public class MainMenuActivity {
    private MainMenuActivity() {
    }

    public static void MainMenu(){
        boolean exitFlag = false;
        Scanner scanner = new Scanner(System.in);
        while(!exitFlag){
            System.out.println("╔════════════════════════════════╗");
            System.out.println("    🎮 欢迎来到文字格斗游戏 🎮   ");
            System.out.println("╚════════════════════════════════╝");
            System.out.println("请选择操作：1登录 2注册 3退出");
            int operateCode=scanner.nextInt();
            switch (operateCode){
                case 1:
                  boolean loginOk=Login.login();
                  if(loginOk) {
                      String username = Login.getCurrentLoginUser().getUsername();
                      Game.startGame(username);
                  }
                  break;
                case 2:
                  Login.register();
                  break;
                case 3:
                  System.out.println("退出程序");
                  exitFlag = true;
                  break;
                default: System.out.println("输入错误，请重新选择");
            }
        }
    }
}
