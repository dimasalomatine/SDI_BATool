$(document).ready(function(){
	function addInterwikiAttr(spanClass,title)
	{
		$('span.'+spanClass).each(function(){
		var langName=this.id.replace('interwiki-'+spanClass+'-','');
		$('#p-lang').find('li').filter(function(){return $(this).hasClass('interwiki-'+langName)}).addClass(spanClass).attr('title',title)
		});
	}
addInterwikiAttr('FA','ערך מומלץ');
addInterwikiAttr('GA','ערך איכותי');
});