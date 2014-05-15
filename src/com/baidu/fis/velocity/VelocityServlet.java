package com.baidu.fis.velocity;


import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

@SuppressWarnings("deprecated")
public class VelocityServlet extends org.apache.velocity.servlet.VelocityServlet
{
    /**
     * @param ctx
     */
    @Override
    @SuppressWarnings("deprecated")
    protected Template handleRequest(Context ctx) throws Exception {
        HttpServletRequest req = (HttpServletRequest)ctx.get(REQUEST);

        return getTemplate(req.getServletPath());
    }

    /**
     * @param config
     */
    @Override
    @SuppressWarnings("deprecated")
    protected Properties loadConfiguration(ServletConfig config) throws IOException {

        Properties p = super.loadConfiguration(config);

        p.load(getServletContext().getResourceAsStream("WEB-INF/velocity.properties"));

        p.setProperty("file.resource.loader.path", getServletContext().getRealPath("./") + "//");

        return p;
    }

    @Override
    protected Context createContext(HttpServletRequest request, HttpServletResponse response) {
        Context context = super.createContext(request, response);

        attachJson(context, request, response);
        includeJsp(context, request, response);

        return context;
    }

    protected void attachJson(Context context, HttpServletRequest request, HttpServletResponse response) {

    }

    protected void includeJsp(Context context, HttpServletRequest request, HttpServletResponse response) {

    }
}
