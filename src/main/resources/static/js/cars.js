'use strict';

document.addEventListener("DOMContentLoaded", ()=>ready());

function ready() {
	searchbtn.addEventListener('click', ()=>{
		let val = search.value;
		let xhr = new XMLHttpRequest();
		xhr.open('POST', '/cars/ajax-filter', true);
		
		xhr.addEventListener('readystatechange', ajaxFilterCallback.bind(xhr) );
		
		xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded')
		xhr.send('q=' + val);
		
	});
}


function ajaxFilterCallback() {
	let xhr = this;
	if (xhr.readyState !== 4) {
		return;
	}
	
	if (xhr.status === 200) {
		let data = xhr.responseText;
		
		// TODO: clear table data
		if (data.length > 0) {
			try {
				let dataJson = JSON.parse(data);
				console.log(dataJson);
				// TODO: fill table with data
			} catch(e) {
				
			} 
		}				
		
	} else {
		alert('Ajax error appear');
	}
}