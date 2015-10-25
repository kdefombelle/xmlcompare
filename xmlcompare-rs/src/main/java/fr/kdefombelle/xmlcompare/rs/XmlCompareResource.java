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
    //~ Static fields/initializers 
    //~ ----------------------------------------------------------------------------------------------------------------

    private static final Logger logger = LoggerFactory.getLogger(XmlCompareResource.class);

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Instance fields 
    //~ ----------------------------------------------------------------------------------------------------------------

    private SimpleXmlComparator xmlComparator = new SimpleXmlComparator();

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods 
    //~ ----------------------------------------------------------------------------------------------------------------

    @POST
    @Produces("application/vnd.ms-excel")
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
        ResponseBuilder response = Response.ok((Object) file).header("Content-Disposition", "attachment; filename=" + file.getName());
        return response.build();
    }

    @GET
    @Produces("application/vnd.ms-excel")
    public Response compare(@QueryParam("controlFile") String test) {
        String reportFileName = "D:\\tmp\\xmlcompare.xlsx";
        logger.info("XXXXXXXXXX" + test);
        List<Difference> differences = xmlComparator.compare(new File("D:\\kdefombelle\\xmlcompare\\xmlcompare-cli\\src\\main\\sample\\loremIpsum1.xml"),
            new File("D:\\kdefombelle\\xmlcompare\\xmlcompare-cli\\src\\main\\sample\\loremIpsum2.xml"));
        ExcelDifferenceWriter excelWriter = new ExcelDifferenceWriter(reportFileName);
        excelWriter.write(differences);
        File file = new File(reportFileName);
        ResponseBuilder response = Response.ok((Object) file).header("Content-Disposition", "attachment; filename=" + file.getName());
        return response.build();
    }
}
