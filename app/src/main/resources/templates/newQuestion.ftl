<#import "template.ftl" as layout />
<@layout.mainLayout title="Nowe Pytanie - Panel administracyjny">
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
          <li>
            <a href="/admin/uczelnie"><img src="/static/img/icon_uczelnie.svg" />Uczelnie</a>
          </li>
          <li class="active">
            <a href="/admin/pytania"><img src="/static/img/icon_pytania.svg" />Pytania</a>
          </li>
          <li>
            <a href="/admin/wyloguj"><img src="/static/img/icon_wyloguj.svg" />Wyloguj</a>
          </li>
        </ul>
      </nav>

      <!-- Page Content -->
      <div id="content">
        <span class="university-form-head">Nowe pytanie</span>

        <form action="/admin/pytania/nowe" method="post" enctype="application/x-www-form-urlencoded" class="university-form">
          <div class="form-group course-description">
            <label for="formGroupExampleInput">Treść pytania</label>
            <input
              name="text"
              type="text"
              class="form-control"
              id="formGroupExampleInput"
              required
            />
          </div>

          <div class="form-group course-description">
              <label for="formGroupExampleInput">Odpowiedź A (pole obowiązkowe)</label>
              <input
                name="answers[]"
                type="text"
                class="form-control"
                id="formGroupExampleInput"
                required
              />
        </div>

        <div class="form-group">
                    <label for="category">Kategoria dla odpowiedzi A:</label>
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
              <label for="formGroupExampleInput">Odpowiedź B (pole obowiązkowe)</label>
              <input
                name="answers[]"
                type="text"
                class="form-control"
                id="formGroupExampleInput"
                required
              />
        </div>

        <div class="form-group">
                            <label for="category">Kategoria dla odpowiedzi B:</label>
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
              <label for="formGroupExampleInput">Odpowiedź C (pole opcjonalne)</label>
              <input
                name="answers[]"
                type="text"
                class="form-control"
                id="formGroupExampleInput"
              />
        </div>

        <div class="form-group">
                            <label for="category">Kategoria dla odpowiedzi C:</label>
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
              <label for="formGroupExampleInput">Odpowiedź D (pole opcjonalne)</label>
              <input
                name="answers[]"
                type="text"
                class="form-control"
                id="formGroupExampleInput"
              />
        </div>

        <div class="form-group">
                            <label for="category">Kategoria dla odpowiedzi D:</label>
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

          <div>
            <a
              href="/admin/pytania"
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