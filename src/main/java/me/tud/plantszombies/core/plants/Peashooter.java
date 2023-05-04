package me.tud.plantszombies.core.plants;

import me.tud.plantszombies.core.Game;
import me.tud.plantszombies.core.types.PlantType;
import me.tud.plantszombies.core.projectiles.Pea;
import org.bukkit.Location;

public class Peashooter extends ShooterPlant {

    public Peashooter() {
        super(PlantType.PEASHOOTER, 1.5, MAX_RANGE);
    }

    @Override
    public void initiate(Game game, Location location) {
        super.initiate(game, location);
        setInstance(getType().spawnEntity(location));
    }

    @Override
    public void shoot() {
        getGame().spawnObject(() -> new Pea(this), getLocation().add(0, 0.5, 0));
    }

    @Override
    protected void onDeath() {
        remove();
        System.out.println("died");
    }

}
