package me.lucko.lpcookbook;

import me.lucko.lpcookbook.commands.AddPermissionCommand;
import me.lucko.lpcookbook.commands.GetGroupsCommand;
import me.lucko.lpcookbook.commands.GetOfflinePrefixCommand;
import me.lucko.lpcookbook.commands.GetPrefixCommand;
import me.lucko.lpcookbook.commands.SetGroupCommand;
import me.lucko.lpcookbook.commands.SetPrefixCommand;
import me.lucko.lpcookbook.listener.PermissionNotifyListener;
import me.lucko.lpcookbook.listener.PlayerFirstJoinListener;
import me.lucko.lpcookbook.listener.PlayerUsernameChangeListener;

import net.luckperms.api.LuckPerms;

import org.bukkit.plugin.java.JavaPlugin;

public class CookbookPlugin extends JavaPlugin {

    private LuckPerms luckPerms;

    @Override
    public void onEnable() {
        // Load an instance of 'LuckPerms' using the services manager.
        this.luckPerms = getServer().getServicesManager().load(LuckPerms.class);

        // Define some example commands.
        getCommand("lpcookbook-addpermission").setExecutor(new AddPermissionCommand(this, this.luckPerms));
        getCommand("lpcookbook-getgroups").setExecutor(new GetGroupsCommand(this, this.luckPerms));
        getCommand("lpcookbook-getofflineprefix").setExecutor(new GetOfflinePrefixCommand(this, this.luckPerms));
        getCommand("lpcookbook-getprefix").setExecutor(new GetPrefixCommand(this, this.luckPerms));
        getCommand("lpcookbook-setgroup").setExecutor(new SetGroupCommand(this, this.luckPerms));
        getCommand("lpcookbook-setprefix").setExecutor(new SetPrefixCommand(this, this.luckPerms));

        // Define some example listeners.
        new PermissionNotifyListener(this, this.luckPerms).register();
        new PlayerFirstJoinListener(this, this.luckPerms).register();
        new PlayerUsernameChangeListener(this, this.luckPerms).register();
    }

}
