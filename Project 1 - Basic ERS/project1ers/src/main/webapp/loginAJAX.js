document.getElementById('loginBtn').onclick = () => {
  document.getElementById('message').innerHTML = "Processing.";
  let xhr = new XMLHttpRequest();
  let inputName = document.getElementById('user').value;
  let inputPW = document.getElementById('pw').value;
  let inputStr = inputName + " " + inputPW;
  xhr.open("POST", "emp-home"); 
  xhr.send(inputStr);
  xhr.onreadystatechange = function () {
    console.log(
      `http status: ${xhr.status} - ${xhr.statusText}
      ready state: ${xhr.readyState}
      response:  ${xhr.responseText}`
    );
    if (xhr.readyState == 2) {
      document.getElementById('message').innerHTML = "Processing..";
    }
    if (xhr.readyState == 3) {
      document.getElementById('message').innerHTML = "Processing...";
    }
    if (xhr.readyState == 4 && xhr.status >= 200 && xhr.status < 300) { // might need condition for responseText, which might need ContentType header 
      if (xhr.responseText.match("Login failed.")) {
        document.getElementById('message').innerHTML = xhr.responseText;
      } else {
        var emp = JSON.parse(xhr.response);
        //console.log(emp);
        document.getElementById('mainDiv').innerHTML =
          `
          <h3>Hello, ${emp.username}.</h3>
          <p id="email">Email: ${emp.email} 
            <button type="button" class="btn btn-warning" onclick="changeForm('email')">Change</button>
          </p>
          <p id="pw">
            <button type="button" class="btn btn-warning" onclick="changeForm('pw')">Change Password</button>
          </p>
          <hr>
          <p>
            <button type="button" class="btn btn-info" onclick="viewReqs('pending')">
              View Pending Requests</button>
            <button type="button" class="btn btn-info" onclick="viewReqs('resolved')">
              View Resolved Requests</button>
          </p>
          <p>
            Make New Request: <input type="number" id="inAmt" placeholder="amount">
            <button type="button" class="btn btn-success" onclick="newReq()">
              Submit</button> 
          </p>
          <div id="viewDiv" class="m-3 p-3"> 
          </div>
          <p id="manSpan" class="m-3 p-3">
          
          </p>
          <p>
          <form action action="logout" method="GET">
            <input type="submit" id="logoutBtn" value="Log Out" class="btn btn-danger">
          </form>
          </p>
          `;
            
        // use changeInfo for replacing email and password
        // later: if username can be changed - must be changed in req table as well
            // unless add new pk empID to emps table and as fk in reqs 

        // manager home button
        if (emp.level == 1) {
          document.getElementById('manSpan').innerHTML
          = `
            <form action="emp-btn" method="GET">
              <input type="submit" value="Manager Home" class="btn btn-primary">
            </form>
            `;
        }
      }
    }
  }

}

function viewReqs(stat) { 
  let xhr = new XMLHttpRequest();
  let statStr = ""+stat;
  console.log(statStr);
  xhr.open("POST", "view"); // get won't send a body!
  xhr.send(statStr);
  xhr.onreadystatechange = function () {
    console.log(
      `http status: ${xhr.status} - ${xhr.statusText}
      ready state: ${xhr.readyState}
      response:  ${xhr.responseText}`
    );
    document.getElementById('viewDiv').innerHTML = 
      `
      <table class="table table-bordered table-hover">
        <thead>
          <tr>
            <th>Request ID</th>
            <th>Amount</th>
            <th>Status</th>
          </tr>
        </thead>
        <tbody id="viewTB">
        </tbody>
      </table>
      `;
    // build table with for loop
    let rList = JSON.parse(xhr.response); // an array - parse here or in loop? or not at all?
    rList.forEach(req => {
      let row = document.createElement('tr');
      document.getElementById('viewTB').append(row);
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

function changeForm(info) {
  if (info == 'email') {
    document.getElementById('email').innerHTML = 
      `
      <input type=password id=currPW placeholder="password" required>
      <input type=email id=inMail placeholder="new email address" required>
      <button class="btn btn-warning" onclick="change('email')">Update</button>
      `;
  }
  if (info == 'pw') {
    document.getElementById('pw').innerHTML =
    `
    <input type=password id=currPW placeholder="current password" required>
    <input type=text id=newPW placeholder="new password" required>
    <button class="btn btn-warning" onclick="change('pass_word')">Update</button>
    `;
  }
  
}

function change(info) {
  let xhr = new XMLHttpRequest();
  let inPW = document.getElementById('currPW').value; 
  let inNew = '';
  if (info == 'email') {
    inNew = document.getElementById('inMail').value; 
  }
  if (info == 'pass_word') {
    inNew = document.getElementById('newPW').value;
  }
  let inStr = inPW+' '+info+' '+inNew;
  console.log("info name: "+info);
  console.log("newPW: "+inNew)
  xhr.open("POST", "emp-btn"); 
  xhr.send(inStr);
  xhr.onreadystatechange = function () {
    console.log(
      `http status: ${xhr.status} - ${xhr.statusText}
      ready state: ${xhr.readyState}
      response:  ${xhr.responseText}`
    );
    if (xhr.readyState == 4 && xhr.status >= 200 && xhr.status < 300) {
      if (xhr.responseText.match("Failed to update.")) { // set status to 404 - better
        document.getElementById('mainDiv').innerHTML = 
        `
        <h3>Invalid password. You will be logged out.</h3>
        <form action="logout" method="GET">
          <input type="submit" id="logoutBtn" class="btn btn-secondary" value="OK">
        </form>
        `
      } else {
          if (info == 'email') {
          document.getElementById('email').innerHTML = 
          `Email: ${xhr.responseText} <button class="btn btn-warning" onclick="changeForm('email')">Change</button>`;
        }
        if (info == 'pass_word') {
          document.getElementById('pw').innerHTML = 
          `Password Updated <button class="btn btn-warning" onclick="changeForm('pw')">Change Password</button>`;
        }
      }
      
    }
  }
}

function newReq() {
  let amt = document.getElementById('inAmt').value;
  let xhr = new XMLHttpRequest();
  xhr.open("POST","new-req");
  xhr.send(amt);
  xhr.onreadystatechange = function () {
    console.log(
      `http status: ${xhr.status} - ${xhr.statusText}
      ready state: ${xhr.readyState}
      response:  ${xhr.responseText}`
    );
    if (xhr.readyState == 4 && xhr.status >= 200 && xhr.status < 300) {
      viewReqs('pending');
    }
  }
}
