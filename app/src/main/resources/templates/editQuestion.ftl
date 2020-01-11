<#import "template.ftl" as layout />
<@layout.mainLayout title="Edycja Pytania - Panel administracyjny">
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
        <span class="university-form-head">Edytuj pytanie</span>

        <form action="/admin/pytania/${question.id}/edycja" method="post" enctype="application/x-www-form-urlencoded" class="university-form">
          <div class="form-group course-description">
            <label for="formGroupExampleInput">Treść pytania</label>
            <input
              name="text"
              type="text"
              class="form-control"
              id="formGroupExampleInput"
              value="${question.text}"
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
                value="${answers[0].text}"
                required
              />
        </div>

          <div class="form-group course-description">
              <label for="formGroupExampleInput">Odpowiedź B (pole obowiązkowe)</label>
              <input
                name="answers[]"
                type="text"
                class="form-control"
                id="formGroupExampleInput"
                value="${answers[1].text}"
                required
              />
        </div>

          <div class="form-group course-description">
              <label for="formGroupExampleInput">Odpowiedź C (pole opcjonalne)</label>
              <input
                name="answers[]"
                type="text"
                class="form-control"
                id="formGroupExampleInput"
                <#if answers[2]??>value="${answers[2].text}"</#if>
              />
        </div>

          <div class="form-group course-description">
              <label for="formGroupExampleInput">Odpowiedź D (pole opcjonalne)</label>
              <input
                name="answers[]"
                type="text"
                class="form-control"
                id="formGroupExampleInput"
                <#if answers[3]??>value="${answers[3].text}"</#if>
              />
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