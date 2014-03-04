/* פונקציות כלליות */

/* פונקציה המוסיפה כפתור לסרגל הכלים */
function addEditButton( imageFile, tagOpen, sampleText, tagClose, speedTip ) {
   imageFile='//upload.wikimedia.org/'+imageFile;
   if ( typeof $ != 'undefined' && typeof $.fn.wikiEditor != 'undefined' ) {
      $( '#wpTextbox1' ).wikiEditor( 'addToToolbar', {
			'section': 'advanced',
			'group': 'insert',
			'tools': {
				'colouredtext': {
					label: speedTip ,
					type: 'button',
					icon: imageFile,
					action: {
						type: 'encapsulate',
						options: {
							pre: tagOpen,
							peri: sampleText,
							post: tagClose
						}
					}
				}
			}
		} );
}
   else{
 mwCustomEditButtons.push( {
        "imageFile": imageFile,
        "tagOpen": tagOpen,
        "sampleText": sampleText,
        "tagClose": tagClose,
        "speedTip": speedTip
    } );
}
}


/* פונקציה להוספת כפתור לאחד מסרגלי הכלים בממשק, מתוך [[:en:User:Omegatron/monobook.js/addlink.js]] */
function addLink(where, url, name, id, title, key, after) {
    // addLink() accepts either an id or a DOM node, addPortletLink() only takes a node
    if (after && !after.cloneNode)
        after = document.getElementById(after);

    return addPortletLink(where, url, name, id, title, key, after);
}