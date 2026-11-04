/* #  HEADER INFO
#  File NAME      : checkSessionAvailability.js
#  PURPOSE        : Session availability check utility
#  INITIAL VERSION : 1.0.0.0
#  **********************************************************************************************************************************
#  VERSION NO      UPDATED BY          UPDATED ON      REASON FOR CHANGE
#  1.0.0.1         Sanchi Agarwal      03-Nov-2026     Added checkServerSession() and checkSessionStatus() for concurrent login session kill detection
#  **********************************************************************************************************************************
*/

var sessionStatus = "";
var setTimeOnLoad = "";

        function setTime()
        {
        	var time = new Date();
        	setTimeOnLoad = time.getTime();
        	
        }
        function checkinterval(intervalTime)
        {
        	
        	var currentTime = new Date();
        	var currTime =  currentTime.getTime();
        	var diifference =  (currTime-setTimeOnLoad)/1000;    
        	//alert("currTime  :"+currTime+" diifference :"+diifference + " intervalTime : " + intervalTime +" setTimeOnLoad : "+setTimeOnLoad);
        	if(Number(diifference)>=Number(intervalTime))
        	{
        	
        		return "N";
        	}
        	else
        	{
        		
        		return "Y";
        	}
        	
        }
        
        //1.0.0.1 Start By Sanchi Agarwal 03-Nov-2026 : Server-side session check for concurrent login session kill detection
        //checkinterval only detects session timeout (elapsed time), not session kill by concurrent login.
        //checkServerSession makes a synchronous AJAX call with isAjaxCalling=Y; the UserLockingFilter returns
        //{"Status":"SessionExpired"} if the session was killed by a login from another window.
        function checkServerSession()
        {
            var xmlHttp;
            if (window.XMLHttpRequest)
            {
                xmlHttp = new XMLHttpRequest();
            }
            else if (window.ActiveXObject)
            {
                xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            try
            {
                xmlHttp.open('GET', 'populateMaster.do?qString=&isAjaxCalling=Y', false);
                xmlHttp.send(null);
                if (xmlHttp.readyState == 4)
                {
                    if (xmlHttp.responseText != null && xmlHttp.responseText.indexOf("SessionExpired") >= 0)
                    {
                        return "N";
                    }
                }
            }
            catch(e)
            {
                // If AJAX call fails, fall back to time-based check result
            }
            return "Y";
        }
        
        //Combines the original time-based check (checkinterval) with the server-side session check (checkServerSession).
        //Use this instead of checkinterval to detect BOTH session timeout AND concurrent login session kill.
        function checkSessionStatus(intervalTime)
        {
            var timeStatus = checkinterval(intervalTime);
            if (timeStatus == "N")
            {
                return "N";
            }
            return checkServerSession();
        }
        //1.0.0.1 End
        
        /*Added by sakib hassan 14-NOV-2011 for chola CR */
        function disabledSaveLink()
		{
			if(document.getElementById("SaveExitLink"))
			{
				document.getElementById("SaveExitLink").disabled = true;
				document.getElementById("SaveExitLink").removeAttribute('href');
				document.getElementById("SaveExitLink").removeAttribute('onclick');
			}
			if(document.getElementById("SaveLink"))
			{
				document.getElementById("SaveLink").disabled = true;
				document.getElementById("SaveLink").removeAttribute('href');
				document.getElementById("SaveLink").removeAttribute('onclick');
			}
			if(document.getElementById("SaveAndContLink"))
			{
				document.getElementById("SaveAndContLink").disabled = true;
				document.getElementById("SaveAndContLink").removeAttribute('href');
				document.getElementById("SaveAndContLink").removeAttribute('onclick');
			}
		}
		/* End code For chola CR 14-NOV-2011*/
