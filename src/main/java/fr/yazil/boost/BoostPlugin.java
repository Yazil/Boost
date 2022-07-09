package fr.yazil.boost;

import fr.yazil.boost.boosts.BoostUpdateLoop;
import fr.yazil.boost.commands.BoostExecutor;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class BoostPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginCommand("boost").setExecutor(new BoostExecutor());
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new BoostUpdateLoop(), 200, 200);
    }


}
