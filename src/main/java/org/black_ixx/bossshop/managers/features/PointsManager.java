package org.black_ixx.bossshop.managers.features;

import org.black_ixx.bossshop.BossShop;
import org.black_ixx.bossshop.managers.ClassManager;
import org.black_ixx.bossshop.pointsystem.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class PointsManager {

    private BSPointsPlugin pa;

    public PointsManager() {
        this(ClassManager.manager.getSettings().getPointsPlugin());
    }


    public PointsManager(PointsPlugin p) {
        if (p == null) {
            this.pa = new BSPointsPluginFailed();
            return;
        }

        if (p != PointsPlugin.NONE) {
            if (Bukkit.getPluginManager().getPlugin(p.getPluginName()) == null) {
                ClassManager.manager.getBugFinder().severe("You defined " + p.getPluginName() + " as Points Plugin... BUT IT WAS NOT FOUND?! Please install it or use an alternative like PlayerPoints (https://www.spigotmc.org/resources/playerpoints.80745/). If you want " + BossShop.NAME + " to auto-detect your Points plugin simply set 'PointsPlugin: auto-detect'.");
                this.pa = new BSPointsPluginFailed();
                return;
            }
        }

        switch (p) {
            case ENJIN_MINECRAFT_PLUGIN:
                this.pa = new BSPointsPluginEnjin();
                break;

            case PLAYERPOINTS:
                this.pa = new BSPointsPluginPlayerPoints();
                break;

            case TOKENENCHANT:
                this.pa = new BSPointsPluginTokenEnchant();
                break;

            case Jobs:
                this.pa = new BSPointsPluginJobs();
                break;

            case GadetsMenu:
                this.pa = new BSPointsPluginGadgetsMenu();
                break;

            case VotingPlugin:
                this.pa = new BSPointsPluginVotingPlugin();
                break;

            case NONE:
                this.pa = new BSPointsPluginNone();
                break;

            case CUSTOM:
                BSPointsPlugin customPoints = BSPointsAPI.get(p.getCustom());
                if (customPoints != null) {
                    this.pa = customPoints;
                }
                break;
        }

        if (this.pa == null) {
            ClassManager.manager.getBugFinder().warn("No PointsPlugin was found... You need one if you want BossShopPro to work with Points! Get PlayerPoints here: https://www.spigotmc.org/resources/playerpoints.80745/");
            this.pa = new BSPointsPluginFailed();
        } else {
            BossShop.log("Successfully hooked into Points plugin " + this.pa.getName() + ".");
        }
    }

    public double getPoints(OfflinePlayer player) {
        return pa.getPoints(player);
    }

    public double setPoints(OfflinePlayer player, double points) {
        return pa.setPoints(player, points);
    }

    public double givePoints(OfflinePlayer player, double points) {
        return pa.givePoints(player, points);
    }

    public double takePoints(OfflinePlayer player, double points) {
        return pa.takePoints(player, points);
    }

    public boolean usesDoubleValues() {
        return pa.usesDoubleValues();
    }

    public enum PointsPlugin {
        NONE(new String[]{"none", "nothing"}),
        PLAYERPOINTS(new String[]{"PlayerPoints", "PlayerPoint", "PP"}),
        ENJIN_MINECRAFT_PLUGIN(new String[]{"EnjinMinecraftPlugin", "Enjin", "EMP"}),
        TOKENENCHANT(new String[]{"TokenEnchant", "TE", "TokenEnchants"}),
        Jobs(new String[]{"Jobs", "JobsReborn"}),
        GadetsMenu(new String[]{"GadgetsMenu"}),
        VotingPlugin(new String[]{"VotingPlugin","VP"}),
        CUSTOM(new String[0]);

        private final String[] nicknames;
        private String custom_name;

        PointsPlugin(String[] nicknames) {
            this.nicknames = nicknames;
        }

        public String getCustom() {
            return this.custom_name;
        }

        public void setCustom(String custom_name) {
            this.custom_name = custom_name;
        }

        public String[] getNicknames() {
            return this.nicknames;
        }

        public String getPluginName() {
            if (getNicknames() == null) {
                return custom_name;
            }
            if (getNicknames().length == 0) {
                return custom_name;
            }
            return getNicknames()[0];
        }
    }

}
