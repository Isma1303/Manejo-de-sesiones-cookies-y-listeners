<%@ page contentType="text/html" pageEncoding="UTF-8" %> <%@ taglib
        uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="utf-8" />
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <title>Acceso denegado - 403</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <link href="${pageContext.request.contextPath}/css/403.css" rel="stylesheet" />
</head>

<body>
<div class="error-container">
  <svg
          class="animal-svg"
          viewBox="0 0 64 64"
          fill="none"
          xmlns="http://www.w3.org/2000/svg"
          aria-hidden="true"
  >
    <circle cx="32" cy="32" r="30" fill="#d6336c" />
    <path d="M21 40c0 4 10 4 10 0s-10-4-10 0z" fill="white" />
    <circle cx="26" cy="28" r="4" fill="white" />
    <circle cx="38" cy="28" r="4" fill="white" />
    <circle cx="26" cy="28" r="2" fill="#d6336c" />
    <circle cx="38" cy="28" r="2" fill="#d6336c" />
  </svg>

  <div class="error-code">403</div>
  <div class="error-message">No tienes permisos para ver esta página.</div>
  <a
          class="btn btn-primary"
          href="${pageContext.request.contextPath}/login.jsp"
  >Ir a Login</a
  >
</div>
</body>
</html>
