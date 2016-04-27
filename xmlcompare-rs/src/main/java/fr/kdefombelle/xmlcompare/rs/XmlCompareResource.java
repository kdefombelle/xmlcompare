package fr.kdefombelle.xmlcompare.rs;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

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

//    @OPTIONS
//    public Response options() {
//        logger.info("------------options");
////        ResponseBuilder responseBuilder = Response.ok().header("Access-Control-Allow-Origin", "*");
//        ResponseBuilder responseBuilder = Response.ok();
//        return new responseBuilder.em();
//    }

    @POST
    @Produces("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response compare(
        //J-
            @FormDataParam("controlFile")
                InputStream controlIs,
            @FormDataParam("controlFile")
                FormDataContentDisposition controlFileDetail,
            @FormDataParam("testFile")
            @DefaultValue("D:\\kdefombelle\\xmlcompare\\xmlcompare-cli\\src\\main\\sample\\loremIpsum2.xml")
                InputStream testIs,
            @FormDataParam("testFile")
                FormDataContentDisposition testFileDetail
        //J+
    ) {
        logger.info("------------compare");
        logger.debug("------------" + controlIs);
        logger.debug("------------" + controlFileDetail);
        String reportFileName = "D:\\tmp\\xmlcompare.xlsx";
        List<Difference> differences = xmlComparator.compare(new InputStreamReader(controlIs), new InputStreamReader(testIs));
        ExcelDifferenceWriter excelWriter = new ExcelDifferenceWriter(reportFileName);
        excelWriter.write(differences);
        File file = new File(reportFileName);
        //J-
        ResponseBuilder responseBuilder = Response.ok((Object) file, MediaType.APPLICATION_OCTET_STREAM)
//                        .header("Access-Control-Allow-Origin","*")
                        .header("Content-Disposition", "attachment; filename=" + file.getName());
        //J+
        return responseBuilder.build();
    }

    @GET
    @Produces("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public Response compare(@QueryParam("controlFile") String test) {
        String reportFileName = "D:\\tmp\\xmlcompare.xlsx";
        logger.info("XXXXXXXXXX" + test);
        List<Difference> differences = xmlComparator.compare(new File("D:\\kdefombelle\\xmlcompare\\xmlcompare-cli\\src\\main\\sample\\loremIpsum1.xml"),
            new File("D:\\kdefombelle\\xmlcompare\\xmlcompare-cli\\src\\main\\sample\\loremIpsum2.xml"));
        ExcelDifferenceWriter excelWriter = new ExcelDifferenceWriter(reportFileName);
        excelWriter.write(differences);
        File file = new File(reportFileName);
        //J-
        ResponseBuilder response = Response.ok((Object) file)
                       // .header("Access-Control-Allow-Origin","*")
                        .header("Content-Disposition","attachment; filename=" + file.getName());
        //J+
        return response.build();
    }
}
