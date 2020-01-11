<#import "template.ftl" as layout />
<@layout.mainLayout title="Nowy kierunek - Panel administracyjny">
<body>
    <div class="wrapper">
      <!-- Sidebar -->
      <nav id="sidebar">
        <div class="sidebar-header">
          <img src="/static/img/logo.svg" width="200px" />
        </div>

        <ul class="list-unstyled components">
          <li>
            <a href="/admin/kierunki"><img src="/static/img/icon_kierunki.svg" />Kierunki</a>
          </li>
          <li class="active">
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
        <span class="university-form-head">Nowa uczelnia</span>

        <form action="/admin/uczelnie/nowa" method="post" enctype="application/x-www-form-urlencoded" class="university-form">
          <div class="form-group course-description">
            <label for="formGroupExampleInput">Nazwa uczelni</label>
            <input
              name="name"
              type="text"
              class="form-control"
              id="formGroupExampleInput"
              required
            />
          </div>

        <div class="form-group course-description">
            <label for="formGroupExampleInput">Miasto</label>
            <input
              name="city"
              type="text"
              class="form-control"
              id="formGroupExampleInput"
              required
            />
        </div>

        <div class="form-group course-description">
            <label for="formGroupExampleInput">Adres www</label>
                <input
                  name="urlAddress"
                  type="text"
                  class="form-control"
                  id="formGroupExampleInput"
                  required
                />
        </div>

        <div class="card courses">
          <div class="card-body">
              <h4 class="card-title">Dostępne kierunki</h4>
                <#list courses as course>
                <div class="form-check">
                  <label class="form-check-label">
                    <input
                      type="checkbox"
                      name="courses_list[]"
                      class="form-check-input"
                      value="${course.id}"
                    />${course.name}</label>
                </div>
                </#list>
          </div>
        </div>

          <div>
            <a
              href="/admin/uczelnie"
              id="cancel"
              name="cancel"
              class="btn btn-primary"
              >ANULUJ</a>
            <button type="submit" class="btn btn-primary">ZAPISZ</button>
          </div>
        </form>
      </div>
    </div>
</body>
</@layout.mainLayout>