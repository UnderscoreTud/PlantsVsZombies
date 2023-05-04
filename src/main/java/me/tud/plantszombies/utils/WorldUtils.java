package me.tud.plantszombies.utils;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public final class WorldUtils {

    private WorldUtils() {
        throw new UnsupportedOperationException();
    }

    public static World createWorld(String name) {
        return createWorld(new WorldCreator(name));
    }

    public static World createWorld(WorldCreator creator) {
        return creator.createWorld();
    }

    public static World copyWorld(String name, World worldToClone) {
        return createWorld(new WorldCreator(name).copy(worldToClone));
    }

    public static World cloneWorld(String name, World worldToClone) {
        File source = getWorldFile(worldToClone);
        File destination = getWorldFile(name);
        try {
            FileUtils.copyDirectory(source, destination, pathname -> {
                String fileName = pathname.getName();
                return !fileName.equals("uid.dat") && !fileName.equals("session.lock");
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return copyWorld(name, worldToClone);
    }

    public static Optional<World> loadAndGetWorld(String name) {
        if (!getWorldFile(name).exists() && Bukkit.getWorld(name) == null)
            return Optional.empty();
        return Optional.ofNullable(createWorld(name));
    }

    public static boolean loadWorld(String name) {
        return loadAndGetWorld(name).isPresent();
    }

    public static boolean unloadWorld(World world) {
        return unloadWorld(world, false);
    }

    public static boolean unloadWorld(World world, boolean save) {
        World mainWorld = Bukkit.getWorlds().get(0);
        if (mainWorld.equals(world)) {
            if (Bukkit.getWorlds().size() > 1) {
                mainWorld = Bukkit.getWorlds().get(1);
            } else {
                mainWorld = null;
            }
        }

        if (mainWorld != null) {
            for (Player player : world.getPlayers())
                player.teleport(mainWorld.getSpawnLocation());
        }

        return Bukkit.unloadWorld(world, save);
    }

    public static boolean deleteWorld(String name) {
        World world = Bukkit.getWorld(name);
        if (world != null)
            return deleteWorld(world);
        return deleteWorldFile(name);
    }

    public static boolean deleteWorld(World world) {
        for (Player player : world.getEntitiesByClass(Player.class))
            player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
        if (!unloadWorld(world))
            return false;
        return deleteWorldFile(world.getName());
    }

    public static File getWorldFile(World world) {
        return getWorldFile(world.getName());
    }

    public static File getWorldFile(String name) {
        return new File(Bukkit.getWorldContainer(), name);
    }

    private static boolean deleteWorldFile(String name) {
        File file = getWorldFile(name);
        if (file.exists()) {
            try {
                FileUtils.deleteDirectory(file);
                return true;
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }

    public static void setGameRules(World world) {
        world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        world.setGameRule(GameRule.COMMAND_BLOCK_OUTPUT, false);
        world.setGameRule(GameRule.DISABLE_RAIDS, true);
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        world.setGameRule(GameRule.DO_ENTITY_DROPS, false);
        world.setGameRule(GameRule.DO_FIRE_TICK, false);
        world.setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
        world.setGameRule(GameRule.DO_INSOMNIA, true);
        world.setGameRule(GameRule.DO_MOB_LOOT, false);
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        world.setGameRule(GameRule.DO_PATROL_SPAWNING, false);
        world.setGameRule(GameRule.DO_TILE_DROPS, false);
        world.setGameRule(GameRule.DO_TRADER_SPAWNING, false);
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
        world.setGameRule(GameRule.DROWNING_DAMAGE, false);
        world.setGameRule(GameRule.FALL_DAMAGE, false);
        world.setGameRule(GameRule.FIRE_DAMAGE, false);
        world.setGameRule(GameRule.FREEZE_DAMAGE, false);
        world.setGameRule(GameRule.MOB_GRIEFING, false);
        world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
        world.setGameRule(GameRule.SPECTATORS_GENERATE_CHUNKS, false);
        world.setGameRule(GameRule.SPAWN_RADIUS, 0);
    }

}
