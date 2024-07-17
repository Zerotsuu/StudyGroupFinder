// taskList.js

document.addEventListener('DOMContentLoaded', function() {
    // Get modal elements
    const createTaskModal = document.getElementById('createTaskModal');
    const editTaskModal = document.getElementById('editTaskModal');

    // Get all close modal buttons
    const closeModalButtons = document.querySelectorAll('[data-close-modal]');

    // Add event listeners to close modal buttons
    closeModalButtons.forEach(button => {
        button.addEventListener('click', closeModal);
    });

    // Get the sort select element
    const sortSelect = document.getElementById('sortBy');

    // Add event listener for sort change
    sortSelect.addEventListener('change', handleSortChange);

    // Function to open create task modal
    window.openCreateModal = function() {
        createTaskModal.classList.remove('hidden');
    }

    // Function to open edit task modal
    window.openEditModal = function(taskId) {
        // Fetch task details and populate the form
        fetchTaskDetails(taskId).then(task => {
            document.getElementById('editTaskId').value = task.id;
            document.getElementById('editTaskTitle').value = task.title;
            document.getElementById('editTaskDescription').value = task.description;
            document.getElementById('editTaskDueDate').value = task.dueDate;
            document.getElementById('editTaskPriority').value = task.priority;
            document.getElementById('editTaskCompleted').checked = completed;

            editTaskModal.classList.remove('hidden');
        });
    }

    // Function to close modals
    function closeModal() {
        createTaskModal.classList.add('hidden');
        editTaskModal.classList.add('hidden');
    }

    // Function to handle sort change
    function handleSortChange() {
        const sortBy = sortSelect.value;
        const studyGroupId = document.body.dataset.studyGroupId;
        window.location.href = contextPath + `/study-groups/${studyGroupId}/tasks?sortBy=${sortBy}`;
    }

    function hideEditForm() {
        document.getElementById('editTaskForm').style.display = 'none';
    }

    // Function to fetch task details
    function fetchTaskDetails(taskId) {
        const studyGroupId = document.body.dataset.studyGroupId;
        return fetch(contextPath + `/study-groups/${studyGroupId}/tasks/${taskId}`)
            .then(response => response.json());
    }

    // Function to submit create task form
    window.submitCreateForm = function(event) {
        event.preventDefault();
        const form = event.target;
        const formData = new FormData(form);
        const studyGroupId = document.body.dataset.studyGroupId;

        fetch(contextPath + `/study-groups/${studyGroupId}/tasks/create`, {
            method: 'POST',
            body: formData
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    closeModal();
                    location.reload(); // Refresh the page to show the new task
                } else {
                    alert('Error creating task: ' + data.message);
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('An error occurred while creating the task');
            });
    }

    // Function to submit edit task form
    window.submitEditForm = function(event) {
        event.preventDefault();
        const form = event.target;
        const formData = new FormData(form);
        const studyGroupId = document.body.dataset.studyGroupId;
        const taskId = document.getElementById('editTaskId').value;

        fetch(contextPath + `/study-groups/${studyGroupId}/tasks/${taskId}/edit`, {
            method: 'POST',
            body: formData
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    closeModal();
                    location.reload(); // Refresh the page to show the updated task
                } else {
                    alert('Error updating task: ' + data.message);
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('An error occurred while updating the task');
            });
    }

});