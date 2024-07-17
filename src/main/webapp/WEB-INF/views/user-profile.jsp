<%--
  Created by IntelliJ IDEA.
  User: Lux
  Date: 15/07/2024
  Time: 5:54 am
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="container mx-auto px-4 py-8">
  <div class="bg-white shadow overflow-hidden sm:rounded-lg">
    <div class="px-4 py-5 sm:px-6">
      <h3 class="text-lg leading-6 font-medium text-gray-900">
        User Profile
      </h3>
      <p class="mt-1 max-w-2xl text-sm text-gray-500">
        Personal details and application.
      </p>
    </div>
    <div class="border-t border-gray-200">
      <dl>
        <div class="bg-gray-50 px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
          <dt class="text-sm font-medium text-gray-500">
            Username
          </dt>
          <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">
            ${profileUser.username}
          </dd>
        </div>
        <div class="bg-white px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
          <dt class="text-sm font-medium text-gray-500">
            Email address
          </dt>
          <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">
            ${profileUser.email}
          </dd>
        </div>
        <div class="bg-gray-50 px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
          <dt class="text-sm font-medium text-gray-500">
            Role
          </dt>
          <dd class="mt-1 text-sm text-gray-900 sm:mt-0 sm:col-span-2">
            ${profileUser.role}
          </dd>
        </div>
      </dl>
    </div>
  </div>

  <!-- Enrolled Courses -->
  <div class="mt-8">
    <h2 class="text-2xl font-semibold text-gray-800 mb-4">Enrolled Courses</h2>
    <div class="bg-white shadow overflow-hidden sm:rounded-md">
      <ul class="divide-y divide-gray-200">
        <c:forEach items="${enrolledCourses}" var="course">
          <li class="px-4 py-4 sm:px-6">
            <div class="flex items-center justify-between">
              <p class="text-sm font-medium text-indigo-600 truncate">
                  ${course.name}
              </p>
              <p class="ml-2 flex-shrink-0 flex">
                                <span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-blue-100 text-blue-800">
                                    ${course.code}
                                </span>
              </p>
            </div>
          </li>
        </c:forEach>
      </ul>
    </div>
    <button onclick="openAddCourseModal()" class="mt-4 bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
      Add Course
    </button>
  </div>

  <!-- Joined Study Groups -->
  <div class="mt-8">
    <h2 class="text-2xl font-semibold text-gray-800 mb-4">Joined Study Groups</h2>
    <div class="bg-white shadow overflow-hidden sm:rounded-md">
      <ul class="divide-y divide-gray-200">
        <c:forEach items="${joinedGroups}" var="group">
          <li>
            <a href="${pageContext.request.contextPath}/study-groups/${group.id}" class="block hover:bg-gray-50">
              <div class="px-4 py-4 sm:px-6">
                <div class="flex items-center justify-between">
                  <p class="text-sm font-medium text-indigo-600 truncate">
                      ${group.name}
                  </p>
                  <div class="ml-2 flex-shrink-0 flex">
                    <p class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 text-green-800">
                        ${group.course.name}
                    </p>
                  </div>
                </div>
                <div class="mt-2 sm:flex sm:justify-between">
                  <div class="sm:flex">
                    <p class="flex items-center text-sm text-gray-500">
                        ${group.members.size()} members
                    </p>
                  </div>
                </div>
              </div>
            </a>
          </li>
        </c:forEach>
      </ul>
    </div>
  </div>

  <!-- Upcoming Study Sessions -->
  <div class="mt-8">
    <h2 class="text-2xl font-semibold text-gray-800 mb-4">Upcoming Study Sessions</h2>
    <div class="bg-white shadow overflow-hidden sm:rounded-md">
      <ul class="divide-y divide-gray-200">
        <c:forEach items="${upcomingStudySessions}" var="session">
          <li>
            <a href="${pageContext.request.contextPath}/study-sessions/${session.id}" class="block hover:bg-gray-50">
              <div class="px-4 py-4 sm:px-6">
                <div class="flex items-center justify-between">
                  <p class="text-sm font-medium text-indigo-600 truncate">
                      ${session.studyGroup.name}
                  </p>
                  <div class="ml-2 flex-shrink-0 flex">
                    <p class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 text-green-800">
                      <fmt:parseDate value="${session.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
                      <fmt:formatDate value="${parsedDateTime}" pattern="MMM d, yyyy HH:mm" />
                    </p>
                  </div>
                </div>
                <div class="mt-2 sm:flex sm:justify-between">
                  <div class="sm:flex">
                    <p class="flex items-center text-sm text-gray-500">
                        ${session.location}
                    </p>
                  </div>
                </div>
              </div>
            </a>
          </li>
        </c:forEach>
      </ul>
    </div>
  </div>
</div>

<!-- Add Course Modal -->
<div id="addCourseModal" class="fixed z-10 inset-0 overflow-y-auto hidden" aria-labelledby="modal-title" role="dialog" aria-modal="true">
  <div class="flex items-end justify-center min-h-screen pt-4 px-4 pb-20 text-center sm:block sm:p-0">
    <div class="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity" aria-hidden="true"></div>
    <span class="hidden sm:inline-block sm:align-middle sm:h-screen" aria-hidden="true">&#8203;</span>
    <div class="inline-block align-bottom bg-white rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full">
      <div class="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
        <h3 class="text-lg leading-6 font-medium text-gray-900" id="modal-title">
          Add Course
        </h3>
        <div class="mt-2">
          <form id="addCourseForm" action="${pageContext.request.contextPath}/users/add-course" method="post">
            <div class="mb-4">
              <label for="courseId" class="block text-sm font-medium text-gray-700">Select Course</label>
              <select id="courseId" name="courseId" class="mt-1 block w-full py-2 px-3 border border-gray-300 bg-white rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm">
                <c:forEach items="${availableCourses}" var="course">
                  <option value="${course.id}">${course.name} (${course.code})</option>
                </c:forEach>
              </select>
            </div>
          </form>
        </div>
      </div>
      <div class="bg-gray-50 px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse">
        <button type="submit" form="addCourseForm" class="w-full inline-flex justify-center rounded-md border border-transparent shadow-sm px-4 py-2 bg-blue-600 text-base font-medium text-white hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500 sm:ml-3 sm:w-auto sm:text-sm">
          Add Course
        </button>
        <button type="button" onclick="closeAddCourseModal()" class="mt-3 w-full inline-flex justify-center rounded-md border border-gray-300 shadow-sm px-4 py-2 bg-white text-base font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 sm:mt-0 sm:ml-3 sm:w-auto sm:text-sm">
          Cancel
        </button>
      </div>
    </div>
  </div>
</div>

<script>
  function openAddCourseModal() {
    document.getElementById('addCourseModal').classList.remove('hidden');
  }

  function closeAddCourseModal() {
    document.getElementById('addCourseModal').classList.add('hidden');
  }
</script>
