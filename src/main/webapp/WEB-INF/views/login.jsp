<%--
  Created by IntelliJ IDEA.
  User: jungmisu
  Date: 2019-01-09
  Time: 15:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>

<html lang="ko">

<head>

    <meta charset="UTF-8">

    <title></title>

    <link rel="stylesheet" type="text/css" href="/login_.css">
   <!-- <link rel="stylesheet" type="text/css" href="css/reset.css">-->
    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
    <script type="text/javascript" src="js/brand.js"></script>

   <!-- <link href="https://fonts.googleapis.com/css?family=Nanum+Gothic:400,700&amp;subset=korean" rel="stylesheet">-->

</head>

<body>
<form id="loginForm" action="/login" method="post">
    <p class="ti1">BOARD LOGIN</p>
    <input type="hidden" name="redirect" value="${param.redirect}">
    <fieldset>
        <legend> LOGIN</legend>
        <div id="login">
            <p><label for="userId" class="labelStyle"></label>
                <input type="text" name = "user-email" id="userId" placeholder="이메일"/>
            </p>

            <p><label for="userPWD" class="labelStyle"></label>
                <input type="password" name = "user-pw" id="userPWD" placeholder="비밀번호"/>
            </p>
        </div>
        <input type="submit" value="로그인" id="btnLogin"/>

        <!--
        <p id="btn1">
            <input type="checkbox" id="saveId" value="saveIdYes"/>
            <label for="saveId">아이디저장</label>
            <span class="find"><a href="#">아이디찾기</a> | <a href="#">비밀번호찾기</a> </span>
        </p>
        -->



    </fieldset>
</form>
</body>
</html>
