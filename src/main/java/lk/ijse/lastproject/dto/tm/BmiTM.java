package lk.ijse.lastproject.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.apache.commons.collections.iterators.ObjectGraphIterator;

import java.util.HashMap;

@Data
@AllArgsConstructor

public class BmiTM {
    private String bmiId;
    private int triposha;
    private String childrenID;
    private int age;
    private double height;
    private double weight;
    private double bmiType;
    private String date;
    private String bmiReang;


//    public static void printBill() throws JRException, SQLException, ClassNotFoundException {
//        JasperDesign load = null;
//        load = JRXmlLoader.load(new File("G:\\IJSE\\GDSE 65\\Jasper-report\\src\\main\\resources\\bill.jrxml"));
//        JRDesignQuery newQuery = new JRDesignQuery();
//        String sql = "select i.description as name , i.price  as price ,oi.qty as qty , i.price*oi.qty as subTotal " +
//                "from item i inner join orderitem oi on i.itemcode = oi.item_code where oi.order_id = '"+"O-001"+"'";
//        newQuery.setText(sql);
//        load.setQuery(newQuery);
//        JasperReport js = JasperCompileManager.compileReport(load);
//        HashMap<String, Object> hm = new HashMap<>();
//        hm.put("name","Pasindu");
//        JasperPrint jp = JasperFillManager.fillReport(js, hm, DBConnection.getInstance().getConnection());
//        JasperViewer viewer = new JasperViewer(jp, false);
//        viewer.show();
//
//    }


}