package fr.yazil.boost.utils;


import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

//Didn't think I was gonna reinvent the wheel today, but well here I am
public class BossBarUtils {

    private final Map<Player, EntityWither> witherMap = new HashMap<>();

    private static BossBarUtils instance;

    public static BossBarUtils getInstance() {
        if (instance == null) {
            instance = new BossBarUtils();
        }
        return instance;
    }


    public void setBossBar(Player p, String text, float health) {
        if(witherMap.containsKey(p)){
            updateBossBar(p, text, health);
        } else {
            createBossBar(p, text, health);
        }
    }

    private void createBossBar(Player p, String text, float health) {
        //Virtual wither entity creation
        EntityWither wither = new EntityWither(((CraftWorld) p.getWorld()).getHandle());

        //Location where the wither should spawn
        Location spawnLoc = p.getLocation().getDirection().multiply(32).add(p.getLocation().toVector()).toLocation(p.getWorld());
        wither.setLocation(spawnLoc.getX(), spawnLoc.getY(), spawnLoc.getZ(), spawnLoc.getPitch(), spawnLoc.getYaw());
        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(wither);

        //Entity metadata creation
        //Doc: https://wiki.vg/index.php?title=Entity_metadata&oldid=7415
        DataWatcher metadata = new DataWatcher(null);
        metadata.a(0, (byte) (1 << 5)); //Invisibility
        metadata.a(2, text); //NameTag
        metadata.a(10, text); // NameTag
        metadata.a(3, (byte) 1); //Always show nametag
        metadata.a(11, (byte) 1); // Always show nametag
        metadata.a(6, health*3); // Health
        metadata.a(17, 0); //Wither head look
        metadata.a(18, 0); //Wither head look
        metadata.a(19, 0); //Wither head look
        metadata.a(20, 1000); //Disable wither glowy effect

        //Set the metadata field accessible
        try {
            Field field = PacketPlayOutSpawnEntityLiving.class.getDeclaredField("l"); // "l" is the metadata field
            field.setAccessible(true);
            field.set(packet, metadata);
        }  catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        //Add the virtual wither to the map for future updates
        witherMap.put(p, wither);
        //Send the packet
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
    }

    private void updateBossBar(Player p, String text, float health) {
        //Entity metadata creation
        //Doc: https://wiki.vg/index.php?title=Entity_metadata&oldid=7415
        DataWatcher metadata = new DataWatcher(null);
        metadata.a(2, text); //NameTag
        metadata.a(10, text); // NameTag
        metadata.a(3, (byte) 1); //Always show nametag
        metadata.a(11, (byte) 1); // Always show nametag
        metadata.a(6, health*3); // Health

        PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata(witherMap.get(p).getId(), metadata, true);
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
    }

    public void teleportBar(Player p) {
        if(!witherMap.containsKey(p))
            return;
        Location tpLoc = p.getLocation().getDirection().multiply(32).add(p.getLocation().toVector()).toLocation(p.getWorld());
        //Doc: https://wiki.vg/index.php?title=Protocol&oldid=7368#Entity_Teleport
        PacketPlayOutEntityTeleport packet = new PacketPlayOutEntityTeleport(witherMap.get(p).getId(),
                (int)(tpLoc.getX() * 32), (int)(tpLoc.getY() * 32), (int)(tpLoc.getZ() * 32),
                (byte) ((int)tpLoc.getPitch() / 256F * 360F), (byte) ((int)tpLoc.getYaw() / 256F * 360F), false);

        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
    }

    public void removeBar(Player p) {
        if(!witherMap.containsKey(p))
            return;
        PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(witherMap.get(p).getId());
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
        witherMap.remove(p);
    }

}
