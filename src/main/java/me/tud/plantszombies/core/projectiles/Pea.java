package me.tud.plantszombies.core.projectiles;

import me.tud.plantszombies.core.GameObject;
import me.tud.plantszombies.core.plants.ShooterPlant;
import me.tud.plantszombies.core.types.ProjectileType;
import me.tud.plantszombies.core.zombies.Zombie;

public class Pea extends Projectile {

    public Pea(ShooterPlant shooter) {
        super(shooter, ProjectileType.PEA);
    }

    @Override
    public void hit(GameObject target) {
        if (!(target instanceof Zombie))
            return;
        remove();
        ((Zombie) target).damage(getType().getDamage());
    }

}
