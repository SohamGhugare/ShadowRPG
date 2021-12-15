package me.shadowdev.shadowrpg.components;

import me.shadowdev.shadowrpg.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum CustomMobs {

    UNDEAD_HUSK("&6Undead Husk", 15, 60, EntityType.HUSK, null, null,
            new LootItem(Utils.createItem(Material.ROTTEN_FLESH, 1, false, false, false,
                    "&eUndead Flesh", "&7Dropped by the Undead Husks.", "&7", "&9&lCOLLECTIBLE"),
                    1, 3, 100));

    // Properties
    private String name;
    private double maxHealth, spawnChance;
    private EntityType type;
    private ItemStack mainItem;
    private ItemStack[] armor;
    private List<LootItem> lootTable;

    // Constructor
    CustomMobs(String name, double maxHealth, double spawnChance, EntityType type, ItemStack mainItem, ItemStack[] armor, LootItem... lootItems) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.spawnChance = spawnChance;
        this.type = type;
        this.mainItem = mainItem;
        this.armor = armor;
        lootTable = Arrays.asList(lootItems);
    }

    // Spawn method
    public LivingEntity spawn(Location location){
        LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(location, type);

        // Setting custom name
        entity.setCustomNameVisible(true);
        entity.setCustomName(Utils.color(name + " &r&c" + (int) maxHealth + "/" + (int) maxHealth) + "♥");

        // Setting health
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
        entity.setHealth(maxHealth);

        // Setting armor
        EntityEquipment inv = entity.getEquipment();
        if (armor != null) inv.setArmorContents(armor);
        inv.setHelmetDropChance(0f);
        inv.setBootsDropChance(0f);
        inv.setChestplateDropChance(0f);
        inv.setLeggingsDropChance(0f);

        // Setting main hand item
        inv.setItemInMainHand(mainItem);
        inv.setItemInMainHandDropChance(0f);

        return entity;
    }

    // Going through the loot table and trying to drop the item
    public void tryDropLoot(Location location){
        for (LootItem item : lootTable){
            item.tryDropItem(location);
        }
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Getter for max health
    public double getMaxHealth() {
        return maxHealth;
    }

    // Getter for spawn chance
    public double getSpawnChance() {
        return spawnChance;
    }
}
