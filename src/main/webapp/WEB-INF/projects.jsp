<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Projects</title>
<link rel="stylesheet" href="/webjars/bootstrap/4.5.0/css/bootstrap.min.css" />
<script src="/webjars/jquery/3.5.1/jquery.min.js"></script>
<script src="/webjars/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="/style/style.css" />
<link rel="stylesheet" href="/style/projects/projects.css" />
<link rel="stylesheet" href="/style/menu.css" />
</head>
<body>

	<div class="nav-container ">
		<div class="menu-row row">
			<div class="col p-0 m-0">
				<div class="left">
					<h1 id="logo">
						<a href="/">Freelance</a>
					</h1>
					<div class="links">
						<a href="/freelance/projects">Projects</a> 
						<a href="/freelance/freelancers">Freelancers</a>
					</div>
				</div>
			</div>
			<div class="col p-0 m-0 ">
				<span class="float-right"> 
					<c:if test="${isClient eq true }">
						<a href="/freelance/projects/new"><button class="post-project">+ Project</button></a>
						<span class="user-info"> 
							<a href="/client/profile/${user.id}">${user.firstname } ${user.lastname }</a>
							<img src="/images/user_pic.svg" alt="user" />
						</span>
					</c:if>
					
					<c:if test="${isClient eq false }">
						<span class="user-info"> 
							<a href="/freelancer/profile/${user.id}">${user.firstname } ${user.lastname }</a>
							<img src="/images/user_pic.svg" alt="user" />
						</span>
					</c:if>
					
					<a href="/logout"><img src="/images/icons/logout (1).png" alt="logout" style="width: 21px; margin: 0 0 0 11px; " /></a>
				</span>
			</div>
		</div>
	</div>

	<div class="dashboard-container">
		<div class="projects-container">
			<div class=" row p-0 m-0">
				
				<div class="col-sm-3 m-0 p-0">
					<div class="categories-container shadow-sm p-4">
						<h3>Categories</h3>
						<hr />
						<c:forEach items="${categories }" var="category">
							<div class="span">
								<a class="p-3 mb-3 w-100 shadow-sm" href="/freelance/projects/category/${category.id}"><p class="p-0 m-0">${category.title }</p></a>
							</div>
						</c:forEach>
					</div>
				</div>
				
				<div class="col-sm-9 m-0 p-0">
					<div class="row m-0">
						<div class="col-12 mx-4 px-4">
							<div class="search-container">
							<form action="/search" method="post">
								<div class="row m-0 p-0">
									<div class="col-sm-10 pl-0">
										<input type="search" placeholder="Search for projects" class="form-control shadow-sm" name="title"/>
									</div>
									<div class="col-sm-2 px-0">
										<input type="submit" class="btn btn-dark btn-block shadow-sm" value="Search"/>
									</div>
								</div>
							</form>
						</div>
						</div>
					</div>

					<div class="row m-0">
						<div class="col-12 px-4 mx-4">
							<c:forEach items="${projects }" var="project">
							<div class="project shadow-sm mt-5">
								<div class="card">
									<div class="header">
										<div class="row">
											<div class="col">
												<a href="/freelance/projects/${project.id}"><h3>${project.title }</h3></a>
											</div>
											<div class="col">
												<p class="float-right time"><fmt:formatDate value="${project.createdAt }" type="date" pattern="MMMM dd, yyyy - h:m a"/></p>
											</div>
										</div>
										<div class="row">
											<div class="col">
												<p class="m-0 p-0">${project.category.title }</p>
											</div>
										</div>
									</div>

									<div class="body">
										<p>${project.description }</p>
									</div>

									<div class="footer m-0 p-0">
										<hr />
										<div class="block">
											<div class="row">
												<div class="col-2">
													<p>Deadline</p>
													<p>3 days left</p>
												</div>

												<div class="col-2">
													<p>Offers</p>
													<p>${project.count(project.id) }/30</p>
												</div>
												<div class="col-8">
													<span class="float-right col3"> 
													<c:if test="${ isFreelancer eq true}">
															<c:choose>

																<c:when test="${project.isContain(user.id) }">
																${project.freelancers_like.size()}
																	<a href="/freelance/projects/${project.id }/unlike">
																	<img src="/images/icons/like.png" alt="like" /></a>
																</c:when>
																<c:otherwise>
																${project.freelancers_like.size()}
																	<a href="/freelance/projects/${project.id }/like">
																	<img src="/images/icons/unlike.png" alt="unlike" /></a>
																</c:otherwise>
															</c:choose>
															
															<c:choose>
																<c:when test="${project.isOffer(user.id) }">
																	<a href="/freelance/projects/${project.id }/withdraw" class="btn btn-dark">Cancel Offer</a>
																</c:when>
																<c:otherwise>
																	<a href="/freelance/projects/${project.id }/offer" class="btn btn-dark">Send Offer</a>
																</c:otherwise>
															</c:choose>
															
														</c:if>
													</span>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</c:forEach>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>