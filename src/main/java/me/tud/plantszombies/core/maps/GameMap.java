package me.tud.plantszombies.core.maps;

import lombok.Getter;
import me.tud.plantszombies.core.Cell;
import me.tud.plantszombies.core.Game;
import me.tud.plantszombies.core.GameObject;
import me.tud.plantszombies.core.ZombieSpawner;
import me.tud.plantszombies.core.types.ZombieType;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

@Getter
public abstract class GameMap {

    private final Game game;
    private final World world;
    private final ZombieSpawner[] waves;
    private int currentWave;

    public GameMap(Game game, World world, Map<ZombieType, Long>[] waves) {
        this.game = game;
        this.world = world;
        this.waves = ZombieSpawner.of(game, waves, spawner -> {
            if (!spawner.isEmpty() || currentWave + 1 >= waves.length)
                return;
            currentWave++;
        });
    }

    public <T extends GameObject> T spawn(Cell cell, T gameObject) {
        return spawn(getLocation(cell), gameObject);
    }

    public <T extends GameObject> T spawn(Location location, T gameObject) {
        gameObject.initiate(game, location);
        game.addGameObject(gameObject);
        return gameObject;
    }

    public abstract void initiate();

    public abstract void start();

    public abstract void end();

    public abstract Location getLocation(Cell cell);

    public abstract @Nullable Cell asCell(Location location);

    public ZombieSpawner getCurrentSpawner() {
        return waves[currentWave];
    }

    public void nextWave() {
        currentWave++;
    }

}
