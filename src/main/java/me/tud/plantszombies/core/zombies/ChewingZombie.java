package me.tud.plantszombies.core.zombies;

import me.tud.plantszombies.core.plants.Plant;
import me.tud.plantszombies.core.types.ZombieType;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.util.RayTraceResult;

public abstract class ChewingZombie extends Zombie {

    private int chewTicks;
    private Plant targetPlant;

    public ChewingZombie(ZombieType type) {
        super(type);
        resetChewCoolDown();
    }

    @Override
    public void tick() {
        super.tick();
        chewTicks++;
        if (canChew() && calculateTargetPlant() != null) {
            chew();
            chewTicks = 0;
        }
    }

    public boolean canChew() {
        return chewTicks >= getType().getChewCoolDownTicks() || chewTicks == -1;
    }

    public void chew() {
        targetPlant.damage(1);
        Location location = getLocation();
        Sound sound = targetPlant.isActive() ? Sound.ENTITY_GENERIC_EAT : Sound.ENTITY_PLAYER_BURP;
        location.getWorld().playSound(location, sound, 1f, 1f);
        ((LivingEntity) getInstance()).swingMainHand();
    }

    public void resetChewCoolDown() {
        chewTicks = getType().getChewCoolDownTicks();
    }

    public void onChewStart() {
        ((Mob) getInstance()).setAI(false);
    }

    public void onChewStop() {
        resetChewCoolDown();
        ((Mob) getInstance()).setAI(true);
    }

    public Plant calculateTargetPlant() {
        RayTraceResult result = rayTrace(0.2, entity -> getGame().getGameObjectFromEntity(entity) instanceof Plant);
        if (result == null) {
            if (targetPlant != null)
                onChewStop();
            targetPlant = null;
            return null;
        }
        Plant plant = targetPlant;
        targetPlant = (Plant) getGame().getGameObjectFromEntity(result.getHitEntity());
        if (plant == null)
            onChewStart();
        return plant;
    }
}
