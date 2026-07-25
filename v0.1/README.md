# 🎮 WordFight — 文字格斗游戏

Java 控制台回合制文字 RPG，支持账号注册登录、角色属性分配、多种技能战斗、敌人成长与连胜奖励。

## 项目结构

```
src/
├── App.java                          # 入口
└── com/xiaogan/
    ├── bean/
    │   ├── Account.java              # 账号模型
    │   ├── GameCharacter.java        # 角色基类（HP/ATK/DEF）
    │   ├── Player.java               # 玩家（继承 + 技能接口）
    │   ├── Enemy.java                # 敌人（4种类型 + AI）
    │   ├── Skill.java                # 技能模型
    │   └── UseSkills.java            # 技能接口
    └── ui/
        ├── Login.java                # 登录/注册/验证码
        ├── MainMenuActivity.java     # 主菜单
        ├── Game.java                 # 角色创建 + 战斗循环
        └── ShowHp.java               # HP条渲染
```

## 功能

- **账号系统**：注册（用户名/密码校验）、登录（验证码、3次锁定）
- **角色创建**：20属性点自由分配到生命/攻击/防御
- **回合制战斗**：3种玩家技能 + 4种敌人（随机AI行为）
- **敌人类型**：初级战士、敏捷刺客、重装坦克（含防御姿态）、神秘法师
- **连胜成长**：每3胜 HP+30 / ATK+3 / DEF+2；敌人随轮数变强
- **战后恢复**：胜利随机恢复20~40 HP
- **HP可视化**：████░░░░ 进度条

## 玩家技能

| 技能 | 消耗 | 效果 |
|------|------|------|
| 普通攻击 | 无 | ATK × 1.0 |
| 强力一击 | 10 HP | ATK × 1.8 |
| 生命汲取 | 10 HP | 恢复 0~20 HP |

注意：别自己把自己弄死了！！！！

## 运行

```bash
# 编译
javac -encoding UTF-8 -d out src/App.java src/com/xiaogan/bean/*.java src/com/xiaogan/ui/*.java

# 运行
java -cp out App
```

需要 JDK 17+。

## 技术栈

Java · OOP（继承/接口/多态）· 控制台 I/O

## 许可

练手项目，随意使用。
