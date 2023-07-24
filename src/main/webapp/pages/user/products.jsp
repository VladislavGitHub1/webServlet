<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Products</title>
</head>
<body>
<form action="controller">
    <input type="hidden" name="command" value="show_all_medicines"/>
    <input type="submit" name="sub" value="Refresh medicines list"/>
    <br/>
</form>
<form action="controller">
    <input type="hidden" name="command" value="logOut"/>
    <input type="submit" value="logOut"/>
</form>
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
<br/>
<form action="controller" method="post">
    <input type="hidden" name="command" value="add_to_order"/>
    <label for="products">Choose products</label>
    <select name="products" id="products" multiple>
        <c:forEach items="${medicines}" var="medicine">
            <option value="${medicine.id}">${medicine.name}</option>
        </c:forEach>
    </select>
    <input type="submit" value="order"/>
</form>
<form action="controller" method="post">
    <input type="hidden" name="command" value="go_to_order_page"/>
    <input type="submit" value="Go to order page"/>
</form>
</body>
</html>
