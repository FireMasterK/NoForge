package net.ccbluex.noforge.injection.transformers;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.MethodNode;

public class ForgeTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        // Check if transform class is network dispatcher
        if(name.equals("net.minecraftforge.fml.common.network.handshake.NetworkDispatcher")) {
            // Read class from byte array
            final ClassReader classReader = new ClassReader(basicClass);
            final ClassNode classNode = new ClassNode();
            classReader.accept(classNode, 0);

            for(final Object o : classNode.methods) {
                final MethodNode methodNode = (MethodNode) o;

                if(methodNode.name.equals("handleVanilla")) {
                    final InsnList insnList = new InsnList();

                    insnList.add(new InsnNode(Opcodes.ICONST_0));
                    insnList.add(new InsnNode(Opcodes.IRETURN));

                    methodNode.instructions = insnList;

                    System.out.println("[NoForge] Patched \"" + methodNode.name + "\" in " + name + ".");
                }
            }

            // Write class to byte array
            final ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            classNode.accept(classWriter);

            return classWriter.toByteArray();
        }

        return basicClass;
    }
}
