package me.tud.plantszombies.commands;

import me.tud.plantszombies.core.Cell;
import me.tud.plantszombies.core.Game;
import me.tud.plantszombies.core.maps.Day;
import me.tud.plantszombies.core.plants.Peashooter;
import me.tud.plantszombies.core.zombies.NormalZombie;
import me.tud.plantszombies.utils.WorldUtils;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestCommand implements CommandExecutor {

    public static Game game = new Game();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 1)
            return true;
        switch (args[0]) {
            case "start" -> {
                game.start(new Day(game, WorldUtils.cloneWorld("game", WorldUtils.loadAndGetWorld("daymap").get())));
                Bukkit.getOnlinePlayers().forEach(player -> player.teleport(game.getMap().getLocation(new Cell(4, 2))));
            }
            case "end" -> {
                game.end();
                game = new Game();
            }
            case "spawnp" -> {
                if (!(sender instanceof Player player))
                    return true;
                Cell cell = game.getMap().asCell(player.getLocation());
                if (cell == null)
                    return true;
                game.spawnObject(Peashooter::new, cell);
            }
            case "spawnz" -> {
                if (!(sender instanceof Player player))
                    return true;
                Cell cell = game.getMap().asCell(player.getLocation());
                if (cell == null)
                    return true;
                game.spawnObject(NormalZombie::new, cell);
            }
            case "random" -> {
                game.getMap().getSpawner().spawnRandom();
            }
            case "delete" -> WorldUtils.deleteWorld("game");
            case "world" -> {
                World world = new WorldCreator("daymap")
                        .generator("VoidGen")
                        .generateStructures(false)
                        .type(WorldType.FLAT)
                        .createWorld();
                Bukkit.getOnlinePlayers().forEach(player -> player.teleport(world.getSpawnLocation()));
            }
        }
        return true;
    }

}
