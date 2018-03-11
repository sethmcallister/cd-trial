package xyz.sethy.cd.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import xyz.sethy.cd.extended.ExtendedPlayer;

public class EntityJoinWorldListener {
	
	@ForgeSubscribe
	public void onEntityJoinWorld(final EntityJoinWorldEvent event) {
		if (!event.entity.worldObj.isRemote && event.entity instanceof EntityPlayer) {
				ExtendedPlayer.get((EntityPlayer) event.entity).sync();
		}
	}
}
