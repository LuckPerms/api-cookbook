package me.lucko.lpcookbook.listener;

import me.lucko.lpcookbook.CookbookPlugin;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.node.NodeAddEvent;
import net.luckperms.api.event.node.NodeRemoveEvent;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.InheritanceNode;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class PlayerRemovedFromGroupListener {
    private final CookbookPlugin plugin;
    private final LuckPerms luckPerms;

    public PlayerRemovedFromGroupListener(CookbookPlugin plugin, LuckPerms luckPerms) {
        this.plugin = plugin;
        this.luckPerms = luckPerms;
    }

    public void register() {
        EventBus eventBus = this.luckPerms.getEventBus();
        eventBus.subscribe(this.plugin, NodeRemoveEvent.class, this::onNodeRemove);
    }

    private void onNodeRemove(NodeRemoveEvent e) {
        // Check if the event was acting on a User
        if (!e.isUser()) {
            return;
        }

        // Check if the node was an inheritance node
        Node node = e.getNode();
        if (node.getType() != NodeType.INHERITANCE) {
            return;
        }

        // Cast the node to InheritanceNode, and the target to User.
        InheritanceNode inheritanceNode = ((InheritanceNode) node);
        User user = (User) e.getTarget();

        String groupName = inheritanceNode.getGroupName();
        Bukkit.broadcastMessage(ChatColor.GREEN + user.getUsername() + " removed from the " + groupName + " group!");
    }

}
