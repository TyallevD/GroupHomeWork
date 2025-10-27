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