package test.fuse.cfg;

import org.apache.camel.Attachment;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.http.common.DefaultHttpBinding;
import org.apache.camel.http.common.HttpMessage;
import org.apache.camel.impl.DefaultAttachment;

import javax.activation.DataSource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;

public class MyAttachmentHttpBinding extends DefaultHttpBinding {

    MyAttachmentHttpBinding() {
    }

    @Override
    protected void populateAttachments(HttpServletRequest request, HttpMessage message) {
        try {
            Collection<Part> parts = request.getParts();
            for (Part part : parts) {
                DataSource ds = new MyAttachmentHttpBinding.PartDataSource(part);
                Attachment attachment = new DefaultAttachment(ds);
                for (String headerName : part.getHeaderNames()) {
                    for (String headerValue : part.getHeaders(headerName)) {
                        attachment.addHeader(headerName, headerValue);
                    }
                }
                message.addAttachmentObject(part.getName(), attachment);
            }
        } catch (Exception e) {
            throw new RuntimeCamelException("Cannot populate attachments", e);
        }
    }

    public final class PartDataSource implements DataSource {
        private final Part part;

        PartDataSource(Part part) {
            this.part = part;
        }

        public String getSubmittedFileName() {
            return part.getSubmittedFileName();
        }

        @Override
        public OutputStream getOutputStream() throws IOException {
            return null;
        }

        @Override
        public String getName() {
            return part.getName();
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return part.getInputStream();
        }

        @Override
        public String getContentType() {
            return part.getContentType();
        }
    }
}