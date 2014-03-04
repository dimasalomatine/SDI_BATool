// image presentation script, used by [[תבנית:מצגת]]
// written by [[user:yonidebest]]

$(function() {
window.toggleImagePresentation = function (id, info)
{
	// where:
	// info = -1 is move image down
	// info = 0 is move image up
	// info > 0 is set image number
	var imgToggleWrapper = document.getElementById("imgToggleWrapper" + id);
	var selected = eval(imgToggleWrapper.selected);
	
	// hide current image and enable link
	var currentNumberedBox = document.getElementById("imgToggleBoxID" + id + "B" + selected);
	currentNumberedBox.style.display = "none";
	var currentNumberedLink = document.getElementById("numberedLinkID" + id + "L" + selected);
	currentNumberedLink.href = "javascript:toggleImagePresentation(" + id + "," + selected + ")";
	currentNumberedLink.disabled = false;
	
	// show target image and disable link
	var targetNumber = (info == 0) ? selected + 1 : ((info == -1) ? selected - 1 : info);
	var targetNumberedBox = document.getElementById("imgToggleBoxID" + id + "B" + targetNumber);
	targetNumberedBox.style.display = "block";
	var targetNumberedLink = document.getElementById("numberedLinkID" + id + "L" + targetNumber);
	targetNumberedLink.removeAttribute("href");
	targetNumberedLink.disabled = true;

	// update up/down links
	var toggleDownLink = document.getElementById("toggleDownID" + id);
	var toggleUpLink = document.getElementById("toggleUpID" + id);
	
	if (targetNumber == imgToggleWrapper.max) // disable up
	{
		toggleUpLink.removeAttribute("href");
		toggleUpLink.disabled = true;
	}
	if (targetNumber == 1) // disable down
	{
		toggleDownLink.removeAttribute("href");
		toggleDownLink.disabled = true;
	}
	if (selected == 1) // enable down
	{
		toggleDownLink.href = "javascript:toggleImagePresentation(" + id + ",-1)";
		toggleDownLink.disabled = false;
	}
	if (selected == imgToggleWrapper.max) // enable up
	{
		toggleUpLink.href = "javascript:toggleImagePresentation(" + id + ",0)";
		toggleUpLink.disabled = false;
	}

	// update selected number
	imgToggleWrapper.selected = targetNumber;
}

function imgPresentation()
{

    var imgToggleWrapper = mw.util.$content.find('div.imgToggleWrapper');
    if (!imgToggleWrapper.length) return;

 if (getParamValue("printable") == "yes") return;

	// for each wrapper
    for (var i = 0; i < imgToggleWrapper.length; i++)
    {
		imgToggleWrapper[i].id = "imgToggleWrapper" + i;
		
        var images = getElementsByClassName(imgToggleWrapper[i], "div", "imgToggleBox" );
        if (images.length == 1) return; // must have at least one box (first box is always empty, used for links
		
		imgToggleWrapper[i].max = images.length - 1;
		
		var linksWrapper = images[0];
		imgToggleWrapper[i].selected = linksWrapper.innerHTML;
		linksWrapper.innerHTML = "<b>מצגת:</b>  ";
		linksWrapper.style.display = "block";
		
		// create down link
		var toggleDown = document.createElement("a");
		toggleDown.id = "toggleDownID" + i;
		toggleDown.style.textDecoration = "none";
		if (imgToggleWrapper[i].selected == 1)
			toggleDown.disabled = true;
		else
			toggleDown.href = "javascript:toggleImagePresentation(" + i + ",-1)"; // id, down one image
		toggleDown.appendChild(document.createTextNode('<'));
		linksWrapper.appendChild(toggleDown);
		linksWrapper.appendChild(document.createTextNode('  '));

		// create numbered links and attach ids to images boxes
		for (var j = 1 ; j < images.length ; j++ )
		{
			// attach ids to images boxes and hide all unselected images
			images[j].id = "imgToggleBoxID" + i + "B" + j;
			if (imgToggleWrapper[i].selected != j)
				images[j].style.display = "none";
			
			// create numbered links
			var numberedLink = document.createElement("a");
			numberedLink.id = "numberedLinkID" + i + "L" + j;
			numberedLink.style.textDecoration = "none";
			if (imgToggleWrapper[i].selected == j)
				numberedLink.disabled = true;
			else
				numberedLink.href = "javascript:toggleImagePresentation(" + i + "," + j + ")"; // id, image number
			numberedLink.appendChild(document.createTextNode(j));
			linksWrapper.appendChild(numberedLink);
			linksWrapper.appendChild(document.createTextNode('  '));
		}
		
		// create up link
		var toggleUp = document.createElement("a");
		toggleUp.id = "toggleUpID" + i;
		toggleUp.style.textDecoration = "none";
		if (imgToggleWrapper[i].selected == imgToggleWrapper[i].max)
			toggleUp.disabled = true;
		else
			toggleUp.href = "javascript:toggleImagePresentation(" + i + ",0)"; // id, up one image
		toggleUp.appendChild(document.createTextNode('>'));
		linksWrapper.appendChild(toggleUp);
    }
}
mw.loader.using('mediawiki.util', imgPresentation);
});