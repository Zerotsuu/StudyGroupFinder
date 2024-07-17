function createStudyGroup() {
    const form = document.getElementById('createStudyGroupForm');
    const formData = new FormData(form);

    fetch(contextPath + `/study-groups/create`, {
        method: 'POST',
        body: formData
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                window.location.href = '/study-groups/' + data.groupId;
            } else {
                alert('Error creating study group: ' + data.message);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred while creating the study group.');
        });
}

function openCreateStudyGroupModal() {
    fetch(contextPath + `/study-groups/create-modal`)
        .then(response => response.text())
        .then(html => {
            window.dispatchEvent(new CustomEvent('open-modal', { detail: html }));
        });
}

function openEditStudyGroupModal(studyGroupId) {
    fetch(contextPath + `/study-groups/${studyGroupId}/edit`)
        .then(response => response.text())
        .then(html => {
            window.dispatchEvent(new CustomEvent('open-modal', { detail: html }));
        })
        .catch(error => console.error('Error:', error));
}

function updateStudyGroup() {
    const form = document.getElementById('editStudyGroupForm');
    const formData = new FormData(form);

    fetch(contextPath + `/study-groups/update`, {
        method: 'POST',
        body: formData,
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            if (data.success) {
                window.dispatchEvent(new CustomEvent('close-modal'));
                location.reload();
            } else {
                alert('Failed to update study group: ' + (data.message || 'Unknown error'));
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred while updating the study group: ' + error.message);
        });
}