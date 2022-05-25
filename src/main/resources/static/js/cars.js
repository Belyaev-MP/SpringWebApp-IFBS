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
                 fillTable(dataJson);
			} catch(e) {
				
			} 
		}				
		
	} else {
		alert('Ajax error appear');
	}
}

function fillTable(data) {
    document.querySelectorAll('#carList tr').forEach(function(el,i){
            if (!el.classList.contains('column-header')) {
                    el.remove();
            }
    });

    let table = document.getElementById('carList');

    data.forEach(function(el, i){
            let tr = document.createElement('tr');


            tr.appendChild(createCell(el.brand.name));
            tr.appendChild(createCell(el.model));
            tr.appendChild(createCell(el.productionDateFormat));

            table.appendChild(tr);
    });

}


function createCell(name) {
    let td = document.createElement('td');
    td.innerText = name;
    return td;
}
