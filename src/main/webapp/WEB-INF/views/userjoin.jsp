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

    <title>회원가입</title>

    <link rel="stylesheet" type="text/css" href="/join.css">
    <script type="text/javascript" src="http://code.jquery.com/jquery-latest.js"></script>
    <script type="text/javascript" src="js/brand.js"></script>

    <link href="https://fonts.googleapis.com/css?family=Nanum+Gothic:400,700&amp;subset=korean" rel="stylesheet">


    <style>



    </style>


</head>

<body>





<!-- ---------------------form------------------------------->
<div class="info">
    <h2>< 회원 정보 입력 ></h2>

    <div class="bord"></div>

    <form action="/userjoin" method="post">

        <fieldset id="sec">
            <legend>정보</legend>
            <ul>
                <li><label class="userId" for="name">이름</label>
                    <input type="text" id="name" name = "user-name" placeholder="한글,영문 / 특수문자 불가" autofocus required>

                </li>

                <li><label for="userPWD" class="labelStyle">비밀번호</label>
                    <input type="password" name = "user-pw" id="userPWD" placeholder="영문 / 숫자 8~12자입력해주세요."  maxLength="12" required />
                </li>

                <li><label class="email" for="email">이메일</label>
                    <input type="text" id="email" name="user-email" placeholder="이메일을 입력해주세요." required>
                </li>

            </ul>
        </fieldset>

        <div class="bord"></div>



        <!----------------------버튼------------------------------------------>
        <div class="bt">

            <div class="before button"><a href="#">이전 </a></div>
            <input type="submit" value="완료" id="btnjoin"/>

        </div>


    </form>
</div>




</body>

</html>