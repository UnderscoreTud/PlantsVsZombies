package me.tud.plantszombies.core;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.RayTraceResult;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

@Getter
public abstract class GameObject {

    @Setter(AccessLevel.PROTECTED)
    private Entity instance;
    private Game game;
    private int aliveTicks = 0;

    public void initiate(Game game, Location location) {
        if (isInitiated())
            throw new IllegalStateException("The GameObject has already been deactivated");
        this.game = game;
    }

    public void remove() {
        if (instance == null || !instance.isValid())
            throw new IllegalStateException("The GameObject has already been deactivated");
        instance.remove();
        getGame().removeGameObject(this);
    }

    public void tick() {
        aliveTicks++;
    }

    public boolean isInitiated() {
        return instance != null;
    }

    public boolean isActive() {
        return instance != null && instance.isValid();
    }

    public Location getLocation() {
        return instance.getLocation();
    }

    public Cell getCell() {
        if (!isInitiated())
            return null;
        return getGame().getMap().asCell(getLocation());
    }

    protected RayTraceResult rayTrace(double maxDistance) {
        return rayTrace(maxDistance, null);
    }

    protected RayTraceResult rayTrace(double maxDistance, @Nullable Predicate<Entity> filter) {
        Location location = getLocation();
        return location.getWorld().rayTraceEntities(location, location.getDirection(), maxDistance, 0.2, filter);
    }

}
