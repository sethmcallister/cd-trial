package xyz.sethy.cd.packet;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.logging.Level;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import xyz.sethy.cd.Main;
import xyz.sethy.cd.extended.ExtendedPlayer;

public class WaterPacketHandler implements IPacketHandler {

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		if (packet.channel.equals("water")) {
			handleExtendedPlayer(packet, player);
		}
	}
	
	
	private void handleExtendedPlayer(Packet250CustomPayload packet, Player player) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		ExtendedPlayer extendedPlayer = ExtendedPlayer.get((EntityPlayer) player);
		try {
			extendedPlayer.setMaxWaterLevel(inputStream.readFloat());
			extendedPlayer.setCurrentWaterLevel(inputStream.readFloat());
			inputStream.close();
		} catch (IOException e) {
			Main.LOGGER.log(Level.SEVERE, e.getMessage(), e.getCause());
		}
	}
}
