package fr.yazil.boost.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BoostResetCommand extends SubCommand{

    public BoostResetCommand() {
        super("reset", "/boost reset [player]");
    }

    @Override
    public void onCommand(CommandSender commandSender, Command command, String[] args) {

    }

}
