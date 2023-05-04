package me.tud.plantszombies.core.plants;

import lombok.Getter;
import me.tud.plantszombies.core.Cell;
import me.tud.plantszombies.core.GameObject;
import me.tud.plantszombies.core.types.PlantType;
import me.tud.plantszombies.core.zombies.Zombie;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;

@Getter
public abstract class ShooterPlant extends Plant {

    protected static final int MAX_RANGE = 9;

    private final int range;
    private final int fireRateTicks;
    private int coolDownTicks = 0;

    public ShooterPlant(PlantType type, double fireRate, int range) {
        super(type);
        this.fireRateTicks = (int) (fireRate * 20);
        this.range = range;
    }

    public abstract void shoot();

    @Override
    public void tick() {
        super.tick();
        coolDownTicks++;
        if (canShoot()) {
            shoot();
            coolDownTicks = 0;
        }
    }

    public boolean canShoot() {
        return coolDownTicks >= fireRateTicks && isInRange();
    }

    public boolean isInRange() {
        Cell cell = getCell();
        int actualRange = (cell.x() + range >= MAX_RANGE ? Math.abs(range - cell.x()) : range + 1) * 3 - 2;
        RayTraceResult result = rayTrace(actualRange, entity -> getGame().getGameObjectFromEntity(entity) instanceof Zombie);
        return result != null;
    }

}
