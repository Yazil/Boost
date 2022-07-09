package fr.yazil.boost.commands;

import fr.yazil.boost.BoostPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class BoostExecutor implements CommandExecutor {

    private final Map<String, SubCommand> subCommands = new HashMap<>();

    public BoostExecutor() {
        registerSubCommand(new BoostPlayerCommand());
        registerSubCommand(new BoostServerCommand());
        registerSubCommand(new BoostResetCommand());
        registerSubCommand(new BoostViewCommand());
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String alias, String[] args) {
        if(args.length != 0)
            if(subCommands.containsKey(args[0])) {
                subCommands.get(args[0]).onCommand(commandSender, command, args);
                return true;
            }

        commandSender.sendMessage(ChatColor.GRAY + "╔════Liste des commandes════╗");
        subCommands.values().forEach( subCommand -> commandSender.sendMessage("- " + subCommand.getUsage()));

        return true;
    }

    private void registerSubCommand(SubCommand subCommand) {
        subCommands.put(subCommand.getName(), subCommand);
    }
}
