//הוספת תיבת משוב בסוף ערכים
$(document).ready(function(){
function defaultFeedbackTitle(){
var x=new Date();
var months=["ינואר","פברואר","מרץ","אפריל","מאי","יוני","יולי","אוגוסט","ספטמבר","אוקטובר","נובמבר","דצמבר"];
return 'משוב מ-'+x.getUTCDate() + ' ב'+months[x.getUTCMonth()]+' '+x.getUTCFullYear();
}
if (wgNamespaceNumber || wgAction!='view' || $('.diff').length || $('.redirectMsg').length || !wgArticleId || wgPageName == 'עמוד_ראשי') return;//articles only

var feedbackBox=$('<div class="feedbackWrapper"><div class="feedbackDiv"><div style="float:left;font-size:x-small;padding-left:1em;"><a href="'+mw.util.wikiGetlink('שיחה:'+wgTitle)+'">משובים קודמים</a></div>משוב על הערך<form id="commentbox" method="post" action="'+wgServer + wgScriptPath+'/index.php?section=new&action=edit&preview=yes&title='+mw.util.wikiUrlencode('שיחה:'+wgTitle)+'"><textarea name="wpTextbox1" rows="1" cols="80" id="feedbackTextArea" placeholder="כאן ניתן לכתוב משוב על הערך...">כאן ניתן לכתוב משוב על הערך...</textarea><div id="feedbackSubmit" style="display:none;"><input type="text" name="wpSummary" id="feedbackTitle" value="כותרת המשוב" placeholder="כותרת המשוב" maxlength="50" /><input type="submit" id="feedbackBtn" value="שליחה"/> | <a id="loadFeedbackHelp">הנחיות</a></div></form><div id="feedbackHelpDiv" style="display:none;">נקודות מומלצות להתייחסות: שלמות, אובייקטיביות, אמינות ורמת הכתיבה.<br />אין לכתוב פניות לנשוא הערך, משובים פוגעניים והשקפות אישיות על נושא הערך<br />הינכם מוזמנים לשפר את הערך על ידי לחיצה על "עריכה" בראש הדף בצד שמאל.<br />תודה ו<a href="'+mw.util.wikiGetlink('ויקיפדיה:ברוכים הבאים')+'">ברוכים הבאים</a> לוויקיפדיה!</div></div></div>');

$('#content').append(feedbackBox);
$('#loadFeedbackHelp').click(function(){$.ajax({
  url: wgServer + wgScriptPath+'/index.php?title='+mw.util.wikiUrlencode('תבנית:הוראות_למשוב')+'&action=render',
  success: function(data) {
    $('#feedbackHelpDiv').html(data);
  }
});});
var firstFocus=function(e){this.value="";$('#feedbackHelpDiv').show('slow');$(this).css('color','#000000');$(this).unbind(e);};
$('#feedbackTextArea').focus(firstFocus);
$('#feedbackTitle').focus(firstFocus);
var grow=function(){var rows=0;var c=this.cols;$.each(this.value.split('\n'),function(i,v){rows+=Math.floor(v.length/c)+1});this.rows=rows+2; this.style.height='auto';$('#feedbackSubmit').toggle(this.value.length>0);};
$('#feedbackTextArea').focus(grow);
$('#feedbackTextArea').blur(grow);
$('#feedbackTextArea').keyup(grow);
$('#commentbox').submit(function(){
if(!($('#feedbackTextArea').attr('value').indexOf('~~'+'~~')+1)) $('#feedbackTextArea').attr('value',$('#feedbackTextArea').attr('value')+'{{'+'משוב}} ~~'+'~~');//sign in the end
if($('#feedbackTitle').attr('value') == 'כותרת המשוב' || $('#feedbackTitle').attr('value') == '') $('#feedbackTitle').attr('value',defaultFeedbackTitle());//default title
return true;});
});