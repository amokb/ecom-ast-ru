package ru.nuzmsh.web.tags;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * @jsp.tag name="hideException"
 *          display-name="hideException"
 *          body-content="scriptless"
 *          description="hideException"
 */
public class HideExceptionTag extends SimpleTagSupport {

    private static final Logger LOG = Logger.getLogger(HideExceptionTag.class) ;
    private static final boolean CAN_TRACE = LOG.isDebugEnabled() ;


    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut() ;
        try {
            getJspBody().invoke(out);
        } catch (Exception e) {
            if(CAN_TRACE) LOG.info("Ошибка",e); ;
            out.print("<div class='error'>") ;
            out.print(e.getMessage()) ;
            out.print("</div>") ;
        }
    }


}
