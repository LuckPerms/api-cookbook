package me.lucko.lpcookbook.listener;

import me.lucko.lpcookbook.CookbookPlugin;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.player.PlayerDataSaveEvent;
import net.luckperms.api.model.PlayerSaveResult;
import net.luckperms.api.model.PlayerSaveResult.Outcome;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.Set;

public class PlayerFirstJoinListener {
    private final CookbookPlugin plugin;
    private final LuckPerms luckPerms;

    public PlayerFirstJoinListener(CookbookPlugin plugin, LuckPerms luckPerms) {
        this.plugin = plugin;
        this.luckPerms = luckPerms;
    }

    public void register() {
        EventBus eventBus = this.luckPerms.getEventBus();
        eventBus.subscribe(this.plugin, PlayerDataSaveEvent.class, this::onDataSave);
    }

    private void onDataSave(PlayerDataSaveEvent e) {
        PlayerSaveResult result = e.getResult();
        Set<Outcome> outcomes = result.getOutcomes();

        if (outcomes.contains(Outcome.CLEAN_INSERT)) {
            Bukkit.broadcastMessage(ChatColor.GREEN + e.getUsername() + " joined the network for the first time!");
        }
    }

}
