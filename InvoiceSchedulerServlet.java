/*+  File NAME 	: InvoiceSchedulerServlet.java

+  PURPOSE		: 
+  CREATED BY	: 
+  CREATION DATE	: 
+  INITIAL VERSION : 1.0.0.0
+  **********************************************************************************************************************************
+  VERSION NO   UPDATED BY       	UPDATED ON      REASON FOR CHANGE
+  
   1.0.0.1		Ravi Kumar   		18-Jan-2021		Code changes related to securityDeposit section 
   1.0.0.2		Ravi Shankar		27-Jun-2025		EaseBuzz QR code 
   1.0.0.3      Narottam Biswal     05-Aug-2025     Easebuzz status API integration
   1.0.0.4      Sanchi Agarwal      1-Oct-2026      Implemented Easebuzz schedulers in JIO folder
 **********************************************************************************************************************************
*/
package qc.batchUpload.controller.action.servlet;


import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import qc.batchUpload.controller.action.InvoiceDeleteScheduler;
import qc.batchUpload.controller.action.InvoiceGenerateScheduler;
import qc.batchUpload.controller.action.PoReportDeleteScheduler;
import qc.common.bean.CommonBean;
import qc.common.servlet.DebitCreditScheduler;
import qc.common.servlet.EInvoiceCancelationScheduler;
import qc.common.servlet.EInvoicingScheduler;
import qc.common.servlet.ESalesInvoiceScheduler;
import qc.common.servlet.EasebuzzInvoiceSchedulerINV;
import qc.common.servlet.EasebuzzStatusApiSchedular;
import qc.common.servlet.PaymentStatusScheduler;
//import org.quartz.StdSchedulerFactory;


public class InvoiceSchedulerServlet extends HttpServlet

{

	protected static Logger log = Logger.getLogger(InvoiceSchedulerServlet.class);
	
	public void init(ServletConfig config) throws ServletException
    {
		super.init(config);
		//1.0.0.2 start
		Locale locale = new Locale("en", "US");
		ResourceBundle applicationResource = ResourceBundle.getBundle("ApplicationResource", locale);
		EasebuzzInvoiceSchedulerINV easebuzzInvoiceSchedulerINV = new EasebuzzInvoiceSchedulerINV();
		Timer easebuzzInvoiceSchedulerTimer = new Timer();
		Integer intervalInMin=Integer.parseInt(applicationResource.getString("EASEBUZZ_INVOICE_SCHEDULER_INTERVAL"));
		easebuzzInvoiceSchedulerTimer.schedule(easebuzzInvoiceSchedulerINV, 1000l, intervalInMin * 60 * 1000);
		//1.0.0.2 end
		
		//start 1.0.0.3
		CommonBean commonBean = new CommonBean();
		Map applicationParameterMap = new HashMap();
		Map dbConnectionMapLLM = CommonBean.getDBConnectionMap("dbConnection/DBConnectionMapLLM", locale);
		applicationParameterMap = commonBean.getApplicationParameter(dbConnectionMapLLM);
		String easebuzzStatSchedulTime=(String)(applicationParameterMap.get("easebuzzStatusScheduleTimer"));//"0 0 6 1/1 * ? *";
		log.info("easebuzz status schedule time : "+easebuzzStatSchedulTime);
		//long easebuzzStatusSchedularTime = 0;
		
		//String easebuzzExpTime="0 37 18 1/1 * ? *";//18:25
	/*	 try {
			 easebuzzStatusSchedularTime = Long.parseLong(easebuzzStatSchedulTime.trim());
		 }catch (NumberFormatException e) {
			e.getMessage();
		} */
		 
		 
		 //cron trigger start
		 JobDetail job = JobBuilder.newJob(EasebuzzStatusApiSchedular.class)
	                .withIdentity("myJob", "group1")
	                .build();
		 
		 
		 Trigger trigger = TriggerBuilder.newTrigger()
	                .withIdentity("myTrigger", "group1")
	                .withSchedule(CronScheduleBuilder.cronSchedule(easebuzzStatSchedulTime))
	                .build();
		 
		 Scheduler scheduler;
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			  scheduler.start();
		      scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			log.info(e.getMessage());
			
		}
	      
		 
		 //cron trigger end
		//end 1.0.0.3
		
		ServletContext ctx = config.getServletContext();
		Map applictionConfig = (HashMap)ctx.getAttribute("bup_application_config");
		
		applictionConfig.put("reportPath", ctx.getAttribute("reportPath"));
		applictionConfig.put("invoiceBulkPrintPath", ctx.getAttribute("invoiceBulkPrintPath"));
				
		applictionConfig.put("customerPhoto", ctx.getAttribute("customerPhoto"));
		applictionConfig.put("po_reports_path", ctx.getAttribute("po_reports_path"));
		
		InvoiceGenerateScheduler invoice = new InvoiceGenerateScheduler(applictionConfig);
		Timer validationTimer = new Timer();
		validationTimer.schedule(invoice, 1000l, 3 * 60 * 1000);
		
		
		InvoiceDeleteScheduler invoiceDeleteScheduler = new InvoiceDeleteScheduler(applictionConfig);
		Timer validationDeleteTimer = new Timer();
		validationDeleteTimer.schedule(invoiceDeleteScheduler, 1000l, 5 * 60 * 1000);
		
		PoReportDeleteScheduler reportDeleteSchedular = new PoReportDeleteScheduler(applictionConfig);
		Timer reportDeleteSchedularTimer = new Timer();
		reportDeleteSchedularTimer.schedule(reportDeleteSchedular, 1000l, 1 * 60 * 1000);
		// 1.0.0.1 start
		PaymentStatusScheduler paymentStatusScheduler = new PaymentStatusScheduler();
		Timer paymentStatusSchedulerTimer = new Timer();
		reportDeleteSchedularTimer.schedule(paymentStatusScheduler, 1000l, 5 * 60 * 1000);
		// 1.0.0.1 end
		//sunny add start
        EInvoicingScheduler eInvoicingScheduler = new EInvoicingScheduler(applictionConfig);
		ExecutorService thread = Executors.newSingleThreadExecutor();
	    thread.submit(eInvoicingScheduler);
	    
	    ESalesInvoiceScheduler eSalesInvoiceScheduler = new ESalesInvoiceScheduler(applictionConfig);
		ExecutorService thread1 = Executors.newSingleThreadExecutor();
	    thread1.submit(eSalesInvoiceScheduler);
	    
	    EInvoiceCancelationScheduler eInvoiceCancelationScheduler = new EInvoiceCancelationScheduler(applictionConfig);
		ExecutorService thread2 = Executors.newSingleThreadExecutor();
	    thread2.submit(eInvoiceCancelationScheduler);
	    
	    DebitCreditScheduler debitCreditScheduler = new DebitCreditScheduler(applictionConfig);
		ExecutorService thread3 = Executors.newSingleThreadExecutor();
	    thread3.submit(debitCreditScheduler);
	    
		//sunny add end
	}
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws  IOException,ServletException
			{
				
			}
	
}
