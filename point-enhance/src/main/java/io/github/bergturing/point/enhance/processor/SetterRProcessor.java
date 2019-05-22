package io.github.bergturing.point.enhance.processor;

import com.sun.source.tree.Tree;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.processing.JavacProcessingEnvironment;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;
import com.sun.tools.javac.tree.TreeTranslator;
import com.sun.tools.javac.util.*;
import io.github.bergturing.point.enhance.annotations.SetterR;
import io.github.bergturing.point.enhance.constants.AnnotationConstants;
import io.github.bergturing.point.enhance.utils.ProcessorUtils;
import io.github.bergturing.point.utils.StringUtils;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

/**
 * SetterR注解的处理器
 *
 * @author bergturing@qq.com
 * @date 2019/5/19
 */
@SupportedAnnotationTypes(AnnotationConstants.SETTER_R)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class SetterRProcessor extends AbstractProcessor {
    /**
     * 方法的后缀
     */
    private static final String METHOD_SUFFIX = "R";

    /**
     * 类名
     */
    private Name className;

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
                        // 调用开始处理的方法
                        before(jcClassDecl);

                        // 遍历内部属性的定义
                        jcClassDecl.defs.forEach(jcClazz -> {
                            // 处理类型为变量的对象
                            if (Tree.Kind.VARIABLE.equals(jcClazz.getKind())) {
                                // 获取变量的定义
                                JCTree.JCVariableDecl jcVariableDecl = (JCTree.JCVariableDecl) jcClazz;

                                // 打印日志
                                messager.printMessage(Diagnostic.Kind.NOTE, String.format("%s start SetterR processing",
                                        jcVariableDecl.getName()));

                                // 生成方法的定义
                                jcClassDecl.defs = jcClassDecl.defs.prepend(generateSetterRDecl(jcVariableDecl));

                                // 打印日志
                                messager.printMessage(Diagnostic.Kind.NOTE, String.format("%s end SetterR processing",
                                        jcVariableDecl.getName()));
                            }
                        });
                        // 处理定义
                        super.visitClassDef(jcClassDecl);

                        // 调用结束处理的方法
                        after();
                    }
                });
            }
        });

        // 返回结果
        return true;
    }

    /**
     * 进行一些初始化工作
     *
     * @param jcClass 类的语法树节点
     */
    private void before(JCTree.JCClassDecl jcClass) {
        this.className = names.fromString(jcClass.name.toString());
    }

    /**
     * 进行一些清理工作
     */
    private void after() {
        this.className = null;
    }

    /**
     * 生成一个方法的定义
     *
     * @param jcVariableDecl 变量的定义对象
     * @return 对应方法的定义
     */
    private JCTree.JCMethodDecl generateSetterRDecl(JCTree.JCVariableDecl jcVariableDecl) {
        // 语句体
        ListBuffer<JCTree.JCStatement> statements = new ListBuffer<>();

        //添加语句 " this.xxx = xxx; "
        statements.append(
                treeMaker.Exec(
                        treeMaker.Assign(
                                treeMaker.Select(
                                        treeMaker.Ident(names.fromString(AnnotationConstants.THIS)),
                                        names.fromString(jcVariableDecl.name.toString())
                                ),
                                treeMaker.Ident(names.fromString(jcVariableDecl.name.toString()))
                        )
                )
        );

        //添加返回语句 " return this; "
        statements.append(
                treeMaker.Return(
                        treeMaker.Ident(names.fromString(AnnotationConstants.THIS)
                        )
                )
        );

        // 方法体
        JCTree.JCBlock block = treeMaker.Block(
                //访问标志
                0,
                //所有的语句
                statements.toList()
        );

        // 返回方法的定义
        return treeMaker.MethodDef(
                // 访问标志
                treeMaker.Modifiers(Flags.PUBLIC),
                // 名字
                this.getMethodName(jcVariableDecl.getName()),
                // 返回类型
                treeMaker.Ident(this.className),
                // 泛型形参列表
                List.nil(),
                // 参数列表
                List.of(ProcessorUtils.cloneJCVariableAsParam(treeMaker, jcVariableDecl)),
                //异常列表
                List.nil(),
                // 方法体
                block,
                // 默认方法（可能是interface中的那个default）
                null);
    }

    /**
     * 获取方法名
     *
     * @param name 字段名名对象
     * @return 当前传入的字段名对象对应的方法名
     */
    private Name getMethodName(Name name) {
        // 返回生成的方法名
        return names.fromString(AnnotationConstants.SET + StringUtils.toUpperCaseFirstOne(name.toString()) + METHOD_SUFFIX);
    }
}
