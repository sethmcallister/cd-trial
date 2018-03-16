package xyz.sethy.cd.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.ForgeSubscribe;
import xyz.sethy.cd.extended.ExtendedPlayer;

public class GUIOpenListener {
	@ForgeSubscribe
	public void onGuiOpen(final GuiOpenEvent event) {
		if (!(event.gui instanceof GuiGameOver))
			return;
		
		ExtendedPlayer player = ExtendedPlayer.get(Minecraft.getMinecraft().thePlayer);
		player.setCurrentExerciseLevel(player.getMaxExerciseLevel());
		player.setCurrentWaterLevel(player.getMaxWaterLevel());
	}
}
