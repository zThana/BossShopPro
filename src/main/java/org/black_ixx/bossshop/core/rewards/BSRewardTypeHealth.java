package org.black_ixx.bossshop.core.rewards;

import org.black_ixx.bossshop.core.BSBuy;
import org.black_ixx.bossshop.managers.ClassManager;
import org.black_ixx.bossshop.managers.misc.InputReader;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.math.BigDecimal;

public class BSRewardTypeHealth extends BSRewardTypeNumber{

    @Override
    public Object createObject(Object o, boolean force_final_state) {
        return InputReader.getDouble(o,-1);
    }

    @Override
    public boolean validityCheck(String item_name, Object o) {
        if (o instanceof Double) {
            return true;
        }
        ClassManager.manager.getBugFinder().severe("Was not able to create ShopItem " + item_name + "! The reward object needs to be a valid Double number. Example: '7.14' or '12.00'.");
        return false;
    }

    @Override
    public void enableType() {

    }

    @Override
    public boolean canBuy(Player p, BSBuy buy, boolean message_if_no_success, Object reward, ClickType clickType) {
        return true;
    }

    @Override
    public String getDisplayReward(Player p, BSBuy buy, Object reward, ClickType clickType) {
        return null;
    }

    @Override
    public String[] createNames() {
        return new String[]{"health","heal"};
    }

    @Override
    public boolean mightNeedShopUpdate() {
        return true;
    }

    @Override
    public boolean isIntegerValue() {
        return false;
    }

    @Override
    public void giveReward(Player p, BSBuy buy, Object reward, ClickType clickType, int multiplier) {
        BigDecimal bd = new BigDecimal((String) reward);
        BigDecimal bd2 = bd.add(BigDecimal.valueOf(p.getHealth()));
        p.setHealth(bd2.doubleValue());
    }
}
