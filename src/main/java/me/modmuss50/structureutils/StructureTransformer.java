package me.modmuss50.structureutils;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

/**
 * Created by Mark on 06/04/2017.
 */
public class StructureTransformer implements IClassTransformer {
	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		if (transformedName.equals("net.minecraft.tileentity.TileEntityStructure")) {
			System.out.println("Found TileEntityStructure");
			ClassNode classNode = ASMUtils.readClassFromBytes(basicClass);
			for (MethodNode methodNode : classNode.methods) {
				if (methodNode.name.equals("readFromNBT") || methodNode.name.equals("func_145839_a ") || (methodNode.name.equals("a") && methodNode.desc.equals("(Ldr;)V"))) {
					System.out.println("Found readFromNBT");
					InsnList insnList = methodNode.instructions;
					for (AbstractInsnNode node : insnList.toArray()) {
						if (node instanceof IntInsnNode) {
							IntInsnNode intInsnNode = (IntInsnNode) node;
							if (intInsnNode.operand == 32) {
								if(intInsnNode.getPrevious() instanceof IntInsnNode){
									continue;
								}
								intInsnNode.setOpcode(Opcodes.SIPUSH);
								intInsnNode.operand = 512;
							}
						}
					}
				}
			}
			return ASMUtils.writeClassToBytes(classNode);
		}
		if (transformedName.equals("net.minecraft.network.NetHandlerPlayServer")) {
			System.out.println("Found NetHandlerPlayServer");
			ClassNode classNode = ASMUtils.readClassFromBytes(basicClass);
			for (MethodNode methodNode : classNode.methods) {
				if (methodNode.name.equals("processCustomPayload") || methodNode.name.equals("func_147349_a") || (methodNode.name.equals("a") && methodNode.desc.equals("(Lit;)V"))) {
					System.out.println("Found processCustomPayload");
					InsnList insnList = methodNode.instructions;
					for (AbstractInsnNode node : insnList.toArray()) {
						if (node instanceof IntInsnNode) {
							IntInsnNode intInsnNode = (IntInsnNode) node;
							if (intInsnNode.operand == 32) {
								if(intInsnNode.getPrevious() instanceof IntInsnNode || intInsnNode.getPrevious() instanceof VarInsnNode){
									continue;
								}
								intInsnNode.setOpcode(Opcodes.SIPUSH);
								intInsnNode.operand = 512;
							}
						}
					}
				}
			}
			return ASMUtils.writeClassToBytes(classNode);
		}
		return basicClass;
	}
}
