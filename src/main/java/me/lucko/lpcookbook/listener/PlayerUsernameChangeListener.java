package me.lucko.lpcookbook.listener;

import me.lucko.lpcookbook.CookbookPlugin;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.player.PlayerDataSaveEvent;
import net.luckperms.api.model.PlayerSaveResult;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class PlayerUsernameChangeListener {
    private final CookbookPlugin plugin;
    private final LuckPerms luckPerms;

    public PlayerUsernameChangeListener(CookbookPlugin plugin, LuckPerms luckPerms) {
        this.plugin = plugin;
        this.luckPerms = luckPerms;
    }

    public void register() {
        EventBus eventBus = this.luckPerms.getEventBus();
        eventBus.subscribe(this.plugin, PlayerDataSaveEvent.class, this::onDataSave);
    }

    private void onDataSave(PlayerDataSaveEvent e) {
        PlayerSaveResult result = e.getResult();
        String previousUsername = result.getPreviousUsername();

        if (previousUsername != null) {
            Bukkit.broadcastMessage(ChatColor.GREEN + previousUsername + " has updated their username to " + e.getUsername() + "!");
        }
    }

}
