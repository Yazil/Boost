package fr.yazil.boost;

import fr.yazil.boost.boosts.BoostsManager;
import fr.yazil.boost.utils.BossBarUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BoostListener implements Listener {

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent e) {
        if(BoostsManager.getInstance().getServerBoost() == null)
            return;

        BoostsManager boostsManager = BoostsManager.getInstance();

        BossBarUtils.getInstance().setBossBar(e.getPlayer(),
                "Boost serveur - x" + boostsManager.getServerBoost().getMultiplier() + " - Exp: " + (boostsManager.getServerBoost().getDuration()- boostsManager.getServerBoost().getActiveFor()) + "min",
                Math.round((1f-((float)boostsManager.getServerBoost().getActiveFor()/(float)boostsManager.getServerBoost().getDuration()))*100f));
    }

    @EventHandler
    public void playerQuitEvent(PlayerQuitEvent e) {
        BossBarUtils.getInstance().removeBar(e.getPlayer());
    }

}
