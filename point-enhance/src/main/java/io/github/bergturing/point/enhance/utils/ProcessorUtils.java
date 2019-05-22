package io.github.bergturing.point.enhance.utils;

import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.TreeMaker;

/**
 * 处理器的工具类
 *
 * @author bergturing@qq.com
 */
public class ProcessorUtils {
    /**
     * 克隆一个字段的语法树节点，该节点作为方法的参数
     * 具有位置信息的语法树节点是不能复用的！
     *
     * @param treeMaker           语法树节点构造器
     * @param prototypeJCVariable 字段的语法树节点
     * @return 方法参数的语法树节点
     */
    public static JCTree.JCVariableDecl cloneJCVariableAsParam(TreeMaker treeMaker, JCTree.JCVariableDecl prototypeJCVariable) {
        // 将遍历定义转换为参数定义
        return treeMaker.VarDef(
                // 访问标志。极其坑爹！！！
                treeMaker.Modifiers(Flags.PARAMETER),
                // 名字
                prototypeJCVariable.name,
                // 类型
                prototypeJCVariable.vartype,
                // 初始化语句
                null
        );
    }
}
