package me.tud.plantszombies.core.zombies;

import me.tud.plantszombies.core.types.ZombieType;
import org.bukkit.Sound;
import org.bukkit.entity.Zombie;

public class ConeZombie extends ArmoredZombie {

    public ConeZombie() {
        super(ZombieType.CONE_ZOMBIE);
    }

    @Override
    protected void onEquipmentBreak() {
        ((Zombie) getInstance()).getEquipment().setHelmet(null);
        getLocation().getWorld().playSound(getInstance(), Sound.ENTITY_ITEM_BREAK, 1f, 1f);
    }

}
