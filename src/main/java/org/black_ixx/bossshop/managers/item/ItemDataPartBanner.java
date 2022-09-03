package org.black_ixx.bossshop.managers.item;

import org.black_ixx.bossshop.core.BSBuy;
import org.black_ixx.bossshop.managers.ClassManager;
import org.bukkit.DyeColor;
import org.bukkit.Tag;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

import java.util.List;

public class ItemDataPartBanner extends ItemDataPart{
    @Override
    public ItemStack transform(ItemStack item, String used_name, String argument) {
        if (!Tag.BANNERS.isTagged(item.getType())) {
            ClassManager.manager.getBugFinder().severe("Mistake in Config: '" + argument + "' is not a valid '" + used_name + "'.");
            return item;
        }
        BannerMeta meta = (BannerMeta) item.getItemMeta();
        String[] parts = argument.split("#", 2);
        DyeColor color = DyeColor.valueOf(parts[0]);
        Pattern pattern = new Pattern(color, PatternType.valueOf(parts[1]));
        meta.addPattern(pattern);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public boolean isSimilar(ItemStack shop_item, ItemStack player_item, BSBuy buy, Player p) {
        return true;
    }

    @Override
    public List<String> read(ItemStack i, List<String> output) {
        BannerMeta meta = (BannerMeta) i.getItemMeta();
        if(!meta.getPatterns().isEmpty()){
            for(Pattern p:meta.getPatterns()){
                String patternName = p.getPattern().name();
                DyeColor color = p.getColor();
                output.add("banner:"+color.name()+"#"+patternName);
            }
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
        return new String[]{"Banner","banner"};
    }
}
