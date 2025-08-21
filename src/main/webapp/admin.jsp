<%@ page contentType="text/html" pageEncoding="UTF-8" %> <%@ taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="es">
  <head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width,initial-scale=1" />
    <title>ShopLite • Admin</title>
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
    />
    <link
      href="${pageContext.request.contextPath}/css/admin.css"
      rel="stylesheet"
    />
  </head>
  <body class="bg-light">
    <nav class="navbar navbar-expand-lg bg-white border-bottom">
      <div class="container">
        <a
          class="navbar-brand text-danger"
          href="${pageContext.request.contextPath}/home"
          >ShopLite • Admin</a
        >
      </div>
    </nav>

    <section class="container my-5" style="max-width: 720px">
      <c:if test="${param.err=='1'}">
        <div class="alert alert-danger">Datos inválidos</div>
      </c:if>
      <div class="card shadow-sm">
        <div class="card-body p-4">
          <h3 class="mb-3">Gestión de Productos</h3>
          <form
            method="post"
            action="${pageContext.request.contextPath}/admin"
            class="row g-3 mb-4"
          >
            <c:if test="${not empty productToEdit}">
              <input type="hidden" name="action" value="updateProduct" />
              <input type="hidden" name="id" value="${productToEdit.id}" />
            </c:if>
            <c:if test="${empty productToEdit}">
              <input type="hidden" name="action" value="addProduct" />
            </c:if>
            <div class="col-md-4">
              <label class="form-label">Nombre del Producto</label>
              <input
                class="form-control"
                name="name"
                placeholder="Nombre"
                value="${productToEdit.name}"
                required
              />
            </div>
            <div class="col-md-3">
              <label class="form-label">Precio</label>
              <input
                type="number"
                step="0.01"
                class="form-control"
                name="price"
                placeholder="Precio"
                value="${productToEdit.price}"
                required
              />
            </div>
            <div class="col-md-2">
              <label class="form-label">Stock</label>
              <input
                type="number"
                class="form-control"
                name="stock"
                placeholder="Stock"
                value="${productToEdit.stock}"
                required
              />
            </div>
            <div class="col-md-4">
              <label class="form-label">URL de Imagen</label>
              <input
                class="form-control"
                name="imageUrl"
                placeholder="/imagenes/producto.png"
                value="${productToEdit.imageUrl}"
              />
            </div>
            <div class="col-md-3 d-flex align-items-end">
              <button class="btn btn-success w-100">
                ${not empty productToEdit ? 'Guardar Cambios' : 'Agregar'}
              </button>
            </div>
          </form>

          <h4 class="mt-5 mb-3">Lista de Productos</h4>
          <div class="table-responsive">
            <table class="table table-striped table-hover">
              <thead>
                <tr>
                  <th>#</th>
                  <th>Nombre</th>
                  <th>Precio</th>
                  <th>Stock</th>
                  <th>Imagen</th>
                  <th>Acciones</th>
                </tr>
              </thead>
              <tbody>
                <c:forEach var="p" items="${products}">
                  <tr>
                    <td>${p.id}</td>
                    <td>${p.name}</td>
                    <td>Q${p.price}</td>
                    <td>${p.stock}</td>
                    <td>
                      <img
                        src="${pageContext.request.contextPath}${p.imageUrl}"
                        alt="${p.name}"
                        style="width: 50px; height: 50px; object-fit: cover"
                      />
                    </td>
                    <td>
                      <a
                        href="?accion=editar&id=${p.id}"
                        class="btn btn-sm btn-primary"
                        >Editar</a
                      >
                      <a
                        href="?accion=eliminar&id=${p.id}"
                        class="btn btn-sm btn-danger"
                        onclick="return confirm('¿Eliminar este producto?');"
                        >Eliminar</a
                      >
                    </td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </section>
  </body>
</html>
