package xyz.sethy.cd.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import xyz.sethy.cd.Main;
import xyz.sethy.cd.extended.ExtendedPlayer;

public class PlayerDamageEntityListener {
	private final float energyNeededToAttack = 0.8f;
	
	@ForgeSubscribe
	public void onPlayerDamageEntity(final LivingAttackEvent event) {
		if (!(event.source.getEntity() instanceof EntityPlayer)) {
			EntityPlayer attacked = (EntityPlayer) event.source.getEntity();
			if (!attacked.getEntityName().equals(Minecraft.getMinecraft().thePlayer.getEntityName()))
				return;
		}
		
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		ExtendedPlayer extendedPlayer = ExtendedPlayer.get(player);
		if (extendedPlayer.getCurrentExerciseLevel() < this.energyNeededToAttack) {
			player.addChatMessage("You are too tired to hurt anybody; please rest or have something to eat.");
			event.setCanceled(true);
			return;
		}
		
		Main.getInstance().getExerciseLearner().getTimesAttacked().addAndGet(1);
		float energyUsed = Main.getInstance().getExerciseLearner().getAttackToUseEnergy();
		
		extendedPlayer.useExerciseEnergy(energyUsed);
	}
}
