 /* HEADER INFO
+  File NAME 	: KarzaVehicleRCSchedular.java
+  PURPOSE		: 
+  CREATED BY	: Sanchi Agarwal
+  CREATION DATE	: 05-Oct-2026
+  INITIAL VERSION : 1.0.0.0
+  **********************************************************************************************************************************
+  VERSION NO   UPDATED BY       	UPDATED ON      REASON FOR CHANGE
+  1.0.0.0      Sanchi Agarwal      05-Oct-2026     Initial Version - IBS Vehicle RC Authentication (branch 6793-ibs-vehicle-rc-authentication-advanced-2Jan2025)
   **********************************************************************************************************************************
 */

package qc.common.servlet;

import java.util.Locale;
import java.util.Map;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import qc.common.bean.CommonBean;
import qc.common.controller.action.IBSCallAction;

@SuppressWarnings("rawtypes")
public class KarzaVehicleRCSchedular extends TimerTask {

	protected static Logger log = Logger.getLogger(KarzaVehicleRCSchedular.class);
	static Locale locale = new Locale("en", "US");

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		log.info("KarzaVehicleRCSchedular - Start");
		try {
			Map dbConnectionMapLMS = CommonBean.getDBConnectionMap("dbConnection/DBConnectionMapLMS", locale);
			IBSCallAction ibsCallAction = new IBSCallAction();
			ibsCallAction.vehicleRCApiCall(dbConnectionMapLMS);
		} catch (Exception e) {
			log.error("KarzaVehicleRCSchedular - Exception: " + e.getMessage());
		}
		log.info("KarzaVehicleRCSchedular - End");
	}
}
