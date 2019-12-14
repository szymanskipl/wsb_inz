<#-- @ftlvariable name="error" type="java.lang.String" -->

<#import "template.ftl" as layout />
<@layout.mainLayout title="Logowanie - Panel administracyjny">

<body class="login-page">
  <form action="/admin/login" method="post" enctype="application/x-www-form-urlencoded" class="login-box col-sm-4">
    <div class="logo"><img src="/static/img/logo.svg" /></div>
    <#if error??>
        <div class="alert alert-danger">
            Niepoprawne dane logowania
        </div>
    </#if>
    <div class="form-group">
      <label for="uname">Login</label>
      <input type="text" class="form-control" id="uname" placeholder="Wprowadź login" name="username" required>
    </div>
    <div class="form-group">
      <label for="pwd">Hasło</label>
      <input type="password" class="form-control" id="pwd" placeholder="Wprowadź hasło" name="password" required>
    </div>
    <div class="text-center">
    <button type="submit" class="btn btn-primary ">ZALOGUJ</button>
  </div>
  </form>
</body>
</@layout.mainLayout>