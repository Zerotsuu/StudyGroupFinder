<%--
  Created by IntelliJ IDEA.
  User: Lux
  Date: 14/07/2024
  Time: 1:35 am
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Study Group Finder</title>
    <link href="${pageContext.request.contextPath}/css/styles.css" rel="stylesheet">
</head>
<body class="bg-gray-100 h-screen flex items-center justify-center">
<div class="bg-white p-8 rounded-lg shadow-md w-96">
    <h2 class="text-2xl font-bold mb-6 text-center text-gray-800">Login</h2>

    <c:if test="${param.error != null}">
        <p class="text-red-500 text-sm text-center mb-4">Invalid username or password.</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/users/login" method="post">
        <div class="mb-4">
            <input type="text" name="username" placeholder="Username" required
                   class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring focus:border-blue-300">
        </div>
        <div class="mb-6">
            <input type="password" name="password" placeholder="Password" required
                   class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring focus:border-blue-300">
        </div>
        <button type="submit"
                class="w-full bg-blue-500 text-white py-2 rounded-md hover:bg-blue-600 focus:outline-none focus:ring focus:border-blue-300">
            Login
        </button>
    </form>

    <p class="mt-4 text-center text-sm text-gray-600">
        Don't have an account?
        <a href="${pageContext.request.contextPath}/users/register" class="text-blue-500 hover:underline">Register here</a>
    </p>
</div>
</body>
</html>
