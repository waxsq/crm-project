package crm.workbench.web.contoller;

import crm.settings.entity.User;
import crm.settings.service.UserService;
import crm.tools.Constant;
import crm.tools.ReturnObject;
import crm.tools.Utils;
import crm.workbench.web.entity.Activity;
import crm.workbench.web.entity.ActivityRemark;
import crm.workbench.web.service.ActivityRemarkService;
import crm.workbench.web.service.ActivityService;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

/**
 * @description:
 * @author: waxsq
 * @date:
 */
@Controller
public class ActivityController {

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityRemarkService activityRemarkService;


    //查询所有用户信息
    @RequestMapping("/workbench/activity/index.do")
    public String index(HttpServletRequest request)
    {
        List<User> list = userService.queryAllUsers();
        request.setAttribute("users",list);
        return "workbench/activity/index";
    }

    @RequestMapping("/workbench/activity/saveCreateActivity.do")
    @ResponseBody
    public Object saveCreateActivity(Activity activity, HttpSession session)
    {
        User user = (User) session.getAttribute(Constant.SESSION_USER.getCode());
        //封装参数
        activity.setId(Utils.getUUID());
        activity.setCreateTime(Utils.getTime());
        activity.setCreateBy(user.getId());
        ReturnObject object = new ReturnObject();
        try {
            //调用service
            int i = activityService.saveCreateActivity(activity);
            if (i>0)
            {
                object.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS.getCode());
            }else {
                object.setCode(Constant.RETURN_OBJECT_CODE_FAIL.getCode());
                object.setMessge("请稍后重试.....");
//                System.out.println(i);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return object;
    }


    @RequestMapping("/workbench/activity/queryActivityCountByCountForPage.do")
    @ResponseBody
    public Object queryActivityCountByCountForPage(String name,String owner,
                                                    String startDate,String endDate,
                                                      int pageNo, int pageSize  )
    {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("beginNo",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        List<Activity> activityList = activityService.queryActivityByConditionForPage(map);
        int i = activityService.queryCountOfActivityByCondition(map);
        //封装json数据
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("activityList",activityList);
        hashMap.put("totalRows",i);
        return hashMap;
    }

    @RequestMapping("/workbench/activity/deleteActivityByIds.do")
    @ResponseBody
    public Object deleteActivityByIds(String[] id)
    {
        System.out.println("控制器方法："+ Arrays.toString(id));
        ReturnObject object = new ReturnObject();
        try{
            //调用service方法
            int i = activityService.deleteActivityByIds(id);
            if (i > 0)
            {
                object.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS.getCode());
                object.setMessge("成功");
            }
            else {
                object.setCode(Constant.RETURN_OBJECT_CODE_FAIL.getCode());
                object.setMessge("信号不好，请稍后......");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            object.setCode(Constant.RETURN_OBJECT_CODE_FAIL.getCode());
            object.setMessge("信号不好，请稍后......");
        }
        return object;
    }


    @RequestMapping("/workbench/activity/selectActivityById.do")
    @ResponseBody
    public Object selectActivityById(String id)
    {
        Activity activity = activityService.selectActivityById(id);
        return activity;
    }


    @RequestMapping("/workbench/activity/updateActivity.do")
    @ResponseBody
    public Object updateActivity(Activity activity,HttpSession session)
    {
        ReturnObject object = new ReturnObject();
        User user = (User) session.getAttribute(Constant.SESSION_USER.getCode());
        try{
            activity.setEditBy(user.getId());
            activity.setEditTime(Utils.getTime());
            int i = activityService.updateActivity(activity);
            System.out.println("**********************");
            System.out.println(activity);
            if (i>0)
            {
                object.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS.getCode());
            }
            else {
                object.setCode(Constant.RETURN_OBJECT_CODE_FAIL.getCode());
                object.setMessge("不是网络问题,请稍后......");
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            object.setCode(Constant.RETURN_OBJECT_CODE_FAIL.getCode());
            object.setMessge("网络问题,请稍后......");
        }
        return object;
    }

    @RequestMapping("/workbench/activity/exprotAllActivity.do")
    public void exprotAllActivity(HttpServletResponse response) throws IOException {
        //查询所有数据
        List<Activity> activityList = activityService.queryActivityAll();
        //将查询的信息写入excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("市场活动表");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("ID");
        cell=row.createCell(1);
        cell.setCellValue("所有者");
        cell=row.createCell(2);
        cell.setCellValue("名称");
        cell=row.createCell(3);
        cell.setCellValue("开始日期");
        cell=row.createCell(4);
        cell.setCellValue("结束日期");
        cell=row.createCell(5);
        cell.setCellValue("成本");
        cell=row.createCell(6);
        cell.setCellValue("描述");
        cell=row.createCell(7);
        cell.setCellValue("创建时间");
        cell=row.createCell(8);
        cell.setCellValue("创建者");
        cell=row.createCell(9);
        cell.setCellValue("修改时间");
        cell=row.createCell(10);
        cell.setCellValue("修改者");

        if (activityList!=null&&activityList.size()>0)
        {
            int i=1;
            for (Activity activity : activityList)
            {
                //遍历创建row
                row = sheet.createRow(i++);
                //每一行创建11列，每一列的数据从activity中获取
                cell=row.createCell(0);
                cell.setCellValue(activity.getId());
                cell=row.createCell(1);
                cell.setCellValue(activity.getOwner());
                cell=row.createCell(2);
                cell.setCellValue(activity.getName());
                cell=row.createCell(3);
                cell.setCellValue(activity.getStartDate());
                cell=row.createCell(4);
                cell.setCellValue(activity.getEndDate());
                cell=row.createCell(5);
                cell.setCellValue(activity.getCost());
                cell=row.createCell(6);
                cell.setCellValue(activity.getDescription());
                cell=row.createCell(7);
                cell.setCellValue(activity.getCreateTime());
                cell=row.createCell(8);
                cell.setCellValue(activity.getCreateBy());
                cell=row.createCell(9);
                cell.setCellValue(activity.getEditTime());
                cell=row.createCell(10);
                cell.setCellValue(activity.getEditBy());
                System.out.println("第"+i);
                System.out.println(activity);
                System.out.println("*********************");
                System.out.println(activity.getEditBy());
                System.out.println("*********************");
            }
        }

//        因为要写在磁盘，也要从磁盘中取出数据，浪费资源，所以进行改造
        //生成excel文件，不用写在磁盘中（优化）
//        OutputStream stream = new FileOutputStream("E:\\idea-core\\crm\\src\\main\\resources\\file\\activityList.xls");
//        workbook.write(stream);
        //关闭资源
//        workbook.close();
//        stream.close();


        //文件下载
//        设置响应报文
        response.setContentType("application/octet-stream;charset=utf-8");
        response.addHeader("Content-Disposition","attachment;filename=activityList.xls");
//        获取输出流
        ServletOutputStream outputStream = response.getOutputStream();
//        不用从磁盘获取数据（优化）
//        FileInputStream inputStream = new FileInputStream("E:\\idea-core\\crm\\src\\main\\resources\\file\\activityList.xls");
//        int len=0;
//        byte[] bytes = new byte[255];
//        while((len = inputStream.read(bytes)) != -1)
//        {
//            outputStream.write(bytes,0,len);
//        }
//
//        inputStream.close();

        workbook.write(outputStream);
        workbook.close();
        outputStream.flush();
    }

    /**
     * 今天注意点：
     *          1、对于全局刷新：不能使用ajax
     *          2、对于全局刷新：一般适用于页面跳转，如果需要返回值，则需要自己设置响应报文
     *          3、对于.xml文件的数据语句，在多表查询的时候要主要重复的字段名，记得取别名
     * @param id
     * @param response
     * @throws IOException
     */
    @RequestMapping("/workbench/activity/exprotActivityById.do")
    public void  exprotActivityById( String[] id, HttpServletResponse response) throws IOException {
        List<Activity> activityList = activityService.queryActivityById(id);
        //将查询的信息写入excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("市场活动表(部分)");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("ID");
        cell=row.createCell(1);
        cell.setCellValue("所有者");
        cell=row.createCell(2);
        cell.setCellValue("名称");
        cell=row.createCell(3);
        cell.setCellValue("开始日期");
        cell=row.createCell(4);
        cell.setCellValue("结束日期");
        cell=row.createCell(5);
        cell.setCellValue("成本");
        cell=row.createCell(6);
        cell.setCellValue("描述");
        cell=row.createCell(7);
        cell.setCellValue("创建时间");
        cell=row.createCell(8);
        cell.setCellValue("创建者");
        cell=row.createCell(9);
        cell.setCellValue("修改时间");
        cell=row.createCell(10);
        cell.setCellValue("修改者");

        if (activityList!=null&&activityList.size()>0)
        {
            int i=1;
            for (Activity activity : activityList)
            {
                //遍历创建row
                row = sheet.createRow(i++);
                //每一行创建11列，每一列的数据从activity中获取
                cell=row.createCell(0);
                cell.setCellValue(activity.getId());
                cell=row.createCell(1);
                cell.setCellValue(activity.getOwner());
                cell=row.createCell(2);
                cell.setCellValue(activity.getName());
                cell=row.createCell(3);
                cell.setCellValue(activity.getStartDate());
                cell=row.createCell(4);
                cell.setCellValue(activity.getEndDate());
                cell=row.createCell(5);
                cell.setCellValue(activity.getCost());
                cell=row.createCell(6);
                cell.setCellValue(activity.getDescription());
                cell=row.createCell(7);
                cell.setCellValue(activity.getCreateTime());
                cell=row.createCell(8);
                cell.setCellValue(activity.getCreateBy());
                cell=row.createCell(9);
                cell.setCellValue(activity.getEditTime());
                cell=row.createCell(10);
                cell.setCellValue(activity.getEditBy());
                System.out.println("第"+i);
                System.out.println(activity);
                System.out.println("*********************");
                System.out.println(activity.getEditBy());
                System.out.println("*********************");
            }
        }
        //文件下载
//        设置响应报文
        response.setContentType("application/octet-stream;charset=utf-8");
        response.addHeader("Content-Disposition","attachment;filename=activityListOne.xls");
//        获取输出流
        ServletOutputStream outputStream = response.getOutputStream();

        workbook.write(outputStream);
        workbook.close();
        outputStream.flush();
    }

    /**
     * 导入功能
     */
    @RequestMapping("/workbench/activity/importActivity.do")
    @ResponseBody
    public Object importActivity(MultipartFile activityFile,HttpSession session)
    {
        //获取当前登录对象
        User attribute = (User) session.getAttribute(Constant.SESSION_USER.getCode());
        ReturnObject object = new ReturnObject();
//        System.out.println("*******************************");
//        System.out.println(attribute.getId());
//        System.out.println(activityFile.getName());
//        System.out.println(attribute.getId());
        try {
            //把接收到的文件保存到磁盘中
//            String filename = activityFile.getOriginalFilename();
//            File upfile = new File("E:\\idea-core\\crm\\src\\main\\resources\\upfile\\",filename);
//            activityFile.transferTo(upfile);
////            解析文件
//            FileInputStream inputStream = new FileInputStream("E:\\idea-core\\crm\\src\\main\\resources\\upfile\\" + filename);
            //省略上面将文件写进磁盘，再将磁盘写入inputstream流中
            InputStream inputStream = activityFile.getInputStream();
            HSSFWorkbook sheets = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = sheets.getSheetAt(0);
            HSSFRow cells = null;
            HSSFCell cell = null;
            ArrayList<Activity> list = new ArrayList<>();
            for (int i = 0; i < sheet.getLastRowNum(); i++) {
                 cells = sheet.getRow(i+1);
                Activity activity = new Activity();

//                ID不能自己生成
                activity.setId(Utils.getUUID());

//                所有者导入文件的人
                activity.setOwner(attribute.getId());
//
                for (int j = 0; j < cells.getLastCellNum(); j++) {
                     cell = cells.getCell(j);
                    String cellValue = Utils.getCellValuesForStr(cell);
//                    用于判断获取数据，用户的文件必须符合规格
                    if (j==0)
                    {
                        activity.setName(cellValue);
                    } else if(j==1){
                        activity.setStartDate(cellValue);
                    }else if(j==2){
                        activity.setEndDate(cellValue);
                    }else if(j==3){
                        activity.setCost(cellValue);
                    }else if(j==4){
                        activity.setDescription(cellValue);
                    }
                    activity.setCreateTime(Utils.getTime());
                    activity.setCreateBy(attribute.getId());
                }
                list.add(activity);
            }

            int i = activityService.saveCreateActivityById(list);

            if (i>=0)
            {
                    object.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS.getCode());
                    object.setMessge("成功");
            } else{
                object.setCode(Constant.RETURN_OBJECT_CODE_FAIL.getCode());
                object.setMessge("失败");
            }

        } catch (IOException e) {
            e.printStackTrace();
            object.setCode(Constant.RETURN_OBJECT_CODE_FAIL.getCode());
            object.setMessge("失败");
        }
        return object;
    }

    /**
     * 查看详情,调用两个service方法
     *          分别是查详细信息和评论
     */
    @RequestMapping("/workbench/activity/queryDetailAcitivityById.do")
    public ModelAndView queryDetailAcitivityById(String id)
    {
        Activity activity = activityService.queryActivityForDetailById(id);
        List<ActivityRemark> remarkList = activityRemarkService.queryRemarkForDetailByActivityId(id);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("workbench/activity/detail");
        mv.addObject("activity",activity);
        mv.addObject("remarkList",remarkList);
        return mv;
    }

}
