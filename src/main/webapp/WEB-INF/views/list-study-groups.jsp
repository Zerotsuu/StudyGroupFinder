<%--
  Created by IntelliJ IDEA.
  User: Lux
  Date: 13/07/2024
  Time: 11:58 pm
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="max-w-4xl mx-auto">
    <div class="flex justify-between items-center mb-6">
        <h1 class="text-3xl font-bold text-gray-800">Study Groups</h1>
        <c:if test="${not empty sessionScope.user}">
            <button onclick="openCreateStudyGroupModal()" class="bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline transition duration-150 ease-in-out">
                Create New Study Group
            </button>
        </c:if>
    </div>

    <c:choose>
        <c:when test="${not empty studyGroups}">
            <ul class="space-y-4">
                <c:forEach items="${studyGroups}" var="group">
                    <li class="bg-white shadow-md rounded-lg overflow-hidden">
                        <div class="p-4">
                            <h2 class="text-xl font-semibold text-gray-800 mb-2">
                                <a href="${pageContext.request.contextPath}/study-groups/${group.id}" class="hover:underline">${group.name}</a>
                            </h2>
                            <p class="text-gray-600 mb-2">${group.description}</p>
                            <p class="text-sm text-gray-500">
                                <span class="font-semibold">Course:</span> ${group.course.name} |
                                <span class="font-semibold">Creator:</span> ${group.creator.username} |
                                <span class="font-semibold">Members:</span> ${group.members.size()}
                            </p>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </c:when>
        <c:otherwise>
            <p class="text-gray-600 bg-white shadow-md rounded-lg p-4">No study groups available. Be the first to create one!</p>
        </c:otherwise>
    </c:choose>
</div>

