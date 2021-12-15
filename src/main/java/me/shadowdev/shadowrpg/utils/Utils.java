package me.shadowdev.shadowrpg.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static String color(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static void msgPlayer(Player player, String... strings){
        for (String string : strings){
            player.sendMessage(color(string));
        }
    }

    public static ItemStack createItem(Material type, int amount, boolean unbreakable, boolean hideUnb, boolean glow,
                                       String name, String... loreLines){
        ItemStack item = new ItemStack(type, amount);
        ItemMeta meta = item.getItemMeta();

        if (glow){
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
        }

        if (unbreakable) meta.setUnbreakable(true);
        if (hideUnb) meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);

        if (name != null) meta.setDisplayName(color(name));

        if (loreLines != null) {
            List<String> lore = new ArrayList<>();
            for (String line : loreLines){
                lore.add(color(line));
            }
            meta.setLore(lore);
        }

        item.setItemMeta(meta);
        return item;
    }

    public static double getRandomOffset() {
        double random = Math.random();
        if (Math.random() > 0.5) random *= -1;
        return random;
    }
}
