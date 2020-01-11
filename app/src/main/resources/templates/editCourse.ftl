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
              <option value="K1" <#if course.category == "K1">selected</#if>>K1 [A-Człowiek]</option>
              <option value="K2" <#if course.category == "K2">selected</#if>>K2 [B-Człowiek]</option>
              <option value="K3" <#if course.category == "K3">selected</#if>>K3 [A-Technika]</option>
              <option value="K4" <#if course.category == "K4">selected</#if>>K4 [B-Technika]</option>
              <option value="K5" <#if course.category == "K5">selected</#if>>K5 [A-Dane]</option>
              <option value="K6" <#if course.category == "K6">selected</#if>>K6 [B-Dane]</option>
              <option value="K7" <#if course.category == "K7">selected</#if>>K7 [A-Sztuka]</option>
              <option value="K8" <#if course.category == "K8">selected</#if>>K8 [B-Sztuka]</option>
              <option value="K9" <#if course.category == "K9">selected</#if>>K9 [A-Zdrowie]</option>
              <option value="K10" <#if course.category == "K10">selected</#if>>K10 [B-Zdrowie]</option>
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