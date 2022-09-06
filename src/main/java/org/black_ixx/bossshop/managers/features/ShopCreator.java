package org.black_ixx.bossshop.managers.features;

import org.black_ixx.bossshop.BossShop;
import org.black_ixx.bossshop.managers.ClassManager;
import org.black_ixx.bossshop.managers.MessageHandler;
import org.bukkit.*;
import org.bukkit.block.banner.Pattern;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.PotionData;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ShopCreator implements Listener {
    private String name = "";
    private String title;
    private final MessageHandler mh;
    private final BossShop plugin;
    public ShopCreator(BossShop plugin,MessageHandler handler){
        this.plugin = plugin;
        this.mh = handler;
        Bukkit.getServer().getPluginManager().registerEvents(this,plugin);
    }
    public void startCreate(Player p,String shopName,String title){
        if(name.equals("")){
            this.name = shopName;
        }
        if(!Objects.equals(name,shopName)){
            this.mh.sendMessage("ShopCreate.SomeoneCreating",p);
            return;
        }
        this.title = title;
        setName(shopName);
        String inv_title = this.mh.get("ShopCreate.Title").replace("%shop%",this.name);
        Inventory inv = Bukkit.createInventory(null,54,inv_title);
        p.openInventory(inv);
    }
    @EventHandler
    private void Listen(InventoryCloseEvent e) {
        if (e.getPlayer() instanceof Player) {
            Player p = (Player) e.getPlayer();
            if (p.getOpenInventory().getTitle().startsWith(this.mh.get("ShopCreate.TitleHead"))) {
                if (p.hasPermission("BossShop.create") || e.getPlayer().isOp()) {
                    Inventory inv = e.getInventory();
                    if (inv.getSize() == 54) {
                        SaveAsFile(inv, p, getName(),getTitle());
                    }
                }
            }
        }
    }
    private void SaveAsFile(Inventory inv,Player p,String shopName,String title) {
        File f = new File(plugin.getDataFolder(),"shops"+File.separator+shopName+".yml");
        if(f.exists()){
            mh.sendMessage("ShopCreate.FileExists",p);
            f.delete();
        }
        YamlConfiguration shop = YamlConfiguration.loadConfiguration(f);
        shop.set("ShopName",shopName);
        shop.set("DisplayName",title);
        ConfigurationSection c = shop.createSection("signs");
        c.set("text","["+shopName+"]");
        c.set("NeedPermissionToCreateSign",true);
        ConfigurationSection shopItems = shop.createSection("shop");
        for(int real_slot=0;real_slot<53;real_slot++){
            ItemStack is = inv.getItem(real_slot);
            if(is==null){
                continue;
            }
            ConfigurationSection item = shopItems.createSection(String.valueOf(real_slot+1));
            item.set("MenuItem",ItemToList(is));
            item.set("RewardType","nothing");
            item.set("PriceType", "nothing");
            item.set("ExtraPermission", "");
            item.set("Message","");
            item.set("InventoryLocation", real_slot+1);
        }
        try {shop.save(f);
        } catch (IOException e) {throw new RuntimeException(e);}
        mh.sendMessage("ShopCreate.Success",p);
        if(ClassManager.manager.getSettings().isReloadAfterCreateShop()){
            ClassManager.manager.getPlugin().reloadPlugin(p);
        }
        setName("");
    }
    private List<String> ItemToList(ItemStack i){
        List<String> list = new ArrayList<>();
        ItemMeta meta = i.getItemMeta();
        Material m = i.getType();
        list.add("type:"+m.name());
        list.add("name:" +meta.getDisplayName());
        list.add("amount:"+i.getAmount());
        if(meta.hasLore()) {
            StringBuilder loreBuilder = new StringBuilder();
            List<String> lores = meta.getLore();
            for(String lore:lores){
                if(lores.get(lores.size()-1).equals(lore)){
                    loreBuilder.append(lore);
                    break;
                }
                loreBuilder.append(lore).append("#");
            }
            list.add("lore:"+loreBuilder);
        }
        Damageable d = (Damageable) meta;
        if(d.hasDamage()){
            if(d.getDamage()<m.getMaxDurability()){
                String dm = String.valueOf(m.getMaxDurability()-d.getDamage());
                list.add("durability:"+dm);
            }
        }
        if(meta.hasEnchants()){
            Map<Enchantment, Integer> enchantments = i.getEnchantments();
            for (Enchantment enchantment : enchantments.keySet()) {
                String enchantName = enchantment.getKey().getKey();
                int lvl = i.getEnchantmentLevel(enchantment);
                list.add("enchantment:"+enchantName+"#"+lvl);
            }
        }
        if(meta.isUnbreakable()){
            list.add("unbreakable:true");
        }
        if(meta.hasCustomModelData()){
            list.add("custommodeldata:"+meta.getCustomModelData());
        }
        //Item meta check start
        if(m.equals(Material.LEATHER_HELMET)||m.equals(Material.LEATHER_CHESTPLATE)||m.equals(Material.LEATHER_LEGGINGS)||m.equals(Material.LEATHER_BOOTS)){
            Color c = ((LeatherArmorMeta) meta).getColor();
            if(!c.equals(Bukkit.getItemFactory().getDefaultLeatherColor())){
               list.add("color:"+c.getRed()+"#"+c.getBlue()+"#"+c.getGreen());
            }
        }
        if(m.equals(Material.POTION)||m.equals(Material.LINGERING_POTION)||m.equals(Material.SPLASH_POTION)){
            PotionData pt = ((PotionMeta) meta).getBasePotionData();
            list.add("potion:"+pt.getType().name()+"#"+pt.isExtended()+"#"+pt.isUpgraded());
            if(pt.getType().getEffectType()!=null) {
                list.add("potioneffect:" + pt.getType().getEffectType().getName() + "#1#600");
            }
        }
        if(Tag.BANNERS.isTagged(m)){
            BannerMeta bannerMeta = (BannerMeta) meta;
            if(!bannerMeta.getPatterns().isEmpty()){
                for(Pattern p:bannerMeta.getPatterns()){
                    String patternName = p.getPattern().name();
                    DyeColor color = p.getColor();
                    list.add("banner:"+color.name()+"#"+patternName);
                }
            }
        }
        if(m.equals(Material.PLAYER_HEAD)||m.equals(Material.PLAYER_WALL_HEAD)){
            SkullMeta skull = (SkullMeta) meta;
            OfflinePlayer player = skull.getOwningPlayer();
            if(player != null){
                list.add("playerhead:"+player.getName());
            }
        }
        //Item meta check end
        return list;
    }
    private String getName(){
        return name;
    }
    private String getTitle(){
        return title;
    }
    private void setName(String name){
        this.name = name;
    }
}
