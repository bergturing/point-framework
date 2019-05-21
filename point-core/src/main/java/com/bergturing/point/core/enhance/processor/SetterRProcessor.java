package com.bergturing.point.core.enhance.processor;

import com.bergturing.point.core.enhance.annotations.SetterR;
import com.sun.source.tree.Tree;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.*;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

import static com.bergturing.point.core.enhance.constants.AnnotationConstants.SETTER_R;

/**
 * SetterR注解的处理器
 *
 * @author bergturing@qq.com
 * @apiNote 2019/5/19
 */
@SupportedAnnotationTypes(SETTER_R)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class SetterRProcessor extends AbstractProcessor {
    /**
     * 用来在编译期打 log
     */
    private Messager messager;

    /**
     * 提供了待处理的抽象语法树
     */
    private JavacTrees javacTrees;

    /**
     * 封装了创建AST节点的一些方法
     */
    private TreeMaker treeMaker;

    /**
     * 提供了创建标识符的方法
     */
    private Names names;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        this.messager = processingEnv.getMessager();
        this.javacTrees = JavacTrees.instance(processingEnv);
        Context context = ((JavacProcessingEnvironment) processingEnv).getContext();
        this.treeMaker = TreeMaker.instance(context);
        this.names = Names.instance(context);
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnv) {
        roundEnv.getElementsAnnotatedWith(SetterR.class).forEach(element -> {
            // 语法树
            JCTree tree = javacTrees.getTree(element);
            if (tree != null) {
                tree.accept(new TreeTranslator() {
                    @Override
                    public void visitClassDef(JCTree.JCClassDecl jcClassDecl) {
                        jcClassDecl.defs.forEach(jcClazz -> {
                            // 处理类型为变量的对象
                            if (Tree.Kind.VARIABLE.equals(jcClazz.getKind())) {
                                // 获取变量的定义
                                JCTree.JCVariableDecl jcVariableDecl = (JCTree.JCVariableDecl) jcClazz;

                                // 打印日志
                                messager.printMessage(Diagnostic.Kind.NOTE, String.format("%s start SetterR processing",
                                        jcVariableDecl.getName()));

                                // 生成方法的定义
                                jcClassDecl.defs = jcClassDecl.defs.prepend(generateGetterDecl(jcVariableDecl));

                                // 打印日志
                                messager.printMessage(Diagnostic.Kind.NOTE, String.format("%s end SetterR processing",
                                        jcVariableDecl.getName()));
                            }
                        });
                        super.visitClassDef(jcClassDecl);
                    }
                });
            }
        });

        // 返回结果
        return true;
    }

    /**
     * 生成一个方法的定义
     *
     * @param jcVariableDecl 变量的定义对象
     * @return 对应方法的定义
     */
    private JCTree.JCMethodDecl generateGetterDecl(JCTree.JCVariableDecl jcVariableDecl) {
        // 语句体
        ListBuffer<JCTree.JCStatement> statements = new ListBuffer<>();

        // 设置值的语句
        statements.append(
                treeMaker.Exec(
                        treeMaker.Assign(
                                treeMaker.Select(
                                        treeMaker.Ident(names.fromString("this")),
                                        names.fromString(jcVariableDecl.name.toString())
                                ),
                                treeMaker.Ident(names.fromString(jcVariableDecl.name.toString()))
                        )
                )
        );

        // 方法的返回值
        statements.append(
                treeMaker.Return(
                        treeMaker.Ident(names.fromString("this"))
                )
        );

        // 方法体
        JCTree.JCBlock block = treeMaker.Block(0, statements.toList());

        // 返回方法的定义
        return treeMaker.MethodDef(treeMaker.Modifiers(Flags.PUBLIC),
                getMethodName(jcVariableDecl.getName()),
                jcVariableDecl.vartype, List.nil(), List.nil(), List.nil(), block,
                null);
    }

    /**
     * 获取方法名
     *
     * @param name 字段名名对象
     * @return 当前传入的字段名对象对应的方法名
     */
    private Name getMethodName(Name name) {
        // 字段名
        String nameString = name.toString();

        // 返回生成的方法名
        return names.fromString("set" + nameString.substring(0, 1).toUpperCase() +
                nameString.substring(1, name.length()) + "R");
    }
}
