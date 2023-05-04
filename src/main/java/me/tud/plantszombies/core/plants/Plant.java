package me.tud.plantszombies.core.plants;

import lombok.Getter;
import me.tud.plantszombies.core.DamageableObject;
import me.tud.plantszombies.core.types.PlantType;

@Getter
public abstract class Plant extends DamageableObject {

    private final PlantType type;

    public Plant(PlantType type) {
        this.type = type;
        setHealth(type.getMaxHealth());
    }

}
