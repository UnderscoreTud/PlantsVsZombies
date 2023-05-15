package me.tud.plantszombies.core.zombies;

import me.tud.plantszombies.core.types.ZombieType;

public abstract class ArmoredZombie extends ChewingZombie {

    private boolean hasEquipment = true;

    public ArmoredZombie(ZombieType type) {
        super(type);
    }

    protected int getBaseZombieHealth() {
        return 10;
    }

    protected abstract void onEquipmentBreak();

    @Override
    protected void onDamage() {
        super.onDamage();
        if (hasEquipment && getHealth() <= getBaseZombieHealth()) {
            hasEquipment = false;
            onEquipmentBreak();
        }
    }


}
