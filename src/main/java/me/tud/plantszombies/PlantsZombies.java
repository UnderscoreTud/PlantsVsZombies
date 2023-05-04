package me.tud.plantszombies;

import me.tud.plantszombies.commands.TestCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlantsZombies extends JavaPlugin {

    private static PlantsZombies instance;

    @Override
    public void onEnable() {
        instance = this;
        getCommand("test").setExecutor(new TestCommand());
    }

    @Override
    public void onDisable() {
        TestCommand.game.end();
    }

    public static PlantsZombies getInstance() {
        return instance;
    }

}
