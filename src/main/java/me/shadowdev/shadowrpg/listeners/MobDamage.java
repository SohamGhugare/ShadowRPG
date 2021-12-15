package me.shadowdev.shadowrpg.listeners;

import me.shadowdev.shadowrpg.commands.SpawnCommand;
import me.shadowdev.shadowrpg.components.CustomMobs;
import me.shadowdev.shadowrpg.utils.Utils;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class MobDamage implements Listener {

    private static HashMap<Entity, CustomMobs> entities = SpawnCommand.getEntities();
    private static Map<Entity, Integer> indicators = new HashMap<>();
    private DecimalFormat formatter = new DecimalFormat("#.##");

    public static HashMap<Entity, Integer> getIndicators(){
        return (HashMap<Entity, Integer>) indicators;
    }

    public static void setIndicators(Map<Entity, Integer> indicators) {
        MobDamage.indicators = indicators;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event){
        Entity rawEntity = event.getEntity();
        if (!entities.containsKey(rawEntity)) return;

        CustomMobs mob = entities.get(rawEntity);
        LivingEntity entity = (LivingEntity) rawEntity;
        double damage = event.getFinalDamage(), health = entity.getHealth() + entity.getAbsorptionAmount();

        if (health > damage){
            // Entity survived
            health -= damage;
            entity.setCustomName(Utils.color(mob.getName() + " &r&c" + (int) health + "/" + (int) mob.getMaxHealth()) + "♥");

        }
        Location loc = entity.getLocation().clone().add(Utils.getRandomOffset(), 1, Utils.getRandomOffset());
        entity.getWorld().spawn(loc, ArmorStand.class, armorStand -> {
            armorStand.setMarker(true);
            armorStand.setVisible(false);
            armorStand.setGravity(false);
            armorStand.setSmall(true);
            armorStand.setCustomNameVisible(true);
            armorStand.setCustomName(Utils.color("&c" + formatter.format(damage)));
            indicators.put(armorStand, 30);
        });



    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){
        if (!(entities.containsKey(event.getEntity()))) return;
        event.setDroppedExp(0);
        event.getDrops().clear();
        entities.remove(event.getEntity()).tryDropLoot(event.getEntity().getLocation());
        SpawnCommand.setEntities(entities);

    }

}
