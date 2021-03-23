package me.lucko.lpcookbook.commands;

import me.lucko.lpcookbook.CookbookPlugin;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.node.matcher.NodeMatcher;
import net.luckperms.api.node.types.InheritanceNode;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class GroupMembersCommand implements CommandExecutor {
    private final CookbookPlugin plugin;
    private final LuckPerms luckPerms;

    public GroupMembersCommand(CookbookPlugin plugin, LuckPerms luckPerms) {
        this.plugin = plugin;
        this.luckPerms = luckPerms;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Please specify a group!");
            return true;
        }

        String groupName = args[0];

        // Get a group object for the group name.
        Group group = this.luckPerms.getGroupManager().getGroup(groupName);

        // Group doesn't exist?
        if (group == null) {
            sender.sendMessage(ChatColor.RED + groupName +  " does not exist!");
            return true;
        }

        // Create a NodeMatcher, matching inheritance nodes for the given group
        NodeMatcher<InheritanceNode> matcher = NodeMatcher.key(InheritanceNode.builder(group).build());

        // Search all users for a match
        this.luckPerms.getUserManager().searchAll(matcher).thenAccept((Map<UUID, Collection<InheritanceNode>> map) -> {
            // Get a set of the group members (their UUIDs)
            Set<UUID> memberUniqueIds = map.keySet();

            // Tell the sender.
            sender.sendMessage(ChatColor.RED + group.getName() + " has " + memberUniqueIds.size() + " member(s).");
            sender.sendMessage(ChatColor.RED + memberUniqueIds.toString());
        });

        return true;
    }
}
