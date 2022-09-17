package org.black_ixx.bossshop.listeners;

import org.black_ixx.bossshop.managers.ClassManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class MenuOpenListener implements Listener {
    @EventHandler
    public void useMaterial(PlayerInteractEvent e){
        if(ClassManager.manager.getPluginConfig().getBoolean("OpenMaterial.Enabled")){
            Material material = e.getPlayer().getInventory().getItemInMainHand().getType();
            if(ClassManager.manager.getPluginConfig().getStringList("OpenMaterial.AllowMaterials").contains(material.name())){
                Action a = e.getAction();
                if(a.equals(Action.RIGHT_CLICK_AIR)||a.equals(Action.RIGHT_CLICK_BLOCK)) {
                    Player p = e.getPlayer();
                    ClassManager.manager.getShops().openShop(p,
                            ClassManager.manager.getPlugin().getConfig().getString("MainShop").toLowerCase());
                }
            }
        }
    }

    @EventHandler
    public void useFKey(PlayerSwapHandItemsEvent e){
        if(ClassManager.manager.getPluginConfig().getBoolean("UseFKey.Enabled")) {
            Player p = e.getPlayer();
            if(ClassManager.manager.getPluginConfig().getBoolean("UseFKey.EnableShift")) {
                if(p.isSneaking()) {
                    e.setCancelled(true);
                    ClassManager.manager.getShops().openShop(p,
                            ClassManager.manager.getPlugin().getConfig().getString("MainShop").toLowerCase());
                }
            }else {
                e.setCancelled(true);
                ClassManager.manager.getShops().openShop(p,
                        ClassManager.manager.getPlugin().getConfig().getString("MainShop").toLowerCase());
            }
        }
    }
}
