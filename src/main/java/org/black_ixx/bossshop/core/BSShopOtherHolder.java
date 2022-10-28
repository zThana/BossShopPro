package org.black_ixx.bossshop.core;

import org.black_ixx.bossshop.annotations.DontUse;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.HashMap;
@DontUse
public class BSShopOtherHolder extends BSShopHolder implements InventoryHolder {
    private BSShopHolder previous_shopholder;
    private final BSShop shop;
    private HashMap<Integer, BSBuy> items;
    public BSShopOtherHolder(BSShop shop, HashMap<Integer, BSBuy> items) {
        super(shop, items);
        this.shop = shop;
        this.items = items;
    }
    public BSShopOtherHolder(BSShop shop, BSShopHolder previous_shopholder) {
        this(shop);
        this.previous_shopholder = previous_shopholder;
    }
    public BSShopOtherHolder(BSShop shop) {
        super(shop);
        this.shop = shop;
    }



    public BSBuy getShopItem(int i) {
        return items.get(i);
    }

    public int getSlot(BSBuy buy) {
        for (int slot : items.keySet()) {
            BSBuy value = items.get(slot);
            if (value == buy) {
                return slot;
            }
        }
        return -1;
    }


    public BSShop getShop() {
        return shop;
    }

    public BSShopHolder getPreviousShopHolder() {
        return previous_shopholder;
    }

    public int getPage() {
        return 0;
    }


    public int getHighestPage() {
        return 0;
    }

    public int getDisplayPage() {
        return 0;
    }


    public void setItems(HashMap<Integer, BSBuy> items) {
        this.items = items;
    }

    @Override
    public Inventory getInventory() {return null;}

    public HashMap<Integer, BSBuy> getItems() {
        return items;
    }
}
