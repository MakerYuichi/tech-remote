 /* HEADER INFO
+  File NAME 	: DeploymentConfigServlet.java
+  PURPOSE		: 
+  CREATED BY	: Sanchi Agarwal
+  CREATION DATE	: 05-Oct-2026
+  INITIAL VERSION : 1.0.0.0
+  **********************************************************************************************************************************
+  VERSION NO   UPDATED BY       	UPDATED ON      REASON FOR CHANGE
+  1.0.0.0      Sanchi Agarwal      05-Oct-2026     Initial Version - Vehicle RC Authentication scheduler (branch 6793-ibs-vehicle-rc-authentication-advanced-2Jan2025)
   **********************************************************************************************************************************
 */

package qc.common.servlet;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Timer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class DeploymentConfigServlet extends HttpServlet

{

	protected static Logger log = Logger.getLogger(DeploymentConfigServlet.class);
	
	public void init(ServletConfig config) throws ServletException
    {
		super.init(config);
		Locale locale = new Locale("en", "US");
		ResourceBundle applicationResource = ResourceBundle.getBundle("ApplicationResource", locale);
		
		// 1.0.0.0 Start - Vehicle RC Authentication Scheduler
		KarzaVehicleRCSchedular karzaVehicleRCSchedular = new KarzaVehicleRCSchedular();
		Timer vehicleRCSchedulerTimer = new Timer();
		Integer intervalInMin = Integer.parseInt(applicationResource.getString("VEHICLE_RC_SCHEDULER_INTERVAL"));
		vehicleRCSchedulerTimer.schedule(karzaVehicleRCSchedular, 1000l, intervalInMin * 60 * 1000);
		// 1.0.0.0 End - Vehicle RC Authentication Scheduler
		
	}
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException
			{
				
			}
	
}
