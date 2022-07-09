package fr.yazil.boost.commands;

import lombok.Data;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Data
public abstract class SubCommand {

    private final String name;
    private final String usage;

    public abstract void onCommand(CommandSender commandSender, Command command, String[] args);

}
