package me.tud.plantszombies.utils;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Zombie;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public final class EntityUtils {

    private EntityUtils() {
        throw new UnsupportedOperationException();
    }

    public static ArmorStand spawnDummyArmorStand(Location location, @Nullable Consumer<ArmorStand> consumer) {
        return location.getWorld().spawn(location, ArmorStand.class, armorStand -> {
            armorStand.setInvulnerable(true);
            armorStand.setPersistent(true);
            armorStand.setCustomNameVisible(true);
            armorStand.setCustomName(null);
            armorStand.setArms(true);
            armorStand.setGravity(false);
            armorStand.setCollidable(false);
            if (consumer != null)
                consumer.accept(armorStand);
        });
    }

    public static Zombie spawnZombie(Location location, @Nullable Consumer<Zombie> consumer) {
        return location.getWorld().spawn(location, Zombie.class, zombie -> {
            zombie.setPersistent(true);
            zombie.setCollidable(false);
            zombie.setShouldBurnInDay(false);
            zombie.setAdult();
            if (consumer != null)
                consumer.accept(zombie);
        });
    }

}
