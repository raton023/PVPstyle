 
package com.craftilandia.pvpstyle;

import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.IChatBaseComponent;
import net.minecraft.server.v1_7_R4.PacketPlayOutChat;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{

	@Override
	public void onEnable() {
getServer().getPluginManager().registerEvents(this, this);
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e){
	if ((e.getEntity().getKiller() != null) && ((e.getEntity().getKiller() instanceof Player))) {
		if (e.getEntity().getKiller().getItemInHand().getType().equals(Material.AIR)) {
			e.setDeathMessage(e.getEntity().getPlayer().getDisplayName() + " kill " + e.getEntity().getKiller().getDisplayName() + "with his fists");
			return;
			}
	String itemname = null;
	String enchants = null;
	if (e.getEntity().getKiller().getItemInHand().getItemMeta().getDisplayName() != null) {
	itemname = e.getEntity().getKiller().getItemInHand().getItemMeta().getDisplayName();
	}else {
	itemname = e.getEntity().getKiller().getItemInHand().getType().name().replace('_', ' ').toLowerCase();
	 }
	enchants = e.getEntity().getKiller().getItemInHand().getItemMeta().getEnchants().toString().replace("Enchantment", "\n").replace("LOOT_BONUS_MOBS", "Looting").replace("{", "").replace("DAMAGE_ALL", "Sharpness").replace("]=", " ").replace("[16, ", "").replace("[17, ", "").replace("[18, ", "").replace("[20, ", "").replace("[21, ", "").replace("[34, ", "").replace("[48, ", "").replace("[49, ", "").replace("[50, ", "").replace("[51, ", "").replace("}", "").replace("DURABILITY", "Durability").replace("ARROW_KNOCKBACK", "Knockback").replace(",", "").replace("[19 KNOCKBACK", "Knockback").replace("FIRE_ASPECT", "Fire Aspect").replace("ARROW_INFINITE", "Infinity").replace("DAMAGE_UNDEAD", "Punch").replace("ARROW_FIRE", "Fire").replace("ARROW_DAMAGE", "Power").replace("DAMAGE_ARTHROPODS", "Arthropods");
	if (e.getEntity().getKiller().getItemInHand().getItemMeta().getEnchants().isEmpty()) {
	    enchants = "No Enchantments";}
	e.setDeathMessage(null);
	@SuppressWarnings("deprecation")
	Player[] c3 = Bukkit.getOnlinePlayers();
	Player[] arrayOfPlayer1;
	int j = (arrayOfPlayer1 = c3).length;
	for (int i = 0; i < j; i++)
	{Player d3 = arrayOfPlayer1[i];
	String JSON = "{\"text\":\"§f" + e.getEntity().getKiller().getDisplayName() + " §4killed §f" + e.getEntity().getDisplayName() + " §4Using §r\",\"extra\":[{\"text\":\"§r[ " + itemname + " §r]\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + itemname + "\n" + enchants + "\"}}]}";
	IChatBaseComponent msg = ChatSerializer.a(JSON);
	PacketPlayOutChat packet2 = new PacketPlayOutChat(msg, true);
	EntityPlayer nmsPlayer = ((CraftPlayer) d3).getHandle();
	nmsPlayer.playerConnection.sendPacket(packet2);}}}	
}
