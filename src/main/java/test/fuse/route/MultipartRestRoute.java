package test.fuse.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

@Component
public class MultipartRestRoute extends RouteBuilder {
	

  @Override
  public void configure() throws Exception {

	  createRestDSLRoute();
	  createStandardRoute();


  }

	private void createStandardRoute() {
		from("servlet:multipart2?httpMethodRestrict=POST&attachmentMultipartBinding=true")
				.log("headers : ${headers}")
				.bean(new HandleUpload())
				.log("processed");
	}

	private void createRestDSLRoute() {
		restConfiguration()
		.endpointProperty("attachmentMultipartBinding", "true")
		.endpointProperty("httpBinding", "#MyAttachmentHttpBinding")
		.component("servlet");

		rest()
		.post("/multipart")
			.consumes(MediaType.MULTIPART_FORM_DATA)
			.produces(MediaType.TEXT_PLAIN)
			.route()
				.choice()
				.when(body().isNotNull())
				.log("File received.")
				.log("headers: ${headers}")
				.bean(new HandleUpload())
				.log("processed")
				.otherwise()
					.log("no file")
					.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HttpServletResponse.SC_BAD_REQUEST))
				.endChoice()
		.end();
	}
}