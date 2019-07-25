<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<tag:html title="Главная" message="${message}">
    <body>
    <h2 style="text-align: center; color: purple">Приветствуем вас на нашем
        сайте!</h2>
    <c:out value="${sessionScope.lang}"></c:out>
    <div class="post">
        <div class="post_img"><img alt="Photo on main page"
                                   style="object-position: 50% 0;"
                                   src="../../img/motivation.jpg">
        </div>
        <div class="post_description">
            <h4>Если вы не можете найти в себе силы сдвинуться с мёртвой точки и
                начать работать
                , то наши тренеры не только мотивируют вас, но и научат
                здоровому образу жизни, правильному питанию и вере в свои
                силы.</h4>
        </div>
    </div>
    <div class="post">
        <div class="post_img">
            <img alt="Photo on main page"
                 style="object-position: 50% 0; height: 400px;"
                 src="../../img/bMipYRFqfjI.jpg">
        </div>
        <div class="post_description">
            <h4>В любом деле самое главное - это начать. Ведь когда сделан
                первый шаг,
                все последующие шаги даются уже гораздо проще, потому что
                появляется
                уверенность в себе и в своих силах.
                Да, в голове возникают такие вопросы, как зачем мне это нужно,
                не стоит
                ли мне сейчас бросить и заниматься другими
                делами. Но ты осознаёшь, что раз уже начал что-то стоящее, то
                нужно это доводить до конца.</h4>
        </div>
    </div>
    </body>
</tag:html>
