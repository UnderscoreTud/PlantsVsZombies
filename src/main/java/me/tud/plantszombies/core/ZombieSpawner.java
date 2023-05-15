package me.tud.plantszombies.core;

import me.tud.plantszombies.core.types.ZombieType;
import me.tud.plantszombies.utils.WeightedRandom;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.Map;
import java.util.function.Consumer;

public class ZombieSpawner {

    private final Game game;
    private final WeightedRandom<ZombieType> typeRandom;
    private final WeightedRandom<Integer> laneRandom;
    private final @Nullable Consumer<ZombieSpawner> onGenerate;

    public ZombieSpawner(Game game, Map<ZombieType, Long> typeMap) {
        this(game, typeMap, null);
    }

    public ZombieSpawner(Game game, Map<ZombieType, Long> typeMap, @Nullable Consumer<ZombieSpawner> onGenerate) {
        this.game = game;
        this.typeRandom = new WeightedRandom<>(typeMap);
        long spawnPerLane = typeRandom.getTotalWeights() / 5;
        this.laneRandom = new WeightedRandom<>(Map.of(
                0, spawnPerLane,
                1, spawnPerLane,
                2, spawnPerLane,
                3, spawnPerLane,
                4, spawnPerLane
        ));
        this.onGenerate = onGenerate;
    }

    public void spawnRandom() {
        if (!laneRandom.canGenerate()) return;
        spawnRandom(laneRandom.generateRandom(generated -> laneRandom.decreaseWeight(generated, 1)));
    }

    public void spawnRandom(@Range(from = 0, to = 4) int lane) {
        if (!typeRandom.canGenerate()) return;
        game.spawnObject(typeRandom.generateRandom(generated -> typeRandom.decreaseWeight(generated, 1)).getFactory(), new Cell(9, lane));
        if (onGenerate != null)
            onGenerate.accept(this);
    }

    public boolean isEmpty() {
        return !(laneRandom.canGenerate() && typeRandom.canGenerate());
    }

    public static ZombieSpawner[] of(Game game, Map<ZombieType, Long>[] waves) {
        return of(game, waves, null);
    }

    public static ZombieSpawner[] of(Game game, Map<ZombieType, Long>[] waves, @Nullable Consumer<ZombieSpawner> onGenerate) {
        ZombieSpawner[] spawners = new ZombieSpawner[waves.length];
        for (int i = 0; i < waves.length; i++)
            spawners[i] = new ZombieSpawner(game, waves[i], onGenerate);
        return spawners;
    }

}
