<#import "template.ftl" as layout />
<@layout.mainLayout title="Edycja kierunku - Panel administracyjny">
<body>
    <div class="wrapper">
      <!-- Sidebar -->
      <nav id="sidebar">
        <div class="sidebar-header">
          <img src="/static/img/logo.svg" width="200px" />
        </div>

        <ul class="list-unstyled components">
          <li class="active">
            <a href="/admin/kierunki"><img src="/static/img/icon_kierunki.svg" />Kierunki</a>
          </li>
          <li>
            <a href="/admin/uczelnie"><img src="/static/img/icon_uczelnie.svg" />Uczelnie</a>
          </li>
          <li>
            <a href="/admin/pytania"><img src="/static/img/icon_pytania.svg" />Pytania</a>
          </li>
          <li>
            <a href="/admin/wyloguj"><img src="/static/img/icon_wyloguj.svg" />Wyloguj</a>
          </li>
        </ul>
      </nav>

      <!-- Page Content -->
      <div id="content">
        <span class="university-form-head">Edytuj kierunek</span>

        <form action="/admin/kierunki/${course.id}/edycja" method="post" enctype="application/x-www-form-urlencoded" class="university-form">
          <div class="form-group course-description">
            <label for="formGroupExampleInput">Nazwa kierunku</label>
            <input
              name="name"
              type="text"
              class="form-control"
              id="formGroupExampleInput"
              value="${course.name}"
              required
            />
          </div>

          <div class="form-group">
            <label for="category">Kategoria</label>
            <select class="form-control" name="category" id="category">
              <#list categories as categories>
                <option value="${categories.id}" <#if categories.id == course.categoryId>selected</#if>>${categories.name}</option>
              </#list>
            </select>
          </div>

          <div class="form-group course-description">
            <label for="formGroupExampleInput2">Opis kierunku</label>
            <textarea
              class="form-control"
              rows="5"
              name="description"
              id="formGroupExampleInput2"
              required
            >${course.description}</textarea>
          </div>

          <div>
            <a
              href="/admin/kierunki"
              id="cancel"
              name="cancel"
              class="btn btn-primary"
              >ANULUJ</a
            >
            <button type="submit" class="btn btn-primary">ZAPISZ</button>
          </div>
        </form>
      </div>
    </div>
</body>
</@layout.mainLayout>