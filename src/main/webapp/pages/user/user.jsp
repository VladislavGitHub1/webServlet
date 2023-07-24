<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Main</title>
</head>
<body>
Hello = ${user_name}
<hr/>
<form action="controller">
    <input type="hidden" name="command" value="logOut"/>
    <input type="submit" value="logOut"/>
</form>
<h2>We have this products</h2>
<table>
    <thead>
    <th>Name</th>
    <th>Description</th>
    <th>Price</th>
    </thead>
    <tbody>
    <c:forEach items="${medicines}" var="medicine" >
        <tr>
            <td>${medicine.name}</td>
            <td>${medicine.description}</td>
            <td>${medicine.price}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<form action="controller" method="post">
    <input type="hidden" name="command" value="create_order"/>
    <input type="submit" value="Create order"/>
</form>
${order_creation_status}
</body>
</html>
