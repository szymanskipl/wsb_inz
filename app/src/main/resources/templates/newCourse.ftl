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
        <span class="university-form-head">Nowy kierunek</span>

        <form action="/admin/kierunki/nowy" method="post" enctype="application/x-www-form-urlencoded" class="university-form">
          <div class="form-group course-description">
            <label for="formGroupExampleInput">Nazwa kierunku</label>
            <input
              name="name"
              type="text"
              class="form-control"
              id="formGroupExampleInput"
              required
            />
          </div>

          <div class="form-group">
            <label for="category">Kategoria</label>
            <select class="form-control" name="category" id="category">
              <option value="K1">K1 [A-Człowiek]</option>
              <option value="K2">K2 [B-Człowiek]</option>
              <option value="K3">K3 [A-Technika]</option>
              <option value="K4">K4 [B-Technika]</option>
              <option value="K5">K5 [A-Dane]</option>
              <option value="K6">K6 [B-Dane]</option>
              <option value="K7">K7 [A-Sztuka]</option>
              <option value="K8">K8 [B-Sztuka]</option>
              <option value="K9">K9 [A-Zdrowie]</option>
              <option value="K10">K10 [B-Zdrowie]</option>
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
            ></textarea>
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