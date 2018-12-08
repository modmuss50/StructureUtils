package me.modmuss50.structureutils;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;


//This command removes all air blocks from a strucutre file
public class CommandClean extends CommandBase {
    @Override
    public String getName() {
        return "structure_clean";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return getName();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(args.length != 1){
            server.sendMessage(new TextComponentString("Invalid arguments"));
        }
        WorldServer worldServer = server.getWorld(0);
        TemplateManager templateManager = worldServer.getStructureTemplateManager();
        ResourceLocation name = new ResourceLocation(args[0]);
        templateManager.readTemplate(name);
        Template template = templateManager.get(server, name);

        int preSize = template.blocks.size();
        template.blocks.removeIf(blockInfo -> blockInfo.blockState.getBlock() == Blocks.AIR);
        int removed = preSize - template.blocks.size();

        sender.sendMessage(new TextComponentString("Removed " + removed + " air blocks"));

        templateManager.writeTemplate(server, name);
    }
}
