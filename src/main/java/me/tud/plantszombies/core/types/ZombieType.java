package me.tud.plantszombies.core.types;

import lombok.Getter;
import me.tud.plantszombies.core.zombies.NormalZombie;
import me.tud.plantszombies.core.zombies.Zombie;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

@Getter
public enum ZombieType {

    ZOMBIE(10, 1, 0.5, 0.5, null, NormalZombie::new)
    ;

    private final int maxHealth, damage;
    private final double speed;
    private final int chewCoolDownTicks;
    private final @Nullable Supplier<ItemStack> hatSupplier;
    private final Supplier<Zombie> factory;

    ZombieType(int maxHealth, int damage, double speed, double chewCoolDown, @Nullable Supplier<ItemStack> hatSupplier, Supplier<Zombie> factory) {
        this.maxHealth = maxHealth;
        this.damage = damage;
        this.speed = speed;
        this.chewCoolDownTicks = (int) (chewCoolDown * 20);
        this.hatSupplier = hatSupplier;
        this.factory = factory;
    }

}
