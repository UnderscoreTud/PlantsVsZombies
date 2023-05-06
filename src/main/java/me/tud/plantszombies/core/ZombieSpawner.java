package me.tud.plantszombies.core;

import me.tud.plantszombies.core.types.ZombieType;
import org.jetbrains.annotations.Range;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class ZombieSpawner {

    private final Game game;
    private final List<ZombieType> types;

    public ZombieSpawner(Game game, Map<ZombieType, Integer> typeMap) {
        this.game = game;
        this.types = new ArrayList<>(typeMap.size());
        typeMap.forEach((type, weight) -> {
            for (int i = 0; i < weight; i++)
                types.add(type);
        });
    }

    public void spawnRandom() {
        spawnRandom(ThreadLocalRandom.current().nextInt(5));
    }

    public void spawnRandom(@Range(from = 0, to = 4) int lane) {
        game.spawnObject(getRandomType().getFactory(), new Cell(9, lane));
    }

    private ZombieType getRandomType() {
        return types.get(ThreadLocalRandom.current().nextInt(types.size()));
    }

}
