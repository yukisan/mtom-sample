package rst.sample.mtom.client.ws;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import org.apache.commons.io.IOUtils;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import rst.sample.mtom.jaxb.Document;
import rst.sample.mtom.jaxb.StoreDocumentRequest;
import rst.sample.mtom.jaxb.StoreDocumentResponse;

public class DocumentsClient extends WebServiceGatewaySupport {

	private static final String[] AUTHORS = new String[] {"Herbert", "Ernie", "Bibo", "Bert"};
	private Random random = new Random();

	
	public DocumentsClient() {
		setMessageSender(new ChunkedEncodingMessageSender());
	}

	public boolean storeDocument(int size) throws IOException {
		Document document = new Document();
		document.setContent(getContentAsByteArray(size));
		document.setAuthor(getAuthor());

		StoreDocumentRequest request = new StoreDocumentRequest();
		request.setDocument(document);

		System.out.println();
		System.out.println("Storing document of size " + size);

		StoreDocumentResponse response = (StoreDocumentResponse) getWebServiceTemplate()
				.marshalSendAndReceive(request);
		boolean success = response.isSuccess();
		System.out.println("success: " + true);
		return success;
	}

	private byte[] getContentAsByteArray(final int size) throws IOException {
		InputStream input = getContentAsStream(size);
		return IOUtils.toByteArray(input);
	}

	private InputStream getContentAsStream(final int size) {
		return new RandomSizeInputStream(size);
	}
	
	private String getAuthor() {
		return AUTHORS[random.nextInt(AUTHORS.length)];
	}

}
