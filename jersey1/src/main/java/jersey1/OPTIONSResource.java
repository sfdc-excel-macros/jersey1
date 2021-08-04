package jersey1;

import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import java.util.*;
import java.util.logging.*;
import javax.ws.rs.QueryParam;
import java.io.*;
import javax.ws.rs.PathParam;
import java.net.URI;
import javax.ws.rs.Produces;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Path("optionsresource")
public class OPTIONSResource {
    private Logger logger = Logger.getLogger(getClass().getName());

    @Context private HttpServletRequest request;
    @Context private HttpServletResponse response;

    public static class CustomObject {
        public String name;
        public List<String> aliases;
        public int age;
    }

    @OPTIONS
    @Produces("application/json")
    @Path("getobj1/{id}")
    public CustomObject getobj1() {
        CustomObject obj = new CustomObject();
        obj.name = "foo";
        obj.aliases = Arrays.asList(new String[] { "baz", "quux" });
        obj.age = 1337;
        return obj;

    }

    @OPTIONS
    @Path("body3")
    @Produces("text/html")
    public String body3(CustomObject obj) {
        if(obj.age > 100) {
            return "wow! " + obj.name;      
        } else {
            StringBuffer sb = new StringBuffer("aliases:");
            if(obj.aliases != null) {
                for(String alias: obj.aliases) {
                    sb.append(" ").append(alias);
                }
            }
            return sb.toString();           
        }
    }

    @OPTIONS
    @Path("body4")
    @Produces("text/html")
    public String body4(CustomObject obj) {
        return helper1(obj);        
    }


    public String helper1(CustomObject obj) {
        if(obj.age > 100) {
            return "wow! " + obj.name;
        } else {
            StringBuffer sb = new StringBuffer("aliases:");
            if(obj.aliases != null) {
                for(String alias: obj.aliases) {
                    sb.append(" ").append(alias);
                }
            }
            return sb.toString();
        }
    }
    public String helper2(CustomObject obj) {
        if(obj.age > 100) {
            return "wow! " + obj.name;
        } else {
            StringBuffer sb = new StringBuffer("aliases:");
            if(obj.aliases != null) {
                for(String alias: obj.aliases) {
                    sb.append(" ").append(alias);
                }
            }
            return sb.toString();
        }
    }



    @OPTIONS
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }

    @QueryParam("bad")
    private String bad1;

    private String good1 = "nothing";

    @OPTIONS
    @Path("second")
    @Produces(MediaType.TEXT_HTML)
    public String second() {
        return "hello<b>hello</b>bad: " + bad1;  
    }

    @OPTIONS
    @Path("responsesink1/{id}")
    public void responsesink1(@PathParam("id") String pp) {
        try {
            response.getWriter().print("responsesink1: ");
            response.getWriter().println(pp);   
        } catch (IOException ioe) {
        }
    }


    @OPTIONS
    @Path("body1/{id}")
    @Produces(MediaType.TEXT_HTML)
    public String body1(String reqbody) {
        return "Body: " + reqbody;      
    }

    @OPTIONS
    @Path("body2/{id}")
    @Produces("text/html")
    public String body2(@PathParam("id") String idval, String reqbody) {
        if(idval.contains("something")) {
            return "id: " + idval;         
        } else {
            return "Body: " + reqbody;    
        }
    }


    @OPTIONS
    @Path("inputstream1/{id}")
    @Produces(MediaType.TEXT_HTML)
    public String inputstream1(InputStream is) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String body = reader.readLine();
            return "body: " + body;     
        } catch (IOException ioe) {
            logger.log(Level.SEVERE, "inputstream1", ioe);
            return "error in inputstream1";
        }
    }

    @OPTIONS
    @Path("badresp1")
    public Response badresp1() {
        return Response.ok("badresp1: " + bad1, "text/html").build();       
    }

    @OPTIONS
    @Path("badresp2/{id}")
    public Response badresp2(@PathParam("id") String whatever) {
        return Response.ok("badresp2: " + whatever, "text/html").build();       
    }


    @OPTIONS
    @Path("redir")
    public Response redir() {
        return Response.seeOther(URI.create("https://www.lycos.com")).build();
    }

    @OPTIONS
    @Path("badredir1")
    public Response badredir1() {
        return Response.seeOther(URI.create(bad1)).build();     
    }

    @OPTIONS
    @Path("badredir2")
    public Response badredir2(@PathParam("url") String u) {
        return Response.seeOther(URI.create(u)).build();        
    }

    @OPTIONS
    @Path("badstream1")
    @Produces(MediaType.TEXT_HTML)
    public StreamingOutput badstream1() {
        return new StreamingOutput() {
            public void write(OutputStream os) throws IOException {
                OutputStreamWriter writer = new OutputStreamWriter(os);
                writer.write(bad1);       
            }
        };
    }

    @OPTIONS
    @Path("badstream2")
    @Produces(MediaType.TEXT_HTML)
    public StreamingOutput badstream2() {
        return new StreamingOutput() {
            public void write(OutputStream os) throws IOException {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
                writer.write(bad1);       
            }
        };
    }

    @OPTIONS
    @Path("badstream3/{id}")
    @Produces(MediaType.TEXT_HTML)
    public StreamingOutput badstream3(final @PathParam("id") String pp) {
        return new StreamingOutput() {
            public void write(OutputStream os) throws IOException {
                OutputStreamWriter writer = new OutputStreamWriter(os);
                writer.write(pp);       
            }
        };
    }

    @OPTIONS
    @Path("badstream4/{id}")
    @Produces(MediaType.TEXT_HTML)
    public StreamingOutput badstream4(final @PathParam("id") String pp) {
        return new StreamingOutput() {
            public void write(OutputStream os) throws IOException {
                OutputStreamWriter writer = new OutputStreamWriter(os);
                helperWrite2(os, pp);
            }
        };
    }
    @OPTIONS
    @Path("goodstream1")
    @Produces(MediaType.TEXT_HTML)
    public StreamingOutput goodstream1() {
        return new StreamingOutput() {
            public void write(OutputStream os) throws IOException {
                OutputStreamWriter writer = new OutputStreamWriter(os);
                helperWrite1(os, "foo");
            }
        };
    }
    private void helperWrite1(OutputStream os, String value) {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
            writer.write(value);
        } catch (IOException ignore) {
        }
    }

    private void helperWrite2(OutputStream os, String value) {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
            writer.write(value);        
        } catch (IOException ignore) {
        }
    }


}
