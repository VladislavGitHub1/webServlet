
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Hello world!
</h1>
<br/>
<form action="controller">
    <input type="hidden" name="command" value="login"/>
    Login: <input type="text" name="login" value=""/>
    <br/>
    Password: <input type="password" name="pass" value=""/>
    <br/>
    <input type="submit" name="sub" value="Push"/>
    <br/>
    ${login_failed}
</form>
<hr/>
<%--<h1>Registration--%>
<%--    </h1>--%>
<%--<form action="controller">--%>
<%--    <input type="hidden" name="command" value="add_user"/>--%>
<%--    Login: <input type="text" name="login" value=""/>--%>
<%--    <br/>--%>
<%--    Password: <input type="password" name="pass" value=""/>--%>
<%--    <br/>--%>
<%--    Name: <input type="text" name="name" value=""/>--%>
<%--    <br/>--%>
<%--    Lastname: <input type="text" name="lastname" value=""/>--%>
<%--    <br/>--%>
<%--    <input type="submit" name="sub" value="Register"/>--%>
<%--    <br/>--%>
<%--    ${authenticate_status}--%>
<%--</form>--%>

</body>
</html>
