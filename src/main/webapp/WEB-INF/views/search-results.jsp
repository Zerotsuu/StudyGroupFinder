<%--
  Created by IntelliJ IDEA.
  User: Lux
  Date: 15/07/2024
  Time: 9:47 am
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="container mx-auto px-4 py-8">
    <h1 class="text-3xl font-bold mb-6">Search Results for "${query}"</h1>

    <c:choose>
        <c:when test="${not empty searchResults}">
            <ul class="space-y-4">
                <c:forEach items="${searchResults}" var="group">
                    <li class="bg-white shadow-md rounded-lg overflow-hidden">
                        <div class="p-4">
                            <h2 class="text-xl font-semibold text-gray-800 mb-2">
                                <a href="${pageContext.request.contextPath}/study-groups/${group.id}" class="hover:underline">${group.name}</a>
                            </h2>
                            <p class="text-gray-600 mb-2">${group.description}</p>
                            <p class="text-sm text-gray-500">
                                <span class="font-semibold">Course:</span> ${group.course.name} |
                                <span class="font-semibold">Members:</span> ${group.members.size()}
                            </p>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </c:when>
        <c:otherwise>
            <p class="text-gray-600">No results found for "${query}". Try a different search term.</p>
        </c:otherwise>
    </c:choose>
</div>
