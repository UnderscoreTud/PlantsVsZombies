package me.tud.plantszombies.core.maps;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.tud.plantszombies.core.Cell;
import me.tud.plantszombies.core.Game;
import me.tud.plantszombies.core.GameObject;
import me.tud.plantszombies.core.ZombieSpawner;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.Nullable;

@Getter
@RequiredArgsConstructor
public abstract class GameMap {

    private final Game game;
    private final World world;
    private final ZombieSpawner spawner;

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

}
