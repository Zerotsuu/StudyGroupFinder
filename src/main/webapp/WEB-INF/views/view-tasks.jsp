<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<body class="bg-gray-100">
<div class="container mx-auto px-4 py-8">
    <h1 class="text-3xl font-bold mb-6">Tasks for ${studyGroup.name}</h1>

    <div class="mb-4">
        <span class="font-semibold">Sort by:</span>
        <a href="?sortBy=dueDate_asc" class="text-blue-500 hover:text-blue-700 ml-2">Due Date (Ascending)</a>
        <a href="?sortBy=dueDate_desc" class="text-blue-500 hover:text-blue-700 ml-2">Due Date (Descending)</a>
        <a href="?sortBy=priority_desc" class="text-blue-500 hover:text-blue-700 ml-2">Priority (High to Low)</a>
        <a href="?sortBy=priority_asc" class="text-blue-500 hover:text-blue-700 ml-2">Priority (Low to High)</a>
    </div>

    <div class="bg-white shadow-md rounded-lg overflow-hidden">
        <table class="min-w-full">
            <thead class="bg-gray-100">
            <tr>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Title</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Due Date</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Priority</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
            </tr>
            </thead>
            <tbody class="bg-white divide-y divide-gray-200">
            <c:forEach items="${tasks}" var="task">
                <tr>
                    <td class="px-6 py-4 whitespace-nowrap">${task.title}</td>
                    <td class="px-6 py-4 whitespace-nowrap">${task.dueDate}</td>
                    <td class="px-6 py-4 whitespace-nowrap">
                        <c:choose>
                            <c:when test="${task.priority == 1}">Low</c:when>
                            <c:when test="${task.priority == 2}">Medium</c:when>
                            <c:when test="${task.priority == 3}">High</c:when>
                        </c:choose>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap">
                        <span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full ${task.completed ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'}">
                                ${task.completed ? 'Completed' : 'Pending'}
                        </span>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                        <button onclick="showEditForm(${task.id}, '${task.title}', '${task.description}', '${task.dueDate}', ${task.priority}, ${task.completed})" class="text-indigo-600 hover:text-indigo-900 mr-2">Edit</button>
                        <form action="/study-groups/${studyGroup.id}/tasks/${task.id}/delete" method="post" class="inline">
                            <button type="submit" class="text-red-600 hover:text-red-900">Delete</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="mt-8">
        <h2 class="text-2xl font-bold mb-4">Add New Task</h2>

        <form:form action="/study-groups/${studyGroup.id}/tasks/create" method="post" modelAttribute="newTask" class="space-y-4">
            <div>
                <form:input path="title" placeholder="Task Title" required="true" class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500" />
            </div>
            <div>
                <form:textarea path="description" placeholder="Task Description" class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500" />
            </div>
            <div>
                <form:input path="dueDate" type="date" required="true" class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500" />
            </div>
            <div>
                <form:select path="priority" class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500">
                    <form:option value="1">Low</form:option>
                    <form:option value="2">Medium</form:option>
                    <form:option value="3">High</form:option>
                </form:select>
            </div>
            <div>
                <button type="submit" class="inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
                    Add Task
                </button>
            </div>
        </form:form>
    </div>

    <div id="editTaskForm" class="fixed inset-0 bg-gray-600 bg-opacity-50 overflow-y-auto h-full w-full" style="display:none;">
        <div class="relative top-20 mx-auto p-5 border w-96 shadow-lg rounded-md bg-white">
            <h3 class="text-lg font-medium leading-6 text-gray-900 mb-4">Edit Task</h3>
            <form id="editTaskFormElement" method="post" class="space-y-4">
                <input type="hidden" id="editTaskId" name="id" />
                <div>
                    <input id="editTaskTitle" name="title" required class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500" />
                </div>
                <div>
                    <textarea id="editTaskDescription" name="description" class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500"></textarea>
                </div>
                <div>
                    <input id="editTaskDueDate" name="dueDate" type="date" required class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500" />
                </div>
                <div>
                    <select id="editTaskPriority" name="priority" class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-indigo-500 focus:border-indigo-500">
                        <option value="1">Low</option>
                        <option value="2">Medium</option>
                        <option value="3">High</option>
                    </select>
                </div>
                <div>
                    <input type="checkbox" id="editTaskCompleted" name="completed" class="rounded border-gray-300 text-indigo-600 shadow-sm focus:border-indigo-300 focus:ring focus:ring-indigo-200 focus:ring-opacity-50" />
                    <label for="editTaskCompleted" class="ml-2 text-sm text-gray-900">Completed</label>
                </div>
                <div>
                    <button type="submit" class="inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
                        Update Task
                    </button>
                    <button type="button" onclick="hideEditForm()" class="ml-2 inline-flex justify-center py-2 px-4 border border-gray-300 shadow-sm text-sm font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
                        Cancel
                    </button>
                </div>
            </form>
        </div>
    </div>

    <script>
        function showEditForm(taskId, title, description, dueDate, priority, completed) {
            console.log(taskId)
            document.getElementById('editTaskId').value = taskId;
            document.getElementById('editTaskTitle').value = title;
            document.getElementById('editTaskDescription').value = description;
            document.getElementById('editTaskDueDate').value = dueDate;
            document.getElementById('editTaskPriority').value = priority;
            document.getElementById('editTaskCompleted').checked = completed;

            document.getElementById('editTaskFormElement').action = "/study-groups/${studyGroup.id}/tasks/"+taskId+"/edit";
            document.getElementById('editTaskForm').style.display = 'block';
        }

        function hideEditForm() {
            document.getElementById('editTaskForm').style.display = 'none';
        }
    </script>
</div>
</body>