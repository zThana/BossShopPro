package org.black_ixx.bossshop.core.prices;

import dev.lone.itemsadder.api.CustomStack;
import org.black_ixx.bossshop.core.BSBuy;
import org.black_ixx.bossshop.managers.ClassManager;
import org.black_ixx.bossshop.managers.misc.InputReader;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.List;

// Not finish
public class BSPriceTypeIAItem extends BSPriceType{
    @Override
    public Object createObject(Object o, boolean force_final_state) {
        return InputReader.readStringListList(o);
    }

    @Override
    public boolean validityCheck(String item_name, Object o) {
        if (Bukkit.getPluginManager().isPluginEnabled("ItemsAdder")){
            ClassManager.manager.getBugFinder().severe("Was not able to create ShopItem " + item_name + "! ItemsAdder isn't loaded in the server.");
            return false;
        }
        if (o != null) {
            return true;
        }
        ClassManager.manager.getBugFinder().severe("Was not able to create ShopItem " + item_name + "! The price object needs to be a valid list of ItemsAdder item data.");
        return false;
    }

    @Override
    public void enableType() {

    }

    @Override
    public boolean hasPrice(Player p, BSBuy buy, Object price, ClickType clickType, boolean messageOnFailure) {
        List<CustomStack> iaitems = (List<CustomStack>) price;
        return false;
    }

    @Override
    public String takePrice(Player p, BSBuy buy, Object price, ClickType clickType) {
        return null;
    }

    @Override
    public String getDisplayPrice(Player p, BSBuy buy, Object price, ClickType clickType) {
        return null;
    }

    @Override
    public String[] createNames() {
        return new String[]{"iaitems"};
    }

    @Override
    public boolean mightNeedShopUpdate() {
        return false;
    }
}
