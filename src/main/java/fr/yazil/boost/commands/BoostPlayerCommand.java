package fr.yazil.boost.commands;

import fr.yazil.boost.boosts.BoostsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BoostPlayerCommand extends SubCommand{

    public BoostPlayerCommand() {
        super("player", "/boost player <player> <power> <time>");
    }

    @Override
    public void onCommand(CommandSender commandSender, Command command, String[] args) {
        if(args.length != 4){
            commandSender.sendMessage(ChatColor.RED + "Utilisation incorrecte: " + getUsage());
            return;
        }

        Player player = Bukkit.getPlayer(args[1]);
        if(player == null) {
            commandSender.sendMessage(ChatColor.RED + "Ce joueur n'est pas en ligne ou n'existe pas.");
            return;
        }

        float power;
        try {
            power = Float.parseFloat(args[2]);
        } catch(NumberFormatException e){
            commandSender.sendMessage(ChatColor.RED + "Power doit être un nombre valide! Ex: 2.5");
            return;
        }

        int time;
        try {
            time = Integer.parseInt(args[3]);
        } catch(NumberFormatException e) {
            commandSender.sendMessage(ChatColor.RED + "Time doit être un entier valide! Ex: 25");
            return;
        }

        BoostsManager boostsManager = BoostsManager.getInstance();
        boostsManager.boostPlayer(player, power, time);
        if(boostsManager.getPlayerBoostMap().containsKey(player)){
            commandSender.sendMessage(ChatColor.GREEN + "Le boost de " + player.getName() + "est maintenant de: x"
                    + boostsManager.getPlayerBoostMap().get(player).getMultiplier()
                    + " et expire dans: "
                    + (boostsManager.getPlayerBoostMap().get(player).getDuration() - boostsManager.getPlayerBoostMap().get(player).getActiveFor())
                    + "min"
            );
            player.sendMessage("Votre boost maintenant de x" +
                    boostsManager.getPlayerBoostMap().get(player).getMultiplier() + " et expire dans " +
                    (boostsManager.getPlayerBoostMap().get(player).getDuration() - boostsManager.getPlayerBoostMap().get(player).getActiveFor()) +
                    "min.");
        } else {
            commandSender.sendMessage(ChatColor.RED + "Le boost du joueur a été supprimé car le multiplicateur ou sa durée était négative.");
        }

    }
}
