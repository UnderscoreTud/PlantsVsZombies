package me.tud.plantszombies.utils;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.Nullable;

import java.net.URL;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public final class HeadFactory {

    public static final ItemStack PEA = createHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQwN2ZkMDNjMDNjMDViNzQ0Y2ZmN2FjMWE5NWQ4MTYxNzA2MjA2ZjY4YzEyYzRjMjJjOTcwNWY3YzM3ZDA4In19fQ");

    private HeadFactory() {
        throw new UnsupportedOperationException();
    }

    public static ItemStack createEmptyHead() {
        return new ItemStack(Material.PLAYER_HEAD);
    }

    public static ItemStack createHead(String texture) {
        return createHead(texture, null);
    }

    public static ItemStack createHead(String texture, @Nullable String signature) {
        ItemStack head = createEmptyHead();
        SkullMeta skullMeta = (SkullMeta) head.getItemMeta();

        PlayerProfile playerProfile = Bukkit.createProfile(UUID.randomUUID());

        Set<ProfileProperty> properties = playerProfile.getProperties();
        properties.removeIf(profileProperty -> profileProperty.getName().equals("textures"));
        properties.add(new ProfileProperty("textures", texture, signature));

        skullMeta.setPlayerProfile(playerProfile);
        head.setItemMeta(skullMeta);
        return head;
    }

    public static CompletableFuture<ItemStack> createHead(URL skinURL) {
        return CompletableFuture.supplyAsync(() -> {
            ItemStack head = createEmptyHead();
            SkullMeta skullMeta = (SkullMeta) head.getItemMeta();

            PlayerProfile playerProfile = Bukkit.createProfile(UUID.randomUUID());
            playerProfile.getTextures().setSkin(skinURL);
            System.out.println(playerProfile.getTextures().getSkin());

            skullMeta.setPlayerProfile(playerProfile);
            head.setItemMeta(skullMeta);
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.getInventory().addItem(head);
                player.updateInventory();
            });
            return head;
        });
    }

    public static ItemStack createHead(OfflinePlayer owningPlayer) {
        ItemStack head = createEmptyHead();
        SkullMeta skullMeta = (SkullMeta) head.getItemMeta();
        skullMeta.setOwningPlayer(owningPlayer);
        head.setItemMeta(skullMeta);
        return head;
    }

}
