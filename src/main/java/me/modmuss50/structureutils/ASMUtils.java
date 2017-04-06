package me.modmuss50.structureutils;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * Created by Mark on 06/04/2017.
 */
public class ASMUtils {
	public static ClassNode readClassFromBytes(byte[] bytes) {
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		return classNode;
	}

	public static MethodNode findMethodNodeOfClass(ClassNode classNode, String methodName, String methodDesc) {
		for (MethodNode method : classNode.methods) {
			if (method.name.equals(methodName) && method.desc.equals(methodDesc)) {
				return method;
			}
		}
		return null;
	}

	public static AbstractInsnNode findFirstInstruction(MethodNode method) {
		return getOrFindInstruction(method.instructions.getFirst());
	}

	public static AbstractInsnNode getOrFindInstruction(AbstractInsnNode firstInsnToCheck) {
		return getOrFindInstruction(firstInsnToCheck, false);
	}

	public static AbstractInsnNode getOrFindInstruction(AbstractInsnNode firstInsnToCheck, boolean reverseDirection) {
		for (AbstractInsnNode instruction = firstInsnToCheck; instruction != null; instruction = reverseDirection ? instruction.getPrevious() : instruction.getNext()) {
			if (instruction.getType() != AbstractInsnNode.LABEL && instruction.getType() != AbstractInsnNode.LINE)
				return instruction;
		}
		return null;
	}

	public static byte[] writeClassToBytes(ClassNode classNode) {
		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);
		return writer.toByteArray();
	}

}
