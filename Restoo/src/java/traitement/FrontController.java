package traitement;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.reflections.Reflections;

public class FrontController extends HttpServlet {
    
    private HashMap listeMethods ;

    public HashMap getListeMethods() {
        return listeMethods;
    }
    
    public void setListeMethods(HashMap temp){
        this.listeMethods = temp ;
    }

    public String [] spliturl(String initurl, PrintWriter out){
        String controller = initurl.split("/")[2];            
        String[] url = (controller.split("\\.")[0]).split("-");
        out.println("<h4>Method:" +url[0] + " </h4>");
        out.println("<h4> control Class:" + url[1] + " </h4>");
        return url;
    }
    
    public Class needclass(String classname) throws Exception{ 
        Class encours = null;
        try {
            encours = Class.forName("classes."+classname);
            return encours;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
    
    public Class getclass(String classname, PrintWriter out){
        Class trouverclass = null;
        try {
            trouverclass = this.needclass(classname);
        } catch (Exception ex) {
            Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return trouverclass;
    }
    
    public Method needmethod(Class c, String method) throws Exception{ 
        try {
            for (Method declaredMethod : c.getDeclaredMethods()) {
                if (declaredMethod.getName().equalsIgnoreCase(method)) {
                    return declaredMethod;
                }
            }
        } catch (SecurityException e) {
            return null;
        }
        return null ;
    }
    
    public Method getmethode(Class now, String method, PrintWriter out){
        Method meth = null;
        try {
            meth = this.needmethod(now, method);
        } catch (Exception ex) {
            Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return meth;
    }
    
    public ModelView invokemethode(Object inclass, Method function, Object[] listeObject){
        ModelView retour = null;
        try {
            retour = (ModelView) function.invoke(inclass, listeObject);
        } catch (IllegalAccessException | IllegalArgumentException | SecurityException | InvocationTargetException ex) {
            Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retour;
    }
    
    public String getAnnotationName(String uri){
        String result = "";
        String[] splitted = uri.split("/");
        for (String splitt : splitted) {
            if (splitt.contains(".do")) {
                result = splitt.split(".do")[0];
            }
        }
        return result ;
    }
    
    public void setListeMethod(String defaultPackage, HashMap listeMethod){
        Reflections reference = new Reflections(defaultPackage);
        Set<Class<?>> classes = reference.getTypesAnnotatedWith(ANNOTCLASS.class);
        
        for (Class classe : classes){
            Method[] methods = classe.getMethods();
            for (Method meth : methods) {
                if (meth.isAnnotationPresent(ANNOT.class)){
                    ArrayList value = new ArrayList();
                    value.add(meth);
                    value.add(classe);
                    listeMethod.put(meth.getAnnotation(ANNOT.class).name(),value);
                }
            }
        } 
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {   
            String urlactuely = request.getRequestURI();

           
            Method meth = null ;
            Class trouverclass = null ;
            
            if (this.getListeMethods() == null){
                this.setListeMethods(new HashMap());
                this.setListeMethod("classes", this.getListeMethods());
            }
            
            
            String annotation = this.getAnnotationName(urlactuely);
            ArrayList methClasse = null ;
           
            if (this.getListeMethods().get(annotation) != null){
                methClasse = (ArrayList) this.getListeMethods().get(annotation);
                meth = (Method) methClasse.get(0);
                trouverclass = (Class) methClasse.get(1);
            }
                 
            
            if (methClasse == null){
                String[] url = this.spliturl(urlactuely, out);
                trouverclass = this.getclass(url[1], out);
                meth = this.getmethode(trouverclass, url[0], out);
            }
                     
            
            RequestDispatcher dispat = request.getRequestDispatcher("#");
            if (meth != null && trouverclass != null){
                Constructor construct = trouverclass.getConstructor(new Class[0]);
                Object current = construct.newInstance(new Object[0]);
                
                if (request.getParameter("#setClassField") != null){
                    try {
                        current = this.setClassField(request, current, out, "");
                    } catch (NoSuchMethodException ex) {
                        Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                Object[] paramInvoke = new Object[0] ;
                boolean reqReq = false ;
                if (request.getParameter("requestArgumentMethod") != null) {
                    reqReq = true ;
                    paramInvoke = new Object[1] ;
                    paramInvoke[0] = request ;
                } 
                if ((request.getParameter("responseArgumentMethod") != null)) {
                    if (reqReq) {
                        paramInvoke = new Object[2] ;
                        paramInvoke[0] = request ;
                        paramInvoke[1] = response ;
                    } else {
                        paramInvoke = new Object[1] ;
                        paramInvoke[0] = response ;
                    }
                }
                
                ModelView hattr = this.invokemethode(current, meth, paramInvoke);//--------------------------------------------------------
                if(hattr != null){
                    
                   
                    
                    String exc = "?exc=" ;
                    if (hattr.getMap() != null){
                        if (hattr.getMap().get("exception") != null) {
                            exc += hattr.getMap().get("exception") ;
                        }
                    }
                    String urlDispatch = hattr.getUrl()+".jsp" ;
                    if (!exc.equalsIgnoreCase("?exc=")) urlDispatch += exc ;
                    
                    dispat = request.getRequestDispatcher(urlDispatch);
                    int indice = 0 ;
                    for (Iterator i=hattr.getMap().keySet().iterator(); i.hasNext();){
                        Object temp = i.next();
                        request.setAttribute(temp.toString(), hattr.getMap().get(temp));
                        indice ++ ;
                    }
                    request.setAttribute("#included", hattr.getInclude()) ;
                    request.setAttribute("taille", indice) ;
                }
            }
            
            out.println("</body>");
            out.println("</html>");
            
            dispat.forward(request, response);
        }
    }
   
    public Object setClassField(HttpServletRequest request, Object current, PrintWriter out, String className) throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException{
        Method tempMethod = null;
        Class[] paramTypes = new Class[1];
        Object[] listeObject = new Object[1];
        String requestName = null ;
        
        for(Field temp : current.getClass().getDeclaredFields()){
            if (!temp.getName().equalsIgnoreCase("id")){
                paramTypes[0] = temp.getType();
                tempMethod = current.getClass().getDeclaredMethod(this.getSetterMethodName(temp), paramTypes);
                
                String tempName = className+temp.getName() ;
                if (request.getParameter(tempName) != null){
                    this.setListeParameter(listeObject, paramTypes[0], request.getParameter(tempName));
                    this.invokemethode(current, tempMethod, listeObject);
                } else {
                    if (tempName.contains(".")){
                        Object nextCurrent = Class.forName(temp.getType().getName()).getConstructor(new Class[0]).newInstance(new Object[0]);
                        requestName = this.getRequestName(className+temp.getName(), temp);
                        listeObject[0] = this.setClassField(request, nextCurrent, out, requestName);
                        this.invokemethode(current, tempMethod, listeObject);   
                    }
                }                
            }
        }
        
        return current;
    }
    
    public String getRequestName(String currentRequestName, Field toAddInRequestName){
        if (!currentRequestName.contains(".")) return toAddInRequestName.getName()+".";
        String[] requestNameSplitted = currentRequestName.split("\\.");
        String result = "" ;
        for (int i=0; i<requestNameSplitted.length - 1; i++){
            result += requestNameSplitted[i]+".";
        }
        result += toAddInRequestName.getName()+".";
        return result ;
    }
    
    public void setListeParameter(Object[] listeObject, Class fieldClass, String request){
        listeObject[0] = request;
        if (fieldClass.getSimpleName().equalsIgnoreCase("Int")){
            listeObject[0] = Integer.valueOf(request);
        }
        if (fieldClass.getSimpleName().equalsIgnoreCase("Float")){
            listeObject[0] = Float.valueOf(request);
        }
        if (fieldClass.getSimpleName().equalsIgnoreCase("Double")){
            listeObject[0] = Double.valueOf(request);
        }
    }
    
    public String getSetterMethodName(Field attr){
        String result = "set";
        result = result.concat(attr.getName().substring(0, 1).toUpperCase()).concat(attr.getName().substring(1, attr.getName().length()));
        return result;
    }
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException ex) {
            Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
