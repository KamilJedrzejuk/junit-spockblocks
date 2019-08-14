package com.twitter.kamilyedrzejuq.clazz;


import com.twitter.kamilyedrzejuq.specification.Block;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.util.Printer;
import org.objectweb.asm.util.Textifier;
import org.objectweb.asm.util.TraceMethodVisitor;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Optional;

public class TestMethodBlocksProvider {

    private final ClassNode classNode = new ClassNode();
    private final Printer printer = new Textifier();
    private final TraceMethodVisitor mp = new TraceMethodVisitor(printer);


    public LinkedList<Block> read(Class<?> clazz, Method method) {
        ClassReader classReader = createClassReader(clazz);
        classReader.accept(classNode, 0);

        Optional<MethodNode> methodNodeOptional = getMethodNode(method);
        LinkedList<Block> blocks = methodNodeOptional.map(this::getMethodBlocks)
                .orElse(new LinkedList<>());

        return blocks;
    }

    private Optional<MethodNode> getMethodNode(Method method) {
        return classNode.methods.stream()
                .filter(methodNode -> methodNode.name.equalsIgnoreCase(method.getName()))
                .findFirst();
    }

    private LinkedList<Block> getMethodBlocks(MethodNode methodNode) {
        LinkedList<Block> blocks = new LinkedList<>();
        InsnList inList = methodNode.instructions;
        for (int i = 0; i < inList.size(); i++) {
            inList.get(i).accept(mp);
            StringWriter sw = new StringWriter();
            printer.print(new PrintWriter(sw));
            printer.getText().clear();

            String lineCode = sw.toString();
            Block.matchWithInstruction(lineCode).ifPresent(blocks::add);
        }
        return blocks;
    }

    private ClassReader createClassReader(Class<?> clazz) {
        try {
            return new ClassReader(clazz.getName());
        } catch (IOException e) {
            throw new ReadClassException(e.getMessage(), e);
        }
    }
}
