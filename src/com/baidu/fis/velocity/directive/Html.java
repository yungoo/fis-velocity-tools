package com.baidu.fis.velocity.directive;


import com.baidu.fis.ResourceSingleton;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.TemplateInitException;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.parser.node.Node;

import java.io.IOException;
import java.io.Writer;

public class Html extends Block {
    @Override
    public void init(RuntimeServices rs, InternalContextAdapter context, Node node) throws TemplateInitException {

        super.init(rs, context, node);

        // for debug
        ResourceSingleton.getInstance().log = log;

        // 从velocity.properties里面读取fis.mapDir。
        // 用来指定map文件存放目录。
        String mapDir = rs.getString("fis.mapDir", "WEB-INF/config");
        ResourceSingleton.getInstance().setMapDir(mapDir);
        ResourceSingleton.getInstance().setDebug(rs.getBoolean("fis.debug", false));
    }

    @Override
    public String getName() {
        return "html";
    }

    @Override
    public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
        String framework;

        writer.write("<html");

        // 如果指定了 framework （通过第一个参数指定）
        // 如: #html( "static/js/mod.js")#end
        if (node.jjtGetNumChildren() > 1) {
            framework = node.jjtGetChild(0).value(context).toString();
            ResourceSingleton.setFramework(framework);

            // 生成attributes
            writer.write(this.buildAttrs(node, context, 1));
        }

        writer.write(">");

        // 让父级去渲染 block body。
        super.render(context, writer);

        writer.write("</html>");

        return true;
    }
}
