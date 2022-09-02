package org.black_ixx.bossshop.pointsystem;

import com.gamingmesh.jobs.Jobs;
import org.bukkit.OfflinePlayer;

public class BSPointsPluginJobs extends BSPointsPlugin {

    public BSPointsPluginJobs() {
        super("Jobs", "JobsReborn");
    }

    @Override
    public double getPoints(OfflinePlayer player) {
        return Jobs.getPlayerManager().getPlayerInfo(player.getUniqueId()).getJobsPlayer().getPointsData().getCurrentPoints();
    }

    @Override
    public double setPoints(OfflinePlayer player, double points) {
        Jobs.getPlayerManager().getPlayerInfo(player.getUniqueId()).getJobsPlayer().getPointsData().setPoints(points);
        return points;
    }

    @Override
    public double takePoints(OfflinePlayer player, double points) {
        Jobs.getPlayerManager().getPlayerInfo(player.getUniqueId()).getJobsPlayer().getPointsData().takePoints(points);
        return getPoints(player);
    }

    @Override
    public double givePoints(OfflinePlayer player, double points) {
        Jobs.getPlayerManager().getPlayerInfo(player.getUniqueId()).getJobsPlayer().getPointsData().addPoints(points);
        return getPoints(player);
    }

    @Override
    public boolean usesDoubleValues() {
        return true;
    }

}
