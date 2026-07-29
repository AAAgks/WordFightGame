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

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FightingGame extends JFrame {
    JMenuItem backMainItem = new JMenuItem("返回主菜单");
    JMenuItem replayGame = new JMenuItem("重新开始游戏");
    JMenuItem reLoginItem = new JMenuItem("重新登录");
    JMenuItem exitGameItem = new JMenuItem("退出游戏");

    // 战斗数据
    private HeroCharacter player;
    private String username;
    private JTextArea logArea;
    private JLabel enemyNameLabel, enemyHpLabel;
    private JLabel playerHpLabel, playerMpLabel, playerNameLabel;
    private JPanel actionPanel;
    private JLabel roundLabel;
    private EnemyCharacter currentEnemy;
    private ArrayList<EnemyCharacter> enemies;
    private int round, wins, battleRound;

    private Login loginWindow;

    public FightingGame(String username, Login loginWindow) {
        this.username = username;
        this.loginWindow = loginWindow;
        initJFrame();
        initMenuBar();
        StartMenu(username);
    }

    private void initJFrame() {
        this.setSize(603, 680);
        this.setAlwaysOnTop(true);
        this.setTitle("文字格斗游戏");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);
    }

    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        JMenu function = new JMenu("功能");
        JMenu aboutMenu = new JMenu("关于我们");
        menuBar.add(function);
        menuBar.add(aboutMenu);
        function.add(backMainItem);
        function.add(replayGame);
        function.add(reLoginItem);
        function.add(exitGameItem);
        backMainItem.addActionListener(e -> {
            getContentPane().removeAll();
            StartMenu(username);
        });
        replayGame.addActionListener(e -> {
            getContentPane().removeAll();
            createPlayer(username);
        });
        reLoginItem.addActionListener(e -> {
            dispose();
            loginWindow.setVisible(true);
        });
        exitGameItem.addActionListener(e -> System.exit(0));
    }

    private void StartMenu(String username) {
        JLabel topLine = new JLabel("╔══════════════════════╗", JLabel.CENTER);
        topLine.setBounds(50, 40, 500, 30);
        topLine.setFont(new Font("宋体", Font.BOLD, 18));
        topLine.setForeground(Color.BLACK);
        getContentPane().add(topLine);

        JLabel welcome = new JLabel("🎮 " + username + " 欢迎来到文字格斗游戏 🎮", JLabel.CENTER);
        welcome.setBounds(50, 70, 500, 30);
        welcome.setFont(new Font("宋体", Font.BOLD, 18));
        welcome.setForeground(Color.BLACK);
        getContentPane().add(welcome);

        JLabel bottomLine = new JLabel("╚══════════════════════╝", JLabel.CENTER);
        bottomLine.setBounds(50, 100, 500, 30);
        bottomLine.setFont(new Font("宋体", Font.BOLD, 18));
        bottomLine.setForeground(Color.BLACK);
        getContentPane().add(bottomLine);

        JButton startButton = new JButton("开始游戏");
        startButton.setBounds(200, 300, 200, 50);
        startButton.setFocusPainted(false);
        getContentPane().add(startButton);
        startButton.addActionListener(e -> {
            getContentPane().removeAll();
            createPlayer(username);
        });

        JButton backLogin = new JButton("返回登录界面");
        backLogin.setBounds(200, 400, 200, 50);
        backLogin.setFocusPainted(false);
        getContentPane().add(backLogin);
        backLogin.addActionListener(e -> {
            dispose();
            loginWindow.setVisible(true);
        });

        JButton exitGame = new JButton("退出游戏");
        exitGame.setBounds(200, 500, 200, 50);
        exitGame.setFocusPainted(false);
        getContentPane().add(exitGame);
        exitGame.addActionListener(e -> {
            dispose();
            System.exit(0);
        });

        backGround();
        setVisible(true);
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    private void backGround() {
        JLabel loginLabel = new JLabel(new ImageIcon("image/backGround/backGround.jpeg"));
        loginLabel.setBounds(0, 0, 603, 680);
        getContentPane().add(loginLabel);
    }

    private void createPlayer(String username) {
        getContentPane().removeAll();
        JLabel titleLabel = new JLabel("创建角色", JLabel.CENTER);
        titleLabel.setBounds(50, 130, 500, 30);
        titleLabel.setFont(new Font("宋体", Font.BOLD, 18));
        titleLabel.setForeground(Color.BLACK);
        getContentPane().add(titleLabel);

        JLabel name = new JLabel("您的角色名为: " + username, JLabel.CENTER);
        name.setBounds(0, 170, 600, 30);
        name.setFont(new Font("宋体", Font.BOLD, 18));
        name.setForeground(Color.BLACK);
        getContentPane().add(name);

        JLabel pointLabel = new JLabel("请分配属性点 (共: 20)", JLabel.CENTER);
        pointLabel.setBounds(0, 210, 600, 30);
        pointLabel.setFont(new Font("宋体", Font.BOLD, 18));
        pointLabel.setForeground(Color.BLACK);
        getContentPane().add(pointLabel);

        String[] statNames = {"生命值", "蓝量", "攻击力", "防御力"};
        int[] baseValues = {120, 50, 15, 5};
        String[] units = {"HP", "MP", "ATK", "DEF"};
        int[] perPoint = {10, 10, 2, 2};
        int[] assigned = {0, 0, 0, 0};
        final int[] remaining = {20};
        final JLabel[] valueLabels = new JLabel[4];
        final JLabel[] assignedLabels = new JLabel[4];

        for (int i = 0; i < 4; i++) {
            int colX = 30 + i * 140;
            final int idx = i;

            JLabel nameLabel = new JLabel(statNames[i], JLabel.CENTER);
            nameLabel.setBounds(colX, 260, 120, 25);
            nameLabel.setFont(new Font("宋体", Font.BOLD, 16));
            getContentPane().add(nameLabel);

            JLabel bonusLabel = new JLabel("每点+" + perPoint[i] + " " + units[i], JLabel.CENTER);
            bonusLabel.setBounds(colX, 282, 120, 18);
            bonusLabel.setFont(new Font("宋体", Font.PLAIN, 11));
            bonusLabel.setForeground(Color.GRAY);
            getContentPane().add(bonusLabel);

            JLabel valueLabel = new JLabel(String.valueOf(baseValues[i]), JLabel.CENTER);
            valueLabel.setBounds(colX, 302, 120, 25);
            valueLabel.setFont(new Font("宋体", Font.BOLD, 16));
            getContentPane().add(valueLabel);
            valueLabels[i] = valueLabel;

            JLabel assignedLabel = new JLabel("已分配: 0", JLabel.CENTER);
            assignedLabel.setBounds(colX, 325, 120, 18);
            assignedLabel.setFont(new Font("宋体", Font.PLAIN, 11));
            assignedLabel.setForeground(Color.GRAY);
            getContentPane().add(assignedLabel);
            assignedLabels[i] = assignedLabel;

            JButton plusBtn = new JButton("+");
            plusBtn.setBounds(colX + 10, 348, 45, 28);
            plusBtn.setFocusPainted(false);
            plusBtn.setMargin(new Insets(0, 0, 0, 0));
            getContentPane().add(plusBtn);

            JButton minusBtn = new JButton("-");
            minusBtn.setBounds(colX + 60, 348, 45, 28);
            minusBtn.setFocusPainted(false);
            minusBtn.setMargin(new Insets(0, 0, 0, 0));
            getContentPane().add(minusBtn);

            JButton allBtn = new JButton("All");
            allBtn.setBounds(colX + 30, 385, 60, 22);
            allBtn.setFont(new Font("宋体", Font.PLAIN, 10));
            allBtn.setFocusPainted(false);
            getContentPane().add(allBtn);
            allBtn.addActionListener(e -> {
                if (remaining[0] == 0 && assigned[idx] == 0) {
                    for (int j = 0; j < 4; j++) {
                        remaining[0] += assigned[j];
                        assigned[j] = 0;
                    }
                    for (int j = 0; j < 4; j++) {
                        valueLabels[j].setText(String.valueOf(baseValues[j]));
                        assignedLabels[j].setText("已分配: 0");
                    }
                    pointLabel.setText("请分配属性点 (共: " + remaining[0] + ")");
                    return;
                }
                if (remaining[0] > 0) {
                    assigned[idx] += remaining[0];
                    remaining[0] = 0;
                    valueLabels[idx].setText(String.valueOf(baseValues[idx] + assigned[idx] * perPoint[idx]));
                    assignedLabels[idx].setText("已分配: " + assigned[idx]);
                    pointLabel.setText("请分配属性点 (剩余: 0)");
                }
            });

            plusBtn.addActionListener(e -> {
                if (remaining[0] > 0) {
                    assigned[idx]++;
                    remaining[0]--;
                    valueLabels[idx].setText(String.valueOf(baseValues[idx] + assigned[idx] * perPoint[idx]));
                    assignedLabels[idx].setText("已分配: " + assigned[idx]);
                    pointLabel.setText("请分配属性点 (剩余: " + remaining[0] + ")");
                }
            });
            minusBtn.addActionListener(e -> {
                if (assigned[idx] > 0) {
                    assigned[idx]--;
                    remaining[0]++;
                    valueLabels[idx].setText(String.valueOf(baseValues[idx] + assigned[idx] * perPoint[idx]));
                    assignedLabels[idx].setText("已分配: " + assigned[idx]);
                    pointLabel.setText("请分配属性点 (剩余: " + remaining[0] + ")");
                }
            });
        }

        JButton autoBtn = new JButton("一键分配");
        autoBtn.setBounds(50, 420, 130, 40);
        autoBtn.setFocusPainted(false);
        getContentPane().add(autoBtn);
        autoBtn.addActionListener(e -> {
            for (int i = 0; i < 4; i++) {
                remaining[0] += assigned[i];
                assigned[i] = 0;
            }
            int each = remaining[0] / 4;
            for (int i = 0; i < 4; i++) {
                assigned[i] = each;
                remaining[0] -= each;
                valueLabels[i].setText(String.valueOf(baseValues[i] + assigned[i] * perPoint[i]));
                assignedLabels[i].setText("已分配: " + assigned[i]);
            }
            pointLabel.setText("请分配属性点 (剩余: " + remaining[0] + ")");
        });

        JButton confirmBtn = new JButton("确认创建");
        confirmBtn.setBounds(220, 420, 200, 40);
        confirmBtn.setFocusPainted(false);
        getContentPane().add(confirmBtn);
        confirmBtn.addActionListener(e -> {
            if (remaining[0] > 0) {
                JOptionPane.showMessageDialog(this, "还有 " + remaining[0] + " 点未分配！", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            this.player = new HeroCharacter(username,
                    baseValues[0] + assigned[0] * perPoint[0],
                    baseValues[1] + assigned[1] * perPoint[1],
                    baseValues[2] + assigned[2] * perPoint[2],
                    baseValues[3] + assigned[3] * perPoint[3]);
            for (Skill s : SkillPool.ALL_SKILLS) {
                this.player.addSkill(s);
            }
            JOptionPane.showMessageDialog(this, "角色创建成功！\n" + player.show());
            getContentPane().removeAll();
            startFight();
        });

        backGround();
        getContentPane().repaint();
    }

    private void startFight() {
        enemies = new ArrayList<>();
        for (EnemyTemplate t : EnemyFactory.getAll()) {
            enemies.add(t.create());
        }
        round = 0;
        wins = 0;
        drawBattleUI();
    }

    private void drawBattleUI() {
        round++;
        // 战前休整
        if (round > 1) {
            int preHeal = (int)(player.getMaxHp() * 0.15);
            player.heal(preHeal);
            appendLog("💚 战前休整，恢复了 " + preHeal + " HP");
        }

        roundLabel = new JLabel("⚔️ 第 " + round + " 场战斗", JLabel.CENTER);
        roundLabel.setBounds(0, 30, 600, 30);
        roundLabel.setFont(new Font("宋体", Font.BOLD, 20));
        roundLabel.setForeground(Color.BLACK);
        getContentPane().add(roundLabel);

        Random r = new Random();
        currentEnemy = enemies.get(r.nextInt(enemies.size()));
        if (round > 1) {
            currentEnemy.setMaxHp(currentEnemy.getMaxHp() + 8);
            currentEnemy.setHp(currentEnemy.getMaxHp());
            currentEnemy.setAttack(currentEnemy.getAttack() + 2);
            currentEnemy.setDefense(currentEnemy.getDefense() + 1);
            currentEnemy.setDefending(false);
        }

        enemyNameLabel = new JLabel("对手：" + currentEnemy.getName()
                + "  |  ATK: " + currentEnemy.getAttack()
                + "  |  DEF: " + currentEnemy.getDefense(), JLabel.CENTER);
        enemyNameLabel.setBounds(0, 65, 600, 25);
        enemyNameLabel.setFont(new Font("宋体", Font.BOLD, 16));
        getContentPane().add(enemyNameLabel);

        enemyHpLabel = new JLabel("HP: " + getBar(currentEnemy.getHp(), currentEnemy.getMaxHp(), 30)
                + "  " + currentEnemy.getHp() + "/" + currentEnemy.getMaxHp(), JLabel.CENTER);
        enemyHpLabel.setBounds(0, 92, 600, 25);
        enemyHpLabel.setFont(new Font("宋体", Font.PLAIN, 15));
        getContentPane().add(enemyHpLabel);

        JLabel sep = new JLabel("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━", JLabel.CENTER);
        sep.setBounds(0, 125, 600, 20);
        sep.setForeground(Color.LIGHT_GRAY);
        getContentPane().add(sep);

        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setFont(new Font("宋体", Font.PLAIN, 13));
        logArea.setBackground(new Color(250, 250, 250));
        JScrollPane scrollPane = new JScrollPane(logArea);
        scrollPane.setBounds(40, 150, 520, 180);
        getContentPane().add(scrollPane);

        appendLog("⚔️ 第 " + round + " 场战斗开始！对手：" + currentEnemy.getName());
        battleRound = 1;

        playerNameLabel = new JLabel("玩家：" + player.getName()
                + "  |  ATK: " + player.getAttack()
                + "  |  DEF: " + player.getDefense(), JLabel.CENTER);
        playerNameLabel.setBounds(0, 340, 600, 20);
        playerNameLabel.setFont(new Font("宋体", Font.BOLD, 15));
        getContentPane().add(playerNameLabel);

        playerHpLabel = new JLabel("HP: " + getBar(player.getHp(), player.getMaxHp(), 25)
                + "  " + player.getHp() + "/" + player.getMaxHp(), JLabel.CENTER);
        playerHpLabel.setBounds(0, 363, 600, 20);
        playerHpLabel.setFont(new Font("宋体", Font.PLAIN, 14));
        double hpPct = (double) player.getHp() / Math.max(1, player.getMaxHp());
        playerHpLabel.setForeground(hpPct > 0.5 ? new Color(0, 150, 0) : hpPct > 0.2 ? new Color(200, 120, 0) : Color.RED);
        getContentPane().add(playerHpLabel);

        playerMpLabel = new JLabel("MP: " + getBar(player.getMp(), player.getMaxMp(), 25)
                + "  " + player.getMp() + "/" + player.getMaxMp(), JLabel.CENTER);
        playerMpLabel.setBounds(0, 385, 600, 20);
        playerMpLabel.setFont(new Font("宋体", Font.PLAIN, 14));
        getContentPane().add(playerMpLabel);

        actionPanel = new JPanel(null);
        actionPanel.setBounds(40, 430, 520, 150);
        getContentPane().add(actionPanel);
        showMainActions();

        backGround();
        getContentPane().repaint();
    }

    private void showMainActions() {
        actionPanel.removeAll();
        JButton atkBtn = new JButton("普通攻击");
        atkBtn.setBounds(10, 5, 120, 35);
        atkBtn.setFocusPainted(false);
        atkBtn.addActionListener(e -> playerAttack());
        actionPanel.add(atkBtn);
        JButton skillBtn = new JButton("使用技能");
        skillBtn.setBounds(140, 5, 120, 35);
        skillBtn.setFocusPainted(false);
        skillBtn.addActionListener(e -> showSkillActions());
        actionPanel.add(skillBtn);
        JButton itemBtn = new JButton("使用道具");
        itemBtn.setBounds(270, 5, 120, 35);
        itemBtn.setFocusPainted(false);
        itemBtn.addActionListener(e -> showItemActions());
        actionPanel.add(itemBtn);
        JButton infoBtn = new JButton("查看属性");
        infoBtn.setBounds(400, 5, 110, 35);
        infoBtn.setFocusPainted(false);
        infoBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, player.show(), "角色属性", JOptionPane.INFORMATION_MESSAGE));
        actionPanel.add(infoBtn);
        actionPanel.revalidate();
        actionPanel.repaint();
    }

    private void showSkillActions() {
        actionPanel.removeAll();
        ArrayList<Skill> skills = player.getSkills();
        int rowH = 30;
        JPanel skillList = new JPanel(null);
        skillList.setPreferredSize(new Dimension(500, skills.size() * (rowH + 2) + 5));
        for (int i = 0; i < skills.size(); i++) {
            final int idx = i;
            Skill s = skills.get(i);
            boolean canAfford = player.getMp() >= s.getCostMp();
            JButton btn = new JButton(s.getSkillName() + " [" + s.getCostMp() + "MP]");
            btn.setBounds(5, 3 + i * (rowH + 2), 170, rowH);
            btn.setFont(new Font("宋体", Font.PLAIN, 12));
            btn.setFocusPainted(false);
            btn.setEnabled(canAfford);
            btn.addActionListener(e -> {
                if (useSkill(idx)) showMainActions(); else showSkillActions();
            });
            skillList.add(btn);
            JLabel desc = new JLabel(s.getDescription() + "  (当前MP: " + player.getMp() + "/" + player.getMaxMp() + ")");
            desc.setBounds(180, 3 + i * (rowH + 2), 330, rowH);
            desc.setFont(new Font("宋体", Font.PLAIN, 12));
            desc.setForeground(Color.DARK_GRAY);
            skillList.add(desc);
        }
        JScrollPane sp = new JScrollPane(skillList);
        sp.setBounds(0, 0, 520, 110);
        sp.getVerticalScrollBar().setUnitIncrement(20);
        actionPanel.add(sp);
        JButton backBtn = new JButton("返回");
        backBtn.setBounds(200, 115, 120, 30);
        backBtn.setFocusPainted(false);
        backBtn.addActionListener(e -> showMainActions());
        actionPanel.add(backBtn);
        actionPanel.revalidate();
        actionPanel.repaint();
    }

    private void showItemActions() {
        actionPanel.removeAll();
        if (player.getBag().isEmpty()) {
            JLabel empty = new JLabel("背包空空如也！", JLabel.CENTER);
            empty.setBounds(0, 10, 520, 80);
            actionPanel.add(empty);
        } else {
            ArrayList<Item> bag = player.getBag();
            int rowH = 30;
            JPanel itemList = new JPanel(null);
            itemList.setPreferredSize(new Dimension(500, bag.size() * (rowH + 2) + 5));
            for (int i = 0; i < bag.size(); i++) {
                final int idx = i;
                Item it = bag.get(i);
                JButton btn = new JButton(it.getName());
                btn.setBounds(5, 3 + i * (rowH + 2), 150, rowH);
                btn.setFont(new Font("宋体", Font.PLAIN, 12));
                btn.setFocusPainted(false);
                btn.addActionListener(e -> { useItemDirect(idx); showMainActions(); });
                itemList.add(btn);
                JLabel desc = new JLabel(it.getDescribe());
                desc.setBounds(160, 3 + i * (rowH + 2), 350, rowH);
                desc.setFont(new Font("宋体", Font.PLAIN, 12));
                desc.setForeground(Color.DARK_GRAY);
                itemList.add(desc);
            }
            JScrollPane sp = new JScrollPane(itemList);
            sp.setBounds(0, 0, 520, 110);
            sp.getVerticalScrollBar().setUnitIncrement(20);
            actionPanel.add(sp);
        }
        JButton backBtn = new JButton("返回");
        backBtn.setBounds(200, 115, 120, 30);
        backBtn.setFocusPainted(false);
        backBtn.addActionListener(e -> showMainActions());
        actionPanel.add(backBtn);
        actionPanel.revalidate();
        actionPanel.repaint();
    }

    private void useItemDirect(int idx) {
        ArrayList<Item> bag = player.getBag();
        if (idx < 0 || idx >= bag.size()) return;
        Item item = bag.get(idx);
        for (ItemEffect effect : item.getEffectMap().keySet()) {
            int val = item.getEffectValue(effect);
            switch (effect) {
                case ADD_HP: player.setHp(player.getHp() + val); break;
                case HEAL_HP_PCT: player.heal(player.getMaxHp() * val / 100); break;
                case ADD_MP: player.setMp(player.getMp() + val); break;
                case ADD_ATK: player.addAtk(val); break;
                case ADD_DEF: player.addDef(val); break;
                case HEAL_MP_PCT: player.recoverMp(player.getMaxMp() * val / 100); break;
            }
        }
        bag.remove(idx);
        appendLog("🎒 使用了道具：" + item.getName() + "，回合结束");
        refreshUI();
        enemyTurn();
    }

    private String getBar(int current, int max, int length) {
        int filled = Math.max(0, current) * length / Math.max(1, max);
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < length; i++) sb.append(i < filled ? "█" : "░");
        sb.append("]");
        return sb.toString();
    }

    private void appendLog(String msg) {
        logArea.append(msg + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    private void refreshUI() {
        roundLabel.setText("⚔️ 第 " + round + " 场战斗");
        enemyNameLabel.setText("对手：" + currentEnemy.getName()
                + "  |  ATK: " + currentEnemy.getAttack()
                + "  |  DEF: " + currentEnemy.getDefense());
        enemyHpLabel.setText("HP: " + getBar(currentEnemy.getHp(), currentEnemy.getMaxHp(), 30)
                + "  " + currentEnemy.getHp() + "/" + currentEnemy.getMaxHp());
        playerNameLabel.setText("玩家：" + player.getName()
                + "  |  ATK: " + player.getAttack()
                + "  |  DEF: " + player.getDefense());
        playerHpLabel.setText("HP: " + getBar(player.getHp(), player.getMaxHp(), 25)
                + "  " + player.getHp() + "/" + player.getMaxHp());
        double hpPct = (double) player.getHp() / Math.max(1, player.getMaxHp());
        playerHpLabel.setForeground(hpPct > 0.5 ? new Color(0, 150, 0) : hpPct > 0.2 ? new Color(200, 120, 0) : Color.RED);
        playerMpLabel.setText("MP: " + getBar(player.getMp(), player.getMaxMp(), 25)
                + "  " + player.getMp() + "/" + player.getMaxMp());
        if (player.isDefending()) {
            playerNameLabel.setText(playerNameLabel.getText() + " [DEFENDING]");
        }
        if (currentEnemy.isDefending()) {
            enemyNameLabel.setText(enemyNameLabel.getText() + " [DEFENDING]");
        }
    }

    private void playerAttack() {
        appendLog("━━━ 第 " + battleRound + " 回合 ━━━");
        boolean enemyWasDefending = currentEnemy.isDefending();
        currentEnemy.setDefending(false);
        int dmg = calculateDamage(player.getAttack(), currentEnemy.getDefense());
        int displayDmg = dmg;
        if (enemyWasDefending) {
            displayDmg = Math.max(dmg / 2, 1);
            appendLog("🛡️ " + currentEnemy.getName() + " 处于防御姿态，伤害减半！");
        }
        currentEnemy.takeDamage(displayDmg);
        appendLog("⚔️ 你对 " + currentEnemy.getName() + " 使用了[普通攻击]，造成 " + displayDmg + " 点伤害！");
        if (!currentEnemy.isAlive()) {
            onEnemyDefeated();
        } else {
            enemyTurn();
        }
    }

    private void enemyTurn() {
        if (!currentEnemy.isAlive()) return;
        boolean playerWasDefending = player.isDefending();
        player.setDefending(false);
        Random r = new Random();
        boolean useSkill = r.nextBoolean();
        if (useSkill && currentEnemy.isDefenseSkill()) {
            currentEnemy.setDefending(true);
            appendLog("🛡️ " + currentEnemy.getName() + " 进入了[" + currentEnemy.getSkillName() + "]姿态！");
        } else if (useSkill) {
            int raw = 0;
            for (int i = 0; i < currentEnemy.getSkillHits(); i++) {
                raw += calculateDamage((int) (currentEnemy.getAttack() * currentEnemy.getSkillRate()), player.getDefense());
            }
            int dmg = playerWasDefending ? Math.max(raw / 2, 1) : raw;
            if (playerWasDefending) appendLog("🛡️ 你处于防御姿态，伤害减半！");
            player.takeDamage(dmg);
            appendLog("💥 " + currentEnemy.getName() + " 对你使用了[" + currentEnemy.getSkillName() + "]，造成 " + dmg + " 点伤害！");
        } else {
            int raw = calculateDamage(currentEnemy.getAttack(), player.getDefense());
            int dmg = playerWasDefending ? Math.max(raw / 2, 1) : raw;
            if (playerWasDefending) appendLog("🛡️ 你处于防御姿态，伤害减半！");
            player.takeDamage(dmg);
            appendLog("💥 " + currentEnemy.getName() + " 对你使用了[普通攻击]，造成 " + dmg + " 点伤害！");
        }
        if (player.isAlive() && (double) player.getHp() / player.getMaxHp() <= 0.2) {
            appendLog(String.format(BattleTip.HP_LACK.getRandomTip(), player.getName()));
        }
        if (!player.isAlive()) {
            onPlayerDefeated();
        } else {
            refreshUI();
            battleRound++;
            appendLog("━━━ 第 " + battleRound + " 回合 ━━━");
        }
    }

    private int calculateDamage(int attack, int defense) {
        int damage = attack * 100 / (100 + defense);
        return Math.max(damage, 1);
    }

    private boolean useSkill(int idx) {
        Skill skill = player.getSkills().get(idx);
        if (player.getMp() < skill.getCostMp()) {
            appendLog(String.format(BattleTip.MP_LACK.getRandomTip(), player.getName(), skill.getSkillName()));
            return false;
        }
        player.consumeMp(skill.getCostMp());
        switch (skill.getType()) {
            case ATTACK:
                boolean enemyWasDef = currentEnemy.isDefending();
                currentEnemy.setDefending(false);
                int total = 0;
                for (int i = 0; i < skill.getHits(); i++) {
                    total += calculateDamage((int) (player.getAttack() * skill.getAtkRate()), currentEnemy.getDefense());
                }
                int displayTotal = total;
                if (enemyWasDef) {
                    displayTotal = Math.max(total / 2, 1);
                    appendLog("🛡️ " + currentEnemy.getName() + " 处于防御姿态，伤害减半！");
                }
                currentEnemy.takeDamage(displayTotal);
                appendLog("⚔️ 你对 " + currentEnemy.getName() + " 使用了【" + skill.getSkillName() + "】，造成 " + displayTotal + " 点伤害！");
                break;
            case HEAL_HP:
                int heal = (int) (player.getMaxHp() * skill.getAtkRate());
                player.heal(heal);
                appendLog("💚 你使用了【" + skill.getSkillName() + "】，恢复 " + heal + " HP");
                break;
            case HEAL_MP:
                int recover = (int) (player.getMaxMp() * skill.getAtkRate());
                player.recoverMp(recover);
                appendLog("💙 你使用了【" + skill.getSkillName() + "】，恢复 " + recover + " MP");
                break;
            case BUFF:
                player.addAtk((int) skill.getAtkRate());
                appendLog("🔥 你使用了【" + skill.getSkillName() + "】，永久 +" + (int) skill.getAtkRate() + " ATK");
                break;
            case DEFENSE:
                player.setDefending(true);
                appendLog("🛡️ 你进入了【" + skill.getSkillName() + "】姿态！");
                break;
        }
        if (!currentEnemy.isAlive()) {
            onEnemyDefeated();
        } else {
            enemyTurn();
        }
        return true;
    }

    private void onEnemyDefeated() {
        String tip = BattleTip.ENEMY_DEATH.getRandomTip();
        appendLog(String.format(tip, currentEnemy.getName(), player.getName()));
        appendLog("🎉 你击败了 " + currentEnemy.getName() + "！");
        wins++;
        int healHp = player.getMaxHp() * 20 / 100;
        int healMp = player.getMaxMp() * 20 / 100;
        player.heal(healHp);
        player.recoverMp(healMp);
        appendLog("💚 战后恢复 " + healHp + " HP, " + healMp + " MP");
        if (new Random().nextInt(10) < 2) {
            Item drop = ItemPool.ALL_ITEMS.get(new Random().nextInt(ItemPool.ALL_ITEMS.size()));
            player.addItem(drop);
            appendLog("🎁 获得道具：" + drop.getName() + " | " + drop.getDescribe());
        }
        appendLog("🌟 当前属性：" + player.show());
        if (wins % 3 == 0) {
            player.setMaxHp(player.getMaxHp() + 30);
            player.setMaxMp(player.getMaxMp() + 20);
            player.setAttack(player.getAttack() + 5);
            player.setDefense(player.getDefense() + 3);
            appendLog("⭐ 属性提升！HP+30 MP+20 ATK+5 DEF+3");
        }
        refreshUI();
        int choice = JOptionPane.showOptionDialog(this,
                "🎉 击败 " + currentEnemy.getName() + "！\n当前胜场：" + wins + "\n" + player.show(),
                "胜利", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, new String[]{"继续战斗", "返回主菜单"}, "继续战斗");
        if (choice == 0) {
            nextBattle();
        } else {
            getContentPane().removeAll();
            StartMenu(username);
        }
    }

    private void onPlayerDefeated() {
        appendLog(String.format(BattleTip.DEATH.getRandomTip(), player.getName()));
        appendLog("💀 你被 " + currentEnemy.getName() + " 击败了...");
        refreshUI();
        JOptionPane.showMessageDialog(this, "游戏结束！\n共击败 " + wins + " 个敌人", "战败", JOptionPane.ERROR_MESSAGE);
        getContentPane().removeAll();
        StartMenu(player.getName());
    }

    private void nextBattle() {
        getContentPane().removeAll();
        drawBattleUI();
    }
}
