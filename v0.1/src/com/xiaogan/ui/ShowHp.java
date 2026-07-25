package com.xiaogan.ui;

import com.xiaogan.bean.GameCharacter;

public class ShowHp {
    public static String showHp(GameCharacter gameCharacter) {
        StringBuilder sb = new StringBuilder();
        String hp = "████████████████████";
        int rateHp = Math.max(0, (int)(gameCharacter.getHP() * 20 / gameCharacter.getMaxHP()));
        sb.append(hp.substring(0, rateHp));
        for (int i = sb.length(); i < hp.length(); i++) {
            sb.append(" ");
        }
        return sb.toString();
    }
}
