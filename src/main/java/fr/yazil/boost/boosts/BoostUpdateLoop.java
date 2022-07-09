package fr.yazil.boost.boosts;

import fr.yazil.boost.utils.BossBarUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BoostUpdateLoop implements Runnable{

    //Runs every minute, increments activeFor variable for both the server boost as well as the players one.
    //It is then ended if the time it was active for is greater than the planed duration.
    @Override
    public void run() {
        BoostsManager boostsManager = BoostsManager.getInstance();
        if(boostsManager.getServerBoost() != null) {
            boostsManager.getServerBoost().incrementActiveFor();

            if(boostsManager.getServerBoost().getActiveFor() >= boostsManager.getServerBoost().getDuration()){
                boostsManager.resetServerBoost();
                Bukkit.broadcastMessage(ChatColor.RED + "Le boost du serveur est terminé !");
                for(Player p : Bukkit.getOnlinePlayers()) {
                    BossBarUtils.getInstance().removeBar(p);
                }
            } else {
                for(Player p : Bukkit.getOnlinePlayers()) {
                    BossBarUtils.getInstance().setBossBar(p,
                            "Boost serveur - x" + boostsManager.getServerBoost().getMultiplier() + " - Exp: " + (boostsManager.getServerBoost().getDuration()- boostsManager.getServerBoost().getActiveFor()) + "min",
                            Math.round((1f-((float)boostsManager.getServerBoost().getActiveFor()/(float)boostsManager.getServerBoost().getDuration()))*100f));
                }
            }
        }

        if(boostsManager.getPlayerBoostMap().size() != 0) {
            List<Player> playerToReset = new ArrayList<>();
            boostsManager.getPlayerBoostMap().forEach(((player, boost) -> {
                boost.incrementActiveFor();

                if(boost.getActiveFor() >= boost.getDuration()) {
                    playerToReset.add(player);
                    player.sendMessage(ChatColor.RED + "Votre boost a expiré !");
                }
            }));

            for(Player p : playerToReset) {
                boostsManager.resetPlayerBoost(p);
            }
        }
    }
}
