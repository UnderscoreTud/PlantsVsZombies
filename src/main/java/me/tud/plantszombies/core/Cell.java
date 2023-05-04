package me.tud.plantszombies.core;

import org.jetbrains.annotations.Range;

public record Cell(@Range(from = -1, to = 9) int x, @Range(from = 0, to = 4) int y) {
}
