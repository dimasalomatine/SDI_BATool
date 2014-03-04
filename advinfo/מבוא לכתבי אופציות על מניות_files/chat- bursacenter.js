

// custom bursacenter
var vspirits_chat_client = "bursacenter";

/**
 * COMMON DHTML FUNCTIONS
 * These are handy functions I use all the time.
 *
 * By Seth Banks (webmaster at subimage dot com)
 * http://www.subimage.com/
 *
 * Up to date code can be found at http://www.subimage.com/dhtml/
 *
 * This code is free for you to use anywhere, just keep this comment block.
 */

/**
 * X-browser event handler attachment and detachment
 * TH: Switched first true to false per http://www.onlinetools.org/articles/unobtrusivejavascript/chapter4.html
 *
 * @argument obj - the object to attach event to
 * @argument evType - name of the event - DONT ADD "on", pass only "mouseover", etc
 * @argument fn - function to call
 */
function addEvent(obj, evType, fn){
 if (obj.addEventListener){
    obj.addEventListener(evType, fn, false);
    return true;
 } else if (obj.attachEvent){
    var r = obj.attachEvent("on"+evType, fn);
    return r;
 } else {
    return false;
 }
}

//function removeEvent(obj, evType, fn, useCapture){
//  if (obj.removeEventListener){
//    obj.removeEventListener(evType, fn, useCapture);
//    return true;
//  } else if (obj.detachEvent){
//    var r = obj.detachEvent("on"+evType, fn);
//    return r;
//  } else {
//    alert("Handler could not be removed");
//  }
//}



/**
 * Code below taken from - http://www.evolt.org/article/document_body_doctype_switching_and_more/17/30655/
 *
 * Modified 4/22/04 to work with Opera/Moz (by webmaster at subimage dot com)
 *
 * Gets the full width/height because it's different for most browsers.
 */
function getViewportHeight() {
	if (window.innerHeight!=window.undefined) return window.innerHeight;
	if (document.compatMode=='CSS1Compat') return document.documentElement.clientHeight;
	if (document.body) return document.body.clientHeight; 

	return window.undefined; 
}
function getViewportWidth() {
	var offset = 17;
	var width = null;
	if (window.innerWidth!=window.undefined) return window.innerWidth; 
	if (document.compatMode=='CSS1Compat') return document.documentElement.clientWidth; 
	if (document.body) return document.body.clientWidth; 
}

/**
 * Gets the real scroll top
 */
function getScrollTop() {
	if (self.pageYOffset) // all except Explorer
	{
		return self.pageYOffset;
	}
	else if (document.documentElement && document.documentElement.scrollTop)
		// Explorer 6 Strict
	{
		return document.documentElement.scrollTop;
	}
	else if (document.body) // all other Explorers
	{
		return document.body.scrollTop;
	}
}
function getScrollLeft() {
	if (self.pageXOffset) // all except Explorer
	{
		return self.pageXOffset;
	}
	else if (document.documentElement && document.documentElement.scrollLeft)
		// Explorer 6 Strict
	{
		return document.documentElement.scrollLeft;
	}
	else if (document.body) // all other Explorers
	{
		return document.body.scrollLeft;
	}
}

/**
 * SUBMODAL v1.6
 * Used for displaying DHTML only popups instead of using buggy modal windows.
 *
 * By Subimage LLC
 * http://www.subimage.com
 *
 * Contributions by:
 * 	Eric Angel - tab index code
 * 	Scott - hiding/showing selects for IE users
 *	Todd Huss - inserting modal dynamically and anchor classes
 *
 * Up to date code can be found at http://submodal.googlecode.com
 */

// Popup code
var gPopupMask = null;
var gPopupContainer = null;
var gReturnFunc;
var gPopupIsShown = false;
var gDefaultPage = "/loading.html";
var gHideSelects = false;
var gReturnVal = null;

var gTabIndexes = new Array();
// Pre-defined list of tags we want to disable/enable tabbing into
var gTabbableTags = new Array("A","BUTTON","TEXTAREA","INPUT","IFRAME");	

// If using Mozilla or Firefox, use Tab-key trap.
if (!document.all) {
	document.onkeypress = keyDownHandler;
}




/* virtual spirits */
var agentstatus;
var activepage=false;
var activeexit=false;
var activetimer=false;
var activebutton=false;
var proactiveMinTimer=30000; //custom bursacenter
var exitMinTimer=3000;
var title_param;
var referrer_param;
var url_param;
var timeInterval;
var loadTime = new Date();
var exitTime;
var chatTime;
var closeTime;
var posX = 0;
var posY = 200;
var browser;

function initAgent() {

var x = readCookie('vspirits_status');
if (x) {
	// a cookie is written if the user has been handled
	// the cookie is removed if the user has closed the chat screen and is pending
	agentstatus=x;
} else {
agentstatus="empty";
}

if (agentstatus=="-1") {
agentstatus="empty";
}

getchatparam();
var tempref=getdomain(referrer_param);
var tempurl=getdomain(url_param);
	
if (agentstatus=="empty"){	
	if (tempref=="") {
	//custom bursacenter referrer
	createCookie("vspirits_status","prospect",1); // write the cookie
	agentstatus="prospect";
	 } 	else {
		if (tempurl==tempref) {
	createCookie("vspirits_status","prospect",1); // write the cookie
	agentstatus="prospect";
		} else {
			//custom bursacenter referrer
			createCookie("vspirits_status","prospect",1); // write the cookie
			agentstatus="prospect";
		}
	}
}
	

if (agentstatus=="empty"){		
	// special demo pages
	if (tempurl.indexOf("virtualspirits.com")!=-1) {
	agentstatus="prospect";
	activepage=true;
	}
	if (tempurl.indexOf("localhost")!=-1) {
	agentstatus="prospect";
	activepage=true;
	}
}

filterpages();
setactive();

if (activeexit || activetimer || activebutton) {
	initPopup();
	if (activeexit) { initExit(); }
	if (activebutton) { showButton(); }
	if (activetimer) { setTimeout('proactivechat()',proactiveMinTimer); }
}
}

function setactive(){
if (activepage){
	if (agentstatus=="prospect"){
		 activeexit=true;
		 activetimer=true;
		 activebutton=true; // custom bursacenter
		 }
	if (agentstatus=="pending"){
		 activeexit=false;
		 activetimer=false;
		 activebutton=true;
	}
	if (agentstatus=="handled"){
		 activeexit=false;
		 activetimer=false;
		 activebutton=true; // custom bursacenter
	}
	if (agentstatus=="closed"){
		 activeexit=false;
		 activetimer=false;
		 activebutton=false;
	}
} else {
	activeexit=false;
	activetimer=false;
	activebutton=false;
	}
}

function initPopup(){
	// Add the HTML to the body
	theBody = document.getElementsByTagName('BODY')[0];
	popmask = document.createElement('div');
	popmask.id = 'popupMask';
	popcont = document.createElement('div');
	popcont.id = 'popupContainer';
	popcont.innerHTML = '' +
		'<div id="popupInner">' +
			'<div id="popupTitleBar">' +
				'<div id="popupTitle"></div>' +
				'<div id="popupControls">' +
					'<img src="http://www.virtualspirits.com/vsa/images/close-orange.png" onclick="setPending();hidePopWin(false);" id="popCloseBox" />' +
				'</div>' +
			'</div>' +
			'<div id="proactive"></div>' +
		'</div>';
	
	//custom bursacenter orange	
	popbutton = document.createElement('div');
	popbutton.id = 'popupButton';
	//popbutton.innerHTML = '<div id="openButton" align="right" style="border:0px solid #000000;position: fixed; bottom: 5px;  left: 10px; _left: 10px; _position:absolute;  _top:expression(document.body.scrollTop+document.body.clientHeight-this.clientHeight-5);direction:ltr;">&nbsp;</div>';
	//custom bursacenter for IE issues
	//custom bursacenter z-index:900;
    popbutton.innerHTML = '<div id="openButton" align="right" style="z-index:900;border:0px solid #000000;position: fixed; top: 5px;  left: 10px; _position:absolute; direction:ltr;">&nbsp;</div>';
	
	theBody.appendChild(popmask);
	theBody.appendChild(popcont);
	theBody.appendChild(popbutton);
	
	gPopupMask = document.getElementById("popupMask");
	gPopupContainer = document.getElementById("popupContainer");
	
	// check to see if this is IE version 6 or lower. hide select boxes if so
	// maybe they'll fix this in version 7?
	var brsVersion = parseInt(window.navigator.appVersion.charAt(0), 10);
	if (brsVersion <= 6 && window.navigator.userAgent.indexOf("MSIE") > -1) {
		// gHideSelects = true;
	}

//alert("initPopup");
}


getbrowser();
addEvent(window, "load", initAgent);

function initExit(){
document.onmousemove = function(evt) {
if (typeof evt == 'undefined') {
myEvent = window.event; } 
else {
myEvent = evt; }

if (browser=="MSIE"){
posX = myEvent.clientX;
posY = myEvent.clientY; 
}
else{
posX = myEvent.pageX-window.pageXOffset;
posY = myEvent.pageY-window.pageYOffset;
}

//document.getElementById("openButton").innerHTML = "mouse: " +posY;

// first attempt
if(posY < 15) { 
//alert("mouse" +posY);
exitchat(); }
}
}

// second attempt if mouse moved too quickly
// window.onbeforeunload = function(){
// if (posY < 15) // button is clicked outside the page
//	{ 
//		alert("mouse" +posY);
//		//call your logout page
//		return exitchat();
//		}
//	} 
// }


function exitchat(){
	if (activeexit==true) {
	exitTime = new Date();
	timeInterval=exitTime-loadTime;
	if (timeInterval>exitMinTimer){
	//call your logout page
	activeexit=false;
	startchat("exit");
	if(confirm('נציג האתר ממתין לתת לך מידע נוסף והצעה')){
		//continue
		}
	else { 
	setPending();
	hidePopWin(false);
		}
	}
	}
}


function proactivechat(){
if (activetimer==true) {
	startchat("timer");
	}
}

function buttonchat(){
	startchat("button");
}


function setPending(){
closeTime = new Date();
timeInterval=closeTime-chatTime;
if (timeInterval<45000){
// time interval too short for real interaction, follow this visitor
createCookie("vspirits_status","pending",1); // write the cookie
agentstatus="pending";
setactive();
if (activebutton) { showButton(); }
}
}

function setClosed(){
hideButton(); // hide the button chat
createCookie("vspirits_status","closed",1); // write the cookie
agentstatus="closed";
setactive();
}

function showButton(){
var buttonstr;
buttonstr = '' +
		'<a href="#" onclick="buttonchat();return false;"><img src="http://www.virtualspirits.com/vsa/images/chat-combi-orange.jpg" border="0" /></a>' +
		'<span style="border:0px solid #000000;position: absolute; top:0px; right:0px; width: 30px; height: 30px;"><a href="#" onclick="setClosed();return false;" style="text-decoration:none">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></span>' +
		'<span style="position: absolute; top:125px; _top:130px; left:35px;">' +
		'<img border="0" align="absmiddle" alt="" src="http://www.virtualspirits.com/vsa/images/blink.gif" /> ' +
		'<img border="0" align="absmiddle" alt="" src="http://www.virtualspirits.com/vsa/images/button-text.gif" />' +
		'</span>';
//custom bursacenter combi
document.getElementById("openButton").innerHTML = buttonstr;
}

function showButtonTimer(){
var buttonstr;
buttonstr = '' +
		'<img src="http://www.virtualspirits.com/vsa/images/chat-combi-orange.jpg" border="0" />' +
		'<span style="position: absolute; top:120px; _top:125px; right:10px;">' +
		'<img border="0" align="absmiddle" alt="" width="25" height="25" src="http://www.virtualspirits.com/vsa/images/progress.gif" /> ' +
		'</span>';
//custom bursacenter combi
document.getElementById("openButton").innerHTML = buttonstr;
}

function hideButton(){
document.getElementById("openButton").innerHTML = "&nbsp;";
}

function getchatparam(){
title_param = encodeURI(document.title);
referrer_param = document.referrer;
url_param=location.href;
url_param=url_param.toLowerCase();
}

function filterpages(){
tempstr=url_param.replace("http://","");
tempstr=tempstr.replace("www.","");

// custom bursacenter
// this allows the chat only on the following pages
// temporarily on this page
if (tempstr.indexOf("courses")!=-1) { activepage=true;} // all the courses pages
if (tempstr.indexOf("contactformcourses")!=-1) { activepage=false;} // contact form

}


function getdomain(url){
if (url!=""){ 
	url=url.split(/\/+/g)[1];
	}
return url;
}

function startchat(launcher){
chatTime = new Date();
timeInterval=chatTime-loadTime;
try {
showButtonTimer(); // show the button chat as timer
createCookie("vspirits_status","handled",1); // write the cookie
agentstatus="handled";
setactive();
if (launcher=="exit"){
// exit chat
showPopWin("http://www.virtualspirits.com/vsa/"+vspirits_chat_client+"vsahebrew.aspx?launcher="+launcher+"&launchertime="+timeInterval+"&referrer="+referrer_param+"&title="+title_param, 500, 450, null, true);
} else {
// timed chat
showPopWin("http://www.virtualspirits.com/vsa/"+vspirits_chat_client+"vsahebrew.aspx?launcher="+launcher+"&launchertime="+timeInterval+"&referrer="+referrer_param+"&title="+title_param, 500, 450, null, false);
}
} catch (e) {
// nothing
//alert(e);
}	
}


 /**
	* @argument width - int in pixels
	* @argument height - int in pixels
	* @argument url - url to display
	* @argument returnFunc - function to call when returning true from the window.
	* @argument showOverlay - show the body overlay
		*/
function showPopWin(url, width, height, returnFunc, showOverlay) {
	// show or hide the window close widget
	document.getElementById("popCloseBox").style.display = "none";
	
	gPopupIsShown = true;
	disableTabIndexes();
	
	if (showOverlay) {
	gPopupMask.style.display = "block";
	}
	
	gPopupContainer.style.display = "block";
	// calculate where to place the window on screen
	centerPopWin(width, height);
	setMaskSize();
	document.getElementById("proactive").innerHTML = "<iframe src="+ url + " style=\"width:1;height:1;background-color:transparent;\" scrolling=\"no\" frameborder=\"0\"  id='iframeContent' name='iframeContent"+Math.round(Math.random()*1000)+"' allowtransparency=\"true\" id=\"popupFrame\" name=\"popupFrame\" width='1' height='1' onload='custom_showIframe();'></iframe>"
	
	// for IE
	if (gHideSelects == true) {
		hideSelectBoxes();
	}

}

//
var gi = 0;
function centerPopWin(width, height) {
	if (gPopupIsShown == true) {
		if (width == null || isNaN(width)) {
			width = gPopupContainer.offsetWidth;
		}
		if (height == null) {
			height = gPopupContainer.offsetHeight;
		}
		
		//var theBody = document.documentElement;
		var theBody = document.getElementsByTagName("BODY")[0];
		//theBody.style.overflow = "hidden";
		var scTop = parseInt(getScrollTop(),10);
		var scLeft = parseInt(theBody.scrollLeft,10);
	
		setMaskSize();
		
		//window.status = gPopupMask.style.top + " " + gPopupMask.style.left + " " + gi++;
		
		var titleBarHeight = parseInt(document.getElementById("popupTitleBar").offsetHeight, 10);
		
		var fullHeight = getViewportHeight();
		var fullWidth = getViewportWidth();
		
		
		// center of page but top of client never above page, in order to always be able to close it
		var toptemp = scTop + ((fullHeight - (height+titleBarHeight)) / 2);
		if (toptemp<scTop) {toptemp=scTop;} // in order to always be able to close it - small browser
		gPopupContainer.style.top = toptemp + "px";
		gPopupContainer.style.left =  (scLeft + ((fullWidth - width) / 2)) + "px";
				
		// top left
		//gPopupContainer.style.top = (scTop + 10) + "px";
		//gPopupContainer.style.left =  (scLeft + 10) + "px";
				
		//alert(fullWidth + " " + width + " " + gPopupContainer.style.left);
	}
}
addEvent(window, "resize", centerPopWin);
addEvent(window, "scroll", centerPopWin);
window.onscroll = centerPopWin;


/**
 * Sets the size of the popup mask.
 *
 */
function setMaskSize() {
	var theBody = document.getElementsByTagName("BODY")[0];
	var fullHeight = getViewportHeight();
	var fullWidth = getViewportWidth();
	
	// Determine what's bigger, scrollHeight or fullHeight / width
	if (fullHeight > theBody.scrollHeight) {
		popHeight = fullHeight;
	} else {
		popHeight = theBody.scrollHeight;
	}
	
	if (fullWidth > theBody.scrollWidth) {
		popWidth = fullWidth;
	} else {
		popWidth = theBody.scrollWidth;
	}
	
	gPopupMask.style.height = popHeight + "px";
	gPopupMask.style.width = popWidth + "px";
}

/**
 * @argument callReturnFunc - bool - determines if we call the return function specified
 * @argument returnVal - anything - return value 
 */
function hidePopWin(callReturnFunc) {
	gPopupIsShown = false;
	document.getElementById("proactive").innerHTML = "&nbsp;";
	var theBody = document.getElementsByTagName("BODY")[0];
	theBody.style.overflow = "";
	restoreTabIndexes();
	if (gPopupMask == null) {
		return;
	}
	gPopupMask.style.display = "none";
	gPopupContainer.style.display = "none";
	if (callReturnFunc == true && gReturnFunc != null) {
		// Set the return code to run in a timeout.
		// Was having issues using with an Ajax.Request();
		gReturnVal = window.frames["popupFrame"].returnVal;
		window.setTimeout('gReturnFunc(gReturnVal);', 1);
	}

	// display all select boxes
	if (gHideSelects == true) {
		displaySelectBoxes();
	}
}


// Tab key trap. iff popup is shown and key was [TAB], suppress it.
// @argument e - event - keyboard event that caused this function to be called.
function keyDownHandler(e) {
    if (gPopupIsShown && e.keyCode == 9)  return false;
}

// For IE.  Go through predefined tags and disable tabbing into them.
function disableTabIndexes() {
	if (document.all) {
		var i = 0;
		for (var j = 0; j < gTabbableTags.length; j++) {
			var tagElements = document.getElementsByTagName(gTabbableTags[j]);
			for (var k = 0 ; k < tagElements.length; k++) {
				gTabIndexes[i] = tagElements[k].tabIndex;
				tagElements[k].tabIndex="-1";
				i++;
			}
		}
	}
}

// For IE. Restore tab-indexes.
function restoreTabIndexes() {
	if (document.all) {
		var i = 0;
		for (var j = 0; j < gTabbableTags.length; j++) {
			var tagElements = document.getElementsByTagName(gTabbableTags[j]);
			for (var k = 0 ; k < tagElements.length; k++) {
				tagElements[k].tabIndex = gTabIndexes[i];
				tagElements[k].tabEnabled = true;
				i++;
			}
		}
	}
}


/**
 * Hides all drop down form select boxes on the screen so they do not appear above the mask layer.
 * IE has a problem with wanted select form tags to always be the topmost z-index or layer
 *
 * Thanks for the code Scott!
 */
function hideSelectBoxes() {
  var x = document.getElementsByTagName("SELECT");

  for (i=0;x && i < x.length; i++) {
    x[i].style.visibility = "hidden";
  }
}

/**
 * Makes all drop down form select boxes on the screen visible so they do not 
 * reappear after the dialog is closed.
 * 
 * IE has a problem with wanting select form tags to always be the 
 * topmost z-index or layer.
 */
function displaySelectBoxes() {
  var x = document.getElementsByTagName("SELECT");

  for (i=0;x && i < x.length; i++){
    x[i].style.visibility = "visible";
  }
}


//helper functions below
function custom_showIframe(){
	//jQuery('#proactive').show();//show window
	//proactive_position(); //position the window
	document.getElementById("iframeContent").style.display = "block";
	document.getElementById("iframeContent").style.width = "500px";
	document.getElementById("iframeContent").style.height = "450px";
	document.getElementById("popupTitleBar").style.width = "500px";
	document.getElementById("popCloseBox").style.display = "block";
	//document.getElementById("popupTitle").innerHTML = strTitle;
	centerPopWin();
	
	hideButton(); // hide the button
	
}

function createCookie(name,value,days) {
	if (days) {
		var date = new Date();
		date.setTime(date.getTime()+(days*24*60*60*1000));
		var expires = "; expires="+date.toGMTString();
	}
	else var expires = "";
	document.cookie = name+"="+value+expires+"; path=/";
}

function readCookie(name) {
	var nameEQ = name + "=";
	var ca = document.cookie.split(';');
	for(var i=0;i < ca.length;i++) {
		var c = ca[i];
		while (c.charAt(0)==' ') c = c.substring(1,c.length);
		if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
	}
	return null;
}

function eraseCookie(name) {
	createCookie(name,"",-1);
}


function getbrowser() {
if (navigator.userAgent.indexOf('MSIE') !=-1)
{
	browser="MSIE";
} else {
	if (navigator.userAgent.indexOf('Firefox') !=-1)
	{
	browser="FF";
	} else {
	browser="other";
	}
}
//alert(browser);
}
