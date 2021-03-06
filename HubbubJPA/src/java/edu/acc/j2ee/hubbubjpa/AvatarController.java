package edu.acc.j2ee.hubbubjpa;

import edu.acc.j2ee.hubbubjpa.domain.HubbubJpaDao;
import edu.acc.j2ee.hubbubjpa.domain.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AvatarController extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String subject = request.getParameter("for");
        if (subject == null || subject.length()== 0)
            subject = ((User)request.getSession().getAttribute("user")).getUsername();
        try {
            HubbubJpaDao dao = (HubbubJpaDao)this.getServletContext().getAttribute("dao");
            User of = dao.findUserByUsername(subject);
            if (of != null) {
                String mime = of.getProfile().getMime();
                byte[] avatar = of.getProfile().getAvatar();
                if (avatar == null || mime == null) {
                    response.sendRedirect("images/domo.jpg");
                    return;
                }
                response.setContentType(mime);
                response.getOutputStream().write(avatar);
            }
            else
                response.sendRedirect("images/domo.jpg");
        } catch (IOException ioe) {
            response.sendRedirect("images/domo.jpg");
        } 
    }
}
