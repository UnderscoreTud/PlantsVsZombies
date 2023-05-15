package me.tud.plantszombies.core.types;

import lombok.Getter;
import me.tud.plantszombies.core.zombies.BasicZombie;
import me.tud.plantszombies.core.zombies.BucketZombie;
import me.tud.plantszombies.core.zombies.ConeZombie;
import me.tud.plantszombies.core.zombies.Zombie;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

@Getter
public enum ZombieType {

    ZOMBIE(10, 1, 0.5, 0.5, null, BasicZombie::new),
    CONE_ZOMBIE(30, 1, 0.5, 0.5, () -> new ItemStack(Material.GOLDEN_HELMET), ConeZombie::new),
    BUCKET_ZOMBIE(65, 1, 0.5, 0.5, () -> new ItemStack(Material.IRON_HELMET), BucketZombie::new),
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
