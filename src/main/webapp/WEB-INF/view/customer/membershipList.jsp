<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zxx">

<head>
    <meta charset="UTF-8">
    <meta name="description" content="TopGym Template">
    <meta name="keywords" content="TopGym, unica, creative, html">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>GD HEALTH</title>

    <!-- Google Font -->
    <link href="https://fonts.googleapis.com/css?family=Roboto:100,300,300i,400,500,700,900" rel="stylesheet">

    <!-- Css Styles -->
    <link rel="stylesheet" href="/css/bootstrap.min.css" type="text/css">
    <link rel="stylesheet" href="/css/font-awesome.min.css" type="text/css">
    <link rel="stylesheet" href="/css/owl.carousel.min.css" type="text/css">
    <link rel="stylesheet" href="/css/magnific-popup.css" type="text/css">
    <link rel="stylesheet" href="/css/barfiller.css" type="text/css">
    <link rel="stylesheet" href="/css/slicknav.min.css" type="text/css">
    <link rel="stylesheet" href="/css/style.css" type="text/css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>

<body>
    <!-- Page Preloder -->
    <div id="preloder">
        <div class="loader"></div>
    </div>


     <jsp:include page="/WEB-INF/header/header.jsp" />
    <!-- Breadcrumb Section Begin -->
    <section class="breadcrumb-area set-bg" data-setbg="/img/elements/element-bg.jpg">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <div class="breadcrumb-content">
                        <h2>회원권 구매</h2>
                        <div class="links">
                            <a href="/customer/home">Home</a>
                            
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
	
		<section>
			<div id="membershipList">
				<c:forEach var="m" items="${membershipList}">
					<div style="border-radius:10px; border-style:solid; border-color:black; background-color:#F6F6F6; margin:1%;">
						<div style="margin:1%;">
							<p>회원권 제목 : ${m.membershipName}</p>
							<p>회원권 개월수 : ${m.membershipMonth}개월</p>
							<p>회원권 가격 : ${m.membershipPrice}원</p>
							<a href="#" class="btn btn-primary" onclick="checkAndRedirect(${m.membershipNo}, ${m.membershipPrice}, '${m.membershipName}', ${m.membershipMonth})">결제하기</a>
						</div>
					</div>
				</c:forEach>
			</div>
		</section>
	
     <jsp:include page="/WEB-INF/footer/footer.jsp" />

    <!-- Js Plugins -->
    <script src="/js/jquery-3.3.1.min.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <script src="/js/jquery.slicknav.js"></script>
    <script src="/js/owl.carousel.min.js"></script>
    <script src="/js/jquery.magnific-popup.min.js"></script>
    <script src="/js/circle-progress.min.js"></script>
    <script src="/js/jquery.barfiller.js"></script>
    <script src="/js/main.js"></script>
<script>
    function checkAndRedirect(membershipNo, membershipPrice, membershipName, membershipMonth) {
    	var a = membershipNo;
    	console.log(a);
    	console.log(membershipNo);
    	var b = membershipPrice;
    	var c = membershipName;
    	var d = membershipMonth;
        // 서버로부터 상품 정보를 가져오는 비동기 함수 (가정)
        fetch('/customer/checkMembership', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            }, 
        })
        .then(response => response.json())
        .then(data => {
            // 여기서 data를 확인하여 이미 상품이 있는 경우 바로 페이지 이동
            if (data) {
                // 상품이 있는 경우에만 confirm 대화상자 띄우기
                if (confirm("상품이 이미 있습니다. 페이지를 이동하시겠습니까?")) {
                    const url = '${pageContext.request.contextPath}/customer/updatePayment?membershipNo='+a+'&membershipPrice='+b+'&membershipName='+c+'&membershipMonth='+d+'';
                    window.location.href = url;
                }
            } else {
                // 상품이 없는 경우에 바로 페이지 이동
                    const url = '${pageContext.request.contextPath}/customer/addPayment?membershipNo='+a+'&membershipPrice='+b+'&membershipName='+c+'';
              	    window.location.href = url;
            }
        })
        .catch(error => console.error('Error:', error));
    }
</script>
</body>

</html>