package me.tud.plantszombies.core.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.tud.plantszombies.utils.ChatUtils;
import me.tud.plantszombies.utils.EntityUtils;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

import java.util.function.Consumer;

@Getter
@AllArgsConstructor
public enum PlantType {

    PEASHOOTER(100, 7.5, 6, armorStand -> armorStand.setCustomName(ChatUtils.colored("&e&lPeashooter"))),
    ;

    private final int cost;
    private final double rechargeTimeSec;
    private final int maxHealth;
    private final Consumer<ArmorStand> consumer;

    public ArmorStand spawnEntity(Location location) {
        return EntityUtils.spawnDummyArmorStand(location, consumer);
    }

}
