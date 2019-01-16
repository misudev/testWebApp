<%--
  Created by IntelliJ IDEA.
  User: jungmisu
  Date: 2019-01-09
  Time: 15:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <meta charset="utf-8">

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>


<body>

<h2 style="text-align:center; margin-top:20px;">게시판 </h2>
<div style="width:90%; padding-top:10px;">

    <c:choose>
        <c:when test="${sessionScope.signedUser != null}">
            <a class="btn btn-default" href="/write"  style='width:10%; float:right;'>글쓰기</a>
            <a class="btn btn-default" href="/logout" style='width:10%; float:right;'>로그아웃</a>
        </c:when>
        <c:when test="${sessionScope.signedUser == null}">
            <a class="btn btn-default" href="/userjoin" style='width:10%; float:right;'>회원가입</a>
            <a class="btn btn-default" href="/login" style='width:10%; float:right;'>로그인</a></c:when>
        <c:otherwise>위조건이외의 실행</c:otherwise>
    </c:choose>

</div>
<table class = "table table-bordered table-hover" style='width:80%; margin: 50px auto;'>
    <thead style="background-color:#ebebeb;">
    <th width="10%">글번호</th>
    <th width="45%">제목</th>
    <th width="15%">글쓴이</th>
    <th width="15%">날짜</th>
    <th width="15%"> 조회수</th>
    </thead>
    <tbody>
    <c:forEach items="${boards}" var="board">
        <tr>
            <td>${board.id}</td>
            <td><a href="/read?id=${board.id}">${board.title}</a></td>
            <td>${board.name}</td>
            <td>${board.regdate}</td>
            <td>${board.readCount}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>


<div class="text-center" style="width:50%; margin:0 auto; margin-top:10px;">
    <ul class = "pagination">
        <li><a href="#">&laquo;</a></li>
        <li><a href="?page=1">1</a></li>
        <li><a href="?page=2">2</a></li>
        <li><a href="?page=3">3</a></li>
        <li><a href="?page=4">4</a></li>
        <li><a href="?page=5">5</a></li>
        <li><a href="?page=6">6</a></li>
        <li><a href="?page=7">7</a></li>
        <li><a href="?page=8">8</a></li>
        <li><a href="?page=9">9</a></li>
        <li><a href="?page=10">10</a></li>
        <li><a href="#">&raquo;</a></li>
    </ul>
</div>


<div class="box1" style="width:40%; margin:3% auto 10%;">
    <div class="form-group" style="width:17%; margin-right:2%; float:left;">
        <label class="sr-only" for="sop">sop</label>
        <select name=sop class="form-control">
            <option value=제목>제목</option>
            <option value=내용>내용</option>
            <option value=작성자>작성자</option>
        </select>
    </div>
    <div class="form-group" style="width:60%; float:left;">
        <label class="sr-only" for="stx">stx</label>
        <input name=stx maxlength=15 size=10 itemname="검색어" required value='' class="form-control">
    </div>
    <div class="form-group" >
        <button class="btn btn-default" style="float: right; width:17%;">검색</button>
    </div>
</div>



</body>
</html>
