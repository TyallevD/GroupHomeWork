// Получение элементов
const addModal = document.getElementById('addTaskModal');
const openBtn = document.getElementById('openAddTaskModal');
const closeBtn = document.querySelectorAll('.close');
const cancelBtn = document.querySelectorAll('.cancel-btn');
const taskTitle = document.getElementById('taskTitle');
const titleCount = document.getElementById('titleCount');


// Открытие модального окна
openBtn.addEventListener('click', function () {
    addModal.style.display = 'block';
    document.getElementById('addTaskForm').reset();
    titleCount.textContent = '0';
});

// Закрытие модального окна
function closeModal() {
    document.getElementById('addTaskModal').style.display = 'none';
    document.getElementById('editTaskModal').style.display = 'none';
}

// Обработчики для кнопок отмены в обоих модальных окнах
cancelBtn.forEach(btn => {
    btn.addEventListener('click', closeModal);
});

closeBtn.forEach(btn => {
    btn.addEventListener('click', closeModal);
});


// Закрытие при клике вне модального окна
window.addEventListener('click', function (event) {
    const addModal = document.getElementById('addTaskModal');
    const editModal = document.getElementById('editTaskModal');

    if (event.target === addModal || event.target === editModal) {
        closeModal();
    }
});

// Счетчик символов для названия
taskTitle.addEventListener('input', function () {
    titleCount.textContent = this.value.length;
});

// Обработка отправки формы
document.getElementById('addTaskForm').addEventListener('submit', function (e) {
    if (taskTitle.value.trim() === '') {
        e.preventDefault();
        alert('Пожалуйста, введите название заметки');
        taskTitle.focus();
    }
});

//Настройка исчезновения алертов
document.addEventListener('DOMContentLoaded', function () {
    const flashMessages = document.querySelectorAll('.alert');

    flashMessages.forEach(function (message) {
        setTimeout(function () {
            message.style.display = 'none';
        }, 2500);
    });
});

// для редактирования задачи
    let currentEditTaskId = null;

    function openEditModal(button) {

    // заполнение данными из элемента таблицы
    const taskId = button.getAttribute('data-id');
    const title = button.getAttribute('data-title');
    const description = button.getAttribute('data-description');
    const completed = button.getAttribute('data-completed')
    currentEditTaskId = taskId;

    // Заполняем форму данными задачи
    document.getElementById('editTaskId').value = taskId;
    document.getElementById('editTaskTitle').value = title;
    document.getElementById('editTaskDescription').value = description;
    document.getElementById('editTaskCompleted').checked =
        completed === 'true' || completed === true;


    document.getElementById('editTaskForm').action = `/api/tasks/${taskId}`;

    // Показываем модальное окно
    document.getElementById('editTaskModal').style.display = 'block';
}

    document.getElementById('editTaskForm').addEventListener('submit', function (e) {
    e.preventDefault();

    const formData = new FormData(this);
    const csrfToken = document.querySelector('input[name="_csrf"]').value;

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
                alert('Ошибка при обновлении заметки');
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Ошибка при обновлении заметки');
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