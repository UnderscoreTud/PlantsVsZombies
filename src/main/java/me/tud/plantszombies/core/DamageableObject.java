package me.tud.plantszombies.core;

import lombok.Getter;
import lombok.Setter;

public abstract class DamageableObject extends GameObject {

    @Getter
    @Setter
    private int health;

    public void damage(int amount) {
        if (amount < 0)
            throw new IllegalArgumentException("Damage amount cannot be negative");
        health = Math.max(health - amount, 0);

        if (health <= 0) {
            onDeath();
        } else {
            onDamage();
        }
    }

    protected void onDamage() {}

    protected abstract void onDeath();

}
