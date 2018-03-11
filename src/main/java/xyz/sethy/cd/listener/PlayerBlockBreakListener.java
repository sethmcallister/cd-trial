package xyz.sethy.cd.listener;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.BlockEvent;
import xyz.sethy.cd.extended.ExtendedPlayer;

public class PlayerBlockBreakListener {
	private final float removePerBlockBreak = 0.07f;
	
	@ForgeSubscribe
	public void onPlayerBlockBreak(final BlockEvent event) {
		ExtendedPlayer player = ExtendedPlayer.get(Minecraft.getMinecraft().thePlayer);
		player.useWater(removePerBlockBreak);
		Minecraft.getMinecraft().getLogAgent().logInfo("Player broke a block, and removed water.");
	}
}
