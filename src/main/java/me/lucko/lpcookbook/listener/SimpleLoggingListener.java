package me.lucko.lpcookbook.listener;

import me.lucko.lpcookbook.CookbookPlugin;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.node.NodeMutateEvent;
import net.luckperms.api.event.user.UserLoadEvent;

public class SimpleLoggingListener {
    private final CookbookPlugin plugin;
    private final LuckPerms luckPerms;

    public SimpleLoggingListener(CookbookPlugin plugin, LuckPerms luckPerms) {
        this.plugin = plugin;
        this.luckPerms = luckPerms;
    }

    public void register() {
        EventBus eventBus = this.luckPerms.getEventBus();
        eventBus.subscribe(this.plugin, UserLoadEvent.class, this::onUserLoad);
        eventBus.subscribe(this.plugin, NodeMutateEvent.class, this::onNodeMutate);
    }

    private void onUserLoad(UserLoadEvent e) {
        this.plugin.getLogger().info("UserLoadEvent fired! - " + e.getUser().getUniqueId());
    }

    private void onNodeMutate(NodeMutateEvent e) {
        this.plugin.getLogger().info("NoteMutateEvent fired! - " + e);
    }

}
