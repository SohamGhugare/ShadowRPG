package me.shadowdev.shadowrpg.commands;

import me.shadowdev.shadowrpg.components.CustomMobs;
import me.shadowdev.shadowrpg.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SpawnCommand implements CommandExecutor {

    private static HashMap<Entity, CustomMobs> entities = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;
        if (!(player.hasPermission("ShadowRPG.mobs.spawn"))){
            Utils.msgPlayer(player, "&cSorry, you don't have permission to do that.");
        }

        CustomMobs[] mobTypes = CustomMobs.values();
        double random = Math.random() * 101;
        CustomMobs spawnType;

        if (args.length == 0){
            // Send help
            Utils.msgPlayer(player, "&b&lShadow&a&lRPG &6&lMobs", "&e/rpgspawn [UNDEAD_HUSK]", "&bMore mobs coming soon!");
            return true;
        }

        switch (args[0]){
            case "UNDEAD_HUSK":
                spawnType = CustomMobs.UNDEAD_HUSK;
                break;
            default:
                // random mob
                Random rand = new Random();
                spawnType = mobTypes[rand.nextInt(mobTypes.length)];
        }

        if (spawnType != null){
            entities.put(spawnType.spawn(player.getLocation()), spawnType);
            Utils.msgPlayer(player, "&6Spawned "+spawnType.getName());
        }

        return true;
    }

    public static HashMap<Entity, CustomMobs> getEntities(){
        return entities;
    }

    public static void setEntities(HashMap<Entity, CustomMobs> e){
        entities = e;
    }
}
