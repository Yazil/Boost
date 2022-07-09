package fr.yazil.boost.commands;

import fr.yazil.boost.boosts.BoostsManager;
import fr.yazil.boost.utils.BossBarUtils;
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
            commandSender.sendMessage(ChatColor.RED + "Utilisation incorrecte: " + getUsage());
            return;
        }

        float power;
        try {
            power = Float.parseFloat(args[1]);
        } catch(NumberFormatException e){
            commandSender.sendMessage(ChatColor.RED + "Power doit être un nombre valide! Ex: 2.5");
            return;
        }

        int time;
        try {
            time = Integer.parseInt(args[2]);
        } catch(NumberFormatException e) {
            commandSender.sendMessage(ChatColor.RED + "Time doit être un entier valide! Ex: 25");
            return;
        }

        BoostsManager boostsManager = BoostsManager.getInstance();
        boostsManager.boostServer(power, time);
        if(boostsManager.getServerBoost() != null) {
            commandSender.sendMessage(ChatColor.GREEN + "Vous avez défini le boost du serveur: x"
                    + boostsManager.getServerBoost().getMultiplier()
                    + " et expire dans : "
                    + (boostsManager.getServerBoost().getDuration() - boostsManager.getServerBoost().getActiveFor())
                    + "min"
            );
            Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "Le boost du serveur est maintenant de x" +
                    boostsManager.getServerBoost().getMultiplier() +
                    " et expire dans " + (boostsManager.getServerBoost().getDuration() - boostsManager.getServerBoost().getActiveFor()) + "min.");
            for(Player p : Bukkit.getOnlinePlayers()) {
                BossBarUtils.getInstance().setBossBar(p,
                        "Boost serveur - x" + boostsManager.getServerBoost().getMultiplier() + " - Exp: " + (boostsManager.getServerBoost().getDuration()- boostsManager.getServerBoost().getActiveFor()) + "min",
                        Math.round((1f-((float)boostsManager.getServerBoost().getActiveFor()/(float)boostsManager.getServerBoost().getDuration()))*100f));
            }
        } else {
            commandSender.sendMessage(ChatColor.RED + "Le boost du serveur a été supprimé car le multiplicateur ou sa durée était négative.");
        }

    }

}
