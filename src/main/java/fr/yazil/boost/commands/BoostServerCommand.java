package fr.yazil.boost.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BoostServerCommand extends SubCommand{

    public BoostServerCommand() {
        super("server", "/boost server <power> <time>");
    }

    @Override
    public void onCommand(CommandSender commandSender, Command command, String[] args) {

    }

}
