<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>DB control</title>
</head>
<body>
<form action="controller">
    <input type="hidden" name="command" value="go_to_admin_page"/>
    <input type="submit" value="Go to admin page"/>
</form>
<form action="controller">
    <input type="hidden" name="command" value="show_all_users"/>
    <input type="submit" value="Show all users"/>
</form>
<table>
    <thead>
    <th>Id</th>
    <th>Login</th>
    <th>Name</th>
    <th>LastName</th>
    <th>UserType</th>
    </thead>
    <tbody>
    <c:forEach items="${user_list}" var="user" >
        <tr>
            <td>${user.id}</td>
            <td>${user.login}</td>
            <td>${user.name}</td>
            <td>${user.lastname}</td>
            <td>${user.userType}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<br/>
<h2>Remove user</h2>
<form action="controller" method="get">
    <input type="hidden" name="command" value="remove_user"/>
    Login: <input type="text" name="login" value=""/>
    <br/>
    ID: <input type="text" name="id" value=""/>
    <br/>
    <input type="submit" name="sub" value="Remove User"/>
    <br/>
    ${remove_status}
</form>
<br/>
<h2>Add product</h2>
<form action="controller" method="post">
    <input type="hidden" name="command" value="add_product"/>
    Name: <input type="text" name="name" value=""/>
    <br/>
    Description: <input type="text" name="description" value=""/>
    <br/>
    Price: <input type="number" name="price" step="0.01" value="" required/>
    <br/>
    Without recipe?
    <label>
        <input type="radio" name="without_recipe" value="true">True
    </label>
    <label>
        <input type="radio" name="without_recipe" value="false">False
    </label>
    <br/>
    <input type="submit" name="sub" value="Add product"/>
    <br/>
    ${add_product_status}
</form>
</body>
</html>
