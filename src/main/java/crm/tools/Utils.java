package crm.tools;

import org.apache.poi.hssf.usermodel.HSSFCell;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @description:
 * @author: waxsq
 * @date:
 */
public class Utils {

    public static Boolean Time(String date)
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = format.format(new Date());
        int i = date.compareTo(s);
        if (i > 0)
        {
            return true;
        }
        else return false;
    }
//随机生成
    public static String getUUID()
    {
      
        String string = UUID.randomUUID().toString().replaceAll("-","");
        return string;
    }


    public static String getTime()
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s = format.format(new Date());
        return s;
    }


    /**
     * excel文件解析列
     * 从指定的HSSFCell对象中获取列的值
     */
    public static String getCellValuesForStr(HSSFCell cell)
    {
        if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
        {
            return cell.getStringCellValue();
        }
        if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
        {
            return String.valueOf(cell.getNumericCellValue());
        }
        if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN)
        {
            return String.valueOf(cell.getBooleanCellValue());
        }
        if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA)
        {
            return String.valueOf(cell.getCellFormula());
        }
        else{
            return "";
        }
    }
}
