document.getElementById('reqsBtn').onclick = () => { 
  let xhr = new XMLHttpRequest();
  xhr.open("GET", "emp-reqs-0"); 
  xhr.send();
  xhr.onreadystatechange = function () {
    console.log(
      `http status: ${xhr.status} - ${xhr.statusText}
      ready state: ${xhr.readyState}
      response:  ${xhr.responseText}`
    )
  }
}