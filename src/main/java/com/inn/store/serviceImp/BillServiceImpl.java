package com.inn.store.serviceImp;

import com.google.common.io.FileBackedOutputStream;
import com.inn.store.JWT.JwtFilter;
import com.inn.store.constants.StoreConstants;
import com.inn.store.dao.BillDao;
import com.inn.store.dao.ProductDao;
import com.inn.store.dao.UserDao;
import com.inn.store.entities.Bill;
import com.inn.store.entities.Product;
import com.inn.store.entities.User;
import com.inn.store.request.BillRequest;
import com.inn.store.request.ProductRequest;
import com.inn.store.service.BillService;
import com.inn.store.utils.StoreUtils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.io.IOUtils;
import org.json.JSONArray;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Service
public class BillServiceImpl implements BillService {
    @Autowired
    BillDao billDao ;

    @Autowired
    UserDao userDao;

    @Autowired
    ProductDao productDao;
    @Autowired
    JwtFilter jwtFilter;
    @Override
    public ResponseEntity<String> generatedRaport(BillRequest billRequest) {
        try{
            log.info("the map is  ==========> {}",billRequest);

                    String fileName= StoreUtils.getUuid();;
                    billRequest.setUuid(fileName);
                    User user = userDao.findUserByEmail(jwtFilter.getCurrentUser());
                    billRequest.setCreatedBy(user);



                String data = "Name : "+billRequest.getCreatedBy().getName()+"\n"+
                              "Contact Number : "+billRequest.getCreatedBy().getContactNumber()+"\n"+
                              "Eamil : "+billRequest.getCreatedBy().getEmail()+"\n"+
                              "Payment Methode : "+billRequest.getPaymentMethod();

                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(StoreConstants.STORE_LOCATION+"\\"+fileName+".pdf"));

                document.open();
                setRectangleInPdf(document);
                // Title
                Paragraph title= new Paragraph("Store Management System",getFont("Header"));
                title.setAlignment(Element.ALIGN_CENTER);// in Center
                document.add(title);

                Paragraph paragraph = new Paragraph(data+"\n \n",getFont("Data"));
                document.add(paragraph);

                PdfPTable table = new PdfPTable(5);//5 : colonne
                table.setWidthPercentage(100);
                addTableHeader(table);

            List<Product> products = new ArrayList<>();
               // JSONArray jsonArray = StoreUtils.getJsonArrayFromString( (String) requestMap.get("productDetails"));
                    int total =0;
                for(int i =0 ;i<billRequest.getProductdetails().size();i++){
                    int id =Integer.parseInt(billRequest.getProductdetails().get(i).get("id"));
                    int quantity= Integer.parseInt(billRequest.getProductdetails().get(i).get("quantity"));
                    Product product = productDao.findById(id).get();
                    table.addCell(product.getName());
                    table.addCell(""+product.getCategory().getName());
                    table.addCell(""+quantity);
                    table.addCell(""+product.getPrice() );
                    table.addCell(""+(product.getPrice()*quantity) );
                    total+=(product.getPrice()*quantity);
                    // add product in the bill
                    products.add(product);
                }

                //save bill
                 Bill bill = new Bill();
                 bill.setUuid(fileName);
                 bill.setPaymentMethod(billRequest.getPaymentMethod());
                 bill.setCreatedBy(user);
                 bill.setProductdetails(products);
                 bill.setTotal(total);

                 billDao.save(bill);
                    log.info("bill is  ==============>  : {}",bill);


                document.add(table);

                Paragraph footer = new Paragraph("\nTotal : "+
                        total+"\n\n"+"Thank you for visiting .",getFont("Data"));
                document.add(footer);
                document.close();
                log.info("count : {}",billDao.count());
                return new ResponseEntity<>("{\"uuid\" : \""+fileName+"\"}",HttpStatus.OK);




        }catch (Exception ex){
            ex.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Bill>> getBills() {
        List<Bill> list = new ArrayList<>();

            if(jwtFilter.isAdmin()){
                list = billDao.findAll();
            }else {
                list =billDao.findAllByCreatedBy(jwtFilter.getCurrentUser());
            }



        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
        log.info("Inside getPdf : requestMap {}",requestMap);
        try{
            byte[] byteArray=new byte[0];
            if(!requestMap.containsKey("uuid"))
                return new ResponseEntity<>(byteArray,HttpStatus.BAD_REQUEST);
            String filePath=StoreConstants.STORE_LOCATION+"\\"+(String)requestMap.get("uuid")+".pdf";
           if( StoreUtils.isFileExiste(filePath) ){
                byteArray = getByteArray(filePath);
                return new ResponseEntity<>(byteArray,HttpStatus.OK);
           }else{
               requestMap.put("isGenerate",false);
              // generatedRaport(requestMap);

               byteArray = getByteArray(filePath);
               return  new ResponseEntity<>(byteArray,HttpStatus.OK);
           }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseEntity<String> deleteBill(Integer id) {
        try{
            if(!billDao.findById(id).isEmpty()){
                billDao.deleteById(id);
                return StoreUtils.getResponseEntity("Bill Deleted Successfully",HttpStatus.OK);
            }else{
                return StoreUtils.getResponseEntity("this Bill doesn't exist , check the id !",HttpStatus.OK);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return StoreUtils.getResponseEntity(StoreConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private byte[] getByteArray(String filePath) throws Exception {
        File initialFile = new File(filePath);
        InputStream targetStream = new FileInputStream(initialFile);
        byte[] byteArray = IOUtils.toByteArray(targetStream);
        targetStream.close();
        return byteArray;
    }


    // PDF functions
    private void addTableHeader(PdfPTable table) {
        log.info("Inide addTableHeader");
        Stream.of("Name","Category","Qantity","Price","Sub Total")
                .forEach(columnTitle->{
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(1);
                    header.setPhrase(new Phrase(columnTitle));// content of the case of tables
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setVerticalAlignment(Element.ALIGN_CENTER);
                    table.addCell(header);
                });

    }

    private Font getFont(String type) {
        log.info("Inside getFont ");
        switch (type){

            case "Header":
               Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE,20,BaseColor.GRAY);

               headerFont.setStyle(Font.BOLD);
               return headerFont;

            case "Data":
                Font dataFont=FontFactory.getFont(FontFactory.TIMES_ROMAN,11,BaseColor.BLACK);
                dataFont.setStyle(Font.BOLD);
                return dataFont;

            default:
                return new Font();
        }
    }

    private void setRectangleInPdf(Document document) throws DocumentException  {
        log.info("Inside setRectangleInPdf");
        Rectangle rectangle = new Rectangle(577,825,18,15);
        rectangle.enableBorderSide(1);
        rectangle.enableBorderSide(2);
        rectangle.enableBorderSide(4);
        rectangle.enableBorderSide(8);

        rectangle.setBorderWidth(1);
        document.add(rectangle);
    }






 }