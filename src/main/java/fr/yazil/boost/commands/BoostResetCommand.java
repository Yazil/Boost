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
            commandSender.sendMessage(ChatColor.GREEN + "Suppression du boost du serveur !");
        } else if (args.length == 2) {
            //Reset a player's boost
            Player player = Bukkit.getPlayer(args[1]);
            if(player == null) {
                commandSender.sendMessage(ChatColor.RED + "Ce joueur n'est pas en ligne ou n'existe pas.");
                return;
            }
            BoostsManager.getInstance().resetPlayerBoost(player);
            commandSender.sendMessage(ChatColor.GREEN + "Suppression du boost de " + player.getName() + "!");
            player.sendMessage(ChatColor.RED + "Votre boost a été supprimé !");
        } else {
            commandSender.sendMessage(ChatColor.RED + "Utilisation incorrecte: " + getUsage());
        }
    }

}
