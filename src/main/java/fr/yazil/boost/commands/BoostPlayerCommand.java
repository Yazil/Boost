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
            commandSender.sendMessage(ChatColor.RED + "Invalid usage ! Correct usage: " + getUsage());
            return;
        }

        Player player = Bukkit.getPlayer(args[1]);
        if(player == null) {
            commandSender.sendMessage(ChatColor.RED + "Player is whether not online or invalid.");
            return;
        }

        float power;
        try {
            power = Float.parseFloat(args[2]);
        } catch(NumberFormatException e){
            commandSender.sendMessage(ChatColor.RED + "Power needs to be a valid number ! Ex: 2.5");
            return;
        }

        int time;
        try {
            time = Integer.parseInt(args[3]);
        } catch(NumberFormatException e) {
            commandSender.sendMessage(ChatColor.RED + "Time needs to be a valid number ! Ex: 25");
            return;
        }

        BoostsManager boostsManager = BoostsManager.getInstance();
        boostsManager.boostPlayer(player, power, time);
        commandSender.sendMessage(ChatColor.GREEN + player.getName() + "'s boost is now: x"
                + boostsManager.getPlayerBoostMap().get(player).getMultiplier()
                + " and remains for another: "
                + (boostsManager.getPlayerBoostMap().get(player).getDuration() - boostsManager.getPlayerBoostMap().get(player).getActiveFor())
                + "min"
        );
    }
}
