package org.black_ixx.bossshop.core.rewards;

import org.black_ixx.bossshop.core.BSBuy;
import org.black_ixx.bossshop.managers.ClassManager;
import org.black_ixx.bossshop.managers.misc.InputReader;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class BSRewardTypeTeleportWorld extends BSRewardType{
    @Override
    public Object createObject(Object o, boolean force_final_state) {
        return InputReader.readString(o, true);
    }

    @Override
    public boolean validityCheck(String item_name, Object o) {
        if (o != null) {
            return true;
        }
        ClassManager.manager.getBugFinder().severe("Was not able to create ShopItem " + item_name + "! The reward object needs to be a world name.");
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
    public void giveReward(Player p, BSBuy buy, Object reward, ClickType clickType) {
        String world = (String) reward;
        World w = Bukkit.getWorld(world);
        if(w==null){
            ClassManager.manager.getMessageHandler().sendMessage("Main.WorldNotExisting",p);
        }
        assert w != null;
        p.teleport(w.getSpawnLocation());
    }

    @Override
    public String getDisplayReward(Player p, BSBuy buy, Object reward, ClickType clickType) {
        return ClassManager.manager.getMessageHandler().get("Display.TeleportWorld").replace("%world%",(String) reward);
    }

    @Override
    public String[] createNames() {
        return new String[]{"world","teleportworld"};
    }

    @Override
    public boolean mightNeedShopUpdate() {
        return true;
    }
}
