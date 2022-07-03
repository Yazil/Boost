package fr.yazil.boost.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BoostPlayerCommand extends SubCommand{

    public BoostPlayerCommand() {
        super("player", "/boost player <player> <power> <time>");
    }

    @Override
    public void onCommand(CommandSender commandSender, Command command, String[] args) {

    }
}
