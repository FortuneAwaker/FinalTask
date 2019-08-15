package by.popovich.last.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.popovich.last.action.Action;
import by.popovich.last.action.ActionManager;
import by.popovich.last.action.ActionManagerFactory;
import by.popovich.last.action.Forward;
import by.popovich.last.dao.mysql.TransactionFactoryImpl;
import by.popovich.last.dao.pool.ConnectionPool;
import by.popovich.last.exception.PersistentException;
import by.popovich.last.service.ServiceFactory;
import by.popovich.last.service.ServiceFactoryImpl;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * Servlet for all requests.
 */
@MultipartConfig(maxFileSize = 16177215)
public class DispatcherServlet extends HttpServlet {
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager
      .getLogger(DispatcherServlet.class);

    /**
     * Log file name.
     */
    public static final String LOG_FILE_NAME = "log.txt";
    /**
     * Log level.
     */
    public static final Level LOG_LEVEL = Level.ALL;
    /**
     * Message format.
     */
    public static final String LOG_MESSAGE_FORMAT = "%n%d%n%p\t%C.%M:%L%n%m%n";

    /**
     * JDBC driver.
     */
    public static final String DB_DRIVER_CLASS = "com.mysql.jdbc.Driver";
    /**
     * Database url.
     */
    public static final String DB_URL
      = "jdbc:mysql://localhost:3306/sport_club?"
      + "useUnicode=true&characterEncoding=UTF-8";
    /**
     * Database user.
     */
    public static final String DB_USER = "club_user";
    /**
     * Database password.
     */
    public static final String DB_PASSWORD = "club_password";
    /**
     * Start size of connection pool.
     */
    public static final int DB_POOL_START_SIZE = 20;
    /**
     * Max size of connection pool.
     */
    public static final int DB_POOL_MAX_SIZE = 2000;
    /**
     * Timeout.
     */
    public static final int DB_POOL_CHECK_CONNECTION_TIMEOUT = 0;

    /**
     * Init.
     */
    public void init() {
        try {
            Logger root = Logger.getRootLogger();
            Layout layout = new PatternLayout(LOG_MESSAGE_FORMAT);
            root.addAppender(new FileAppender(layout, LOG_FILE_NAME,
              true));
            root.addAppender(new ConsoleAppender(layout));
            root.setLevel(LOG_LEVEL);
            ConnectionPool.getInstance().init(DB_DRIVER_CLASS, DB_URL,
              DB_USER, DB_PASSWORD, DB_POOL_START_SIZE, DB_POOL_MAX_SIZE,
              DB_POOL_CHECK_CONNECTION_TIMEOUT);
        } catch (PersistentException | IOException e) {
            LOGGER.error(
              "It is impossible to initialize application", e);
            destroy();
        }
    }

    /**
     * Getter for service factory.
     *
     * @return service factory
     * @throws PersistentException if error in DB handling.
     */
    public ServiceFactory getFactory() throws PersistentException {
        return new ServiceFactoryImpl(new TransactionFactoryImpl());
    }

    /**
     * Method GET.
     *
     * @param request  request.
     * @param response response.
     * @throws IOException      IOException.
     * @throws ServletException ServletException.
     */
    public void doGet(final HttpServletRequest request,
                      final HttpServletResponse response)
      throws IOException, ServletException {
        process(request, response);
    }

    /**
     * Method POST.
     *
     * @param request  request.
     * @param response response.
     * @throws IOException      IOException.
     * @throws ServletException ServletException.
     */
    public void doPost(final HttpServletRequest request,
                       final HttpServletResponse response)
      throws IOException, ServletException {
        process(request, response);
    }

    /**
     * Method processes GET and POST.
     *
     * @param request  request.
     * @param response response.
     * @throws IOException      IOException.
     * @throws ServletException ServletException.
     */
    private void process(final HttpServletRequest request,
                         final HttpServletResponse response)
      throws IOException, ServletException {
        Action action = (Action) request.getAttribute("action");
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                @SuppressWarnings("unchecked")
                Map<String, Object> attributes = (Map<String, Object>)
                  session.getAttribute("redirectedData");
                if (attributes != null) {
                    for (String key : attributes.keySet()) {
                        request.setAttribute(key, attributes.get(key));
                    }
                    session.removeAttribute("redirectedData");
                }
            }
            ActionManager actionManager = ActionManagerFactory
              .getManager(getFactory());
            LOGGER.info(action.getName());
            Forward forward = actionManager.execute(action, request, response);
            actionManager.close();
            if (session != null && forward != null
              && !forward.getAttributes().isEmpty()) {
                session.setAttribute("redirectedData", forward.getAttributes());
            }
            String requestedUri = request.getRequestURI();
            if (forward != null && forward.isRedirect()) {
                String redirectedUri = request.getContextPath()
                  + forward.getForward();
                LOGGER.debug(String.format(
                  "Request for URI \"%s\" id redirected to URI \"%s\"",
                  requestedUri, redirectedUri));
                response.sendRedirect(redirectedUri);
            } else {
                String jspPage;
                if (forward != null) {
                    jspPage = forward.getForward();
                } else {
                    jspPage = action.getName() + ".jsp";
                }
                jspPage = "/WEB-INF/jsp" + jspPage;
                LOGGER.debug(String.format(
                  "Request for URI \"%s\" is forwarded to JSP \"%s\"",
                  requestedUri, jspPage));
                getServletContext().getRequestDispatcher(jspPage)
                  .forward(request, response);
            }
        } catch (PersistentException e) {
            LOGGER.error("It is impossible to process request", e);
            request.setAttribute("error", "Ошибка обработки данных");
            getServletContext().getRequestDispatcher(
              "/WEB-INF/jsp/error.jsp").forward(request, response);
        }
    }
}
