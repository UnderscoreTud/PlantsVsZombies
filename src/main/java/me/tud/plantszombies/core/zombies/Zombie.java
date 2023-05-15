package me.tud.plantszombies.core.zombies;

import lombok.Getter;
import me.tud.plantszombies.core.Cell;
import me.tud.plantszombies.core.DamageableObject;
import me.tud.plantszombies.core.Game;
import me.tud.plantszombies.core.types.ZombieType;
import me.tud.plantszombies.utils.EntityUtils;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;

@Getter
public abstract class Zombie extends DamageableObject {

    private final ZombieType type;
    private Location endOfLane;

    public Zombie(ZombieType type) {
        this.type = type;
        setHealth(type.getMaxHealth());
    }

    @Override
    public void initiate(Game game, Location location) {
        super.initiate(game, location);
        setInstance(spawnZombie(location));
        ((Mob) getInstance()).getPathfinder().setCanFloat(true);
        endOfLane = getGame().getMap().getLocation(new Cell(-1, getCell().y()));
    }

    @Override
    public void tick() {
        super.tick();
        ((Mob) getInstance()).getPathfinder().moveTo(getEndOfLane(), getType().getSpeed());
    }

    @Override
    protected void onDamage() {
        if (isActive())
            ((org.bukkit.entity.Zombie) getInstance()).damage(0);
    }

    @Override
    protected void onDeath() {
        ((LivingEntity) getInstance()).damage(Integer.MAX_VALUE);
        remove();
        System.out.println("zombie died");
    }


    private org.bukkit.entity.Zombie spawnZombie(Location location) {
        return EntityUtils.spawnZombie(location, zombie -> {
            zombie.setNoDamageTicks(0);
            if (type.getHatSupplier() != null)
                zombie.getEquipment().setHelmet(type.getHatSupplier().get());
        });
    }

}
