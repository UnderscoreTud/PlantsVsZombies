package me.tud.plantszombies.core.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.tud.plantszombies.utils.HeadFactory;
import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;

@Getter
@RequiredArgsConstructor
public enum ProjectileType {

    PEA(1, 0.5, () -> HeadFactory.PEA)
    ;

    private final int damage;
    private final double speed;
    private final Supplier<ItemStack> displayItemSupplier;

}
