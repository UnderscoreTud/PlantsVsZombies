package me.tud.plantszombies.core.zombies;

import me.tud.plantszombies.core.GameObject;
import me.tud.plantszombies.core.plants.Plant;
import me.tud.plantszombies.core.types.ZombieType;
import org.bukkit.util.RayTraceResult;

public class NormalZombie extends ChewingZombie {

    public NormalZombie() {
        super(ZombieType.ZOMBIE);
    }

    @Override
    public void tick() {
        super.tick();
    }

}
