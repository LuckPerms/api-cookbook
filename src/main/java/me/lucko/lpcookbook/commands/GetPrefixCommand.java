package me.lucko.lpcookbook.commands;

import me.lucko.lpcookbook.CookbookPlugin;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetPrefixCommand implements CommandExecutor {
    private final CookbookPlugin plugin;
    private final LuckPerms luckPerms;

    public GetPrefixCommand(CookbookPlugin plugin, LuckPerms luckPerms) {
        this.plugin = plugin;
        this.luckPerms = luckPerms;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // This command is only for players.
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command is only for players!");
            return true;
        }

        Player player = (Player) sender;

        // Get a LuckPerms cached metadata for the player.
        CachedMetaData metaData = this.luckPerms.getPlayerAdapter(Player.class).getMetaData(player);

        // Get their prefix.
        String prefix = metaData.getPrefix();

        // Tell the sender.
        sender.sendMessage(ChatColor.RED + "Your prefix is: " + ChatColor.RESET + prefix);
        return true;
    }
}
