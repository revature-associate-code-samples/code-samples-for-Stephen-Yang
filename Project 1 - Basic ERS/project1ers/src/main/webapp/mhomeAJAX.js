function showStatMenu() {
  document.getElementById("moreSpan").innerHTML =
  `
  <select id="statMenu">
    <option value="pending" onclick="showPendTable()">Pending</option>
    <option value="resolved" onclick="showResTable()">Resolved</option>
  </select>
  `;
}

function showEmpTable() {
  let xhr = new XMLHttpRequest();
  xhr.open("GET", "view"); // get won't send a body!
  xhr.send();
  xhr.onreadystatechange = function () {
    console.log(
      `http status: ${xhr.status} - ${xhr.statusText}
      ready state: ${xhr.readyState}
      response:  ${xhr.responseText}`
    );
    document.getElementById("tableDiv").innerHTML = 
    `
    <table class="table table-bordered table-hover">
      <thead>
        <tr>
          <th>Username</th>
          <th>Email</th>
          <th>Action</th>
        </tr>
      </thead>
      <tbody id="eviewTB">
      </tbody>
    </table>
    `;
    // build table with for loop
    let eList = JSON.parse(xhr.response); // an array - parse here or in loop? or not at all?
    eList.forEach(emp => {
      let row = document.createElement('tr');
      document.getElementById('eviewTB').append(row);
      let empTD = document.createElement('td');
      row.append(empTD);
      empTD.innerHTML = emp.username;
      let emailTD = document.createElement('td');
      row.append(emailTD);
      emailTD.innerHTML = emp.email;
      let actTD = document.createElement('td');
      row.append(actTD);
      actTD.innerHTML = 
      `
      <button class="btn btn-info" onclick=showIndTable('${emp.username}')>
        View Requests
      </button>
      `;
    });
  }
  
}

function showIndTable(ename) {
  let xhr = new XMLHttpRequest();
  xhr.open("POST", "view"); // get won't send a body!
  xhr.send(ename);
  xhr.onreadystatechange = function () {
    console.log(
      `http status: ${xhr.status} - ${xhr.statusText}
      ready state: ${xhr.readyState}
      response:  ${xhr.responseText}`
    );
    document.getElementById('tableDiv').innerHTML = 
      `
      <h4>Requests From ${ename}</h4>
      <table class="table table-bordered table-hover">
        <thead>
          <tr>
            <th>Request ID</th>
            <th>Amount</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody id="indTB">
        </tbody>
      </table>
      `;

    let rList = JSON.parse(xhr.response); 
    rList.forEach(req => {
      let row = document.createElement('tr');
      document.getElementById('indTB').append(row);
      let idTD = document.createElement('td');
      row.append(idTD);
      idTD.innerHTML = req.id;
      let amtTD = document.createElement('td');
      row.append(amtTD);
      amtTD.innerHTML = req.amount;
      let statTD = document.createElement('td');
      row.append(statTD);
      switch (req.status) {
        case 0: statTD.innerHTML = "Pending"; break;
        case 1: statTD.innerHTML = "Approved"; break;
        case -1: statTD.innerHTML = "Denied"; break;
      } 
    });
  }

}

function showPendTable() {
  let xhr = new XMLHttpRequest();
  xhr.open("POST", "view"); // get won't send a body!
  xhr.send("all pending");
  xhr.onreadystatechange = function () {
    console.log(
      `http status: ${xhr.status} - ${xhr.statusText}
      ready state: ${xhr.readyState}
      response:  ${xhr.responseText}`
    );
    document.getElementById("tableDiv").innerHTML = 
    `
    <table id="pendTable" class="table table-bordered table-hover">
      <thead>
        <tr>
          <th>Request ID</th>
          <th>Employee</th>
          <th>Amount</th>
          <th>Action</th>
        </tr>
      </thead>
      <tbody id="pendTB">
      </tbody>
    </table>
    `;
    // build table with for loop
    let rList = JSON.parse(xhr.response); // an array - parse here or in loop? or not at all?
    rList.forEach(req => {
      let row = document.createElement('tr');
      document.getElementById('pendTB').append(row);
      let idTD = document.createElement('td');
      row.append(idTD);
      idTD.innerHTML = req.id;
      let empTD = document.createElement('td');
      row.append(empTD);
      empTD.innerHTML = req.empname;
      let amtTD = document.createElement('td');
      row.append(amtTD);
      amtTD.innerHTML = req.amount;
      let actTD = document.createElement('td');
      row.append(actTD);
      actTD.setAttribute('id',req.id+'TD');
      actTD.innerHTML = 
        `
        <button class="btn btn-success" onclick="resolve('a ${req.id}')">Approve</button>  
        <button class="btn btn-danger" onclick="resolve('d ${req.id}')">Deny</button>
        `;
    });
  }

}

function showResTable() {
    let xhr = new XMLHttpRequest();
    xhr.open("POST", "view"); // get won't send a body!
    xhr.send("all resolved");
    xhr.onreadystatechange = function () {
    console.log(
      `http status: ${xhr.status} - ${xhr.statusText}
      ready state: ${xhr.readyState}
      response:  ${xhr.responseText}`
    );
    document.getElementById("tableDiv").innerHTML = 
    `
    <table id="resTable" class="table table-bordered table-hover">
      <thead>
        <tr>
          <th>Request ID</th>
          <th>Employee</th>
          <th>Amount</th>
          <th>Resolution</th>
          <th>Resolved By</th>
        </tr>
      </thead>
      <tbody id="resTB">
      </tbody>
    </table>  
    `;
    // build table with for loop
    let rList = JSON.parse(xhr.response); // an array - parse here or in loop? or not at all?
    rList.forEach(req => {
      let row = document.createElement('tr');
      document.getElementById('resTB').append(row);
      let idTD = document.createElement('td');
      row.append(idTD);
      idTD.innerHTML = req.id;
      let empTD = document.createElement('td');
      row.append(empTD);
      empTD.innerHTML = req.empname;
      let amtTD = document.createElement('td');
      row.append(amtTD);
      amtTD.innerHTML = req.amount;
      let statTD = document.createElement('td');
      row.append(statTD);
      if (req.status == 1) { 
        statTD.innerHTML = "Approved";
      } else {
        statTD.innerHTML = "Denied";
      }
      let byTD = document.createElement('td');
      row.append(byTD);
      byTD.innerHTML = req.mName;
    });
  }
}

function resolve(str) {
  let xhr = new XMLHttpRequest();
  xhr.open("POST", "new-req"); // get won't send a body!
  xhr.send(str);
  xhr.onreadystatechange = function () {
    console.log(
      `http status: ${xhr.status} - ${xhr.statusText}
      ready state: ${xhr.readyState}
      response:  ${xhr.responseText}`
    );
    if (xhr.readyState == 4 && xhr.status >= 200 && xhr.status < 300) {
      document.getElementById(str.substring(2)+'TD').innerHTML = "Resolved";
    }
  }
}