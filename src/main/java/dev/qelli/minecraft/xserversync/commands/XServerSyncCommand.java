package dev.qelli.minecraft.xserversync.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;

import dev.qelli.minecraft.xserversync.XServerSync;
import dev.qelli.minecraft.xserversync.utils.Constants;

public class XServerSyncCommand implements CommandExecutor, TabCompleter {
    XServerSync plugin;

    public XServerSyncCommand(XServerSync plugin) {
        this.plugin = plugin;
        register();
    }

    public void register() {
        PluginCommand command = plugin.getCommand("xserversync");
        command.setExecutor(this);
        command.setAliases(List.of("xss", "xsync"));
        command.setPermission(Constants.Permissions.Admin);
        command.setTabCompleter(this);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 0) {
            return List.of();
        }
        if (args.length == 1) {
            return List.of(
                Constants.Actions.Commands.Init,
                Constants.Actions.Commands.Log,
                Constants.Actions.Commands.Reload
            );
        }
        if (args.length == 2) {

        }
        return List.of();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            return false;
        }

        switch (args[0]) {
            case Constants.Actions.Commands.Init:
                plugin.getInstanceManager().load();
                return true;
            case Constants.Actions.Commands.Log:
                plugin.getInstanceManager().log();
                return true;
        }

        return false;
    }
}
