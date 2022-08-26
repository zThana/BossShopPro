package org.black_ixx.bossshop.managers.features;

import org.black_ixx.bossshop.BossShop;
import org.black_ixx.bossshop.managers.MessageHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShopCreator implements Listener {
    private String name;
    private String title;
    private final MessageHandler mh;
    private final BossShop plugin;
    public ShopCreator(BossShop plugin,MessageHandler handler){
        this.plugin = plugin;
        this.mh = handler;
    }
    public void startCreate(Player p,String shopName,String title){
        this.title = title;
        if(Objects.equals(name,null)){
            this.name = shopName;
        }
        if(!Objects.equals(shopName,name)){
            mh.sendMessage("ShopCreate.SomeoneCreating",p);
            return;
        }
        String inv_title = mh.get("ShopCreate.Title").replace("%shop%",name);
        Inventory inv = Bukkit.createInventory(null,54,inv_title);
        p.openInventory(inv);
    }
    @EventHandler
    private void Listen(InventoryCloseEvent e) {
        if (e.getPlayer() instanceof Player) {
            Player p = (Player) e.getPlayer();
            if (p.getOpenInventory().getTitle().startsWith(mh.get("ShopCreate.TitleHead"))) {
                if (p.hasPermission("BossShop.create") || e.getPlayer().isOp()) {
                    Inventory inv = e.getInventory();
                    if (inv.getSize() == 54) {
                        SaveAsFile(inv, p, name);
                    }
                }
            }
        }
    }
    private void SaveAsFile(Inventory inv,Player p,String name) {
                File f = new File(plugin.getDataFolder(),"shops"+File.separator+name+".yml");
                if(f.exists()){
                    mh.sendMessage("ShopCreate.FileExists",p);
                    FileWriter fw;
                    try {fw = new FileWriter(f);
                        fw.write("");
                        fw.flush();
                        fw.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                FileConfiguration shop = YamlConfiguration.loadConfiguration(f);
                shop.addDefault("ShopName",name);
                shop.addDefault("DisplayName",title);
                ConfigurationSection c = shop.createSection("signs");
                c.addDefault("text","["+name+"]");
                c.addDefault("NeedPermissionToCreateSign",true);
                ConfigurationSection shopItems = shop.createSection("shop");
                for(int real_slot=0;real_slot<53;real_slot++){
                    ItemStack is = inv.getItem(real_slot);
                    if(is==null){
                       continue;
                    }
                    ConfigurationSection item = shopItems.createSection(String.valueOf(real_slot));
                    item.addDefault("MenuItem",ItemToList(is));
                }
                mh.sendMessageDirect("ShopCreate.Success",p);
                this.name = "";
    }
    private List<String> ItemToList(ItemStack i){
        List<String> list = new ArrayList<>();
        ItemMeta meta = i.getItemMeta();
        Material m = i.getType();
        list.add("type:"+m.name());
        list.add("name:"+meta.getDisplayName());
        list.add("amount:"+i.getAmount());
        if(meta.hasLore()) {
            StringBuilder loreBuilder = new StringBuilder();
            for(String lore:meta.getLore()){
                loreBuilder.append(lore).append("#");
            }
            list.add("lore:"+loreBuilder);
        }
        Damageable d = (Damageable) meta;
        if(d.hasDamage()){
            if(d.getDamage()<m.getMaxDurability()){
                list.add("durability:"+d.getDamage());
            }
        }
        return list;
    }
}
