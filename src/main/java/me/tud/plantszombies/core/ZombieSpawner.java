package me.tud.plantszombies.core;

import lombok.RequiredArgsConstructor;
import me.tud.plantszombies.core.types.ZombieType;
import org.jetbrains.annotations.Range;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
public class ZombieSpawner {

    private final Game game;
    private final Map<ZombieType, Integer> typeMap;

    public void spawnRandom() {
        spawnRandom(ThreadLocalRandom.current().nextInt(5));
    }

    public void spawnRandom(@Range(from = 0, to = 4) int lane) {
        game.spawnObject(getRandomType().getFactory(), new Cell(9, lane));
    }

    private ZombieType getRandomType() {
        List<ZombieType> types = new ArrayList<>();
        for (Map.Entry<ZombieType, Integer> entry : typeMap.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++)
                types.add(entry.getKey());
        }
        return types.get(ThreadLocalRandom.current().nextInt(types.size()));
    }

}
