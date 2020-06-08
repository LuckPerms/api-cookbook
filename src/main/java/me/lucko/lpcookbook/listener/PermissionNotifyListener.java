package me.lucko.lpcookbook.listener;

import me.lucko.lpcookbook.CookbookPlugin;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.node.NodeAddEvent;
import net.luckperms.api.event.node.NodeRemoveEvent;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.types.InheritanceNode;
import net.luckperms.api.node.types.PermissionNode;
import net.luckperms.api.node.types.PrefixNode;
import net.luckperms.api.node.types.SuffixNode;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PermissionNotifyListener {
    private final CookbookPlugin plugin;
    private final LuckPerms luckPerms;

    public PermissionNotifyListener(CookbookPlugin plugin, LuckPerms luckPerms) {
        this.plugin = plugin;
        this.luckPerms = luckPerms;
    }

    public void register() {
        EventBus eventBus = this.luckPerms.getEventBus();
        eventBus.subscribe(this.plugin, NodeAddEvent.class, this::onNodeAdd);
        eventBus.subscribe(this.plugin, NodeRemoveEvent.class, this::onNodeRemove);
    }

    private void onNodeAdd(NodeAddEvent e) {
        if (!e.isUser()) {
            return;
        }

        User target = (User) e.getTarget();
        Node node = e.getNode();

        // LuckPerms events are posted async, we want to process on the server thread!
        this.plugin.getServer().getScheduler().runTask(this.plugin, () -> {
            Player player = this.plugin.getServer().getPlayer(target.getUniqueId());
            if (player == null) {
                return; // Player not online.
            }

            if (node instanceof PermissionNode) {
                String permission = ((PermissionNode) node).getPermission();
                player.sendMessage(ChatColor.YELLOW + "You were given the " + permission + " permission!");

            } else if (node instanceof InheritanceNode) {
                String groupName = ((InheritanceNode) node).getGroupName();
                player.sendMessage(ChatColor.YELLOW + "You were added to the " + groupName + " group!");

            } else if (node instanceof PrefixNode) {
                String prefix = ((PrefixNode) node).getMetaValue();
                player.sendMessage(ChatColor.YELLOW + "You were given the " + prefix + " prefix!");

            } else if (node instanceof SuffixNode) {
                String suffix = ((SuffixNode) node).getMetaValue();
                player.sendMessage(ChatColor.YELLOW + "You were given the " + suffix + " suffix!");
            }
        });
    }

    private void onNodeRemove(NodeRemoveEvent e) {
        if (!e.isUser()) {
            return;
        }

        User target = (User) e.getTarget();
        Node node = e.getNode();

        // LuckPerms events are posted async, we want to process on the server thread!
        this.plugin.getServer().getScheduler().runTask(this.plugin, () -> {
            Player player = this.plugin.getServer().getPlayer(target.getUniqueId());
            if (player == null) {
                return; // Player not online.
            }

            if (node instanceof PermissionNode) {
                String permission = ((PermissionNode) node).getPermission();
                player.sendMessage(ChatColor.DARK_RED + "You no longer have the " + permission + " permission!");

            } else if (node instanceof InheritanceNode) {
                String groupName = ((InheritanceNode) node).getGroupName();
                player.sendMessage(ChatColor.DARK_RED + "You are no longer in the " + groupName + " group!");

            } else if (node instanceof PrefixNode) {
                String prefix = ((PrefixNode) node).getMetaValue();
                player.sendMessage(ChatColor.DARK_RED + "You no longer have the " + prefix + " prefix!");

            } else if (node instanceof SuffixNode) {
                String suffix = ((SuffixNode) node).getMetaValue();
                player.sendMessage(ChatColor.DARK_RED + "You no longer have the " + suffix + " suffix!");
            }
        });
    }

}
