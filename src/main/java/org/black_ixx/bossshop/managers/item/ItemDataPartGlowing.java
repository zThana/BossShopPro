package org.black_ixx.bossshop.managers.item;

import org.black_ixx.bossshop.core.BSBuy;
import org.black_ixx.bossshop.managers.misc.InputReader;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemDataPartGlowing extends ItemDataPart {
    @Override
    public ItemStack transform(ItemStack item, String used_name, String argument) {
        boolean b = false;
        if(argument!=null){
            b = InputReader.getBoolean(argument,false);
        }
        if(b) {
            item.addUnsafeEnchantment(Enchantment.OXYGEN, 1);
            ItemMeta meta = item.getItemMeta();
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            item.setItemMeta(meta);
        }
        return item;
    }

    @Override
    public boolean isSimilar(ItemStack shop_item, ItemStack player_item, BSBuy buy, Player p) {
        return true;
    }

    @Override
    public List<String> read(ItemStack i, List<String> output) {
        ItemMeta meta = i.getItemMeta();
        if(meta.hasItemFlag(ItemFlag.HIDE_ENCHANTS)){
            output.add("glowing:true");
        }
        return output;
    }

    @Override
    public int getPriority() {
        return PRIORITY_NORMAL;
    }

    @Override
    public boolean removeSpaces() {
        return true;
    }

    @Override
    public String[] createNames() {
        return new String[]{"glowing"};
    }
}
