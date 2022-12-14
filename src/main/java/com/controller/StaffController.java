package com.controller;

import com.pojo.sample;
import com.pojo.sampleBorrow;
import com.pojo.user;
import com.service.SampleBorrowService;
import com.service.SampleService;
import com.service.UnitService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/staff")
@MultipartConfig(maxFileSize = 5*1024*1024)
public class StaffController extends HttpServlet {

    @Autowired
    private UserService userService;
    @Autowired
    private SampleService sampleService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private SampleBorrowService sampleBorrowService;

    //点击游客登录
    @RequestMapping("/toLogin")
    public String toLogin() {
        return "staffLogin.jsp";
    }

    //登录
    @RequestMapping("/Login")
    public String Login(user u, HttpSession session){
        System.out.println(u.getUsername());
        user formatpass=userService.selectByRegName(u.getUsername());
        //成功登录
        String des="";
        System.out.println(formatpass.getIdentity());
        if(formatpass!=null&&formatpass.getPassword().equals(u.getPassword())&&formatpass.getIdentity().equals("staff")){
            session.setAttribute("staffName",u.getUsername());
            //记录登录时间
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
            Date date = new Date(System.currentTimeMillis());
            session.setAttribute("thisLoginTime",date);
            session.setAttribute("username",u.getUsername());
            return "redirect:/staff/afterLogin.jsp";
        }else{
            return "redirect:/staff/failToLogin.jsp";
        }
    }

    //找回密码
    @RequestMapping("/tofind")
    public String toforget(HttpSession session){
        session.setAttribute("pwd","");
        return "redirect:/staff/findpwd.jsp";
    }

    @RequestMapping("/find")
    public String find(HttpServletRequest request,HttpSession session) throws UnsupportedEncodingException {
        String username1= new String(request.getParameter("username").getBytes("iso-8859-1"),"utf-8");
        user u=userService.selectByRegName(username1);
        if(u==null){
            return "redirect:/userNameError.jsp";
        }{//找到了user
            session.setAttribute("userInfo",u);
            return "redirect:/staff/findpwd.jsp";
        }
    }

    @RequestMapping("/findout")
    public String findout(HttpServletRequest request,HttpSession session) throws UnsupportedEncodingException {
        String answer= new String(request.getParameter("answer").getBytes("iso-8859-1"),"utf-8");
        user t= (user) session.getAttribute("userInfo");
        if(answer.equals(t.getAnswer())){
            session.setAttribute("pwd","密码是："+t.getPassword());
        }
        return "redirect:/staff/findpwd.jsp";
    }
    //查看并且修改个人信息
    @RequestMapping("/toChange")
    public String tochange(HttpSession session){
        String name= (String) session.getAttribute("staffName");
        user t=userService.selectByRegName(name);
        session.setAttribute("staffInfo",t);
        return "redirect:/staff/changeInfo.jsp";
    }

    //修改
    @RequestMapping("/change")
    public String change(@RequestParam("username") String username, @RequestParam("realname") String realname, @RequestParam("telephone") String telephone, @RequestParam("email") String email, @RequestParam("address") String address, @RequestParam("question") String question, @RequestParam("answer") String answer, @RequestParam("gender") String gender, HttpServletRequest request, HttpSession  session) throws UnsupportedEncodingException {
        user u=new user();
        String username1= new String(request.getParameter("username").getBytes("iso-8859-1"),"utf-8");
        String address1= new String(request.getParameter("address").getBytes("iso-8859-1"),"utf-8");
        String gender1= new String(request.getParameter("gender").getBytes("iso-8859-1"),"utf-8");
        String realname1= new String(request.getParameter("realname").getBytes("iso-8859-1"),"utf-8");
        String question1= new String(request.getParameter("question").getBytes("iso-8859-1"),"utf-8");
        String answer1= new String(request.getParameter("answer").getBytes("iso-8859-1"),"utf-8");

        // System.out.println(addr);
        u.setUsername(username1);
        u.setAddress(address1);
        u.setEmail(email);
        u.setGender(gender1);
        u.setRealname(realname1);
        u.setTelephone(telephone);
        u.setQuestion(question1);
        u.setAnswer(answer1);
        session.setAttribute("staffInfo",u);
        System.out.println(address1);
        //用户名不重复
        userService.updateInfo(u);
        return "redirect:/staff/afterLogin.jsp";
    }

    //进入重置密码
    @RequestMapping("/toReset")
    public String toReset(){
        return "redirect:/staff/resetPassword.jsp";
    }
    //重置密码
    @RequestMapping("/reset")
    public String reset(HttpServletRequest request) throws UnsupportedEncodingException {
        String username=new String(request.getParameter("username").getBytes("iso-8859-1"),"utf-8");
        String question=new String(request.getParameter("question").getBytes("iso-8859-1"),"utf-8");
        String newPassword=new String(request.getParameter("newPassword").getBytes("iso-8859-1"),"utf-8");
        String answer=new String(request.getParameter("answer").getBytes("iso-8859-1"),"utf-8");
        user un=new user();
        un.setUsername(username);
        un.setQuestion(question);
        un.setPassword(newPassword);
        user getAnswer=userService.selectByQuestion(un);
        if(getAnswer.getAnswer().equals(answer)){
            userService.changePassword(un);
            return "redirect:/staff/afterLogin.jsp";
        }else{//答案输入错误
            return "redirect:/staff/answerError.jsp";
        }
    }


    //查看本单位的标本
    @RequestMapping("/toSampleManagement")
    public String toSampleManagement(HttpSession session){
        /**
         * 仅展示本单位的标本
         * 先选出工作人员的单位
         * 再找符合条件的标本
         */
        String staffName= (String) session.getAttribute("staffName");
        user u=userService.selectByRegName(staffName);
        String attachUnit=u.getAttachUnit();
        List<sample> sampleList=sampleService.selectByUnit(attachUnit);
        session.setAttribute("unitSamples",sampleList);
        return "redirect:/staff/unitSamples.jsp";
    }

    @RequestMapping("/toNewSample")
    public String toNewSample(){
        return "redirect:/staff/newSampleMethod.jsp";
    }

    //手工录入
    @RequestMapping("/newSampleByHand")
    public String newSampleByHand(sample sam,HttpServletRequest request, HttpSession session, HttpServletResponse resp) throws IOException {

        sample s=new sample();
        String name= new String(request.getParameter("name").getBytes("iso-8859-1"),"utf-8");
        String birthplace= new String(request.getParameter("birthplace").getBytes("iso-8859-1"),"utf-8");
        String birthtime= new String(request.getParameter("birthtime").getBytes("iso-8859-1"),"utf-8");
        String material= new String(request.getParameter("material").getBytes("iso-8859-1"),"utf-8");
        String years= new String(request.getParameter("years").getBytes("iso-8859-1"),"utf-8");
        String recordman= new String(request.getParameter("recordman").getBytes("iso-8859-1"),"utf-8");
        String description= new String(request.getParameter("description").getBytes("iso-8859-1"),"utf-8");
        String isVisible= new String(request.getParameter("isVisible").getBytes("iso-8859-1"),"utf-8");
        s.setName(name);
        s.setBirthtime(birthtime);
        s.setBirthplace(birthplace);
        s.setMaterial(material);
        s.setYears(years);
        s.setRecordman(recordman);
        s.setDescription(description);
        s.setIsVisible(isVisible);
        //记录录入时间
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        Date date = new Date(System.currentTimeMillis());
        s.setIntime(formatter.format(date));
        //单位
        String staffName= (String) session.getAttribute("staffName");
        user u=userService.selectByRegName(staffName);
        String attachUnit=u.getAttachUnit();
        s.setBelongUnit(attachUnit);
        //获取上传头像
        //定义 文件名
        String filename=null;
        //定义文件保存的本地路径
        String localPath=request.getServletContext().getRealPath("")+"/img";
        System.out.println(localPath);
        /*
         * 获取前台传过来的file值，如果不为空，则执行该if里面的代码
         * 需要注意的是：这里我将MultipartFile即file属性定义在了商品的实体类中。
         */
        if(!sam.getFile().isEmpty()){
            //用uuid随机生成一个文件裸名
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            //获得文件类型（可以判断如果不是图片，禁止上传）
            String contentType=sam.getFile().getContentType();
            if (!contentType.equals("image/jpeg")  && !contentType.equals("image/png") && !contentType.equals("image/gif")) {
                resp.setContentType("text/html;charset=utf-8");
                PrintWriter out = resp.getWriter();
                out.flush();
                out.println("<script>");
                out.println("alert('请上传图片文件!');");
                out.println("history.back();");
                out.println("</script>");
                return "redirect:/staff/handIn.jsp";
            } else {
                //获得文件后缀名
                String suffixName=contentType.substring(contentType.indexOf("/")+1);
                //得到文件名（文件名由文件裸名与后缀名组合而成）
                filename=uuid+"."+suffixName;
                //获取商品实体类中的file属性赋值，而file属性是MultipartFile类型的，将前台上传的该图片保存至该本地路径的文件夹中
                sam.getFile().transferTo(new File(localPath+filename));
            }
        }
        //把图片的相对路径保存至数据库
        s.setImagepath(localPath+filename); //设置商品实体类中的image属性的值
        sampleService.insertSample(s);
        return "redirect:/staff/toSampleManagement";
    }

    //excel导入
    @RequestMapping("/importExcel")
    public String importExcel(@RequestParam("excelFile") MultipartFile excelFile, HttpServletRequest request,HttpSession session) throws IOException {

        // 1: 转存文件
        if (!excelFile.isEmpty()) {
            /**
             * 这里的getRealPath("/")是打包之后的项目的根目录。
             * 也就是 target\项目名-1.0-SNAPSHOT\
             */
            String storePath = "D://";
            excelFile.transferTo(new File(storePath + excelFile.getOriginalFilename()));
        }

        // 2: 解析excel数据
        List<String[]> excelData = com.tao.springstarter.common.util.POIUtil.readExcelFile(excelFile, 1);

        List<sample> userInfoList = new ArrayList<>();
        //单位
        String staffName= (String) session.getAttribute("staffName");
        user u=userService.selectByRegName(staffName);
        String attachUnit=u.getAttachUnit();
        for (String[] arr : excelData) {
            sample sampleInfo = new sample();
            sampleInfo.setName(arr[0]);
            sampleInfo.setBirthplace(arr[1]);
            sampleInfo.setBirthtime(arr[2]);
            sampleInfo.setMaterial(arr[3]);
            sampleInfo.setYears(arr[4]);
            sampleInfo.setRecordman(arr[5]);
            sampleInfo.setDescription(arr[6]);
            sampleInfo.setIsVisible(arr[7]);
            //记录录入时间
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
            Date date = new Date(System.currentTimeMillis());
            sampleInfo.setIntime(formatter.format(date));
            sampleInfo.setBelongUnit(attachUnit);
            sampleService.insertSample(sampleInfo);
        }

        return "redirect:/staff/toSampleManagement";
    }

    //queryUnit
    //查询
    @RequestMapping("/queryUnit")
    public String queryUnit(HttpServletRequest request,HttpSession session) throws UnsupportedEncodingException {
        //queryUnitName
        String method=new String(request.getParameter("select").getBytes("iso-8859-1"),"utf-8");
        System.out.println(method);
        String queryUnitName= new String(request.getParameter("queryUnitName").getBytes("iso-8859-1"),"utf-8");
        session.setAttribute("query",queryUnitName);
        List<sample> sampleList;
        if(method.equals("标本名称")){
            sampleList=sampleService.selectByName(queryUnitName);
        }else if(method.equals("出土地")){
            sampleList=sampleService.selectByPlace(queryUnitName);
        }else if(method.equals("标本材质")){
            sampleList=sampleService.selectByMaterial(queryUnitName);
        }else{//所属单位
            sampleList=sampleService.selectByVagueUnit(queryUnitName);
        }
        session.setAttribute("unitSamples",sampleList);
        return "redirect:/staff/unitSamples.jsp";
    }

    //详情
    @RequestMapping("/detail/{id}")
    public String detail(@PathVariable("id") int id, HttpSession session){
        sample sample=sampleService.selectAllByID(id);
        //存入编号
        session.setAttribute("sample",sample);
        return "redirect:/admin/sampleDetail.jsp";
    }
    @RequestMapping("/detailBack")
    public String detailBack(){
        return "redirect:/staff/unitSamples.jsp";
    }

    //修改
    //进入修改标本信息页面
    @RequestMapping("/toUpdateSample/{id}")
    public String toUpdateSample(@PathVariable("id") int id,HttpSession session){
        sample sample=sampleService.selectAllByID(id);
        //存入编号
        session.setAttribute("sample",sample);
        return "redirect:/staff/updateSample.jsp";
    }

    //修改标本信息
    @RequestMapping("/updateSample")
    public String updateSample(HttpServletRequest request,HttpSession session) throws UnsupportedEncodingException {
        sample s=new sample();
        String name= new String(request.getParameter("name").getBytes("iso-8859-1"),"utf-8");
        String birthplace= new String(request.getParameter("birthplace").getBytes("iso-8859-1"),"utf-8");
        String birthtime= new String(request.getParameter("birthtime").getBytes("iso-8859-1"),"utf-8");
        String material= new String(request.getParameter("material").getBytes("iso-8859-1"),"utf-8");
        String years= new String(request.getParameter("years").getBytes("iso-8859-1"),"utf-8");
        String recordman= new String(request.getParameter("recordman").getBytes("iso-8859-1"),"utf-8");
        String description= new String(request.getParameter("description").getBytes("iso-8859-1"),"utf-8");
        String intime= new String(request.getParameter("intime").getBytes("iso-8859-1"),"utf-8");
        String isVisible=new String(request.getParameter("isVisible").getBytes("iso-8859-1"),"utf-8");
        System.out.println(name+"|"+material+"|"+years);
        sample temp= (sample) session.getAttribute("sample");
        int id=temp.getSampleID();
        s.setSampleID(id);
        s.setName(name);
        s.setBirthplace(birthplace);
        s.setBirthtime(birthtime);
        s.setMaterial(material);
        s.setYears(years);
        s.setRecordman(recordman);
        s.setDescription(description);
        s.setIntime(intime);
        s.setIsVisible(isVisible);
        session.setAttribute("sample",s);
        System.out.println(sampleService.updateSample(s));
        return "redirect:/staff/toSampleManagement";
    }

    //删除
    //标本删除
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id,HttpSession session,HttpServletResponse response) throws IOException {
        sampleBorrow sampleBorrow=sampleBorrowService.selectBySampleID(id);
        if(sampleBorrow==null||!(sampleBorrow.getOutState().equals("已借出"))){//标本没有被借阅
            sampleService.deleteByID(id);
        }else{//标本被借阅
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().print("<script>alert('该标本被借阅，无法删除!');window.location='http://localhost:8080/staff/unitSamples.jsp'; </script>");
        }
        return "redirect:/staff/toSampleManagement";
    }

    //标本借阅
    @RequestMapping("/toBorrowSample")
    public String toBorrowSample(HttpSession session){
        //单位
        String staffName= (String) session.getAttribute("staffName");
        user u=userService.selectByRegName(staffName);
        String attachUnit=u.getAttachUnit();
        //单位不同的标本
        List<sample> sl=sampleService.selectBorrow(attachUnit);
        session.setAttribute("samples",sl);
        return "redirect:/staff/borrowSamples.jsp";
    }

    @RequestMapping("/borrowSample/{id}")
    public String borrowSample(@PathVariable("id") int id,HttpSession session) {
        //输入借阅人的联系方式、地址等信息后提交，等待被借阅单位工作人员的审核

        session.setAttribute("bid",id);
       return "redirect:/staff/borrowInfo.jsp";
    }

    @RequestMapping("/finalBorrow")
    public String finalBorrow(HttpSession session,HttpServletRequest request) throws UnsupportedEncodingException {
        String staffName= (String) session.getAttribute("staffName");
        user u=userService.selectByRegName(staffName);
        int id= (int) session.getAttribute("bid");
        String borrowTele= new String(request.getParameter("borrowTele").getBytes("iso-8859-1"),"utf-8");
        String borrowAddress= new String(request.getParameter("borrowAddress").getBytes("iso-8859-1"),"utf-8");
        String borrowReason= new String(request.getParameter("borrowReason").getBytes("iso-8859-1"),"utf-8");
        String remarks= new String(request.getParameter("remarks").getBytes("iso-8859-1"),"utf-8");
        int userID=u.getUserID();
        String borrowman=u.getRealname();
        sample t=sampleService.selectAllByID(id);
        String belongUnit=t.getBelongUnit();
        int sampleID=t.getSampleID();
        String sampleName=t.getName();
        sampleBorrow s=new sampleBorrow();
        s.setBelongUnit(belongUnit);
        s.setSampleID(sampleID);
        s.setBorrowMan(borrowman);
        s.setBorrowTele(borrowTele);
        s.setBorrowReason(borrowReason);
        s.setRemarks(remarks);
        s.setBorrowAddress(borrowAddress);
        s.setUserID(userID);
        s.setInState("待审核");
        s.setOutState("待审核");
        s.setSampleName(sampleName);
        sampleBorrow temp=sampleBorrowService.selectBySampleID(sampleID);
        if(temp==null) {//没有被借阅
            sampleBorrowService.insert(s);
        }

        return "redirect:/staff/borrowSamples.jsp";
    }

    //借入借出
    @RequestMapping("/toInOut")
    public String toInOut(){
        return "redirect:/staff/inAndOut.jsp";
    }

    //借入管理
    @RequestMapping("/toIn")
    public String toIn(HttpSession session){
        //从sb表中找userID一致的
        String staffName= (String) session.getAttribute("staffName");
        user u=userService.selectByRegName(staffName);
        int userID=u.getUserID();
        List<sampleBorrow> sampleBorrowList=sampleBorrowService.selectByUserID(userID);
        session.setAttribute("sampleBorrowList",sampleBorrowList);
        return "redirect:/staff/allIn.jsp";
    }

    //归还标本
    @RequestMapping("/returnSample/{id}")
    public String returnSample(@PathVariable("id") int id){
        sampleBorrow sampleBorrow=sampleBorrowService.selectBySampleID(id);
        sampleBorrow.setInState("归还");
        sampleBorrowService.update(sampleBorrow);
        return "redirect:/staff/toIn";
    }

    @RequestMapping("/in")
    public String in(HttpSession session,HttpServletRequest request) throws UnsupportedEncodingException {
        String method= new String(request.getParameter("select").getBytes("iso-8859-1"),"utf-8");
        System.out.println(method);
        List<sampleBorrow> list= (List<sampleBorrow>) session.getAttribute("sampleBorrowList");
        List<sampleBorrow> fl = new ArrayList<>();
        if(method.equals("待审核")){
            for(sampleBorrow t:list){
                if(t.getInState().equals("待审核")){
                    fl.add(t);
                    System.out.println(t.getSampleName());
                }
            }
        }else if(method.equals("已审核")){
            for(sampleBorrow t:list){
                if(t.getInState().equals("已审核")||t.getInState().equals("归还")){
                    fl.add(t);
                }
            }
        }else if(method.equals("已结束")){
            for(sampleBorrow t:list){
                if(t.getInState().equals("已结束")){
                    fl.add(t);
                }
            }
        }
        session.setAttribute("inList",fl);
        return "redirect:/staff/allIn.jsp";
    }



    //借出管理
    @RequestMapping("/toOut")
    public String toOut(HttpSession session){
        //从sb表中找单位一致的
        String staffName= (String) session.getAttribute("staffName");
        user u=userService.selectByRegName(staffName);
        String unit=u.getAttachUnit();
        List<sampleBorrow> sampleBorrowList=sampleBorrowService.selectByUnit(unit);
        session.setAttribute("sampleBorrowList",sampleBorrowList);
        return "redirect:/staff/allOut.jsp";
    }
    @RequestMapping("/out")
    public String out(HttpSession session,HttpServletRequest request) throws UnsupportedEncodingException {
        String method= new String(request.getParameter("select").getBytes("iso-8859-1"),"utf-8");
        System.out.println(method);
        List<sampleBorrow> list= (List<sampleBorrow>) session.getAttribute("sampleBorrowList");
        List<sampleBorrow> fl = new ArrayList<>();
        if(method.equals("待审核")){
            for(sampleBorrow t:list){
                if(t.getOutState().equals("待审核")){
                    fl.add(t);
                    System.out.println(t.getSampleName());
                }
            }
        }else if(method.equals("已借出")){
            for(sampleBorrow t:list){
                if(t.getOutState().equals("已借出")){
                    fl.add(t);
                }
            }
        }else if(method.equals("已结束")){
            for(sampleBorrow t:list){
                if(t.getOutState().equals("已结束")){
                    fl.add(t);
                }
            }
        }
        session.setAttribute("outList",fl);
        return "redirect:/staff/allOut.jsp";
    }


    //审核通过
    @RequestMapping("/pass/{id}")
    public String pass(@PathVariable("id") int id,HttpSession session){
        sampleBorrow  sampleBorrow=sampleBorrowService.selectBySampleID(id);
        sampleBorrow.setInState("已审核");
        sampleBorrow.setOutState("已借出");
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        Date date= (Date) session.getAttribute("thisLoginTime");
        sampleBorrow.setBorrowTime(formatter.format(date));
        sampleBorrowService.update(sampleBorrow);
        List<sampleBorrow> t= (List<com.pojo.sampleBorrow>) session.getAttribute("outList");
        t.clear();
        return "redirect:/staff/toOut";
    }

    //审核不通过
    @RequestMapping("/notPass/{id}")
    public String notPass(@PathVariable("id") int id,HttpSession session){
        sampleBorrow  sampleBorrow=sampleBorrowService.selectBySampleID(id);
        sampleBorrow.setInState("已结束");
        sampleBorrow.setOutState("已结束");
        sampleBorrowService.update(sampleBorrow);
        List<sampleBorrow> t= (List<com.pojo.sampleBorrow>) session.getAttribute("outList");
        t.clear();
        return "redirect:/staff/toOut";
    }

    //确认归还
    @RequestMapping("/returnOut/{id}")
    public String returnOut(@PathVariable("id") int id,HttpSession session){
        sampleBorrow  sampleBorrow=sampleBorrowService.selectBySampleID(id);
        sampleBorrow.setInState("已结束");
        sampleBorrow.setOutState("已结束");
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        Date date= (Date) session.getAttribute("thisLoginTime");
        sampleBorrow.setReturnTime(formatter.format(date));
        sampleBorrowService.update(sampleBorrow);
        List<sampleBorrow> t= (List<com.pojo.sampleBorrow>) session.getAttribute("outList");
        t.clear();
        return "redirect:/staff/toOut";
    }

    @RequestMapping("/count")
    public String count(HttpSession session){
//        	本单位总标本数；
        //从sb表中找单位一致的
        String staffName= (String) session.getAttribute("staffName");
        user u=userService.selectByRegName(staffName);
        String unit=u.getAttachUnit();
        List<sample> samples=sampleService.selectByUnit(unit);
        int allSamples=samples.size();
        session.setAttribute("allSamples",allSamples);
//	当前在库总标本数；
        List<sampleBorrow> sampleBorrowList=sampleBorrowService.selectByUnit(unit);
        int inSample=allSamples-sampleBorrowList.size();
        session.setAttribute("inSample",inSample);
        //	近年借阅情况统计（按年、按月借阅的折线图、柱状图等）
        //所有被借走的标本
        //按年
        List<sampleBorrow> sampleBorrowList1=sampleBorrowService.selectByUnit(unit);

        int[] year=new int[13];
        for(int j=0;j<13;j++){
            year[j]=0;
        }
        for (sampleBorrow s : sampleBorrowList1) {
            //获取月份
            int temp=Integer.valueOf(s.getBorrowTime().substring(5,7));
            System.out.println(s.getBorrowTime().substring(5,7));
            year[temp]+=1;
        }


        return "redirect:/staff/count2.html";
    }


    @RequestMapping("/exit")
    public  String exit(HttpSession session){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        Date date= (Date) session.getAttribute("thisLoginTime");
        String name= (String) session.getAttribute("username");
        user user=new user();
        user.setUsername(name);
        user.setLastLoginTime(formatter.format(date));
        userService.updateByTime(user);
        return "redirect:../homePage.jsp";
    }
}
