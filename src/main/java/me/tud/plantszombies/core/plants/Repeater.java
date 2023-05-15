package me.tud.plantszombies.core.plants;

import me.tud.plantszombies.PlantsZombies;
import me.tud.plantszombies.core.projectiles.Pea;
import me.tud.plantszombies.core.types.PlantType;
import org.bukkit.Bukkit;

public class Repeater extends ShooterPlant {

    public Repeater() {
        super(PlantType.REPEATER, 1.5, MAX_RANGE);
    }

    @Override
    public void shoot() {
        shootPea();
        Bukkit.getScheduler().runTaskLater(PlantsZombies.getInstance(), () -> {
            shootPea();
            coolDownTicks = 0;
        }, 5L);
    }

    private void shootPea() {
        getGame().spawnObject(() -> new Pea(this), getLocation().add(0, 0.5, 0));
    }

}
