package me.lucko.lpcookbook.commands;

import me.lucko.lpcookbook.CookbookPlugin;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.data.DataMutateResult;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AddPermissionCommand implements CommandExecutor {
    private final CookbookPlugin plugin;
    private final LuckPerms luckPerms;

    public AddPermissionCommand(CookbookPlugin plugin, LuckPerms luckPerms) {
        this.plugin = plugin;
        this.luckPerms = luckPerms;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 2) {
            sender.sendMessage(ChatColor.RED + "Please specify a player & a permission!");
            return true;
        }

        String playerName = args[0];
        String permission = args[1];

        // Get an OfflinePlayer object for the player
        OfflinePlayer player = this.plugin.getServer().getOfflinePlayer(playerName);

        // Player not known?
        if (!player.hasPlayedBefore()) {
            sender.sendMessage(ChatColor.RED + playerName +  " has never joined the server!");
            return true;
        }

        // Create a node to add to the player.
        Node node = Node.builder(permission).build();

        // Load, modify & save the user in LuckPerms.
        this.luckPerms.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {
            // Try to add the node to the user.
            DataMutateResult result = user.data().add(node);

            // Check to see the result of adding the node.
            if (result.wasSuccessful()) {
                sender.sendMessage(ChatColor.RED + user.getUsername() + " now has permission " + permission + "!");
            } else {
                sender.sendMessage(ChatColor.RED + user.getUsername() + " already has that permission!");
            }
        });

        return true;
    }
}
