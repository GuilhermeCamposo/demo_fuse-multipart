package test.fuse.route;

import org.apache.camel.Attachments;
import org.apache.camel.Exchange;
import org.apache.camel.Handler;
import org.apache.camel.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

import javax.activation.DataHandler;
import java.lang.invoke.MethodHandles;
import java.util.Map;

public class HandleUpload {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());



    @Handler
    public void processUpload(@Attachments Map<String, DataHandler> attachments,
                              @Header("Content-Type") String contentType,
                              @Header("id") String id, Exchange exchange)
            throws Exception {

        LOG.info("Upload received");



        String reponseMsg = "Exchange body: " + exchange.getIn().getBody();
        reponseMsg += "\nAttachment size: " + (attachments == null ? 0 : attachments.size());
        reponseMsg += "\nAttachment object size: " + (exchange.getIn().getAttachmentObjects() == null ? 0
                        : exchange.getIn().getAttachmentObjects().size());

        if (exchange.getIn().getAttachmentNames() != null) {
            for (String name : exchange.getIn().getAttachmentNames()) {
                reponseMsg += "\nAttachment name: " + name;
            }
        }

        if (contentType == null ||  !contentType.startsWith(MediaType.MULTIPART_FORM_DATA_VALUE)) {
            LOG.warn("Unsupported media type!");
            throw new Exception();
        }

        if (attachments.size() == 0) {
            LOG.warn("No attachments found!");
        } else {
            for (String key : attachments.keySet()) {
                reponseMsg += "\nFilename: " + key;
            }
        }

        exchange.getIn().setBody(reponseMsg);

    }
}