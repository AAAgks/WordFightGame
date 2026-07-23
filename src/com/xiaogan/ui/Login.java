package com.xiaogan.ui;

import com.xiaogan.bean.Account;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public  class Login {
    private static Login currentLoginUser;
    private String username;
    private static final ArrayList<Account> accounts = new ArrayList<>();
    private static Random random=new Random();
    private static Scanner scanner=new Scanner(System.in);

    public Login() {
    }
    public Login(String username) {
        this.username = username;
    }

    public void start() {
        MainMenuActivity.MainMenu();
    }

    public  String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static Login getCurrentLoginUser() {
        return currentLoginUser;
    }

    public static void setCurrentLoginUser(Login currentLoginUser) {
        Login.currentLoginUser = currentLoginUser;
    }

    public static boolean login() {
        while (true){
            int count=3;
            System.out.println("请输入用户名：");
            System.out.println("输入exit退出");
            String username = scanner.next();
            if(isAccountLocked(username)) {
                System.out.println("账号已锁定，请联系管理员！");
                break;
            }
            if("exit".equals(username)){
                break;
            }
            boolean pwdPass = false;
            String password="";
            if(isUserExists(username)) {
                while(true){
                    System.out.println("请输入密码,你还有"+count+"次机会");
                    password = scanner.next();
                    if(!verifyPasswordMatch(username,password)){
                        if(count==1){
                            System.out.println("密码错误次数过多，请重新输入用户名！");
                            for(Account acc:accounts){
                                if(acc.getUsername().equals(username)) acc.setStatus("locked");
                            }
                            break;
                        }
                        System.out.println("密码错误，请重新输入！");
                        count--;
                    }else{
                        pwdPass = true;
                        break;
                    }
                }
            }else if(!(isUserExists(username))){
                System.out.println("用户名不存在，请重新输入！");
                continue;
            }
            if(!pwdPass) continue;
            while (true){
                String verificationCode = getVerifyCode ();
                System.out.println(verificationCode);
                System.out.println("请输入验证码：");
                String scannerVerifyCode = scanner.next();
                if(!verificationCode(scannerVerifyCode,verificationCode)){
                    System.out.println("验证码错误，请重新输入！");
                }else{
                    System.out.println("登录成功！");
                    currentLoginUser = new Login(username);
                    return true;
                }
            }
        }
        return false;
    }
    public static boolean verificationCode(String scannerVerifyCode,String verificationCode){
        String lowerStr1=scannerVerifyCode.toLowerCase();
        String lowerStr2=verificationCode.toLowerCase();
        return lowerStr1.equals(lowerStr2);
    }
    public static boolean isAccountLocked(String username){
        for(Account acc:accounts){
            if(acc.getUsername().equals(username)&&"locked".equals(acc.getStatus())) return true;
        }
        return false;
    }
    public static boolean register() {
        String username;
        String password;
        while (true) {
            System.out.println("请输入用户名：");
            System.out.println("用户名长度必须在3到16个字符之间！");
            username = scanner.next();
            if(!checkUsername(username)) {
                System.out.println("用户名不符合要求，请重新输入！");
                continue;
            }
            if(isUserExists(username)){
                System.out.println("用户名已存在，请重新输入！");
                continue;
            }
            System.out.println("请输入密码：");
            System.out.println("密码长度必须在3到8个字符之间！");
            System.out.println("且只能是字母加数字的组合，不能有其他字符");

            while(true){
                System.out.print("请输入密码：");
                password = scanner.next();
                if(checkPassword(password)){
                    break;
                }
                System.out.println("密码不符合格式要求，请重新输入！");
            }
            while(true){
                System.out.println("请确认密码：");
                String password2= scanner.next();
                if(password.equals(password2)) {
                    break;
                }
                System.out.println("两次输入的密码不一致，请重新输入！");
            }
            break;
        }

        System.out.println("注册成功！");
        String generateRandomId = generateRandomId ();
        accounts.add(new Account(generateRandomId, username, password, "normal"));
        System.out.println("你的ID是"+generateRandomId);
        return true;
    }

    public static boolean verifyPasswordMatch(String username,String password) {
        for (Account acc:accounts) {
            if(acc.getUsername().equals(username) && acc.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isUserExists(String username) {
        for (Account acc: accounts) {
            if(username.equals(acc.getUsername())) return true;
        }
        return false;
    }
    private static boolean checkUsername(String username) {
        if (username == null || username.isEmpty()) {
            return false;
        }
        if (username.length() < 3||username.length() > 16) {
            return false;
        }

        boolean isNumExist=false;
        boolean isLetterExist=false;

        for (char c:username.toCharArray()) {
            if (c >= '0' && c <= '9') {
                isNumExist = true;
            }else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                isLetterExist = true;
            }
            else return false;
        }
        return isLetterExist;
    }
    private static boolean checkPassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        if(password.length() < 3 || password.length() > 8){
            return false;
        }
        boolean isNumExist=false;
        boolean isLetterExist=false;
        for (char c:password.toCharArray()) {
            if (c >= '0' && c <= '9') {
                isNumExist = true;
            }else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                isLetterExist = true;
            }
            else return false;
        }
        if(!(isNumExist && isLetterExist)){
            return false;
        }
        return true;
    }

    private static String getVerifyCode(){
        StringBuilder sb=new StringBuilder();
        final char[] CHAR_POOL = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        final char[] NUMBER_POOL = "0123456789".toCharArray();
        for (int i = 0; i < 4; i++){
            char c=CHAR_POOL[random.nextInt(CHAR_POOL.length)];
            sb.append(c);
        }
        sb.append(NUMBER_POOL[random.nextInt(NUMBER_POOL.length)]);
        for (int i = sb.length()-1; i >= 0; i--) {
            int index=random.nextInt(i+1);
            char temp=sb.charAt(index);
            sb.setCharAt(index,sb.charAt(i));
            sb.setCharAt(i,temp);
        }
        return sb.toString();
    }
    private static String generateRandomId () {
        StringBuilder sb=new StringBuilder();
        final char[] NUMBER_POOL = "0123456789".toCharArray();
        sb.append("xiaogan");
        for (int i = 0; i < 5; i++){
            char c=NUMBER_POOL[random.nextInt(NUMBER_POOL.length)];
            sb.append(c);
        }
        return sb.toString();
    }
}
