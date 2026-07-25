package com.xiaogan.ui;

import com.xiaogan.domain.User;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Login {


    public void start(){
        ArrayList<User> list = new ArrayList<>();
        while (true) {
            System.out.println("╔════════════════════════════════╗");
            System.out.println("    🎮 欢迎来到文字格斗游戏 🎮   ");
            System.out.println("╚════════════════════════════════╝");
            System.out.println("请选择操作：1登录 2注册 3忘记密码 4退出");
            Scanner sc = new Scanner(System.in);
            String choice = sc.next();
            switch (choice){
                case "1"-> login(list);
                case "2"-> register(list);
                case "3"-> resetPassword(list);
                case "4"-> {
                    System.out.println("游戏已退出");
                    System.exit(0);
                }
                default-> System.out.println("无效的选择，请重新输入");
            }
        }
    }


    private void login(ArrayList<User> list){
        Scanner sc=new Scanner(System.in);
        while (true) {
            System.out.println("用户登录");
            System.out.println("请输入用户名：");
            String username=sc.next();
            int index =findIndex(list, username);
            if(index==(-1)){
                System.out.println("用户名不存在");
                break;
            }
            boolean status=list.get(index).isStatus();
            if(!status){
                System.out.println("用户"+username+"已经锁定，请联系官方客服：123-12306");
                break;
            }
            User u = list.get(index);
            String rightPassword=u.getPassword();
            for (int i = 0; i < 3; i++) {
                System.out.println("请输入密码：");
                String password = sc.next();
                while (true) {

                    String verifyCode = createCode();
                    System.out.println(verifyCode);
                    System.out.println("正确的验证码为："+verifyCode);
                    System.out.println("请输入验证码：");
                    String code = sc.next();
                    if (!verifyCode.equalsIgnoreCase(code)) {
                        System.out.println("验证码错误");
                    } else {
                        break;
                    }
                }
                if (!password.equals(rightPassword)) {
                    System.out.println("密码错误");
                    if (i == 2) {
                        u.setStatus(false); //锁定用户
                        System.out.println("用户" + username + "已经锁定，请联系官方客服：123-12306");
                        break;
                    }else{
                        System.out.println("你还剩" + (2 - i) + "次机会");
                    }
                } else {
                    System.out.println("密码正确");
                    System.out.println("登录成功");
                    FightingGame fg=new FightingGame();
                    fg.gameStart(username);
                    break;
                }
            }
            break;
        }
    }

    private void register(ArrayList<User> list){
        System.out.println("用户注册");
        User u = new User();



        while(true){
            Scanner sc=new Scanner(System.in);
            System.out.println("请输入用户名：");
            String username=sc.next();
            if(!checkLen(username,3,16)){
                System.out.println("用户名长度必须在3到16个字符之间");
                continue;
            }
            if(!checkUsername(username)){
                System.out.println("只能由字母、数字组成，不能是纯数字");
                continue;
            }
            if(userContain(list,username)){
                System.out.println("用户名已存在");

                return;
            }
            u.setUsername(username);
            break;
        }


        while(true){
            Scanner sc=new Scanner(System.in);
            System.out.println("请输入手机号：");
            String  phoneNumber=sc.next();
            if(phoneNumber.isEmpty()){
                System.out.println("手机号不能为空");
                continue;
            }
            if(phoneNumber.charAt(0)!='1'){
                System.out.println("手机号必须以1开头");
                continue;
            }
            if(!checkLen(phoneNumber,11,11)){
                System.out.println("手机号长度必须为11位");
                continue;
            }
            int[] count = getCount(phoneNumber);
            if(count[0]!=11){
                System.out.println("手机号必须是数字");
                continue;
            }
            u.setPhoneNumber(phoneNumber);
            break;
        }

        while(true){
            Scanner sc=new Scanner(System.in);
            System.out.println("请输入密码：");
            String password1=sc.next();
            if(!checkLen(password1,3,8)){
                System.out.println("密码长度必须在3到8个字符之间");
                continue;
            }
            if(!checkPassword(password1)){

                System.out.println("只能是字母加数字的组合，不能有其他字母");
                continue;
            }
            System.out.println("请确认密码");
            String password2= sc.next();
            if(!password1.equals(password2)){
                System.out.println("两次输入的密码不一致");
                continue;
            }
            u.setPassword(password1);
            break;
        }
        list.add(u);
        System.out.println("注册成功");
        System.out.println("您的用户名是："+u.getUsername()+"，密码是："+u.getPassword()+"，手机号是："+u.getPhoneNumber());
    }

    private void resetPassword(ArrayList<User> list){
        Scanner s=new Scanner(System.in);

        System.out.println("请输入账号：");
        String username=s.next();
        int index=findIndex(list, username);
        if(index==-1){
            System.out.println("用户名不存在");
            return;
        }

        System.out.println("请输入你的手机号码:：");
        String phoneNumber=s.next();
        if(!list.get(index).getPhoneNumber().equals(phoneNumber)){
            System.out.println("手机号码不正确");
            return;
        }

        while (true) {
            System.out.println("请输入新密码：");
            String newPassword1=s.next();
            if(!checkLen(newPassword1,3,8)){
                System.out.println("密码长度必须在3到8个字符之间");
                continue;
            }
            if(!checkPassword(newPassword1)){
                System.out.println("只能是字母加数字的组合，不能有其他字母");
                continue;
            }
            System.out.println("请确认密码");
            String newPassword2=s.next();
            if(!newPassword2.equals(newPassword1)){
                System.out.println("两次输入的密码不一致");
                continue;
            }
            list.get(index).setPassword(newPassword1);
            System.out.println("密码修改成功");
            break;
        }
    }

    private int[] getCount(String str){
        int digitCount=0;
        int letterCount=0;
        int symbolCount=0;
        for (char c:str.toCharArray()) {
            if((c>='a'&&c<='z')||(c>='A'&&c<='Z')){
                letterCount++;
            }else if(c>='0'&&c<='9'){
                digitCount++;
            }else{
                symbolCount++;
            }
        }
        return new int[]{digitCount,letterCount,symbolCount};
    }

    private boolean userContain(ArrayList<User> list,String username){
        for(User u:list){
            if(u.getUsername().equals(username)) return true;
        }
        return false;
    }
    private boolean checkUsername(String username){
        int[] count = getCount(username);
        return count[0]>=0&&count[1]>0&&count[2]==0;
    }

    private boolean checkPassword(String password){
        int[] count = getCount(password);
        return count[0]>0&&count[1]>0&&count[2]==0;
    }

    private boolean checkLen(String str,int min,int max){
        return str.length()>=min&&str.length()<=max;
    }

    private String createCode(){
        StringBuilder sb=new StringBuilder();
        Random r=new Random();
        char[] letterPool="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        char[] numberPool="0123456789".toCharArray();
        for(int i=0;i<4;i++){
            int randomIndex=r.nextInt(letterPool.length);
            sb.append(letterPool[randomIndex]);
        }
        sb.append(numberPool[r.nextInt(numberPool.length)]);
        char[] ch=sb.toString().toCharArray();
        for (int i = sb.length()-1; i > 0; i--) {
            int randomIndex=r.nextInt(i+1);
            char temp=ch[randomIndex];
            ch[randomIndex]=ch[i];
            ch[i]=temp;
        }
        return new String(ch);
    }

    private int findIndex(ArrayList<User> list,String username) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getUsername().equals(username)) {
                return i;
            }
        }
        return -1;
    }
    private boolean isStatus(ArrayList<User> list,String username){
        int index = findIndex(list, username);
        if (index == -1) return false;
        return list.get(index).isStatus();
    }

}
