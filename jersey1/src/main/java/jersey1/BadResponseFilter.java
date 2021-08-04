package jersey1;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.container.ContainerResponseFilter;
import java.net.URI;
import javax.ws.rs.core.Response;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import java.io.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.logging.*;

@Provider
@PreMatching
public class BadResponseFilter implements ContainerResponseFilter {
    private Logger logger = Logger.getLogger(getClass().getName());
    public void filter(ContainerRequestContext ctx, ContainerResponseContext respctx) throws IOException {
        String hdr = ctx.getHeaderString("Referer");
        if(hdr == null) {
            hdr = "";
        }
        if(hdr.contains("TOK1")) {
            ctx.abortWith(Response.ok(hdr).build());        
        } else if(hdr.contains("TOK2")) {
            ctx.abortWith(Response.ok(ctx.getCookies().get("sid").getName()).build());        
        } else if(hdr.contains("TOK3")) {
            ctx.abortWith(Response.ok(ctx.getCookies().get("sid").getValue()).build());        
        } else if(hdr.contains("TOK4")) {
            ctx.abortWith(Response.ok(ctx.getCookies().get("sid").toString()).build());        
        } else if(hdr.contains("TOK5")) {
            ctx.abortWith(Response.ok(ctx.getHeaders().getFirst("Referer")).build());            
        } else if(hdr.contains("TOK6")) {
            ctx.abortWith(Response.ok(ctx.getHeaders().get("Referer").get(0)).build());            
        } else if(hdr.contains("TOK7")) {
            ctx.abortWith(Response.ok("this is fine").build());
        } else if(hdr.contains("TOK8")) {
            ctx.abortWith(Response.ok(ctx.getUriInfo().getAbsolutePath().getRawPath()).build());                   
        } else if(hdr.contains("TOK9")) {
            ctx.setRequestUri(URI.create(hdr));     
        } else if(hdr.contains("TOK10")) {
            ctx.setRequestUri(URI.create("http://www.example.com"), URI.create(hdr));     
        } else if(hdr.contains("TOK11")) {
            ctx.setRequestUri(URI.create(hdr), URI.create("http://www.example.com"));     
        } else if(hdr.contains("TOK12")) {
            ctx.setRequestUri(URI.create("http://www.example.com"));
        } else if(hdr.contains("TOK13")) {
            ctx.setRequestUri(URI.create("http://www.example.com"), URI.create("http://www.example.com"));
        } else if(hdr.contains("TOK14")) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(ctx.getEntityStream()));
            ctx.abortWith(Response.ok(reader.readLine()).build());          
        } else if(hdr.contains("TOK15")) {
            OutputStream os = respctx.getEntityStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write("hello");
            bw.write(hdr);      
        } else if(hdr.contains("TOK16")) {
            respctx.setEntity(hdr);     
        } else if(hdr.contains("TOK17")) {
            respctx.setEntity("yo");
        } else if(hdr.contains("TOK18")) {
            respctx.setEntity(hdr, null, MediaType.TEXT_HTML_TYPE);      
        }

    }

}
