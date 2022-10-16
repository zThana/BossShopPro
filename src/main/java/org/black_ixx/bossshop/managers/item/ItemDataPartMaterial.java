package org.black_ixx.bossshop.managers.item;

import dev.lone.itemsadder.api.CustomStack;
import org.black_ixx.bossshop.core.BSBuy;
import org.black_ixx.bossshop.managers.ClassManager;
import org.black_ixx.bossshop.managers.misc.InputReader;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.List;

public class ItemDataPartMaterial extends ItemDataPart {

    @Override
    public ItemStack transform(ItemStack item, String used_name, String argument) {
        int durability = 0;
        Material m;

        if (argument.contains(":")) {
            String[] parts = argument.split(":");
            if (parts.length > 1) {
                durability = InputReader.getInt(parts[1].trim(), 0);
            }
            argument = parts[0].trim();
        }

        if (argument.startsWith("itemsadder/")){
            if (Bukkit.getPluginManager().isPluginEnabled("ItemsAdder")) {
                String namespaceID = argument.split("/")[1];
                CustomStack cs = CustomStack.getInstance(namespaceID);
                if (cs == null) {
                    ClassManager.manager.getBugFinder().warn("ItemsAdder item not found: " + namespaceID);
                    return item;
                }
                return cs.getItemStack();
            }
            ClassManager.manager.getBugFinder().warn("ItemsAdder is not loaded into the server. You can get it here: https://www.spigotmc.org/resources/%E2%9C%A8itemsadder%E2%AD%90emotes-mobs-items-armors-hud-gui-emojis-blocks-wings-hats-liquids.73355/");
            return item;
        }

        m = InputReader.readMaterial(argument);

        if (m == null) {
            ClassManager.manager.getBugFinder().severe("Mistake in Config: '" + argument + "' is not a valid '" + used_name + "'. Unable to find a fitting material.");
            return item;
        }

        item.setType(m);
        Damageable d = (Damageable) item.getItemMeta();
        d.setDamage(durability);
        item.setItemMeta(d);
        return item;
    }

    @Override
    public int getPriority() {
        return PRIORITY_MOST_EARLY;
    }

    @Override
    public boolean removeSpaces() {
        return true;
    }

    @Override
    public String[] createNames() {
        return new String[]{"type", "id", "material"};
    }


    @Override
    public List<String> read(ItemStack i, List<String> output) {
        output.add("type:" + i.getType().name());
        return output;
    }


    @Override
    public boolean isSimilar(ItemStack shop_item, ItemStack player_item, BSBuy buy, Player p) {
        return shop_item.getType().name().equals(player_item.getType().name());
    }
}
