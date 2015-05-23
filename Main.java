package com.craftilandia.pvptool;
	import java.util.List;

import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.IChatBaseComponent;
import net.minecraft.server.v1_7_R4.PacketPlayOutChat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
			getConfig().options().copyHeader();
			getConfig().options().copyDefaults();
	if(getConfig().getString("noenchantments").isEmpty()){
		getConfig().set("noenchantments", "No Enchantments");
			}
	if(getConfig().getString("pvpdeathmessage").isEmpty()){
		getConfig().set("pvpdeathmessage", "&fkiller &4just killed &fvictim &4using &r");
	}
	if(getConfig().getString("fistdead").isEmpty()){
		getConfig().set("fistdead", "&fkiller &4just killed &fvictim &4using &ahis hands");
	}

			saveConfig();
		}
		@EventHandler
		public void onDeath(PlayerDeathEvent e){
		if ((e.getEntity().getKiller() != null) && ((e.getEntity().getKiller() instanceof Player))) {
			if (e.getEntity().getKiller().getItemInHand().getType().equals(Material.AIR)) {
				String deadfistmsg = getConfig().getString("fistdead").replace("killer", e.getEntity().getKiller().getDisplayName()).replace("victim", e.getEntity().getDisplayName());
				String deadfistmsgcolor = ChatColor.translateAlternateColorCodes('&', deadfistmsg);
				e.setDeathMessage(deadfistmsgcolor);
				return;}
		String itemname = null;
		
		String enchants = null;	
		if (e.getEntity().getKiller().getItemInHand().getItemMeta().getDisplayName() != null) 
		{itemname = e.getEntity().getKiller().getItemInHand().getItemMeta().getDisplayName();}
		else 
		{itemname = e.getEntity().getKiller().getItemInHand().getType().name().replace('_', ' ').toLowerCase();}
		if (e.getEntity().getKiller().getItemInHand().getItemMeta().getEnchants().isEmpty()) {
			enchants = getConfig().getString("noenchantments");
			}
		else 
		{enchants = e.getEntity().getKiller().getItemInHand().getItemMeta().getEnchants().toString().replace("Enchantment", "\n").replace("LOOT_BONUS_MOBS", "Looting").replace("{", "").replace("DAMAGE_ALL", "Sharpness").replace("]=", " ").replace("[16, ", "").replace("[17, ", "").replace("[18, ", "").replace("[20, ", "").replace("[21, ", "").replace("[34, ", "").replace("[48, ", "").replace("[49, ", "").replace("[50, ", "").replace("[51, ", "").replace("}", "").replace("DURABILITY", "Durability").replace("ARROW_KNOCKBACK", "Knockback").replace(",", "").replace("[19 KNOCKBACK", "Knockback").replace("FIRE_ASPECT", "Fire Aspect").replace("ARROW_INFINITE", "Infinity").replace("DAMAGE_UNDEAD", "Punch").replace("ARROW_FIRE", "Fire").replace("ARROW_DAMAGE", "Power").replace("DAMAGE_ARTHROPODS", "Arthropods");}
		e.setDeathMessage(null);
		String deadmsg = getConfig().getString("pvpdeathmessage").replace("killer", e.getEntity().getKiller().getDisplayName()).replace("victim", e.getEntity().getDisplayName());
		String deadmsgcolor = ChatColor.translateAlternateColorCodes('&', deadmsg);
		//String JSON = "{\"text\":\"§f" + e.getEntity().getKiller().getDisplayName() + " §4killed §f" + e.getEntity().getDisplayName() + " §4Using §r\",\"extra\":[{\"text\":\"§r " + itemname + " §r\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + itemname + "\n" + enchants + "\"}}]}";
		String JSON = "{\"text\":\"§4" + deadmsgcolor + "§r\",\"extra\":[{\"text\":\"§r " + itemname + " §r\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + itemname + "\n" + enchants + "\"}}]}";
	    IChatBaseComponent msg = ChatSerializer.a(JSON);
	    PacketPlayOutChat packet = new PacketPlayOutChat(msg);
	    List<Player> players = e.getEntity().getWorld().getPlayers();
	    for(int i = 0; i < players.size() ; i++){
	    EntityPlayer nmsPlayer = ((CraftPlayer) players.get(i).getPlayer()).getHandle();
	    nmsPlayer.playerConnection.sendPacket(packet);}}}
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
	if(sender instanceof Player){
		Player p = (Player)sender;
		if(p.hasPermission("pvptool.set")){
		if(command.getName().equalsIgnoreCase("pvp")){
			if(p.getWorld().getPVP() == true){
				p.getWorld().setPVP(false);
				Bukkit.broadcastMessage("PvP Deactivated");
				return true;}
			if(p.getWorld().getPVP() == false){
				p.getWorld().setPVP(true);
				Bukkit.broadcastMessage("PvP Activated");
				return true;}}}}return false;}
	}


