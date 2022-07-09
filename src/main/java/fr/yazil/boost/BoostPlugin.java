package fr.yazil.boost;

import fr.yazil.boost.boosts.BoostUpdateLoop;
import fr.yazil.boost.commands.BoostExecutor;
import fr.yazil.boost.utils.BossBarUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class BoostPlugin extends JavaPlugin{

    @Override
    public void onEnable() {
        getServer().getPluginCommand("boost").setExecutor(new BoostExecutor());
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new BoostUpdateLoop(), 1200, 1200);
        getServer().getPluginManager().registerEvents(new BoostListener(), this);

        //Update the virtual wither pos for the bossbar
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                if(Bukkit.getOnlinePlayers().size() == 0)
                    return;

                for(Player p: Bukkit.getOnlinePlayers()) {
                    BossBarUtils.getInstance().teleportBar(p);
                }
            }
        }, 20 , 20);
    }


}
