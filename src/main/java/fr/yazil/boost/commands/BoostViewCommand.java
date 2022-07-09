package fr.yazil.boost.commands;

import fr.yazil.boost.boosts.Boost;
import fr.yazil.boost.boosts.BoostsManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BoostViewCommand extends SubCommand{

    private final int BOOSTS_PER_PAGE = 20;

    public BoostViewCommand() {
        super("view", "/boost view [page]");
    }

    @Override
    public void onCommand(CommandSender commandSender, Command command, String[] args) {
        int page = 1;
        if(args.length == 2){
            try {
                page = Integer.parseInt(args[1]);
            } catch(NumberFormatException e) {
                commandSender.sendMessage(ChatColor.RED + "Page doit être un numéro valide! Ex: 25");
                return;
            }
        }
        sendBoosterList(commandSender, page);
    }

    //Sends the list of active boosts to commandSender.
    //It only sends BOOSTS_PER_PAGE boosts per page.
    private void sendBoosterList(CommandSender commandSender, int page) {
        BoostsManager boostsManager = BoostsManager.getInstance();
        //Displays the header with the page number
        int maxPages = Math.max(1, Math.round(boostsManager.getPlayerBoostMap().size()/BOOSTS_PER_PAGE));
        commandSender.sendMessage("╔══════════Liste des boosts(" + page +"/" + maxPages + ")══════════╗");
        if(page == 1 && boostsManager.getServerBoost() != null) {
            Boost serverBoost = boostsManager.getServerBoost();
            commandSender.sendMessage("Boost serveur - x" + serverBoost.getMultiplier() + " - Exp:" + (serverBoost.getDuration()- serverBoost.getActiveFor()) + "min");
        }

        //Sends the BOOSTS_PER_PAGE boosts. This code is messy, there must be a better way than this.
        List<Player> playerSet = new ArrayList<>(boostsManager.getPlayerBoostMap().keySet());
        for (int i = BOOSTS_PER_PAGE*(page-1); i < BOOSTS_PER_PAGE*(page); i++) {
            Player p;
            try {
                p = playerSet.get(i);
            } catch (IndexOutOfBoundsException e) {
                break;
            }

            Boost playerBoost = boostsManager.getPlayerBoostMap().get(p);
            commandSender.sendMessage(p.getName() + " - x" + playerBoost.getMultiplier() + " - Exp:" + (playerBoost.getDuration()-playerBoost.getActiveFor()) + "min");
        }
    }

}
