package me.modmuss50.structureutils;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = "sutils", name = "Structure Utils")
public class SUtils {

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event){
        event.registerServerCommand(new CommandClean());
    }
}
