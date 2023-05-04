package me.tud.plantszombies.core.maps;

import me.tud.plantszombies.core.Cell;
import me.tud.plantszombies.core.Game;
import me.tud.plantszombies.core.ZombieSpawner;
import me.tud.plantszombies.core.types.ZombieType;
import me.tud.plantszombies.utils.WorldUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class Day extends GameMap {

    public Day(Game game, World world) {
        super(game, world, new ZombieSpawner(game, Map.of(ZombieType.ZOMBIE, 1)));
    }

    @Override
    public void initiate() {
        WorldUtils.setGameRules(getWorld());
        getWorld().setTime(6000);
    }

    @Override
    public void start() {

    }

    @Override
    public void end() {
        WorldUtils.deleteWorld(getWorld());
    }

    @Override
    public Location getLocation(Cell cell) {
        return new Location(getWorld(), cell.x() * 3 + 1.5, 65, cell.y() * 3 + 1.5).setDirection(new Vector(90, 0, 0));
    }

    @Override
    public @Nullable Cell asCell(Location location) {
        int x, y;
        if ((x = location.getBlockX() / 3) > 9 || (y = location.getBlockZ() / 3) > 4)
            return null;
        return new Cell(x, y);
    }

}
