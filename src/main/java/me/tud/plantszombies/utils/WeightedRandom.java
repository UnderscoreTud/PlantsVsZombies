package me.tud.plantszombies.utils;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class WeightedRandom<T> {

    private final List<T> elements = new ArrayList<>();
    private final Map<T, Long> weightsMap;

    public WeightedRandom(Map<T, Long> elementsMap) {
        this.weightsMap = new HashMap<>(elementsMap);
        elementsMap.forEach((type, weight) -> {
            for (long i = 0; i < weight; i++)
                elements.add(type);
        });
    }

    public long getWeight(T element) {
        return weightsMap.getOrDefault(element, 0L);
    }

    public void setWeight(T element, long weight) {
        long oldWeight = getWeight(element);
        if (weight == oldWeight)
            return;
        if (weight > oldWeight) increaseWeight(element, weight - oldWeight);
        else decreaseWeight(element, oldWeight - weight);
    }

    public void increaseWeight(T element, long amount) {
        if (amount <= 0) throw new IllegalArgumentException("amount must be greater than 0");
        for (long i = 0; i < amount; i++)
            elements.add(element);
        weightsMap.compute(element, (t, integer) -> integer == null ? amount : amount + integer);
    }

    public void decreaseWeight(T element, long amount) {
        if (amount <= 0) throw new IllegalArgumentException("amount must be greater than 0");
        for (long i = 0; i < amount; i++)
            elements.remove(element);
        weightsMap.compute(element, (t, integer) -> integer == null ? amount : Math.max(0, integer - amount));
    }

    public @UnmodifiableView Map<T, Long> getWeightsMap() {
        return Collections.unmodifiableMap(weightsMap);
    }

    public long getTotalWeights() {
        return elements.size();
    }

    public boolean canGenerate() {
        return elements.size() > 0;
    }

    public T generateRandom() {
        return generateRandom(null);
    }

    public T generateRandom(@Nullable Consumer<T> onGenerate) {
        if (elements.size() <= 0) throw new IllegalStateException("There are no elements in the list");
        int index = ThreadLocalRandom.current().nextInt(elements.size());
        T element = elements.get(index);
        if (onGenerate != null)
            onGenerate.accept(element);
        return element;
    }

}
