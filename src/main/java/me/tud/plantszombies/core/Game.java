package me.tud.plantszombies.core;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import lombok.Getter;
import me.tud.plantszombies.PlantsZombies;
import me.tud.plantszombies.core.maps.GameMap;
import me.tud.plantszombies.core.plants.Plant;
import me.tud.plantszombies.core.zombies.Zombie;
import me.tud.plantszombies.utils.ConcurrentBiMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Supplier;

public class Game {

    private final BiMap<GameObject, Entity> instanceMap = new ConcurrentBiMap<>();
    @Getter
    private GameMap map;
    @Getter
    private State state = State.NOT_STARTED;
    private BukkitTask task;

    public void start(GameMap map) {
        if (state != State.NOT_STARTED)
            throw new IllegalStateException("The game state is not 'NOT_STARTED'");
        state = State.RUNNING;
        this.map = map;
        map.initiate();
        map.start();
        task = Bukkit.getScheduler().runTaskTimer(PlantsZombies.getInstance(), () -> {
            tick();
            if (checkEnded())
                end();
        }, 0L, 1L);
    }

    private boolean checkEnded() {
        return false;
    }

    public void end() {
        if (state == State.ENDED)
            throw new IllegalStateException("The game has already ended");
        state = State.ENDED;
        map.end();
        task.cancel();
        task = null;
        instanceMap.entrySet().removeIf(entry -> {
            entry.getKey().remove();
            return true;
        });
    }

    public void addGameObject(GameObject gameObject) {
        if (!gameObject.isActive())
            throw new IllegalArgumentException("Cannot add an inactive game object to the game");
        instanceMap.put(gameObject, gameObject.getInstance());
    }

    public void removeGameObject(GameObject gameObject) {
        if (gameObject.isActive())
            gameObject.remove();
        instanceMap.remove(gameObject);
    }

    private void tick() {
        instanceMap.entrySet().removeIf(entry -> !entry.getKey().isActive());
        for (GameObject gameObject : instanceMap.keySet()) {
            if (gameObject.isActive())
                gameObject.tick();
        }
    }

    public <T extends GameObject> T spawnObject(Supplier<T> supplier, Cell cell) {
        return getMap().spawn(cell, supplier.get());
    }

    public <T extends GameObject> T spawnObject(Supplier<T> supplier, Location location) {
        return getMap().spawn(location, supplier.get());
    }

    public GameObject getGameObjectFromEntity(Entity entity) {
        return instanceMap.inverse().get(entity);
    }

    public boolean isSameSide(GameObject first, GameObject second) {
        return (first instanceof Plant && second instanceof Plant) || (first instanceof Zombie && second instanceof Zombie);
    }

    public boolean isPlant(GameObject gameObject) {
        return gameObject instanceof Plant;
    }

    public boolean isZombie(GameObject gameObject) {
        return gameObject instanceof Zombie;
    }

    public enum State {

        NOT_STARTED,
        RUNNING,
        ENDED

    }

}
