document.addEventListener('DOMContentLoaded', () => { 
  const employeeForm = document.getElementById("employeeForm"); 
  const employeeTable = document.getElementById("employeeTable").getElementsByTagName("tbody")[0]; 
 
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
            <td>${employee.department}</td> 
            <td>$${employee.salary ? employee.salary.toFixed(2) : '0.00'}</td> 
            <td> 
                <button onclick="editEmployee('${employee.id}')">Edit</button>
                <button onclick="deleteEmployee('${employee.id}')">Delete</button>
            </td> 
          `; 
        }); 
      })
      .catch(err => console.error("Error fetching employees:", err)); 
  } 
 
  employeeForm.addEventListener("submit", (e) => { 
    e.preventDefault(); 
 
    const id = document.getElementById("employeeId").value; 
    const firstName = document.getElementById("firstName").value; 
    const lastName = document.getElementById("lastName").value; 
    const email = document.getElementById("email").value; 
    const department = document.getElementById("department").value; 
    const salary = parseFloat(document.getElementById("salary").value); 
 
    const method = id ? "PUT" : "POST"; 
    const url = id ? `${apiBaseUrl}/${id}` : apiBaseUrl; 
 
    fetch(url, { 
      method: method, 
      headers: { 
        "Content-Type": "application/json", 
      }, 
      body: JSON.stringify({ firstName, lastName, email, department, salary }), 
    }) 
      .then((response) => {
        if (!response.ok) throw new Error("Failed to save employee");
        return response.json();
      })
      .then(() => { 
        employeeForm.reset(); 
        document.getElementById("employeeId").value = "";
        fetchEmployees(); 
      })
      .catch(err => alert(err.message)); 
  }); 
 
  window.editEmployee = function (id) { 
    fetch(`${apiBaseUrl}/${id}`) 
      .then((response) => response.json()) 
      .then((employee) => { 
        document.getElementById("employeeId").value = employee.id; 
        document.getElementById("firstName").value = employee.firstName; 
        document.getElementById("lastName").value = employee.lastName; 
        document.getElementById("email").value = employee.email; 
        document.getElementById("department").value = employee.department; 
        document.getElementById("salary").value = employee.salary; 
      }); 
  }; 
 
  window.deleteEmployee = function (id) { 
    if (confirm("Are you sure you want to delete this employee?")) {
      fetch(`${apiBaseUrl}/${id}`, { 
        method: "DELETE", 
      }).then(() => fetchEmployees()); 
    }
  }; 

  // Initial fetch on load
  fetchEmployees(); 
});