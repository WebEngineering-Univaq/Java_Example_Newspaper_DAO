package it.univaq.f4i.iw.ex.newspaper.controller;

import it.univaq.f4i.iw.ex.newspaper.data.dao.NewspaperDataLayer;
import it.univaq.f4i.iw.framework.data.DataException;
import it.univaq.f4i.iw.framework.result.DataModelFiller;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author giuse
 */
public class DummyModelFiller implements DataModelFiller {

    @Override
    public void fillDataModel(Map datamodel, HttpServletRequest request, ServletContext context) {
        //datamodel.put("current_timestamp", Calendar.getInstance().getTime());
        try {
            datamodel.put("latest_issue", ((NewspaperDataLayer) request.getAttribute("datalayer")).getIssueDAO().getLatestIssue());
        } catch (DataException ex) {
            Logger.getLogger(DummyModelFiller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
