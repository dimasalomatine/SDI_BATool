function setPage()
{
        var pageGroupID;
        var pageID;
        var url = document.location.href;
        var begin = url.indexOf("_s.") + 3;
        var end = url.indexOf("\/", begin);
        if(begin > -1 && end > begin)
                pageGroupID = url.substring(begin, end);

        begin = end + 1; 
        end = url.indexOf("?", begin);
        if(end == -1)
                end = url.indexOf("\/", begin);
        if(end == -1)
                end = url.length;
        if(begin > -1 && end > begin)
                pageID = url.substring(begin, end);

        document.category.pageGroupID.value = pageGroupID;
        document.category.pageID.value = pageID;
}

function goCategory(categoryID)
{
        setPage();
        document.category.categoryID.value = categoryID;
        document.category.submit();
}

function goContent(contentID, categoryID,action)
{
        setPage();
        document.category.action = action;
        document.category.categoryID.value = categoryID;
        document.category.contentID.value = contentID;
        document.category.submit();
}

function goPam(action, contentID, parentCategoryID, categoryID)
{
	document.intForm.action = action;
	document.intForm.contentID.value = contentID;
	document.intForm.categoryID.value = categoryID;
	document.intForm.parentCategoryID.value = parentCategoryID;
	document.intForm.submit();
}

function pageCategory(categoryID, pageNum)
{
	document.category.currentPage.value = pageNum;
	goCategory(categoryID);
}
