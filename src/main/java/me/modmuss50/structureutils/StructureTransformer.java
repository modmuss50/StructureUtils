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
				if (methodNode.name.equals("readFromNBT") || methodNode.name.equals("func_145839_a") || (methodNode.name.equals("a") && methodNode.desc.equals("(Lfy;)V"))) {
					System.out.println("Found readFromNBT");
					InsnList insnList = methodNode.instructions;
					for (AbstractInsnNode node : insnList.toArray()) {
						if (node instanceof IntInsnNode) {
							IntInsnNode intInsnNode = (IntInsnNode) node;
							if (intInsnNode.operand == 32) {
								if (intInsnNode.getPrevious() instanceof IntInsnNode) {
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
				if (methodNode.name.equals("processCustomPayload") || methodNode.name.equals("func_147349_a") || (methodNode.name.equals("a") && methodNode.desc.equals("(Llh;)V"))) {
					System.out.println("Found processCustomPayload");
					InsnList insnList = methodNode.instructions;
					for (AbstractInsnNode node : insnList.toArray()) {
						if (node instanceof IntInsnNode) {
							IntInsnNode intInsnNode = (IntInsnNode) node;
							if (intInsnNode.operand == 32) {
								if (intInsnNode.getPrevious() instanceof IntInsnNode || intInsnNode.getPrevious() instanceof VarInsnNode) {
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
		if (transformedName.equals("net.minecraft.world.gen.structure.template.Template")) {
			System.out.println("Found template");
			ClassNode classNode = ASMUtils.readClassFromBytes(basicClass);
			for (MethodNode methodNode : classNode.methods) {
				if (methodNode.desc.equals("(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/gen/structure/template/ITemplateProcessor;Lnet/minecraft/world/gen/structure/template/PlacementSettings;I)V") || methodNode.name.equals("func_189960_a") || methodNode.desc.equals("(Lams;Let;Lbcg;Lbcf;I)V")) {
					System.out.println("Found addBlocksToWorld");
					InsnList insnList = methodNode.instructions;
					int count = 0;
					boolean obf = !name.equals(transformedName);
					System.out.println("Found rotate");
					VarInsnNode varInsnNode1 = new VarInsnNode(Opcodes.ALOAD, 1);
					VarInsnNode varInsnNode10 = new VarInsnNode(Opcodes.ALOAD, 15);
					VarInsnNode varInsnNode15 = new VarInsnNode(Opcodes.ALOAD, 10);
					MethodInsnNode methodInsnNode1 = new MethodInsnNode(Opcodes.INVOKEVIRTUAL,
						obf ? "net/minecraft/world/World" : "net/minecraft/world/World",
						obf ? "func_175690_a" : "setTileEntity", obf ? "(Let;Lavh;)V" : "(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/tileentity/TileEntity;)V", false);
					AbstractInsnNode targetNode = null;
					for (AbstractInsnNode node : insnList.toArray()) {
						if (node instanceof MethodInsnNode) {
							MethodInsnNode methodInsnNode = (MethodInsnNode) node;
							if (methodInsnNode.getOpcode() == Opcodes.INVOKEVIRTUAL) {
								if (methodInsnNode.name.equals("getTileEntity") || methodInsnNode.name.equals("func_175625_s") || (methodInsnNode.name.equals("r") && methodInsnNode.desc.equals("(Let;)Lavh;"))) {
									count++;
									if (count == 2) {
										methodInsnNode.setOpcode(Opcodes.INVOKESTATIC);
										methodInsnNode.owner = "net/minecraft/tileentity/TileEntity";
										methodInsnNode.name = obf ? "func_190200_a" : "create";
										methodInsnNode.desc = obf ? "(Lams;Lfy;)Lavh;" : "(Lnet/minecraft/world/World;Lnet/minecraft/nbt/NBTTagCompound;)Lnet/minecraft/tileentity/TileEntity;";
										VarInsnNode varInsnNode = (VarInsnNode) methodInsnNode.getPrevious();
										varInsnNode.var--;
										FieldInsnNode fieldInsnNode = new FieldInsnNode(Opcodes.GETFIELD, obf ? "bch$b" : "net/minecraft/world/gen/structure/template/Template$BlockInfo", obf ? "field_186244_c" : "tileentityData", obf ? "Lfy;" : "Lnet/minecraft/nbt/NBTTagCompound;");
										insnList.insertBefore(methodInsnNode, fieldInsnNode);
									}
								}
								if (methodInsnNode.name.equals("rotate") || methodInsnNode.name.equals("func_189667_a") || (methodInsnNode.name.equals("a") && methodInsnNode.desc.equals("(Latk;)V"))) {
									targetNode = methodInsnNode.getNext();
								}
							}
						}
					}
					insnList.insertBefore(targetNode, methodInsnNode1);
					insnList.insertBefore(methodInsnNode1, new LabelNode());
					insnList.insertBefore(methodInsnNode1, varInsnNode1);
					insnList.insertBefore(methodInsnNode1, varInsnNode15);
					insnList.insertBefore(methodInsnNode1, varInsnNode10);
				}
			}
			return ASMUtils.writeClassToBytes(classNode);
		}
		return basicClass;
	}
}
