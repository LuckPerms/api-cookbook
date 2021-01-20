package me.lucko.lpcookbook.listener;

import me.lucko.lpcookbook.CookbookPlugin;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.node.NodeAddEvent;
import net.luckperms.api.event.node.NodeRemoveEvent;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.InheritanceNode;
import net.luckperms.api.node.types.PrefixNode;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class GroupPrefixChangedListener {
    private final CookbookPlugin plugin;
    private final LuckPerms luckPerms;

    public GroupPrefixChangedListener(CookbookPlugin plugin, LuckPerms luckPerms) {
        this.plugin = plugin;
        this.luckPerms = luckPerms;
    }

    public void register() {
        EventBus eventBus = this.luckPerms.getEventBus();
        eventBus.subscribe(this.plugin, NodeAddEvent.class, this::onNodeAdd);
        eventBus.subscribe(this.plugin, NodeRemoveEvent.class, this::onNodeRemove);
    }

    private void onNodeAdd(NodeAddEvent e) {
        // Check if the event was acting on a Group
        if (!e.isGroup()) {
            return;
        }

        // Check if the node was a prefix node
        Node node = e.getNode();
        if (node.getType() != NodeType.PREFIX) {
            return;
        }

        // Cast the node to PrefixNode, and the target to Group.
        PrefixNode prefixNode = ((PrefixNode) node);
        Group group = (Group) e.getTarget();

        String newPrefix = prefixNode.getMetaValue();
        Bukkit.broadcastMessage(ChatColor.GREEN + "The prefix " + newPrefix + " was added to " + group.getName());
    }

    private void onNodeRemove(NodeRemoveEvent e) {
        // Check if the event was acting on a Group
        if (!e.isGroup()) {
            return;
        }

        // Check if the node was a prefix node
        Node node = e.getNode();
        if (node.getType() != NodeType.PREFIX) {
            return;
        }

        // Cast the node to PrefixNode, and the target to Group.
        PrefixNode prefixNode = ((PrefixNode) node);
        Group group = (Group) e.getTarget();

        String oldPrefix = prefixNode.getMetaValue();
        Bukkit.broadcastMessage(ChatColor.GREEN + "The prefix " + oldPrefix + " was removed from " + group.getName());
    }

}
