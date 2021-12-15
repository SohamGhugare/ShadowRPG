package me.shadowdev.shadowrpg;

import me.shadowdev.shadowrpg.commands.SpawnCommand;
import me.shadowdev.shadowrpg.listeners.MobDamage;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public final class ShadowRPG extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic


        // Registering commands
        this.getCommand("rpgspawn").setExecutor(new SpawnCommand());

        // Registering events
        getServer().getPluginManager().registerEvents(new MobDamage(), this);

        // Registering runnables
        new BukkitRunnable() {
            private HashMap<Entity, Integer> indicators = MobDamage.getIndicators();
            Set<Entity> stands = indicators.keySet();
            List<Entity> removal = new ArrayList<>();
            @Override
            public void run() {
                for (Entity stand : stands) {
                    int ticksLeft = indicators.get(stand);
                    if (ticksLeft == 0) {
                        stand.remove();
                        removal.add(stand);
                        continue;
                    }
                    ticksLeft--;
                    indicators.put(stand, ticksLeft);
                }
                stands.removeAll(removal);
                MobDamage.setIndicators(indicators);
            }

        }.runTaskTimer(this, 0L, 1L);

    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
