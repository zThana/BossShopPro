package org.black_ixx.bossshop.core.rewards;

import org.black_ixx.bossshop.core.BSBuy;
import org.black_ixx.bossshop.managers.ClassManager;
import org.black_ixx.bossshop.managers.misc.InputReader;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

public class BSRewardTypeMap extends BSRewardType{
    @Override
    public Object createObject(Object o, boolean force_final_state) {
        return InputReader.readString(o,false);
    }

    @Override
    public boolean validityCheck(String item_name, Object o) {
        if(o != null){
            String[] parts = o.toString().split("[|]");
            if(parts.length == 6||parts.length==3){
                return true;
            }
            ClassManager.manager.getBugFinder().severe("Was not able to create ShopItem " + item_name + "! " +
                    "The reward object needs to be written in the format of 'isLocked(boolean)|isTrackingPosition(boolean)|isUnlimitedTracking(boolean)' or 'isLocked(boolean)|isTrackingPosition(boolean)|isUnlimitedTracking(boolean)|red|blue|green'");
            return false;
        }
        ClassManager.manager.getBugFinder().severe("Was not able to create ShopItem " + item_name + "! The reward must not be empty.");
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
        ItemStack is = new ItemStack(Material.MAP);
        MapView mapView = Bukkit.createMap(p.getWorld());
        String[] parts = reward.toString().split("[|]");
        MapMeta meta = (MapMeta) is.getItemMeta();
        mapView.setLocked(Boolean.parseBoolean(parts[0]));
        mapView.setCenterX((int) p.getLocation().getX());
        mapView.setCenterZ((int) p.getLocation().getZ());
        mapView.setWorld(p.getWorld());
        mapView.setTrackingPosition(Boolean.parseBoolean(parts[1]));
        mapView.setUnlimitedTracking(Boolean.parseBoolean(parts[2]));
        meta.setMapView(mapView);
        if(parts.length==6){
            Color c = Color.fromRGB(InputReader.getInt(parts[3].trim(),0),
                    InputReader.getInt(parts[4].trim(),0),InputReader.getInt(parts[5].trim(),0));
            meta.setColor(c);
        }
        is.setItemMeta(meta);
        p.getInventory().addItem(is);
    }

    @Override
    public String getDisplayReward(Player p, BSBuy buy, Object reward, ClickType clickType) {
        return null;
    }

    @Override
    public String[] createNames() {
        return new String[]{"map"};
    }

    @Override
    public boolean mightNeedShopUpdate() {
        return false;
    }

    @Override
    public boolean allowAsync() {
        return true;
    }
}
