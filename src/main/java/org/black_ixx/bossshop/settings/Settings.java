package org.black_ixx.bossshop.settings;

import org.black_ixx.bossshop.managers.ClassManager;
import org.black_ixx.bossshop.managers.features.PointsManager.PointsPlugin;

import java.util.List;

public class Settings {
    public final static String CHECK_STACK_SIZE = "CheckStackSize";
    public final static String DEBUG = "Debug";
    public final static String RELOAD_AFTER_CREATE_SHOP = "ReloadAfterCreateShop";
    public final static String INPUT_TIMEOUT = "InputTimeout";
    public final static String NUMBER_GROUPING_SIZE = "NumberDisplay.GroupingSize";
    public final static String NUMBER_LOCALE = "NumberDisplay.Locale";
    public final static String Language = "Language";
    public final static String ALLOW_UNSAFE_ENCHANTMENTS = "AllowUnsafeEnchantments";
    public final static String ALLOW_SELLING_GREATER_ENCHANTS = "CanPlayersSellItemsWithGreaterEnchants";
    public final static String CLOSE_SHOP_AFTER_PURCHASE = "CloseShopAfterPurchase";
    public final static String SOUND_SHOPITEM_PURCHASE = "Sound.Shopitem.Purchase"; //Actions with a price that is not nothing
    public final static String SOUND_SHOPITEM_CLICK = "Sound.Shopitem.Click"; //Actions with pricetype nothing
    public final static String SOUND_SHOPITEM_NOPERMISSION = "Sound.Shopitem.NoPermission";
    public final static String SOUND_SHOPITEM_NOTENOUGHMONEY = "Sound.Shopitem.NotEnoughMoney";
    public final static String SOUND_SHOP_OPEN = "Sound.Shop.Open";
    public final static String SOUND_SHOP_CLOSE = "Sound.Shop.Close";
    public final static String SOUND_SHOP_CHANGE_PAGE = "Sound.Shop.ChangePage";
    public final static String SOUND_SHOP_CHANGE_SHOP = "Sound.Shop.ChangeShop";
    public final static String HIDE_ITEMS_PLAYERS_DONT_HAVE_PERMISSIONS_FOR = "HideItemsPlayersDoNotHavePermissionsFor";

    private boolean signs, money, points, vault, permissions, bungeecord, pointsdisplay, moneydisplay, serverpinging, load_subfolders, transactionslog, check_stacksize, exp_use_level,
            shopcommands, serverpinging_fixconnector, itemall_show_final_reward, inventory_full_drop_items, purchase_async, allow_selling_damaged_items;
    private int serverpinging_delay, serverpinging_waittime, serverpinging_timeout, autorefresh_delay, max_line_length, number_grouping_size, input_timeout;
    private PointsPlugin pointsplugin;
    private List<String> money_formatting, points_formatting;

    /**
     * Get object
     *
     * @param key key of value
     * @return string
     */
    public String getString(String key) {
       return ClassManager.manager.getPluginConfig().getString(key);
    }

    /**
     * Get boolean
     *
     * @param key id of property
     * @return boolean
     */
    public boolean getBoolean(String key) {
        return ClassManager.manager.getPluginConfig().getBoolean(key);
    }

    /**
     * Get integer
     * @return integer
     */
    public Integer getInt(String key){
        return ClassManager.manager.getPluginConfig().getInt(key);
    }

    public boolean getShopCommandsEnabled() {
        return shopcommands;
    }

    public void setShopCommandsEnabled(boolean b) {
        shopcommands = b;
    }

    public boolean getSignsEnabled() {
        return signs;
    }

    public boolean getMoneyEnabled() {
        return money;
    }

    public void setMoneyEnabled(boolean b) {
        money = b;
    }

    public boolean getPointsEnabled() {
        return points;
    }

    public void setPointsEnabled(boolean b) {
        points = b;
    }

    public boolean getVaultEnabled() {
        return vault;
    }

    public void setVaultEnabled(boolean b) {
        vault = b;
    }

    public boolean getPermissionsEnabled() {
        return permissions;
    }

    public void setPermissionsEnabled(boolean b) {
        permissions = b;
    }

    public boolean getPurchaseAsync() {
        return purchase_async;
    }

    public boolean getExpUseLevel() {
        return exp_use_level;
    }

    public boolean getBungeeCordServerEnabled() {
        return bungeecord;
    }

    public void setBungeeCordServerEnabled(boolean b) {
        bungeecord = b;
    }

    public boolean getBalancePointsVariableEnabled() {
        return pointsdisplay;
    }

    public void setBalancePointsVariableEnabled(boolean b) {
        pointsdisplay = b;
    }

    public PointsPlugin getPointsPlugin() {
        return pointsplugin;
    }

    public boolean getTransactionLogEnabled() {
        return transactionslog;
    }

    public boolean getServerPingingEnabled(boolean check_if_loaded_already) {
        if (check_if_loaded_already) {
            return ClassManager.manager.getServerPingingManager() != null;
        }
        return serverpinging;
    }

    public int getServerPingingSpeed() {
        return serverpinging_delay;
    }

    public int getServerPingingWaittime() {
        return serverpinging_waittime;
    }

    public int getServerPingingTimeout() {
        return serverpinging_timeout;
    }

    public boolean getServerPingingFixConnector() {
        return serverpinging_fixconnector;
    }

    public boolean getInventoryFullDropItems() {
        return inventory_full_drop_items;
    }

    public int getMaxLineLength() {
        return max_line_length;
    }

    public int getAutoRefreshSpeed() {
        return autorefresh_delay;
    }

    public boolean getLoadSubfoldersEnabled() {
        return load_subfolders;
    }

    public List<String> getMoneyFormatting() {
        return money_formatting;
    }

    public List<String> getPointsFormatting() {
        return points_formatting;
    }

    public boolean getItemAllShowFinalReward() {
        return itemall_show_final_reward;
    }

    public boolean getAllowSellingDamagedItems() {
        return allow_selling_damaged_items;
    }
}
