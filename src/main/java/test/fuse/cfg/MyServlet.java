package test.fuse.cfg;

import org.apache.camel.component.servlet.CamelHttpTransportServlet;

import javax.servlet.annotation.MultipartConfig;


@MultipartConfig(location = "/tmp")
public class MyServlet extends CamelHttpTransportServlet {
}
