<#import "template.ftl" as layout />
<@layout.mainLayout title="Pytania - Panel administracyjny">
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
        <div class="card header">
          Pytania
          <a href="/admin/pytania/nowe"><span class="btn btn-primary add-btn">+ DODAJ NOWE</span></a>
        </div>
        <table class="table table-condensed" id="myTable">
          <tbody>
          <#list questions as question>
            <tr>
              <td class="col-sm-10" id="row-head">
                <div data-toggle="collapse" data-target="#row${question.id}" data-parent="#myTable">
                  <span class="head1">${question.text}</span>
                </div>
                <div id="row${question.id}" class="collapse">
                    <span class="head2">Odpowiedzi:</span>
                    <ol type="A">
                    <#list answers as answer>
                    <#if answer.questionId == question.id><li>${answer.text}</li></#if>
                    </#list>
                    </ol>
                </div>
              </td>
              <td class="col-sm-1 action-icons">
                    <a href="/admin/pytania/${question.id}/edycja">
                        <img src="/static/img/icon_edit.svg"/>
                    </a>
              </td>
              <td class="col-sm-1 action-icons">
                <a href="/admin/pytania/${question.id}/usun">
                   <img src="/static/img/icon_delete.svg"/>
                </a>
              </td>
            </tr>
            </#list>
          </tbody>
        </table>
      </div>
    </div>
  </body>
</@layout.mainLayout>