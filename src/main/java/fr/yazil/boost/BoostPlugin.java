package fr.yazil.boost;

import fr.yazil.boost.boosts.BoostsManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class BoostPlugin extends JavaPlugin {

    @Getter
    private BoostsManager boostsManager;

    @Override
    public void onEnable() {
        boostsManager = new BoostsManager();
    }


}
