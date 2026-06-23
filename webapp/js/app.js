const API = 'http://localhost:8080/api';

document.addEventListener('DOMContentLoaded', () => {
  setupTabs();
  loadGroups();
  loadTeams();
  loadFixture();
  setupForm();
});

function setupTabs() {
  document.querySelectorAll('.tab').forEach(tab => {
    tab.addEventListener('click', () => {
      document.querySelectorAll('.tab').forEach(t => t.classList.remove('active'));
      document.querySelectorAll('.tab-content').forEach(c => c.classList.remove('active'));
      tab.classList.add('active');
      document.getElementById(tab.dataset.tab).classList.add('active');
      if (tab.dataset.tab === 'fixture') loadFixture();
    });
  });
}

async function loadGroups() {
  const res = await fetch(API + '/groups');
  const groups = await res.json();
  const container = document.getElementById('groups');
  container.innerHTML = '';
  groups.forEach(g => {
    const card = document.createElement('div');
    card.className = 'group-card';
    card.innerHTML = `<h2>Grupo ${g.name}</h2>
      <table>
        <thead><tr><th>Equipo</th><th>País</th></tr></thead>
        <tbody>${g.teams.map(t => `<tr><td>${t.name}</td><td>${t.country}</td></tr>`).join('')}</tbody>
      </table>`;
    container.appendChild(card);
  });
}

async function loadTeams() {
  const res = await fetch(API + '/teams');
  const teams = await res.json();
  const localSel = document.getElementById('local');
  const visitorSel = document.getElementById('visitor');
  teams.forEach(t => {
    localSel.innerHTML += `<option value="${t.id}">${t.name}</option>`;
    visitorSel.innerHTML += `<option value="${t.id}">${t.name}</option>`;
  });
}

async function loadFixture() {
  const res = await fetch(API + '/matches');
  const matches = await res.json();
  const tbody = document.querySelector('#fixture-table tbody');
  const empty = document.querySelector('.empty-msg');
  tbody.innerHTML = '';
  if (matches.length === 0) {
    empty.style.display = 'block';
    document.getElementById('fixture-table').style.display = 'none';
  } else {
    empty.style.display = 'none';
    document.getElementById('fixture-table').style.display = 'table';
    matches.forEach(m => {
      const tr = document.createElement('tr');
      tr.innerHTML = `<td>${m.localName}</td><td>${m.visitorName}</td>
        <td>${m.localGoals}</td><td>${m.visitorGoals}</td>
        <td>${m.date}</td><td>${m.phase}</td><td>${m.status}</td>`;
      tbody.appendChild(tr);
    });
  }
}

function setupForm() {
  const form = document.getElementById('result-form');
  const log = document.getElementById('log');
  form.addEventListener('submit', async (e) => {
    e.preventDefault();
    const local = document.getElementById('local');
    const visitor = document.getElementById('visitor');
    if (local.value === visitor.value) {
      showLog('Seleccione dos equipos diferentes.', 'error');
      return;
    }
    const params = new URLSearchParams({
      localId: local.value,
      visitorId: visitor.value,
      localGoals: document.getElementById('goalsLocal').value,
      visitorGoals: document.getElementById('goalsVisitor').value,
      phase: document.getElementById('phase').value,
      date: document.getElementById('date').value,
      groupId: 0
    });
    try {
      const res = await fetch(API + '/matches', {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: params
      });
      const data = await res.json();
      if (data.ok) {
        showLog('Partido guardado correctamente.', 'success');
        form.reset();
        loadFixture();
      } else {
        showLog('Error: ' + (data.error || 'desconocido'), 'error');
      }
    } catch (err) {
      showLog('Error de conexión: ' + err.message, 'error');
    }
  });
}

function showLog(msg, type) {
  const log = document.getElementById('log');
  log.textContent = msg;
  log.className = type;
}
