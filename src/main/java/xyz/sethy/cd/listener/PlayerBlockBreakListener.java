package xyz.sethy.cd.listener;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.BlockEvent;
import xyz.sethy.cd.Main;
import xyz.sethy.cd.extended.ExtendedPlayer;

public class PlayerBlockBreakListener {
	// Creates a final float that is how much water is removed when the block is broken
	private final float removePerBlockBreak = 0.7f;
	// Defines how much energy is needed to break a block
	private final float energyNeededToBreak = 1f;
	
	@ForgeSubscribe
	public void onPlayerBlockBreak(final BlockEvent event) {
		// Gets the extendedPlayer for the player on the game
		ExtendedPlayer player = ExtendedPlayer.get(Minecraft.getMinecraft().thePlayer);
		if (player == null)
			return;
		
		// Checks if they player has enough energy to break a block, if they don't then deny the event
		if (player.getCurrentExerciseLevel() < this.energyNeededToBreak) {
			event.setResult(Result.DENY);
			return;
		}
		// Deducts the amount of water used from breaking the block from the player
		player.useWater(removePerBlockBreak);
		
		// Increments the value of how many blocks have been placed, in the exercise learner
		Main.getInstance().getExerciseLearner().getBlockedPlaced().addAndGet(1);

		// Gets how much energy will be used for breaking the block, gets lower over time
		float energyToUse = Main.getInstance().getExerciseLearner().getBlockPlaceToUseEnergy();
		// Deducts that amount of energy from the player
		player.useExerciseEnergy(energyToUse);
	}
}
