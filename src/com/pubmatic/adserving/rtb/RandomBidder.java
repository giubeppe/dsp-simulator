package com.pubmatic.adserving.rtb;

import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 * Servlet implementation class RandomBidder
 */
@WebServlet(name = "bidder", description = "A random bidder. Return just empty JSON after a random amount of time", urlPatterns = { "/bidder" })
public class RandomBidder extends HttpServlet {
	
	private static Logger logger = Logger.getAnonymousLogger();
	private static final double MAX_WAIT_IN_MS = 3000.0;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RandomBidder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		double maxWaitInMs;
		try { maxWaitInMs = Double.parseDouble(request.getParameter("maxwait")); } catch (Exception e) { maxWaitInMs = MAX_WAIT_IN_MS; }
		String reqId = request.getParameter("id");

		printAllParameters(request);
		
		long nowInMs = System.currentTimeMillis();
		long wait = (long) (maxWaitInMs*Math.random()); 
		try { Thread.sleep(wait); } catch (InterruptedException e) {}
		
//		System.out.println("waiting "+wait+" ms for request "+reqId);
		
		response.setContentType("text/json");
		Writer writer = response.getWriter();
		writer.write("{\"debug\": {\"delay\": "+wait+", \"req-id\": \""+reqId+"\", \"max-wait\": "+maxWaitInMs+", \"wait\": "+wait+", \"epoch\": "+nowInMs+"}}");
	}

	private void printAllParameters(HttpServletRequest request) {
		
		for (Entry<String, String[]> entry: request.getParameterMap().entrySet()) {
			String key = entry.getKey();
			String[] values = entry.getValue();
			
			logger.info("MANDINGO: '"+key+"' => {"+StringUtils.join(values,",")+"}");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
;	}

}
