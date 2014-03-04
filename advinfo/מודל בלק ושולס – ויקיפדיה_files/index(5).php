/** Collapsible tables *********************************************************
 *
 *  Description: Allows tables to be collapsed - using $.makeCollapsible
 */

var autoCollapse = 2;
$(document).ready(function(){
	mw.loader.using('jquery.makeCollapsible',function(){
		$('table.collapsed').addClass('mw-collapsed').makeCollapsible();
		if(($('table.collapsible').length + $('table.mw-collapsible').length)>autoCollapse){
			$('table.collapsible.autocollapse').addClass('mw-collapsed').makeCollapsible();
		}
		$('table.collapsible').makeCollapsible();
	});
});