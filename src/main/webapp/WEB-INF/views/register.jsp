<%--
  Created by IntelliJ IDEA.
  User: Lux
  Date: 14/07/2024
  Time: 1:43 am
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - Study Group Finder</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 h-screen flex items-center justify-center">
<div class="max-w-md w-full bg-white rounded-lg shadow-md p-8">
    <h2 class="text-2xl font-bold text-center text-gray-800 mb-8">Register</h2>

    <c:if test="${not empty errorMessage}">
        <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative mb-4" role="alert">
            <span class="block sm:inline">${errorMessage}</span>
        </div>
    </c:if>

    <form:form action="${pageContext.request.contextPath}/users/register" method="post" modelAttribute="registrationForm">
        <div class="mb-4">
            <form:label path="username" class="block text-gray-700 text-sm font-bold mb-2">Username</form:label>
            <form:input path="username" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required="required" />
            <form:errors path="username" class="text-red-500 text-xs italic" />
        </div>

        <div class="mb-4">
            <form:label path="email" class="block text-gray-700 text-sm font-bold mb-2">Email</form:label>
            <form:input path="email" type="email" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" required="required" />
            <form:errors path="email" class="text-red-500 text-xs italic" />
        </div>

        <div class="mb-4">
            <form:label path="password" class="block text-gray-700 text-sm font-bold mb-2">Password</form:label>
            <form:password path="password" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline" required="required" />
            <form:errors path="password" class="text-red-500 text-xs italic" />
        </div>

        <div class="mb-6">
            <form:label path="confirmPassword" class="block text-gray-700 text-sm font-bold mb-2">Confirm Password</form:label>
            <form:password path="confirmPassword" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline" required="required" />
            <form:errors path="confirmPassword" class="text-red-500 text-xs italic" />
        </div>

        <div class="flex items-center justify-between">
            <button type="submit" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline">
                Register
            </button>
            <a class="inline-block align-baseline font-bold text-sm text-blue-500 hover:text-blue-800" href="${pageContext.request.contextPath}/users/login">
                Already have an account?
            </a>
        </div>
    </form:form>
</div>
</body>
</html>


