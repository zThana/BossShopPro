package org.black_ixx.bossshop.core;

import org.black_ixx.bossshop.annotations.DontUse;
import org.black_ixx.bossshop.managers.ClassManager;
import org.black_ixx.bossshop.misc.Misc;
import org.black_ixx.bossshop.settings.Settings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashSet;
import java.util.Set;

@DontUse
public class BSOtherShop extends BSShop{
    private int shop_id;
    private final Set<BSBuy> items = new LinkedHashSet<>();

    public BSOtherShop(int shop_id, String shop_name, String sign_text, boolean needPermToCreateSign, String displayname, String[] commands) {
        super(shop_id, shop_name, sign_text, needPermToCreateSign, displayname, 1, commands);
        this.shop_id = shop_id;
    }

    public BSOtherShop(int shop_id) {
        super(shop_id);
        this.shop_id = shop_id;
    }

    public void openInventory(Player p, boolean remember_current_shop) {
        openInventory(p, InventoryType.CRAFTING, remember_current_shop);
    }

    @Override
    public void reloadShop() {
       //empty
    }

    public void addShopItem(BSBuy buy, ItemStack menu_item, ClassManager manager) {
        buy.updateShop(this, menu_item, manager, true);
    }

    public void openInventory(Player p, InventoryType type, boolean remember_current_shop) {
        BSShopOtherHolder oldshopholder = null;

        if (remember_current_shop) {
            InventoryView openinventory = p.getOpenInventory();
            if (openinventory.getTopInventory().getHolder() instanceof BSShopOtherHolder||openinventory.getTopInventory().getHolder() instanceof BSShopHolder){
                oldshopholder = (BSShopOtherHolder) openinventory.getTopInventory().getHolder();
            }
        }

        ClassManager.manager.getMessageHandler().sendMessage("Main.OpenShop", p, null, p, this, null, null);
        if (ClassManager.manager.getPlugin().getAPI().isValidShop(p.getOpenInventory())) {
            Misc.playSound(p, ClassManager.manager.getSettings().getString(Settings.SOUND_SHOP_CHANGE_SHOP));
        } else {
            Misc.playSound(p, ClassManager.manager.getSettings().getString(Settings.SOUND_SHOP_OPEN));
        }

        p.openInventory(createInventory(p, ClassManager.manager, type, oldshopholder));
        ClassManager.manager.getPlayerDataHandler().openedShop(p, this);//TODO: only store previous shop, not current shop somehow.
    }

    public void close() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (ClassManager.manager.getPlugin().getAPI().isValidShop(p.getOpenInventory())) {
                BSShopOtherHolder holder = (BSShopOtherHolder) p.getOpenInventory().getTopInventory().getHolder();
                if (holder.getShop() == this) {
                    p.closeInventory();
                }
            }
        }
    }

    public void loadInventorySize() {

    }

    public Inventory createInventory(Player p, ClassManager manager, InventoryType type, BSShopOtherHolder oldshopholder){
        return manager.getShopCustomizer().createInventory(this, items, p, manager, type, oldshopholder);
    }
}
