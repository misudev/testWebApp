<%--
  Created by IntelliJ IDEA.
  User: jungmisu
  Date: 2019-01-15
  Time: 17:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html lang="ko">

<head>
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" type="text/css" href="login_.css">
    <link rel="stylesheet" type="text/css" href="css/reset.css">
    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
    <script type="text/javascript" src="js/brand.js"></script>
    <link href="https://fonts.googleapis.com/css?family=Nanum+Gothic:400,700&amp;subset=korean" rel="stylesheet">

    <style>
    </style>
</head>
<body>

<form id="loginForm" action="/loginform" method="post">
    <p class="ti1">BOARD LOGIN</p>
    <fieldset>
        <legend> LOGIN</legend>
        <div id="login">
            <p><label for="user-email" class="labelStyle"></label>
                <input type="text" name="user-email" id="user-email" placeholder="이메일"/>
            </p>

            <p><label for="user-pw" class="labelStyle"></label>
                <input type="password" id="user-pw" placeholder="비밀번호"/>
            </p>
        </div>
        <input type="submit" value="로그인" id="btnLogin"/>

    </fieldset>
</form>
</body>
</html>
