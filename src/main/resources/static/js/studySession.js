function openCreateStudySessionModal(studyGroupId) {
    fetch(contextPath + `/study-groups/${studyGroupId}/sessions/create`)
        .then(response => response.text())
        .then(html => {
            // Dispatch a custom event that Alpine.js is listening for
            window.dispatchEvent(new CustomEvent('open-modal', { detail: html }));
        })
        .catch(error => console.error('Error:', error));
}

function createStudySession() {
    const form = document.getElementById('createStudySessionForm');
    const formData = new FormData(form);

    fetch(contextPath + `/study-sessions/create`, {
        method: 'POST',
        body: formData,
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const contentType = response.headers.get("content-type");
            if (contentType && contentType.indexOf("application/json") !== -1) {
                return response.json();
            } else {
                return response.text();
            }
        })
        .then(data => {
            if (typeof data === 'object' && data.success) {
                window.dispatchEvent(new CustomEvent('close-modal'));
                location.reload();
            } else if (typeof data === 'string') {
                // The server returned non-JSON data, likely an error page
                console.error('Server returned non-JSON response:', data);
                alert('An error occurred while creating the study session. Please check the console for details.');
            } else {
                alert('Failed to create study session: ' + (data.message || 'Unknown error'));
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred while creating the study session: ' + error.message);
        });
}
function openEditStudySessionModal(studySessionId) {
    fetch(contextPath + `/study-sessions/${studySessionId}/edit`)
        .then(response => response.text())
        .then(html => {
            window.dispatchEvent(new CustomEvent('open-modal', { detail: html }));
        })
        .catch(error => console.error('Error:', error));
}

function updateStudySession() {
    const form = document.getElementById('editStudySessionForm');
    const formData = new FormData(form);

    fetch(contextPath + `/study-sessions/update`, {
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
                alert('Failed to update study session: ' + (data.message || 'Unknown error'));
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred while updating the study session: ' + error.message);
        });
}