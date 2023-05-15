package me.tud.plantszombies.core.zombies;

import me.tud.plantszombies.core.types.ZombieType;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.EntityEquipment;

public class BucketZombie extends ArmoredZombie {

    public BucketZombie() {
        super(ZombieType.BUCKET_ZOMBIE);
    }

    @Override
    protected void onEquipmentBreak() {
        ((Zombie) getInstance()).getEquipment().setHelmet(null);
        getLocation().getWorld().playSound(getInstance(), Sound.ENTITY_ITEM_BREAK, 1f, 1f);
    }

}
