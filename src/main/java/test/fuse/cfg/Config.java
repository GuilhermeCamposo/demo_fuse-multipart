package test.fuse.cfg;


import org.apache.camel.http.common.CamelServlet;
import org.apache.camel.http.common.HttpBinding;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class Config {



    @Bean
    ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean servlet = new ServletRegistrationBean (new MyServlet(), "/camel/*");
        servlet.setName(CamelServlet.class.getSimpleName());
        return servlet;
    }


    @Bean(name = "MyAttachmentHttpBinding")
    HttpBinding attachmentHttpBindingBean(){
        return new MyAttachmentHttpBinding();
    }


}