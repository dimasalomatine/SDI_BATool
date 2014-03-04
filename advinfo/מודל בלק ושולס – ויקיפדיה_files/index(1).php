/* שינוי קישורי ההעלאה: קישור אחד לדף הסבר (שממנו יש קישורים לדפים הרלוונטיים), וקישור ישיר לוויקישיתוף */
$(document).ready(function(){
  var commonsLink = "//commons.wikimedia.org/w/index.php?title=Special:UploadWizard&uselang=he";
  if (typeof(customCommonsLink) != 'undefined') commonsLink = customCommonsLink;
  try {
    if ( $('#t-upload').length )
        $('#t-upload').html('<a href="'+mw.util.wikiGetlink('ויקיפדיה:העלאת_קובץ_לשרת')+'">' + ( wgUserLanguage == "he" || wgUserLanguage == "fairuse" ? 'העלאת קובץ לשרת' : 'Upload file to server' ) + '</a>' +
            ' / <a id="commonsLink" href="' + commonsLink + '">' + ( wgUserLanguage == "he" || wgUserLanguage == "fairuse" ? 'לוויקישיתוף' : "to commons" ) + '</a>');
  }
  catch(e)
  {
    return;// lets just ignore what's happened
  }
});