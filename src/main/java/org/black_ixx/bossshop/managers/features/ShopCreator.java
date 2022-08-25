package org.black_ixx.bossshop.managers.features;

import com.google.common.annotations.Beta;
import org.black_ixx.bossshop.BossShop;
import org.black_ixx.bossshop.managers.ClassManager;
import org.black_ixx.bossshop.managers.MessageHandler;
import org.bukkit.Bukkit;
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

@Beta
public class ShopCreator implements Listener {
    private final MessageHandler mh;
    private String title;
    private String shopName;
    private final BossShop plugin;
    public ShopCreator(BossShop plugin){
        mh = ClassManager.manager.getMessageHandler();
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }

    public void createShop(Player p,String shopName, String title){
        this.title = title;
        this.shopName = shopName;
        Inventory inv = Bukkit.createInventory(null,54,mh.get("ShopCreate.Title").replace("%shop%",shopName));
        p.openInventory(inv);
    }

    @EventHandler
    private void onClose(InventoryCloseEvent e) throws IOException {
        Inventory inventory = e.getInventory();
        if(e.getPlayer().getOpenInventory().getTitle().equals(mh.get("ShopCreate.TitleHead"))){
            SaveAsFile(inventory, (Player) e.getPlayer());
        }
    }

    private void SaveAsFile(Inventory inv,Player p) throws IOException {
        File f = new File(plugin.getDataFolder(),"shops"+File.separator+shopName+".yml");
        if(f.exists()){
            p.sendMessage(mh.get("ShopCreate.FileExists"));
            FileWriter fw = new FileWriter(f);
            fw.write("");
            fw.flush();
            fw.close();
        }
        FileConfiguration shop = YamlConfiguration.loadConfiguration(f);
        addDefaults(shop);
        addItems(inv.getContents(),shop,inv);
        mh.sendMessage("ShopCreate.SaveSuccess",p);
    }

    private void addDefaults(FileConfiguration f){
        f.addDefault("ShopName",shopName);
        f.addDefault("DisplayName",title);
        ConfigurationSection se = f.createSection("signs");
        se.addDefault("text",'['+shopName+']');
        se.addDefault("NeedPermissionToCreateSign",true);
    }

    private void addItems(ItemStack[] items,FileConfiguration f,Inventory inv){
        ConfigurationSection se = f.createSection("shop");
        int id = 1;
        for(ItemStack i : items){
            ConfigurationSection item = se.createSection(String.valueOf(id));
            int slot = inv.first(i);
            if(slot==-1)continue;
            item.addDefault("MenuItem",itemToList(i));
            item.addDefault("RewardType","nothing");
            item.addDefault("PriceType","nothing");
            item.addDefault("ExtraPermission","");
            item.addDefault("Message","");
            item.addDefault("InventoryLocation",slot);
            id++;
        }
    }

    private List<String> itemToList(ItemStack s){
        List<String> l = new ArrayList<>();
        ItemMeta meta = s.getItemMeta();
        l.add("type:"+s.getType().name());
        l.add("amount:"+s.getAmount());
        l.add("name:"+meta.getDisplayName());
        StringBuilder real_lore = new StringBuilder();
        if(!meta.getLore().isEmpty()||!Objects.equals(meta.getLore(),null)){
            for(String lore:meta.getLore()){
                real_lore.append(lore).append("#");
            }
            l.add("lore:"+ real_lore);
        }
        Damageable d = (Damageable) meta;
        if(d.hasDamage()) {
            if (d.getDamage() < s.getType().getMaxDurability()) {
                l.add("durability:" + d.getDamage());
            }
        }
        return l;
    }
}
