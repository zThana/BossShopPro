package org.black_ixx.bossshop.managers.features;

import org.black_ixx.bossshop.BossShop;
import org.black_ixx.bossshop.managers.ClassManager;
import org.black_ixx.bossshop.managers.MessageHandler;
import org.black_ixx.bossshop.settings.Settings;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.banner.Pattern;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.Player;
import org.bukkit.entity.TropicalFish;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.material.Colorable;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;

import java.io.File;
import java.io.IOException;
import java.util.*;

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
        if(getName().equals("")){
            setName(shopName);
        }
        if(!Objects.equals(name,shopName)){
            this.mh.sendMessage("ShopCreate.SomeoneCreating",p);
            return;
        }
        this.title = title;
        String inv_title = this.mh.get("ShopCreate.Title").replace("%shop%",getName());
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
        if(Objects.equals(shopName, "")){
            return;
        }
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
        setName("");
        mh.sendMessage("ShopCreate.Success",p);
        if(ClassManager.manager.getSettings().getBoolean(Settings.RELOAD_AFTER_CREATE_SHOP)){
            ClassManager.manager.getPlugin().reloadPlugin(p);
        }
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
            if(d.getDamage()>0){
                String dm = String.valueOf(d.getDamage());
                list.add("durability:"+dm);
            }
        }
        Map<Enchantment, Integer> enchantments = i.getEnchantments();
        for(Enchantment en:enchantments.keySet()){
            String name = en.getKey().getKey();
            int lvl = enchantments.get(en);
            list.add("enchantment:"+name+"#"+lvl);
        }
        if(meta.isUnbreakable()){
            list.add("unbreakable:true");
        }
        if(meta.hasCustomModelData()){
            list.add("custommodeldata:"+meta.getCustomModelData());
        }
        //Item meta check start
        if(meta instanceof Colorable){
            Colorable colorable = (Colorable) meta;
            Color color = colorable.getColor() != null ? colorable.getColor().getColor() : Color.fromRGB(0,0,0);
            list.add("color:"+color.getRed()+"#"+color.getGreen()+"#"+color.getBlue());
        }
        if(meta instanceof LeatherArmorMeta){
            Color c = ((LeatherArmorMeta) meta).getColor();
            if(!c.equals(Bukkit.getItemFactory().getDefaultLeatherColor())){
               list.add("color:"+c.getRed()+"#"+c.getGreen()+"#"+c.getBlue());
            }
        }
        if(meta instanceof EnchantmentStorageMeta){
            EnchantmentStorageMeta enchantmentStorage = (EnchantmentStorageMeta) meta;
            if(enchantmentStorage.hasStoredEnchants()){
                for(Enchantment enchantment:enchantmentStorage.getStoredEnchants().keySet()){
                    String name = enchantment.getKey().getKey();
                    int lvl = enchantments.get(enchantment);
                    list.add("enchantment:"+name+"#"+lvl);
                }
            }
        }
        if(meta instanceof PotionMeta){
            PotionMeta potion = (PotionMeta) meta;
            PotionData pt = potion.getBasePotionData();
            list.add("potion:"+pt.getType().name()+"#"+pt.isExtended()+"#"+pt.isUpgraded());
            if(pt.getType().getEffectType()!=null) {
                list.add("potioneffect:" + pt.getType().getEffectType().getName() + "#1#600");
            }
            if(potion.hasCustomEffects()){
                for(PotionEffect pe:potion.getCustomEffects()){
                    String effectName = pe.getType().getName();
                    list.add("potioneffect:"+effectName+"#"+pe.getAmplifier()+"#"+pe.getDuration()+"#");
                }
            }
        }
        if(meta instanceof BannerMeta){
            BannerMeta bannerMeta = (BannerMeta) meta;
            if(!bannerMeta.getPatterns().isEmpty()){
                for(Pattern p:bannerMeta.getPatterns()){
                    String patternName = p.getPattern().name();
                    DyeColor color = p.getColor();
                    list.add("banner:"+color.name()+"#"+patternName);
                }
            }
        }
        if(meta instanceof SkullMeta){
            SkullMeta skull = (SkullMeta) meta;
            OfflinePlayer player = skull.getOwningPlayer();
            if(player != null){
                list.add("playerhead:"+player.getName());
            }
        }
        if(meta instanceof TropicalFishBucketMeta){
            TropicalFishBucketMeta tropicalFishBucket = (TropicalFishBucketMeta) i.getItemMeta();
            if(tropicalFishBucket.hasVariant()) {
                TropicalFish.Pattern p = tropicalFishBucket.getPattern();
                DyeColor color = tropicalFishBucket.getPatternColor();
                list.add("tropicalfish:" + color.name() + "#" + p.name());
            }
        }
        if(meta instanceof SuspiciousStewMeta){
            SuspiciousStewMeta suspiciousStew = (SuspiciousStewMeta) i.getItemMeta();
            if(suspiciousStew.hasCustomEffects()){
                for(PotionEffect pe:suspiciousStew.getCustomEffects()){
                    String effectName = pe.getType().getName();
                    int duration = pe.getDuration();
                    int amplifier = pe.getAmplifier();
                    list.add("suspiciousstew:"+effectName+"#"+duration+"#"+amplifier);
                }
            }
        }
        if(isHighThan116()){
            if(meta instanceof AxolotlBucketMeta){
                AxolotlBucketMeta axolotlBucket = (AxolotlBucketMeta) i.getItemMeta();
                Axolotl.Variant variant = axolotlBucket.getVariant();
                list.add("axolotl:" + variant.name());
            }
        }
        //Item meta check end

        //remove duplicates
        HashSet<String> hashSet = new HashSet<>(list);
        list = new ArrayList<>(hashSet);

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
    private boolean isHighThan116(){
        String version = Bukkit.getServer().getBukkitVersion().split("-")[0];
        int version2 = Integer.parseInt(version.split("\\.")[1]);
        return version2 >= 17;
    }
}
