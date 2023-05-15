package me.tud.plantszombies.core.plants;

import lombok.Getter;
import me.tud.plantszombies.core.DamageableObject;
import me.tud.plantszombies.core.Game;
import me.tud.plantszombies.core.types.PlantType;
import org.bukkit.Location;

@Getter
public abstract class Plant extends DamageableObject {

    private final PlantType type;

    public Plant(PlantType type) {
        this.type = type;
        setHealth(type.getMaxHealth());
    }

    @Override
    public void initiate(Game game, Location location) {
        super.initiate(game, location);
        setInstance(getType().spawnEntity(location));
    }

    @Override
    protected void onDeath() {
        remove();
    }

}
