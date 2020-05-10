package me.lucko.lpcookbook.commands;

import me.lucko.lpcookbook.CookbookPlugin;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.platform.PlayerAdapter;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.stream.Collectors;

public class GetGroupsCommand implements CommandExecutor {
    private final CookbookPlugin plugin;
    private final LuckPerms luckPerms;

    public GetGroupsCommand(CookbookPlugin plugin, LuckPerms luckPerms) {
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

        // Get a Bukkit player adapter.
        PlayerAdapter<Player> playerAdapter = this.luckPerms.getPlayerAdapter(Player.class);

        // Get a LuckPerms user for the player.
        User user = playerAdapter.getUser(player);

        // Get all of the groups they inherit from on the current server.
        Collection<Group> groups = user.getInheritedGroups(playerAdapter.getQueryOptions(player));

        // Convert to a comma separated string (e.g. "admin, mod, default")
        String groupsString = groups.stream().map(Group::getName).collect(Collectors.joining(", "));

        // Tell the sender.
        sender.sendMessage(ChatColor.RED + "You inherit from: " + groupsString);
        return true;
    }
}
