package fr.kdefombelle.xmlcompare.rs;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.StreamingOutput;

import org.custommonkey.xmlunit.Difference;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.kdefombelle.xmlcompare.core.ExcelDifferenceWriter;
import fr.kdefombelle.xmlcompare.core.SimpleXmlComparator;


@Path("xmlcompare")
public class XmlCompareResource {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    private final Logger logger = LoggerFactory.getLogger(XmlCompareResource.class);

    private SimpleXmlComparator xmlComparator = new SimpleXmlComparator();

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

//    @POST
//    @Produces("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    public Response compare(
//        //J-
//            @FormDataParam("controlFile")
//                InputStream controlIs,
//            @FormDataParam("controlFile")
//                FormDataContentDisposition controlFileDetail,
//            @FormDataParam("testFile")
//                InputStream testIs,
//            @FormDataParam("testFile")
//                FormDataContentDisposition testFileDetail
//        //J+
//    ) {
//        logger.info("------------compare");
//        logger.debug("------------" + controlIs);
//        logger.debug("------------" + controlFileDetail);
//        String reportFileName = "D:\\tmp\\xmlcompare.xlsx";
//        List<Difference> differences = xmlComparator.compare(new InputStreamReader(controlIs), new InputStreamReader(testIs));
//        File file = new File(reportFileName);
//        ExcelDifferenceWriter excelWriter = new ExcelDifferenceWriter();
//        try {
//            excelWriter.write(differences, new FileOutputStream(file));
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        //J-
//        ResponseBuilder responseBuilder = Response.ok((Object) file, MediaType.APPLICATION_OCTET_STREAM)
//                        .header("Access-Control-Allow-Origin","*")
//                        .header("Content-Disposition", "attachment; filename=" + file.getName());
//        //J+
//        return responseBuilder.build();
//    }

    @POST
    @Produces("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Path("/compared")
    public Response compared(
        //J-
            @FormDataParam("controlFile") InputStream controlIs,
            @FormDataParam("controlFile") FormDataContentDisposition controlFileDetail,
            @FormDataParam("testFile") InputStream testIs,
            @FormDataParam("testFile") FormDataContentDisposition testFileDetail
        //J+
    ) {
        logger.info("------------compare");
        logger.debug("------------" + controlIs);
        logger.debug("------------" + controlFileDetail);
        List<Difference> differences = xmlComparator.compare(new InputStreamReader(controlIs), new InputStreamReader(testIs));
        ExcelDifferenceWriter excelWriter = new ExcelDifferenceWriter();

        StreamingOutput stream = new StreamingOutput() {
            public void write(OutputStream output) throws IOException, WebApplicationException {
                try {
                    excelWriter.write(differences, output);
                } catch (Exception e) {
                    throw new WebApplicationException(e);
                }
            }
        };

        //J-
        ResponseBuilder responseBuilder = Response.ok((Object) stream, MediaType.APPLICATION_OCTET_STREAM)
                        .header("Access-Control-Allow-Origin", "*")
                        .header("Content-Disposition", "attachment; filename=comparison.xlsx");
        //J+
        return responseBuilder.build();
    }

//    @OPTIONS
//    public Response options() {
//        logger.info("------------options");
//        ResponseBuilder responseBuilder = Response.ok().header("Access-Control-Allow-Origin", "*");
//        return new responseBuilder.build();
//    }

//    @GET
//    @Path("/echo/{word}")
//    public Response test(@PathParam("word") String word) {
//        logger.info("XXXXXXXXXX" + word);
//        Bean bean = new Bean();
//        bean.setName(word);
//        ResponseBuilder responseBuilder = Response.ok(bean).header("Access-Control-Allow-Origin", "*");
//        return responseBuilder.build();
//    }

}
