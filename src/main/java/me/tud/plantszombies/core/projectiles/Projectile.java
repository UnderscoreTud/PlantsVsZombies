package me.tud.plantszombies.core.projectiles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.tud.plantszombies.core.Game;
import me.tud.plantszombies.core.GameObject;
import me.tud.plantszombies.core.types.ProjectileType;
import me.tud.plantszombies.utils.EntityUtils;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

@Getter
public abstract class Projectile extends GameObject {

    private final GameObject shooter;
    private final ProjectileType type;
    private final ItemStack displayItem;

    public Projectile(GameObject shooter, ProjectileType type) {
        this.shooter = shooter;
        this.type = type;
        this.displayItem = type.getDisplayItemSupplier().get();
    }

    @Override
    public void initiate(Game game, Location location) {
        super.initiate(game, location);
        setInstance(spawnItem(location, getDisplayItem()));
    }

    @Override
    public void tick() {
        super.tick();
        Entity instance = getInstance();
        instance.teleportAsync(getLocation().add(type.getSpeed(), 0, 0)).whenComplete((aBoolean, throwable) -> {
            if (!aBoolean || throwable != null)
                return;
            RayTraceResult result = rayTrace(0.01, entity ->
                    !entity.equals(instance)
                            && !entity.equals(shooter.getInstance())
                            && !(entity instanceof Player)
                            && !getGame().isSameSide(shooter, getGame().getGameObjectFromEntity(entity)));
            if (result != null && result.getHitEntity() != null) {
                GameObject gameObject = getGame().getGameObjectFromEntity(result.getHitEntity());
                if (gameObject != null)
                    hit(gameObject);
            }
        });
        if (getAliveTicks() > 10 * 20)
            remove();
    }

    public abstract void hit(GameObject target);

    protected static Entity spawnItem(Location location, ItemStack displayItem) {
        return EntityUtils.spawnDummyArmorStand(location, armorStand -> {
            armorStand.setInvisible(true);
            armorStand.setCustomNameVisible(false);
            armorStand.getEquipment().setHelmet(displayItem, true);
            armorStand.setSmall(true);
        });
    }

}
