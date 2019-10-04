package demo.rc.web.servlets;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NonBlockingIOServlet extends AbstractServlet {

	private static final long serialVersionUID = 1L;
	
	public static final Logger logger = Logger.getLogger(NonBlockingIOServlet.class.getName());

	private static String HEAVY_RESOURCE = "This is non-blocking IO servlet, don't have to wait till i finish my work, you may continue...";

	@Override
	protected void processRequest(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {

		final ByteBuffer content = ByteBuffer.wrap(HEAVY_RESOURCE.getBytes(StandardCharsets.UTF_8));
		final AsyncContext async = request.startAsync();
		final ServletOutputStream out = response.getOutputStream();
		
		out.setWriteListener(new WriteListener() {
			public void onWritePossible() throws IOException {
				while (out.isReady()) {
					if (!content.hasRemaining()) {
						response.setStatus(200);
						async.complete();
						return;
					}
					out.write(content.get());
				}
			}
			
			public void onError(Throwable throwable) {
				
			}
		});
	}
}
