<%--
  Created by IntelliJ IDEA.
  User: Lux
  Date: 15/07/2024
  Time: 10:35 am
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <script>
        var contextPath = '${pageContext.request.contextPath}';
    </script>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title} - Study Group Finder</title>
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/js/studyGroup.js"></script>
    <script src="${pageContext.request.contextPath}/js/studySession.js"></script>
    <script src="https://cdn.jsdelivr.net/gh/alpinejs/alpine@v2.x.x/dist/alpine.min.js" defer></script>
</head>
<body class="bg-gray-100 min-h-screen flex flex-col">
<header class="bg-white shadow">
    <nav class="container mx-auto px-6 py-3">
        <div class="flex items-center justify-between">
            <div class="flex items-center">
                <a href="${pageContext.request.contextPath}/" class="text-xl font-bold text-gray-800 mr-6">Study Group Finder</a>
                <ul class="flex space-x-4">
                    <li><a href="${pageContext.request.contextPath}/study-groups" class="text-gray-800 hover:text-blue-500">Study Groups</a></li>
                    <c:choose>
                        <c:when test="${empty sessionScope.user}">
                            <li><a href="${pageContext.request.contextPath}/users/login" class="text-gray-800 hover:text-blue-500">Login</a></li>
                            <li><a href="${pageContext.request.contextPath}/users/register" class="text-gray-800 hover:text-blue-500">Register</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="${pageContext.request.contextPath}/users/${sessionScope.user.id}" class="text-gray-800 hover:text-blue-500">Profile</a></li>
                            <li><a href="${pageContext.request.contextPath}/users/logout" class="text-gray-800 hover:text-blue-500">Logout</a></li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
            <div class="flex-grow mx-4 max-w-md">
                <form action="${pageContext.request.contextPath}/search" method="get" class="flex items-center">
                    <input type="text" name="query" placeholder="Search study groups..."
                           class="w-full px-4 py-2 rounded-l-md border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500">
                    <button type="submit" class="bg-blue-500 text-white px-4 py-2 rounded-r-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500">
                        Search
                    </button>
                </form>
            </div>
        </div>
    </nav>
</header>

<%--MAIN CONTENT--%>
<main class="container mx-auto px-6 py-8 flex-grow">
    <jsp:include page="${content}" />
</main>

<footer class="bg-white shadow mt-auto">
    <div class="container mx-auto px-6 py-3">
        <p class="text-center text-gray-600">© 2024 Study Group Finder. All rights reserved.</p>
    </div>
</footer>

<!-- Modal component -->
<div x-data="{ isOpen: false, modalContent: '' }" x-on:open-modal.window="isOpen = true; modalContent = $event.detail">
    <div x-show="isOpen" class="fixed inset-0 z-50 overflow-y-auto" aria-labelledby="modal-title" role="dialog" aria-modal="true">
        <div class="flex items-end justify-center min-h-screen pt-4 px-4 pb-20 text-center sm:block sm:p-0">
            <div x-show="isOpen" x-transition:enter="ease-out duration-300" x-transition:enter-start="opacity-0" x-transition:enter-end="opacity-100" x-transition:leave="ease-in duration-200" x-transition:leave-start="opacity-100" x-transition:leave-end="opacity-0" class="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity" aria-hidden="true"></div>
            <span class="hidden sm:inline-block sm:align-middle sm:h-screen" aria-hidden="true">&#8203;</span>
            <div x-show="isOpen" x-transition:enter="ease-out duration-300" x-transition:enter-start="opacity-0 translate-y-4 sm:translate-y-0 sm:scale-95" x-transition:enter-end="opacity-100 translate-y-0 sm:scale-100" x-transition:leave="ease-in duration-200" x-transition:leave-start="opacity-100 translate-y-0 sm:scale-100" x-transition:leave-end="opacity-0 translate-y-4 sm:translate-y-0 sm:scale-95" class="inline-block align-bottom bg-white rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full">
                <div x-html="modalContent"></div>
            </div>
        </div>
    </div>
</div>


</body>
</html>
