/* הוספת קישור לדפי המשנה של הדף הנוכחי */
function subPagesLink()
{
  try {
    var uploadItem = document.getElementById( "t-upload" );
    if ( !uploadItem || document.getElementById("t-prefixindex") ) return;

    var link = document.createElement("a");
    link.appendChild ( document.createTextNode(wgUserLanguage == "he" ? "דפי משנה" : "Sub pages") );
    link.href = "/wiki/Special:PrefixIndex/" + encodeURIComponent(wgPageName).replace(/%2F/g,"/").replace(/%24/g,"$").replace(/%2C/g,",").replace(/%3A/g,":").replace(/%40/g,"@") + "/";
    link.title = wgUserLanguage == "he" ? "רשימת דפי המשנה של דף זה" : "List of the sub pages of this page";

    var item = document.createElement("li");
    item.id = "t-prefixindex";
    item.appendChild ( link );

    uploadItem.parentNode.insertBefore ( item, uploadItem );
  }
  catch(e)
  {
    return;      // lets just ignore what's happened
  }
}

if ( wgNamespaceNumber > 0 && wgNamespaceNumber != 6 && wgNamespaceNumber != 14 ) addOnloadHook ( subPagesLink );