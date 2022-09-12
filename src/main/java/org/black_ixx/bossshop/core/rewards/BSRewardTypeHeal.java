package org.black_ixx.bossshop.core.rewards;

import org.black_ixx.bossshop.core.BSBuy;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class BSRewardTypeHeal extends BSRewardTypeNumber{

    @Override
    public Object createObject(Object o, boolean force_final_state) {
        return null;
    }

    @Override
    public boolean validityCheck(String item_name, Object o) {
        return true;
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
        return new String[]{"heal"};
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
        p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
    }
}
