package org.black_ixx.bossshop.settings;

import org.black_ixx.bossshop.managers.ClassManager;
import org.black_ixx.bossshop.managers.features.PointsManager.PointsPlugin;
import org.black_ixx.bossshop.managers.misc.InputReader;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Settings {

    public final static int
            LANGUAGE = 0;
    public final static int RELOAD_AFTER_CREATE_SHOP = 1;
    public final static int HIDE_ITEMS_PLAYERS_DONT_HAVE_PERMISSIONS_FOR = 2;
    public final static int ALLOW_UNSAFE_ENCHANTMENTS = 3;
    public final static int ALLOW_SELLING_GREATER_ENCHANTS = 4;
    public final static int CLOSE_SHOP_AFTER_PURCHASE = 5;
    public final static int CLICK_DELAY = 6;
    public final static int SOUND_SHOPITEM_PURCHASE = 7; //Actions with a price that is not nothing
            public final static int SOUND_SHOPITEM_CLICK = 8; //Actions with pricetype nothing
            public final static int SOUND_SHOPITEM_NOPERMISSION = 9;
    public final static int SOUND_SHOPITEM_NOTENOUGHMONEY = 10;
    public final static int SOUND_SHOP_OPEN = 11;
    public final static int SOUND_SHOP_CLOSE = 12;
    public final static int SOUND_SHOP_CHANGE_PAGE = 13;
    public final static int SOUND_SHOP_CHANGE_SHOP = 14;
    public final static int USE_F_KEY = 15;
    public final static int OPEN_MATERIAL = 16;


    private boolean signs, money, points, vault, permissions, bungeecord, pointsdisplay, moneydisplay, serverpinging, load_subfolders, transactionslog, check_stacksize, exp_use_level,
            shopcommands, serverpinging_fixconnector, itemall_show_final_reward, inventory_full_drop_items, purchase_async, allow_selling_damaged_items, reload_after_create_shop
            , use_F_key, openMaterial;
    private int serverpinging_delay, serverpinging_waittime, serverpinging_timeout, autorefresh_delay, max_line_length, number_grouping_size, input_timeout;
    private String mainshop, number_locale, language;
    private PointsPlugin pointsplugin;
    private List<String> money_formatting, points_formatting;
    private List<Material> openMaterials;

    private boolean debug;

    private final Map<Integer, SettingsProperty> properties = new LinkedHashMap<>();


    /**
     * Load configuration settings from config
     * @param config config to load from
     */
    public void loadConfig(ConfigurationSection config) {
        properties.clear();
        properties.put(LANGUAGE, new ShopProperty(config,"Language",String.class));
        properties.put(RELOAD_AFTER_CREATE_SHOP, new ShopProperty(config,"ReloadAfterCreateShop", Boolean.class));
        properties.put(HIDE_ITEMS_PLAYERS_DONT_HAVE_PERMISSIONS_FOR, new ShopItemProperty(config, "HideItemsPlayersDoNotHavePermissionsFor", Boolean.class));
        properties.put(ALLOW_UNSAFE_ENCHANTMENTS, new ShopItemProperty(config, "AllowUnsafeEnchantments", Boolean.class));
        properties.put(ALLOW_SELLING_GREATER_ENCHANTS, new ShopItemProperty(config, "CanPlayersSellItemsWithGreaterEnchants", Boolean.class));
        properties.put(CLOSE_SHOP_AFTER_PURCHASE, new ShopItemProperty(config, "CloseShopAfterPurchase", Boolean.class));
        properties.put(CLICK_DELAY, new ShopItemProperty(config, "ClickDelay", Integer.class));
        properties.put(SOUND_SHOPITEM_PURCHASE, new ShopItemProperty(config, "Sound.Shopitem.Purchase", String.class));
        properties.put(SOUND_SHOPITEM_CLICK, new ShopItemProperty(config, "Sound.Shopitem.Click", String.class));
        properties.put(SOUND_SHOPITEM_NOPERMISSION, new ShopItemProperty(config, "Sound.Shopitem.NoPermission", String.class));
        properties.put(SOUND_SHOPITEM_NOTENOUGHMONEY, new ShopItemProperty(config, "Sound.Shopitem.NotEnoughMoney", String.class));
        properties.put(SOUND_SHOP_OPEN, new ShopProperty(config, "Sound.Shop.Open", String.class));
        properties.put(SOUND_SHOP_CLOSE, new ShopProperty(config, "Sound.Shop.Close", String.class));
        properties.put(SOUND_SHOP_CHANGE_PAGE, new ShopProperty(config, "Sound.Shop.ChangePage", String.class));
        properties.put(SOUND_SHOP_CHANGE_SHOP, new ShopProperty(config, "Sound.Shop.ChangeShop", String.class));
        properties.put(USE_F_KEY, new ShopProperty(config, "UseFKey", Boolean.class));
        properties.put(OPEN_MATERIAL, new ShopProperty(config,"OpenMaterial.Enabled", Boolean.class));
    }

    /**
     * Update a config
     * @param o config
     */
    public void update(Object o) {
        for (SettingsProperty property : properties.values()) {
            property.update(o);
        }
    }

    /**
     * Update config
     */
    public void update() {
        Configuration main_config = ClassManager.manager.getPlugin().getConfig();
        for (SettingsProperty property : properties.values()) {
            property.load(main_config);
        }
    }

    /**
     * Get property
     * @param id id of property
     * @return property
     */
    public SettingsProperty getProperty(int id) {
       return properties.get(id);
    }

    /**
     * Get property boolean
     * @param id id of property
     * @param input object to get
     * @return boolean
     */
    public boolean getPropertyBoolean(int id, Object input) {
        SettingsProperty property = getProperty(id);
        if (property != null) {
            return property.getBoolean(input);
        }
        return false;
    }

    /**
     * Get property int
     * @param id id of property
     * @param input object to get
     * @param def default
     * @return int
     */
    public int getPropertyInt(int id, Object input, int def) {
        SettingsProperty property = getProperty(id);
        if (property != null) {
            return property.getInt(input);
        }
        return def;
    }

    /**
     * Get property string
     * @param id id of property
     * @param input object to get
     * @param def default
     * @return string
     */
    public String getPropertyString(int id, Object input, String def) {
        SettingsProperty property = getProperty(id);
        if (property != null) {
            return property.getString(input);
        }
        return def;
    }

    public void setPurchaseAsyncEnabled(boolean b) {
        purchase_async = b;
    }

    public void setServerPingingEnabled(boolean b) {
        serverpinging = b;
    }

    public void setServerPingingWaitTime(int i) {
        serverpinging_waittime = i;
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

    public void setSignsEnabled(boolean b) {
        signs = b;
        if (ClassManager.manager.getPlugin().getSignListener() != null) {
            ClassManager.manager.getPlugin().getSignListener().setSignsEnabled(b);
        }
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

    public boolean isReloadAfterCreateShop(){
        return reload_after_create_shop;
    }

    public void setReloadAfterCreateShop(boolean b){
        reload_after_create_shop = b;
    }

    public void setLanguage(String lang){
        language = lang;
    }

    public String getLanguage(){
        return language;
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

    public void setExpUseLevel(boolean b) {
        exp_use_level = b;
    }

    public String getMainShop() {
        return mainshop;
    }

    public void setMainShop(String main) {
        mainshop = main;
    }

    public boolean getBungeeCordServerEnabled() {
        return bungeecord;
    }

    public void setBungeeCordServerEnabled(boolean b) {
        bungeecord = b;
    }

    public boolean getBalanceVariableEnabled() {//TODO: probably remove this
        return moneydisplay;
    }

    public void setBalanceVariableEnabled(boolean b) {
        moneydisplay = b;
    }

    public boolean getBalancePointsVariableEnabled() {
        return pointsdisplay;
    }

    public void setBalancePointsVariableEnabled(boolean b) {
        pointsdisplay = b;
    }

    public boolean isDebugEnabled() {
        return debug;
    }

    public void setDebugEnabled(boolean b) {
        debug = b;
    }

    public PointsPlugin getPointsPlugin() {
        return pointsplugin;
    }

    public void setPointsPlugin(PointsPlugin p) {
        pointsplugin = p;
    }

    public boolean getTransactionLogEnabled() {
        return transactionslog;
    }

    public void setTransactionLogEnabled(boolean b) {
        transactionslog = b;
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

    public void setServerPingingSpeed(int i) {
        serverpinging_delay = i;
    }

    public int getServerPingingWaittime() {
        return serverpinging_waittime;
    }

    public int getServerPingingTimeout() {
        return serverpinging_timeout;
    }

    public void setServerPingingTimeout(int i) {
        serverpinging_timeout = i;
    }

    public boolean getServerPingingFixConnector() {
        return serverpinging_fixconnector;
    }

    public void setServerPingingFixConnector(boolean b) {
        serverpinging_fixconnector = b;
    }

    public boolean getInventoryFullDropItems() {
        return inventory_full_drop_items;
    }

    public void setInventoryFullDropItems(boolean b) {
        inventory_full_drop_items = b;
    }

    public int getMaxLineLength() {
        return max_line_length;
    }

    public void setMaxLineLength(int i) {
        max_line_length = i;
    }

    public int getAutoRefreshSpeed() {
        return autorefresh_delay;
    }

    public void setAutoRefreshSpeed(int i) {
        autorefresh_delay = i;
    }

    public boolean getLoadSubfoldersEnabled() {
        return load_subfolders;
    }

    public void setLoadSubfoldersEnabled(boolean b) {
        load_subfolders = b;
    }

    public boolean getCheckStackSize() {
        return check_stacksize;
    }

    public void setCheckStackSize(boolean b) {
        check_stacksize = b;
    }

    public List<String> getMoneyFormatting() {
        return money_formatting;
    }

    public void setMoneyFormatting(List<String> formatting) {
        this.money_formatting = formatting;
    }

    public List<String> getPointsFormatting() {
        return points_formatting;
    }

    public void setPointsFormatting(List<String> formatting) {
        this.points_formatting = formatting;
    }

    public String getNumberLocale() {
        return number_locale;
    }

    public void setNumberLocale(String s) {
        this.number_locale = s;
    }

    public int getNumberGroupingSize() {
        return number_grouping_size;
    }

    public void setNumberGroupingSize(int i) {
        this.number_grouping_size = i;
    }

    public boolean getItemAllShowFinalReward() {
        return itemall_show_final_reward;
    }

    public void setItemAllShowFinalReward(boolean b) {
        itemall_show_final_reward = b;
    }

    public boolean getAllowSellingDamagedItems() {
        return allow_selling_damaged_items;
    }

    public void setAllowSellingDamagedItems(boolean b) {
        allow_selling_damaged_items = b;
    }

    public int getInputTimeout() {
        return input_timeout;
    }

    public void setInputTimeout(int i) {
        input_timeout = i;
    }
    public void setUseFKey(boolean b){
        use_F_key = b;
    }
    public void setOpenMaterialEnabled(boolean b){
        openMaterial = b;
    }
    public void setOpenMaterial(List<String> materials){
        List<Material> materialList = new ArrayList<>();
        for(String material:materials){
            Material m = InputReader.readMaterial(material);
            materialList.add(m);
        }
        openMaterials = materialList;
    }
}
