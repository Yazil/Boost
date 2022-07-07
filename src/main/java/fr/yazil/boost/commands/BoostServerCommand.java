package fr.yazil.boost.commands;

import fr.yazil.boost.boosts.BoostsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BoostServerCommand extends SubCommand{

    public BoostServerCommand() {
        super("server", "/boost server <power> <time>");
    }

    @Override
    public void onCommand(CommandSender commandSender, Command command, String[] args) {
        if(args.length != 3){
            commandSender.sendMessage(ChatColor.RED + "Invalid usage ! Correct usage: " + getUsage());
            return;
        }

        float power;
        try {
            power = Float.parseFloat(args[1]);
        } catch(NumberFormatException e){
            commandSender.sendMessage(ChatColor.RED + "Power needs to be a valid number ! Ex: 2.5");
            return;
        }

        int time;
        try {
            time = Integer.parseInt(args[2]);
        } catch(NumberFormatException e) {
            commandSender.sendMessage(ChatColor.RED + "Time needs to be a valid number ! Ex: 25");
            return;
        }

        BoostsManager boostsManager = BoostsManager.getInstance();
        boostsManager.boostServer(power, time);
        commandSender.sendMessage(ChatColor.GREEN + "The server's boost is now: x"
                + boostsManager.getServerBoost().getMultiplier()
                + " and remains for another: "
                + (boostsManager.getServerBoost().getDuration() - boostsManager.getServerBoost().getActiveFor())
                + "min"
        );
    }

}
