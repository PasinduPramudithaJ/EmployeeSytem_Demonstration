document.addEventListener('DOMContentLoaded', () => { 
  const employeeForm = document.getElementById("employeeForm"); 
  const employeeTable = document 
    .getElementById("employeeTable") 
    .getElementsByTagName("tbody")[0]; 
 
  const apiBaseUrl = "http://localhost:8080/api/employees"; 
 
  function fetchEmployees() { 
    fetch(apiBaseUrl) 
      .then((response) => response.json()) 
      .then((data) => { 
        employeeTable.innerHTML = ""; 
        data.forEach((employee) => { 
          const row = employeeTable.insertRow(); 
          row.innerHTML = ` 
                        <td>${employee.id}</td> 
                        <td>${employee.firstName}</td> 
                        <td>${employee.lastName}</td> 
                        <td>${employee.email}</td> 
                        <td> 
                            <button onclick="editEmployee('${employee.id}')">Edit</button>
                            <button onclick="deleteEmployee('${employee.id}')">Delete</button>
                        </td> 
                    `; 
        }); 
      }); 
  } 
 
  employeeForm.addEventListener("submit", (e) => { 
    e.preventDefault(); 
 
    const id = document.getElementById("employeeId").value; 
    const firstName = document.getElementById("firstName").value; 
    const lastName = document.getElementById("lastName").value; 
    const email = document.getElementById("email").value; 
 
    const method = id ? "PUT" : "POST"; 
    const url = id ? `${apiBaseUrl}/${id}` : apiBaseUrl; 
 
    fetch(url, { 
      method: method, 
      headers: { 
        "Content-Type": "application/json", 
      }, 
      body: JSON.stringify({ firstName, lastName, email }), 
    }) 
      .then((response) => response.json()) 
      .then(() => { 
        employeeForm.reset(); 
        fetchEmployees(); 
      }); 
  }); 
 
  window.editEmployee = function (id) { 
    fetch(`${apiBaseUrl}/${id}`) 
      .then((response) => response.json()) 
      .then((employee) => { 
        document.getElementById("employeeId").value = employee.id; 
        document.getElementById("firstName").value = employee.firstName; 
        document.getElementById("lastName").value = employee.lastName; 
        document.getElementById("email").value = employee.email; 
      }); 
  }; 
 
  window.deleteEmployee = function (id) { 
    fetch(`${apiBaseUrl}/${id}`, { 
      method: "DELETE", 
    }).then(() => fetchEmployees()); 
  }; 
fetchEmployees(); 
}); 