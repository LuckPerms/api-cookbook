package me.lucko.lpcookbook.commands;

import me.lucko.lpcookbook.CookbookPlugin;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.concurrent.CompletableFuture;

public class GetOfflinePrefixCommand implements CommandExecutor {
    private final CookbookPlugin plugin;
    private final LuckPerms luckPerms;

    public GetOfflinePrefixCommand(CookbookPlugin plugin, LuckPerms luckPerms) {
        this.plugin = plugin;
        this.luckPerms = luckPerms;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Please specify a player name!");
            return true;
        }

        String playerName = args[0];

        // Check to see if the player already has some data loaded.
        User onlineUser = this.luckPerms.getUserManager().getUser(playerName);
        if (onlineUser != null) {
            // Get LuckPerms cached metadata for the player.
            CachedMetaData metaData = onlineUser.getCachedData().getMetaData();

            // Get their prefix.
            String prefix = metaData.getPrefix();

            // Tell the sender.
            sender.sendMessage(ChatColor.RED + onlineUser.getUsername() +  "'s prefix is: " + ChatColor.RESET + prefix);
            return true;
        }

        // Get an OfflinePlayer object for the player
        OfflinePlayer player = this.plugin.getServer().getOfflinePlayer(playerName);

        // Player not known?
        if (player == null ||!player.hasPlayedBefore()) {
            sender.sendMessage(ChatColor.RED + playerName +  " has never joined the server!");
            return true;
        }

        // Then load a user for them.
        CompletableFuture<User> userLoadTask = this.luckPerms.getUserManager().loadUser(player.getUniqueId());

        // Add a callback to the user lookup task.
        userLoadTask.thenAccept((User user) -> {
            // Get LuckPerms cached metadata for the user.
            CachedMetaData metaData = user.getCachedData().getMetaData();

            // Get their prefix.
            String prefix = metaData.getPrefix();

            // Tell the sender.
            sender.sendMessage(ChatColor.RED + user.getUsername() +  "'s prefix is: " + ChatColor.RESET + prefix);
        });

        return true;
    }
}
