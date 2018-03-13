package xyz.sethy.cd;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import xyz.sethy.cd.exercise.ExerciseLearner;
import xyz.sethy.cd.gui.WaterGUI;
import xyz.sethy.cd.listener.EntityConstructingListener;
import xyz.sethy.cd.listener.EntityJoinWorldListener;
import xyz.sethy.cd.listener.PlayerBlockBreakListener;
import xyz.sethy.cd.listener.PlayerInteractListener;
import xyz.sethy.cd.packet.WaterPacketHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@NetworkMod(clientSideRequired=true, serverSideRequired=false, channels = {"extendedPlayer"}, packetHandler = WaterPacketHandler.class)
@Mod(modid = Main.MODID, name = Main.MODID, version = Main.VERSION)
public class Main {
    public static final String MODID = "CD";
    public static final String VERSION = "1.0";
    public static final Logger LOGGER = Logger.getLogger(MODID); 
    private static Main instance;
    private ExerciseLearner exerciseLearner;
    
    @EventHandler
    public void init(FMLInitializationEvent event) {
    	setInstance(this);
    	this.exerciseLearner = new ExerciseLearner();
    	
    	MinecraftForge.EVENT_BUS.register(new WaterGUI());
    	MinecraftForge.EVENT_BUS.register(new EntityConstructingListener());
    	MinecraftForge.EVENT_BUS.register(new EntityJoinWorldListener());
    	MinecraftForge.EVENT_BUS.register(new PlayerInteractListener());
    	MinecraftForge.EVENT_BUS.register(new PlayerBlockBreakListener());
    }
   
    private static void setInstance(Main newInstance) {
    	instance = newInstance;
    }
    
    public static Main getInstance() {
    	return instance;
    }
    
    public ExerciseLearner getExerciseLearner() {
    	return this.exerciseLearner;
    }
}
