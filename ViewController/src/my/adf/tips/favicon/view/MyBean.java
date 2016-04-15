package my.adf.tips.favicon.view;

import adf.util.EL;

import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import oracle.adf.view.rich.context.AdfFacesContext;
import oracle.adf.view.rich.render.ClientEvent;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class MyBean {

    public void refreshFaviconListener(ActionEvent ae) {
        String faviconType = ae.getComponent().getAttributes().get("faviconType").toString();
        EL.set("#{pageFlowScope.favicon}", faviconType);
        
        String servLisParId = ae.getComponent().getAttributes().get("servLisParId").toString();
        
        FacesContext context = FacesContext.getCurrentInstance();
        UIComponent parentComoponent = context.getViewRoot().findComponent(servLisParId);

        String resourcePath = parentComoponent.getAttributes().get("resourcePath").toString();
        String faviconName = parentComoponent.getAttributes().get("faviconName").toString();
        String reqContextPath = (String) EL.get("#{facesContext.externalContext.requestContextPath}");

        String faviconPath = reqContextPath + resourcePath + faviconType + faviconName;

        String servLisType = parentComoponent.getAttributes().get("servLisType").toString();
        
        String script = "defineBrowserType({ 'faviconPath':'"+ faviconPath +"', 'servLisParId' : '"+ servLisParId 
                                        +"', 'servLisType' : '"+servLisType+"'});";
        addScriptOnPartialRequest(script);
    }

    /**
     * Add a script to the render kit
     */
    public static void addScriptOnPartialRequest(String script) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (AdfFacesContext.getCurrentInstance().isPartialRequest(context)) {
            ExtendedRenderKitService erks = Service.getRenderKitService(context, ExtendedRenderKitService.class);
            erks.addScript(context, script);
        }
    }

    /** 
     * Refresh page serverListener
     */
    public void refreshPage(ClientEvent clientEvent) {
        FacesContext fctx = FacesContext.getCurrentInstance();
        String page = fctx.getViewRoot().getViewId();
        ViewHandler ViewH = fctx.getApplication().getViewHandler();
        UIViewRoot UIV = ViewH.createView(fctx, page);
        UIV.setViewId(page);
        fctx.setViewRoot(UIV);
    }
    
}
