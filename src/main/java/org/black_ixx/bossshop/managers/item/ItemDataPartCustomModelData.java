package org.black_ixx.bossshop.managers.item;

import org.black_ixx.bossshop.core.BSBuy;
import org.black_ixx.bossshop.managers.ClassManager;
import org.black_ixx.bossshop.managers.misc.InputReader;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Objects;

public class ItemDataPartCustomModelData extends ItemDataPart {
    @Override
    public ItemStack transform(ItemStack item, String used_name, String argument) {
        int custommodeldata = InputReader.getInt(argument, -1);
        if (custommodeldata == -1) {
            ClassManager.manager.getBugFinder().severe("Mistake in Config: '" + argument + "' is not a valid '" + used_name + "'. It needs to be a number like '1', '12' or '64'.");
            return item;
        }
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(custommodeldata);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public boolean isSimilar(ItemStack shop_item, ItemStack player_item, BSBuy buy, Player p) {
        if(!Objects.isNull(shop_item.getItemMeta())&&!Objects.isNull(player_item.getItemMeta())) {
            return Objects.equals(shop_item.getItemMeta().getCustomModelData(), player_item.getItemMeta().getCustomModelData());
        }
        return false;
    }

    @Override
    public List<String> read(ItemStack i, List<String> output) {
        if (i.hasItemMeta()) {
            int d = i.getItemMeta().getCustomModelData();
            if (d != -1) {
                output.add("CustomModelData:" + d);
            }
        }
        return output;
    }

    @Override
    public int getPriority() {
        return PRIORITY_EARLY;
    }

    @Override
    public boolean removeSpaces() {
        return false;
    }

    @Override
    public String[] createNames() {
        return new String[]{"CustomModelData","custommodeldata"};
    }
}
