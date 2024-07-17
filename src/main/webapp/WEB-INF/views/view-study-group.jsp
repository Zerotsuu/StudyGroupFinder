<%--
  Created by IntelliJ IDEA.
  User: Lux
  Date: 14/07/2024
  Time: 2:38 am
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<c:set var="formatter" value='${DateTimeFormatter.ofPattern("MMMM d, yyyy \'at\' h:mm a")}' />

<div class="max-w-4xl mx-auto bg-white shadow-lg rounded-lg overflow-hidden">
    <div class="px-6 py-4">
        <h1 class="text-3xl font-bold text-gray-800 mb-2">${studyGroup.name}</h1>
        <p class="text-gray-600 mb-4">${studyGroup.description}</p>

        <div class="bg-gray-100 rounded-lg p-4 mb-6">
            <h2 class="text-xl font-semibold text-gray-800 mb-2">Group Details</h2>
            <p class="mb-1"><span class="font-semibold">Course:</span> ${studyGroup.course.name}</p>
            <p class="mb-1"><span class="font-semibold">Created by:</span> ${studyGroup.creator.username}</p>
            <p><span class="font-semibold">Members:</span> ${studyGroup.members.size()}</p>
            <div class="mt-4">
                <a href="${pageContext.request.contextPath}/study-groups/${studyGroup.id}/tasks"
                   class="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline transition duration-150 ease-in-out">
                    View Tasks
                </a>
            </div>
        </div>

        <div class="flex justify-between items-center mb-6">

            <c:if test="${sessionScope.user.id eq studyGroup.creator.id or sessionScope.user.role eq 'ADMIN'}">
                <div class="flex space-x-2">
                    <button onclick="openEditStudyGroupModal(${studyGroup.id})" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
                        Edit Study Group
                    </button>
                    <form action="${pageContext.request.contextPath}/study-groups/${studyGroup.id}/delete" method="post" onsubmit="return confirm('Are you sure you want to delete this group?');">
                        <button type="submit" class="bg-red-500 hover:bg-red-600 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline transition duration-150 ease-in-out">
                            Delete Group
                        </button>
                    </form>
                </div>
            </c:if>
        </div>

        <div class="mb-6">
            <h2 class="text-xl font-semibold text-gray-800 mb-2">Members</h2>
            <ul class="bg-gray-100 rounded-lg p-4">
                <c:forEach items="${studyGroup.members}" var="member">
                    <li class="mb-2 last:mb-0">
                        <a href="${pageContext.request.contextPath}/users/${member.id}" class="text-blue-600 hover:underline">${member.username}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>

        <div class="mb-6">
            <h2 class="text-xl font-semibold text-gray-800 mb-2">Tasks</h2>

                <div class="bg-white shadow-md rounded-lg overflow-hidden">
                    <table class="min-w-full">
                        <thead class="bg-gray-100">
                        <tr>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Title</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Due Date</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Priority</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>

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
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>

        </div>
        <div class="mb-6">
            <h2 class="text-xl font-semibold text-gray-800 mb-2">Study Sessions</h2>
            <c:choose>
                <c:when test="${not empty studySessions}">
                    <ul class="bg-gray-100 rounded-lg p-4">
                        <c:forEach items="${studySessions}" var="session">
                            <li class="mb-4 last:mb-0 pb-4 last:pb-0 border-b last:border-b-0 border-gray-300">
                                <p class="font-semibold text-lg">
                                        ${session.dateTime.format(formatter)}
                                </p>
                                <p class="text-gray-600 mb-1"><span class="font-semibold">Location:</span> ${session.location}</p>
                                <p class="text-gray-600 mb-2">${session.description}</p>
                                <c:if test="${sessionScope.user.id eq studyGroup.creator.id or sessionScope.user.role eq 'ADMIN'}">
                                    <div class="mt-2">
                                        <a onclick="return openEditStudySessionModal(${session.id})" class="cursor-pointer text-blue-500 hover:text-blue-700 mr-2">Edit</a>
                                        <form action="${pageContext.request.contextPath}/study-sessions/${session.id}/delete" method="post" class="inline" onsubmit="return confirm('Are you sure you want to delete this session?');">
                                            <button type="submit" class="text-red-500 hover:text-red-700">Delete</button>
                                        </form>
                                    </div>
                                </c:if>
                            </li>
                        </c:forEach>
                    </ul>
                </c:when>
                <c:otherwise>
                    <p class="text-gray-600 bg-gray-100 rounded-lg p-4">No study sessions scheduled yet.</p>
                </c:otherwise>
            </c:choose>
        </div>

        <c:if test="${not empty sessionScope.user}">
            <div class="mt-6">
                <button onclick="openCreateStudySessionModal(${studyGroup.id})" class="bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline transition duration-150 ease-in-out">
                    Create New Study Session
                </button>
            </div>
        </c:if>

        <c:set var="isMember" value="false" />
        <c:forEach items="${studyGroup.members}" var="member">
            <c:if test="${member.id eq sessionScope.user.id}">
                <c:set var="isMember" value="true" />
            </c:if>
        </c:forEach>
        <c:choose>
            <c:when test="${empty sessionScope.user}">
                <div class="mt-6">
                    <a href="${pageContext.request.contextPath}/users/login" class="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline transition duration-150 ease-in-out">
                        Log in to join this group
                    </a>
                </div>
            </c:when>
            <c:when test="${!isMember}">
                <div class="mt-6">
                    <form action="${pageContext.request.contextPath}/study-groups/${studyGroup.id}/join" method="post">
                        <button type="submit" class="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline transition duration-150 ease-in-out">
                            Join Group
                        </button>
                        <c:forEach items="${studyGroup.members}"><p>${User.joinedGroups}</p></c:forEach>
                    </form>
                </div>
            </c:when>
            <c:when test="${isMember}">
                <div class="mt-6">
                    <form action="${pageContext.request.contextPath}/study-groups/${studyGroup.id}/leave" method="post" onsubmit="return confirm('Are you sure you want to leave this group?');">
                        <button type="submit" class="bg-red-500 hover:bg-red-600 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline transition duration-150 ease-in-out">
                            Leave Group
                        </button>
                    </form>
                </div>
            </c:when>
        </c:choose>
    </div>
</div>
