const API = 'http://localhost:8080/api/students';

async function fetchStudents() {
  const res = await fetch(API);
  const data = await res.json();
  const tbody = document.querySelector('#studentsTable tbody');
  tbody.innerHTML = '';
  data.forEach(s => {
    const tr = document.createElement('tr');
    tr.innerHTML = `
      <td>${s.id}</td>
      <td>${s.name}</td>
      <td>${s.email}</td>
      <td>${s.course}</td>
      <td>
        <button onclick="editStudent(${s.id})">Edit</button>
        <button onclick="deleteStudent(${s.id})">Delete</button>
      </td>`;
    tbody.appendChild(tr);
  });
}

async function addStudent(student) {
  await fetch(API, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(student)
  });
  fetchStudents();
}

async function updateStudent(id, student) {
  await fetch(`${API}/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(student)
  });
  fetchStudents();
}

async function deleteStudent(id) {
  await fetch(`${API}/${id}`, { method: 'DELETE' });
  fetchStudents();
}

function editStudent(id) {
  const row = [...document.querySelectorAll('#studentsTable tbody tr')]
                .find(tr => +tr.children[0].textContent === id);
  document.getElementById('studentId').value = id;
  document.getElementById('name').value = row.children[1].textContent;
  document.getElementById('email').value = row.children[2].textContent;
  document.getElementById('course').value = row.children[3].textContent;
}

document.getElementById('studentForm').addEventListener('submit', e => {
  e.preventDefault();
  const id = document.getElementById('studentId').value;
  const student = {
    name: document.getElementById('name').value,
    email: document.getElementById('email').value,
    course: document.getElementById('course').value
  };
  if (id) {
    updateStudent(id, student);
  } else {
    addStudent(student);
  }
  e.target.reset();
  document.getElementById('studentId').value = '';
});

document.getElementById('resetBtn').addEventListener('click', () => {
  document.getElementById('studentForm').reset();
  document.getElementById('studentId').value = '';
});

fetchStudents();
