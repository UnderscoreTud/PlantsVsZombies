package me.tud.plantszombies.core.plants;

import me.tud.plantszombies.core.projectiles.Pea;
import me.tud.plantszombies.core.types.PlantType;

public class Peashooter extends ShooterPlant {

    public Peashooter() {
        super(PlantType.PEASHOOTER, 1.5, MAX_RANGE);
    }

    @Override
    public void shoot() {
        getGame().spawnObject(() -> new Pea(this), getLocation().add(0, 0.5, 0));
    }

}
