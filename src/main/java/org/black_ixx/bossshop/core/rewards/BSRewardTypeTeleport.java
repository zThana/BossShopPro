package org.black_ixx.bossshop.core.rewards;

import org.black_ixx.bossshop.core.BSBuy;
import org.black_ixx.bossshop.managers.ClassManager;
import org.black_ixx.bossshop.managers.misc.InputReader;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public class BSRewardTypeTeleport extends BSRewardType{
    @Override
    public Object createObject(Object o, boolean force_final_state) {
        return InputReader.readString(o, true);
    }

    @Override
    public boolean validityCheck(String item_name, Object o) {
        boolean b = false;
        if (o != null) {
            String[] objs = ((String)o).trim().split("[|]");
            if(objs.length == 4 || objs.length == 6){
                b = true;
            }else{
                ClassManager.manager.getBugFinder().severe("Was not able to create ShopItem " + item_name + "! The reward object needs to be written in the format of 'WorldName|X axis|Y axis|Z axis' or 'WorldName|X axis|Y axis|Z axis|yaw|pitch'.");
            }
        }else {
            ClassManager.manager.getBugFinder().severe("Was not able to create ShopItem " + item_name + "! The reward object needs to be written in the format of 'WorldName|X axis|Y axis|Z axis' or 'WorldName|X axis|Y axis|Z axis|yaw|pitch'.");
        }
        return b;
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
        String[] rewards = ((String)reward).trim().split("[|]");
        World w = Bukkit.getWorld(rewards[0]);
        Location loc;
        if(rewards.length == 6){
            loc = new Location(w, Double.parseDouble(rewards[1]), Double.parseDouble(rewards[2]), Double.parseDouble(rewards[3]),
                    Float.parseFloat(rewards[4]), Float.parseFloat(rewards[5]));
        }else {
            loc = new Location(w, Double.parseDouble(rewards[1]), Double.parseDouble(rewards[2]), Double.parseDouble(rewards[3]));
        }
        p.teleport(loc);
    }

    @Override
    public String getDisplayReward(Player p, BSBuy buy, Object reward, ClickType clickType) {
        String[] rewards = ((String)reward).trim().split("[|]");
        return ClassManager.manager.getMessageHandler().get("Display.Teleport").replace("%world%",rewards[0])
                .replace("%x%",rewards[1]).replace("%y%",rewards[2]).replace("%z%",rewards[3]);
    }

    @Override
    public String[] createNames() {
        return new String[]{"teleport","tp"};
    }

    @Override
    public boolean mightNeedShopUpdate() {
        return true;
    }
}
