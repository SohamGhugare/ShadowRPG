package me.shadowdev.shadowrpg.components;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class LootItem {

    // Properties
    private ItemStack item;
    private int min = 1, max = 1;
    private double dropRate;
    private static Random randomizer = new Random();

    public LootItem(ItemStack item, double dropRate) {
        this.item = item;
        this.dropRate = dropRate;
    }

    public LootItem(ItemStack item, int min, int max, double dropRate) {
        this.item = item;
        this.min = min;
        this.max = max;
        this.dropRate = dropRate;
    }

    // Try to drop the item considering its drop rate.
    public void tryDropItem(Location location) {
        if (Math.random() * 101 > dropRate) return;

        int amount = randomizer.nextInt(max - min + 1) + min;
        if (amount == 0) return;

        ItemStack item = this.item.clone();
        item.setAmount(amount);

        location.getWorld().dropItemNaturally(location, item);
    }
}
