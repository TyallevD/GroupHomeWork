const closeBtn = document.querySelectorAll('.close');
const cancelBtn = document.querySelectorAll('.cancel-btn');

// Закрытие при клике вне модального окна
window.addEventListener('click', function (event) {
    const editUserModal = document.getElementById('editUserModal');

    if (event.target === editUserModal) {
        closeModal();
    }
});

cancelBtn.forEach(btn => {
    btn.addEventListener('click', closeModal);
});

closeBtn.forEach(btn => {
    btn.addEventListener('click', closeModal);
});

function closeModal() {
    document.getElementById('editUserModal').style.display = 'none';
}

//Для изменения пользователя
//     let currentEditUserId = null;

function openEditUserModal(button) {

    // заполнение данными из элемента таблицы
    const userId = button.getAttribute('data-id');
    const username = button.getAttribute('data-username');
    const role = button.getAttribute('data-role');
    const email = button.getAttribute('data-email');
    const enabled = button.getAttribute('data-enabled');

    // Заполняем форму данными задачи
    document.getElementById('editUserId').value = userId;
    document.getElementById('editUserRole').value = role;
    document.getElementById('editUserEmail').value = email;
    document.getElementById('editUserEnabled').checked =
        enabled === 'true' || enabled === true;

    const modalTitle = document.querySelector('#editUserModal h3');
    modalTitle.textContent = `Редактирование пользователя: ${username}`;

    document.getElementById('editUserForm').action = `/admin/users/${userId}`;

    // Показываем модальное окно
    document.getElementById('editUserModal').style.display = 'block';
}

document.getElementById('editUserForm').addEventListener('submit', function (e) {
    e.preventDefault();

    const formData = new FormData(this);
    const csrfToken = document.querySelector('input[name="_csrf"]').value;

    // Для отладки - посмотрим что отправляем
    for (let [key, value] of formData.entries()) {
        console.log(`${key}: ${value}`);
    }



    fetch(this.action, {
        method: 'POST',
        headers: {
            'X-CSRF-TOKEN': csrfToken
        },
        body: formData
    })
        .then(response => {
            if (response.ok) {
                window.location.reload();
            } else {
                alert('Ошибка при обновлении пользователя');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Ошибка при обновлении пользователя');
        });
});

//сортировка
let sortStates = {};

function sortTable(columnIndex) {
    let tbody = document.getElementsByTagName('tbody')[0];
    let rows = Array.from(tbody.getElementsByTagName('tr'));

    let headers = document.querySelectorAll("th");


    let sortedRows = rows.sort((rowA, rowB) => {
        let cellA = rowA.cells[columnIndex].innerText.toLowerCase();
        let cellB = rowB.cells[columnIndex].innerText.toLowerCase();

        let sortMult = sortStates[columnIndex] === 'asc' ? -1 : 1;

        if (isNaN(cellA) && !isNaN(cellB)) {
            return sortMult * (Number(cellA) - Number(cellB));
        }
        return sortMult * cellA.localeCompare(cellB);
    });

    for (const header of headers) {
        let arr = header.innerText.split(' ');
        header.innerText = arr[0];
    }

    if (sortStates[columnIndex] === 'asc') {
        sortStates[columnIndex] = 'desc'
        headers[columnIndex].innerText += ' ▼'
    } else {
        sortStates[columnIndex] = 'asc'
        headers[columnIndex].innerText += ' ▲'
    }

    sortedRows.forEach(row => {
        tbody.appendChild(row);
    });
}