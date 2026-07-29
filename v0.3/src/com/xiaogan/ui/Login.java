package com.xiaogan.ui;

import com.xiaogan.domain.User;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Login extends JFrame {

    static ArrayList<User> list = new ArrayList<>();

    JMenuItem backMainItem  = new JMenuItem("返回主菜单");
    JMenuItem registerItem  = new JMenuItem("注册");
    JMenuItem forgetPwdItem  = new JMenuItem("忘记密码");
    JMenuItem updatePwdItem  = new JMenuItem("修改密码");
    JMenuItem exitGameItem  = new JMenuItem("退出游戏");

    private void initJFrame(){
        this.setSize(603, 680);
        this.setAlwaysOnTop(true);
        this.setTitle("文字格斗游戏");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
    }

    private void initMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu function=new JMenu("功能");
        JMenu aboutMenu = new JMenu("关于我们");
        menuBar.add(function);
        menuBar.add(aboutMenu);



        function.add(backMainItem);
        function.add(registerItem);
        function.add(forgetPwdItem);
        function.add(updatePwdItem);
        function.add(exitGameItem );

        backMainItem.addActionListener(e -> {
            getContentPane().removeAll();
            login(list);
        });
        registerItem.addActionListener(e -> {
            register(list);
        });
        forgetPwdItem.addActionListener(e -> {
            getContentPane().removeAll();
            showForgetPwd(list);
        });
        updatePwdItem.addActionListener(e -> {
            getContentPane().removeAll();
            showUpdatePwd(list);
        });
        exitGameItem.addActionListener(e -> System.exit(0));


    }

    private void backGround(){
        JLabel loginLabel = new JLabel(new ImageIcon("image/backGround/backGround.jpeg"));
        loginLabel.setBounds(0, 0, 603, 680);
        getContentPane().add(loginLabel);
    }

    public void start(){
        initJFrame();
        initMenuBar();
        login(list);
    }

    private void tips(JTextComponent comp, JLabel label) {
        comp.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                label.setVisible(comp.getText().isEmpty());
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                label.setVisible(comp.getText().isEmpty());
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                label.setVisible(comp.getText().isEmpty());
            }
        });
    }

    private void login(ArrayList<User> list) {
        getContentPane().removeAll();

        JLabel titleLabel = new JLabel("登录", JLabel.CENTER);
        titleLabel.setBounds(0, 120, 600, 40);
        titleLabel.setFont(new Font("宋体", Font.BOLD, 28));
        titleLabel.setForeground(Color.BLACK);
        getContentPane().add(titleLabel);

        JLabel msgLabel = new JLabel();
        msgLabel.setBounds(150, 200, 200, 30);
        getContentPane().add(msgLabel);


        JLabel userLabel = new JLabel("账号：");
        userLabel.setBounds(150, 250, 100, 30);
        getContentPane().add(userLabel);

        JLabel hintLabel1 = new JLabel("请输入账号");
        hintLabel1.setBounds(205, 250, 200, 30);
        hintLabel1.setForeground(Color.GRAY);
        hintLabel1.setVisible(true);
        getContentPane().add(hintLabel1);

        JTextField userTextField = new JTextField();
        userTextField.setBounds(200, 250, 200, 30);
        userTextField.setOpaque(false);
        getContentPane().add(userTextField);

        tips(userTextField,hintLabel1);

        JLabel pwdLabel = new JLabel("密码：");
        pwdLabel.setBounds(150, 300, 100, 30);
        getContentPane().add(pwdLabel);

        JLabel hintLabel2 = new JLabel("请输入密码");
        hintLabel2.setBounds(205, 300, 200, 30);
        hintLabel2.setForeground(Color.GRAY);
        hintLabel2.setVisible(true);
        getContentPane().add(hintLabel2);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(200, 300, 200, 30);
        passwordField.setOpaque(false);
        getContentPane().add(passwordField);

        tips(passwordField,hintLabel2);

        JLabel codeLabel = new JLabel("验证码：");
        codeLabel.setBounds(150, 350, 100, 30);
        getContentPane().add(codeLabel);

        JLabel hintLabel3 = new JLabel("请输入验证码");
        hintLabel3.setBounds(205, 350, 200, 30);
        hintLabel3.setForeground(Color.GRAY);
        hintLabel3.setVisible(true);
        getContentPane().add(hintLabel3);

        JTextField codeTextField = new JTextField();
        codeTextField.setBounds(200, 350, 100, 30);
        codeTextField.setOpaque(false);
        getContentPane().add(codeTextField);

        tips(codeTextField,hintLabel3);

        JButton verifyCodeButton = new JButton(createCode());
        verifyCodeButton.setBounds(300, 350, 100, 30);
        getContentPane().add(verifyCodeButton);
        verifyCodeButton.setBorderPainted(false);
        verifyCodeButton.setContentAreaFilled(false);
        verifyCodeButton.setFocusPainted(false);
        verifyCodeButton.addActionListener(e -> verifyCodeButton.setText(createCode()));


        JButton loginButton = new JButton("登录");
        loginButton.setBounds(200, 400, 200, 30);
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);

        //登录验证
        final String[] lastUser = {""};
        final int[] attempts = {0};
        getContentPane().add(loginButton);
        getRootPane().setDefaultButton(loginButton);
        loginButton.addActionListener(e -> {
            String username = userTextField.getText().trim();
            String password = new String(passwordField.getPassword());

            String[] err = {""};
            String[] result = checkLogin(list, username, password, codeTextField.getText(), verifyCodeButton.getText(), lastUser, attempts);

            if (result[1].equals("true")) {
                JOptionPane.showMessageDialog(this, "登录成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
                setVisible(false);
                new FightingGame(username, this);

            } else {
                JOptionPane.showMessageDialog(this, result[0], "错误", JOptionPane.ERROR_MESSAGE);
                verifyCodeButton.setText(createCode());
            }
        });

        backGround();
        this.setVisible(true);
        getContentPane().revalidate();
        getContentPane().repaint();

    }

    public String[] checkLogin(ArrayList<User> list, String username, String password, String code, String verifyCode, String[] lastUser, int[] attempts) {

        if (username.isEmpty()) {
            return new String[]{"请输入用户名", "false"};
        }

        if (!username.equals(lastUser[0])) {
            lastUser[0] = username;
            attempts[0] = 0;
        }

        if (password.isEmpty()) {
            return new String[]{"请输入密码", "false"};
        }
        if (code.isEmpty()) {
            return new String[]{"请输入验证码", "false"};
        }
        if (!code.equalsIgnoreCase(verifyCode)) {
            return new String[]{"验证码错误", "false"};
        }

        int index = findIndex(list, username);
        if (index == -1) {
            return new String[]{"用户名不存在", "false"};
        }

        User u = list.get(index);

        // 查有没有被锁定
        if (!u.isStatus()) {
            return new String[]{"用户已锁定，请联系客服：123-12306", "false"};
        }

        // 比对密码
        if (!password.equals(u.getPassword())) {
            attempts[0]++;
            if (attempts[0] >= 3) {
                u.setStatus(false);
                return new String[]{"密码错误3次，用户已锁定！", "false"};
            } else {
                return new String[]{"密码错误！还剩 " + (3 - attempts[0]) + " 次机会", "false"};
            }
        }
        return new String[]{"登录成功", "true"};
    }

    private void register(ArrayList<User> list){
        getContentPane().removeAll();

        JLabel titleLabel = new JLabel("注册账号", JLabel.CENTER);
        titleLabel.setBounds(0, 100, 600, 40);
        titleLabel.setFont(new Font("宋体", Font.BOLD, 28));
        titleLabel.setForeground(Color.BLACK);
        getContentPane().add(titleLabel);

        JLabel userLabel = new JLabel("账号：");
        userLabel.setBounds(150, 200, 100, 30);
        getContentPane().add(userLabel);

        JLabel hintLabel1 = new JLabel("3到16个字符的数字和字母");
        hintLabel1.setBounds(205, 200, 200, 30);
        hintLabel1.setForeground(Color.GRAY);
        hintLabel1.setVisible(true);
        getContentPane().add(hintLabel1);

        JTextField userTextField = new JTextField();
        userTextField.setBounds(200, 200, 200, 30);
        userTextField.setOpaque(false);
        getContentPane().add(userTextField);

        tips(userTextField,hintLabel1);

        JLabel pwdLabel = new JLabel("密码：");
        pwdLabel.setBounds(150, 250, 100, 30);
        getContentPane().add(pwdLabel);

        JLabel hintLabel2 = new JLabel("3到8个字符的数字和字母");
        hintLabel2.setBounds(205, 250, 200, 30);
        hintLabel2.setForeground(Color.GRAY);
        hintLabel2.setVisible(true);
        getContentPane().add(hintLabel2);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(200, 250, 200, 30);
        passwordField.setOpaque(false);
        getContentPane().add(passwordField);

        tips(passwordField,hintLabel2);

        JLabel pwdLabel2 = new JLabel("确认密码：");
        pwdLabel2.setBounds(130, 300, 100, 30);
        getContentPane().add(pwdLabel2);
        JPasswordField passwordField2 = new JPasswordField();
        passwordField2.setBounds(200, 300, 200, 30);
        passwordField2.setOpaque(false);
        getContentPane().add(passwordField2);


        JLabel phoneLabel = new JLabel("手机号：");
        phoneLabel.setBounds(150, 350, 100, 30);
        getContentPane().add(phoneLabel);

        JLabel hintLabel3 = new JLabel("请输入手机号");
        hintLabel3.setBounds(205, 350, 200, 30);
        hintLabel3.setForeground(Color.GRAY);
        hintLabel3.setVisible(true);
        getContentPane().add(hintLabel3);

        JTextField phoneTextField = new JTextField();
        phoneTextField.setBounds(200, 350, 200, 30);
        phoneTextField.setOpaque(false);
        getContentPane().add(phoneTextField);

        tips(phoneTextField,hintLabel3);

        JButton registerButton = new JButton("注册");
        registerButton.setBounds(200, 400, 200, 30);
        registerButton.setBorderPainted(false);
        registerButton.setFocusPainted(false);
        getContentPane().add(registerButton);
        getRootPane().setDefaultButton(registerButton);

        registerButton.addActionListener(e -> {
            String username = userTextField.getText().trim();
            String password = new String(passwordField.getPassword());
            String password2 = new String(passwordField2.getPassword());
            String phoneNumber = phoneTextField.getText().trim();

            String[] result=checkRegister(list, username, password, password2, phoneNumber);

            if("true".equals(result[1])){
                JOptionPane.showMessageDialog(this, result[0], "注册成功", JOptionPane.INFORMATION_MESSAGE);
                login(list);
                return;
            }
            JOptionPane.showMessageDialog(this, result[0], "注册失败", JOptionPane.ERROR_MESSAGE);
        });



        backGround();
        getContentPane().revalidate();
        getContentPane().repaint();

    }

    private String[] checkRegister(ArrayList<User> list, String username, String password1, String password2, String phoneNumber){
        int index=findIndex(list, username);
        //账号验证
        if(username.isEmpty()){
            return new String[]{"用户名不能为空", "false"};
        }
        if(index!=-1){
            return new String[]{"用户名已存在", "false"};
        }
        if(!checkLen(username,3,16)||!checkUsername(username)){
                return new String[]{"账号格式错误", "false"};
        }
        //密码验证
        if(!checkLen(password1,3,8)||!checkPassword(password1)){
            return new String[]{"密码格式错误", "false"};
        }

        if(!password1.equals(password2)){
            return new String[]{"两次输入的密码不一致", "false"};
        }
        //手机号验证
        if(phoneNumber.isEmpty()){
            return new String[]{"手机号不能为空", "false"};
        }
        if(!checkLen(phoneNumber,11,11)){
            return new String[]{"手机号长度必须为11位", "false"};
        }
        if(phoneNumber.charAt(0)!='1'){
            return new String[]{"手机号必须以1开头", "false"};
        }
        int[] count = getCount(phoneNumber);
        if(count[0]!=11){
            return new String[]{"手机号必须全部是数字", "false"};
        }

        User u=new User();
        u.setUsername(username);
        u.setPassword(password1);
        u.setPhoneNumber(phoneNumber);
        u.setStatus(true);
        u.setId(u.createId());
        list.add(u);


        return new String[]{"注册成功", "true"};
    }

    // === 忘记密码（GUI版）===
    private void showForgetPwd(ArrayList<User> list) {
        JLabel titleLabel = new JLabel("忘记密码", JLabel.CENTER);
        titleLabel.setBounds(0, 100, 600, 40);
        titleLabel.setFont(new Font("宋体", Font.BOLD, 28));
        getContentPane().add(titleLabel);

        JLabel userLabel = new JLabel("账号：");
        userLabel.setBounds(150, 180, 100, 30);
        getContentPane().add(userLabel);
        JTextField userField = new JTextField();
        userField.setBounds(200, 180, 200, 30);
        getContentPane().add(userField);

        JLabel phoneLabel = new JLabel("手机号：");
        phoneLabel.setBounds(150, 220, 100, 30);
        getContentPane().add(phoneLabel);
        JTextField phoneField = new JTextField();
        phoneField.setBounds(200, 220, 200, 30);
        getContentPane().add(phoneField);

        JLabel pwdLabel = new JLabel("新密码：");
        pwdLabel.setBounds(150, 260, 100, 30);
        getContentPane().add(pwdLabel);
        JPasswordField pwdField = new JPasswordField();
        pwdField.setBounds(200, 260, 200, 30);
        getContentPane().add(pwdField);

        JLabel pwdLabel2 = new JLabel("确认密码：");
        pwdLabel2.setBounds(130, 300, 100, 30);
        getContentPane().add(pwdLabel2);
        JPasswordField pwdField2 = new JPasswordField();
        pwdField2.setBounds(200, 300, 200, 30);
        getContentPane().add(pwdField2);

        JButton submitBtn = new JButton("确认修改");
        submitBtn.setBounds(150, 360, 120, 35);
        submitBtn.setFocusPainted(false);
        getContentPane().add(submitBtn);
        getRootPane().setDefaultButton(submitBtn);

        JButton backBtn = new JButton("返回登录");
        backBtn.setBounds(300, 360, 120, 35);
        backBtn.setFocusPainted(false);
        getContentPane().add(backBtn);

        submitBtn.addActionListener(e -> {
            String username = userField.getText().trim();
            String phone = phoneField.getText().trim();
            String pwd1 = new String(pwdField.getPassword());
            String pwd2 = new String(pwdField2.getPassword());
            if (username.isEmpty()) { JOptionPane.showMessageDialog(this, "请输入账号"); return; }
            int idx = findIndex(list, username);
            if (idx == -1) { JOptionPane.showMessageDialog(this, "用户名不存在"); return; }
            if (!list.get(idx).getPhoneNumber().equals(phone)) { JOptionPane.showMessageDialog(this, "手机号不正确"); return; }
            if (!checkLen(pwd1, 3, 8)) { JOptionPane.showMessageDialog(this, "密码长度3-8位"); return; }
            if (!checkPassword(pwd1)) { JOptionPane.showMessageDialog(this, "密码须字母+数字组合"); return; }
            if (!pwd1.equals(pwd2)) { JOptionPane.showMessageDialog(this, "两次密码不一致"); return; }
            list.get(idx).setPassword(pwd1);
            JOptionPane.showMessageDialog(this, "密码修改成功！");
            getContentPane().removeAll();
            login(list);
        });

        backBtn.addActionListener(e -> {
            getContentPane().removeAll();
            login(list);
        });

        backGround();
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    // === 修改密码（GUI版）===
    private void showUpdatePwd(ArrayList<User> list) {
        JLabel titleLabel = new JLabel("修改密码", JLabel.CENTER);
        titleLabel.setBounds(0, 100, 600, 40);
        titleLabel.setFont(new Font("宋体", Font.BOLD, 28));
        getContentPane().add(titleLabel);

        JLabel userLabel = new JLabel("账号：");
        userLabel.setBounds(150, 180, 100, 30);
        getContentPane().add(userLabel);
        JTextField userField = new JTextField();
        userField.setBounds(200, 180, 200, 30);
        getContentPane().add(userField);

        JLabel oldPwdLabel = new JLabel("旧密码：");
        oldPwdLabel.setBounds(150, 220, 100, 30);
        getContentPane().add(oldPwdLabel);
        JPasswordField oldPwdField = new JPasswordField();
        oldPwdField.setBounds(200, 220, 200, 30);
        getContentPane().add(oldPwdField);

        JLabel pwdLabel = new JLabel("新密码：");
        pwdLabel.setBounds(150, 260, 100, 30);
        getContentPane().add(pwdLabel);
        JPasswordField pwdField = new JPasswordField();
        pwdField.setBounds(200, 260, 200, 30);
        getContentPane().add(pwdField);

        JLabel pwdLabel2 = new JLabel("确认密码：");
        pwdLabel2.setBounds(130, 300, 100, 30);
        getContentPane().add(pwdLabel2);
        JPasswordField pwdField2 = new JPasswordField();
        pwdField2.setBounds(200, 300, 200, 30);
        getContentPane().add(pwdField2);

        JButton submitBtn = new JButton("确认修改");
        submitBtn.setBounds(150, 360, 120, 35);
        submitBtn.setFocusPainted(false);
        getContentPane().add(submitBtn);
        getRootPane().setDefaultButton(submitBtn);

        JButton backBtn = new JButton("返回登录");
        backBtn.setBounds(300, 360, 120, 35);
        backBtn.setFocusPainted(false);
        getContentPane().add(backBtn);

        submitBtn.addActionListener(e -> {
            String username = userField.getText().trim();
            String oldPwd = new String(oldPwdField.getPassword());
            String pwd1 = new String(pwdField.getPassword());
            String pwd2 = new String(pwdField2.getPassword());
            if (username.isEmpty()) { JOptionPane.showMessageDialog(this, "请输入账号"); return; }
            int idx = findIndex(list, username);
            if (idx == -1) { JOptionPane.showMessageDialog(this, "用户名不存在"); return; }
            if (!oldPwd.equals(list.get(idx).getPassword())) { JOptionPane.showMessageDialog(this, "旧密码不正确"); return; }
            if (!checkLen(pwd1, 3, 8)) { JOptionPane.showMessageDialog(this, "密码长度3-8位"); return; }
            if (!checkPassword(pwd1)) { JOptionPane.showMessageDialog(this, "密码须字母+数字组合"); return; }
            if (!pwd1.equals(pwd2)) { JOptionPane.showMessageDialog(this, "两次密码不一致"); return; }
            list.get(idx).setPassword(pwd1);
            JOptionPane.showMessageDialog(this, "密码修改成功！");
            getContentPane().removeAll();
            login(list);
        });

        backBtn.addActionListener(e -> {
            getContentPane().removeAll();
            login(list);
        });

        backGround();
        getContentPane().revalidate();
        getContentPane().repaint();
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
}
