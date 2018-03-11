package xyz.sethy.cd.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;
import xyz.sethy.cd.extended.ExtendedPlayer;

@SideOnly(Side.CLIENT)
public class WaterGUI extends Gui {
	// Creates a static final variable of a ResourceLocaton that is the texture water bar
	private static final ResourceLocation resourceLocation = new ResourceLocation("CD", "water_bar.png");
	// Creates a final float which is how much water is lost when they water a full block.
	private final float waterLossPerBlock = 0.09f;
	
	// Variables to be used in the calculation of how many blocks they have walked.
	private int lastX = Integer.MAX_VALUE;
	private int lastZ = Integer.MAX_VALUE;
	private float lastFoodLevel = Float.MAX_VALUE;
	
	public WaterGUI() {
		super();
	}
	
	// Annotation subscribes this function to be called every time the RenderGameOverlayEvent is called, with the priority of normal (higher it is, the earlier it called
	@ForgeSubscribe(priority = EventPriority.NORMAL)
	public void onRenderGameOverlay(final RenderGameOverlayEvent event) {
		// Checks if the event is cancelable or if the type of overlay that is being rendered is not the of the type EXPERIENCE
		if (event.isCancelable() || event.type != ElementType.EXPERIENCE)
			return;
		// Assigns the entityPlayer location the Minecraft.getMinecraft().thePlayer variable, however casted to an EntityPlayer
		EntityPlayer entityPlayer = (EntityPlayer) Minecraft.getMinecraft().thePlayer;
		
		// Assigns the variable player variable to the ExtenedPlayer object created for the EnyityPlayer for the player that is playing the game
		ExtendedPlayer player = ExtendedPlayer.get(entityPlayer);
		
		// Checked if the player is null, this event may have been called before the object was created, (synchronous nature of the single threaded Forge client)
		if (player == null) {
			return;
		}


		// Calls the function below, with the argument of the entityPlayer which is assigned above
		doPlayerLocationChecks(entityPlayer);
		
		
		
		// Assign as a local variable, to save latency getting from another class.
		float waterLevel = player.getCurrentWaterLevel();
		
		// Send a message to a player every time they're water level gets to a multiple of 3.
		if ((waterLevel % 3) == 1) {
			// Send a message to the player alerting them of their water level getting low.
			entityPlayer.addChatMessage("You are becoming lower on water, consider having something to drink.");
		}
		
		if (player.getCurrentWaterLevel() < 0) {
			return;
		}
		
		// Assign the xPos local variable to 2
		int xPos = 2;
		// Assign the yPos local variable to 2
		int yPos = 2;
		
		// Sets the openGL colour, of 4f to the red, blue, green, and alpha values to 1.0F, within this current GUI
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		// Disables the openGL lighting feature inside of the current GUI
		GL11.glDisable(GL11.GL_LIGHTING);
		
		// Bind the texture of this GUI to the resourceLocation static variable
		Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
		
		// Assign the waterBarWidth to the values of both of the currentWaterLevel, and the maxWaterLevel values, and them multiplied by 101
		int waterBarWidth = (int)(((player.getCurrentWaterLevel() / player.getMaxWaterLevel())) * 101);
		// Draws this current GUI at the previously assigned locations, xPos, and yPos, however yPos is incremented by 1.
		this.drawTexturedModalRect(xPos, yPos + 1, 0, 4, waterBarWidth, 2);
	}
	
	
	private void doPlayerLocationChecks(EntityPlayer player) {
		// Creates a local variable called x which is assigned to the players current x coordinate
		int x = player.getPlayerCoordinates().posX;
		// Creates a local variable called z which is assigned to the players current z coordinate
		int z = player.getPlayerCoordinates().posZ;
		
		// Checks if the lastX and lastZ variables are currently assigned the Integer.MAX_VALUE
		if (lastX == Integer.MAX_VALUE && lastZ == Integer.MAX_VALUE) {
			// Assigns the lastX variable to the local x variable
			lastX = x;
			// Assigned the lastZ variable to the local z variable
			lastZ = z;
			// Returns to the last executed line of the code (before this function was called) (It's a function as it modifies data)
			return;
		}
		
		// Checks if the lastX and lastZ equals there current counterparts (Checks if the player has moved since statement was last called)
		if (lastX == x && lastZ == z)
			return; // Will return the last executed line of code (before this function was called)
		
		// Assigned the movedX variable to the distance they have travelled across the X coordinates (Math.abs makes this integer positive if it is negative, makes it easier to work with later on)
		int movedX = Math.abs(lastX - x);
		// Same as line 98, however, for the Z coordinate
		int movedZ = Math.abs(lastZ - z);
		
		// Adds both of the aforementioned variables
		int movedTotal = movedX + movedZ;

		// Calculates how much water was used by calculating how many blocks they had a moved, and then multiplying that by how water is removed by block of movement
		float waterUsed = movedTotal * waterLossPerBlock;
	
		// Gets the ExtendedPlayer object associated with the player
		ExtendedPlayer extendedPlayer = ExtendedPlayer.get(player);
		
		// Calls the useWater method from inside the ExtenedPlayer object
		extendedPlayer.useWater(waterUsed);
		
		// Assigns the lastZ variable in to the local variable of z
		lastZ = z;
		// Same as line 115 however, for lastX and x
		lastX = x;
	}
}
