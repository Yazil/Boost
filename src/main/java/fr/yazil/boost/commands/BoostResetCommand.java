package fr.yazil.boost.commands;

import fr.yazil.boost.boosts.BoostsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BoostResetCommand extends SubCommand{

    public BoostResetCommand() {
        super("reset", "/boost reset [player]");
    }

    @Override
    public void onCommand(CommandSender commandSender, Command command, String[] args) {
        if(args.length == 1) {
            //Reset the server's boost
            BoostsManager.getInstance().resetServerBoost();
            commandSender.sendMessage(ChatColor.GREEN + "Resetting the server's boost !");
        } else if (args.length == 2) {
            //Reset a player's boost
            Player player = Bukkit.getPlayer(args[1]);
            if(player == null) {
                commandSender.sendMessage(ChatColor.RED + "Player is whether not online or invalid.");
                return;
            }
            BoostsManager.getInstance().resetPlayerBoost(player);
            commandSender.sendMessage(ChatColor.GREEN + "Resetting " + player.getName() +"'s boost !");
        } else {
            commandSender.sendMessage(ChatColor.RED + "Invalid usage ! Correct usage: " + getUsage());
        }
    }

}
