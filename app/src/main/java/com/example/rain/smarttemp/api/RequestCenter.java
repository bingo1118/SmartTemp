package com.example.rain.smarttemp.api;

import com.example.rain.smarttemp.global.MyApp;
import com.example.rain.smarttemp.model.DePartmentResonse;
import com.example.rain.smarttemp.model.HttpResponse;
import com.example.rain.smarttemp.model.PassPersonByConditionResponse;
import com.example.rain.smarttemp.model.PassRecordResponse;
import com.example.rain.smarttemp.model.PassSum;
import com.example.rain.smarttemp.model.PersonListResponse;
import com.example.rain.smarttemp.model.User;
import com.imooc.lib_network.okhttp.CommonOkHttpClient;
import com.imooc.lib_network.okhttp.listener.DisposeDataHandle;
import com.imooc.lib_network.okhttp.listener.DisposeDataListener;
import com.imooc.lib_network.okhttp.request.CommonRequest;
import com.imooc.lib_network.okhttp.request.RequestParams;

/**
 * 请求中心
 */
public class RequestCenter {
    public static final String ROOT_URL = "http://139.199.58.208:80/smartTemp/";
    public static final String WEB_URL="http://139.199.58.208:80";

    static class HttpConstants {


        //登陆
        public static String LOGIN = ROOT_URL + "login";
        //通行记录
        public static String PASSRECORD = ROOT_URL + "listPassRecord";
        //通行统计
        public static String PASSSUM = ROOT_URL + "getCurrentStats";
        //查询所有部门
        public static String DEPARTMENT = ROOT_URL + "listDepartment";
        //条件查询通行人员
        public static String PASSPERSONBYCONDITION = ROOT_URL + "getCurrentPersonListWithCondition";
        //查询人员列表
        public static String PERSONLIST = ROOT_URL + "listPerson";
        //查询报警列表
        public static String ALARMLIST = ROOT_URL + "listAlarm";
        //查询人员通行列表
        public static String PASSBYPERSONIDLIST = ROOT_URL + "getPassRecordByPersonId";
    }
    /**
     * 用户登陆请求
     */
    public static void login(String userId,String pwd,DisposeDataListener listener) {

        RequestParams params = new RequestParams();
        params.put("userId",userId);
        params.put("pwd", pwd);
        RequestCenter.getRequest(HttpConstants.LOGIN, params, listener, HttpResponse.class);
    }

    /**
     * 用户通行记录
     */
    public static void getPassRecords(String userId,String departmentId,String pageNo,String startTime,String endTime,DisposeDataListener listener) {

        RequestParams params = new RequestParams();
        params.put("userId",userId);
        params.put("departmentId", departmentId);
        params.put("pageNo",pageNo);
        params.put("startTime", startTime);
        params.put("endTime",endTime);
        RequestCenter.getRequest(HttpConstants.PASSRECORD, params, listener, PassRecordResponse.class);
    }

    /**
     * 查询人员通行历史
     */
    public static void getPassRecordByPersonId(String personId,String pageNo,DisposeDataListener listener) {

        RequestParams params = new RequestParams();
        params.put("personId",personId);
        params.put("pageNo", pageNo);
        RequestCenter.getRequest(HttpConstants.PASSBYPERSONIDLIST, params, listener, PassRecordResponse.class);
    }

    /**
     * 查询报警历史列表
     */
    public static void getAlarmList(String userId,String pageNo,String departmentId,String startTime,String endTime,DisposeDataListener listener) {

        RequestParams params = new RequestParams();
        params.put("userId",userId);
        params.put("pageNo", pageNo);
        params.put("departmentId",departmentId);
        params.put("startTime",startTime);
        params.put("endTime",endTime);
        RequestCenter.getRequest(HttpConstants.ALARMLIST, params, listener, PassRecordResponse.class);
    }

    /**
     * 条件查询今日通行人员列表
     * params:userId(必须)，pageNo(分页页码)，option(必须，0/1/2 员工/访客/体温异常)
     */
    public static void getCurrentPersonListWithCondition(String userId,String pageNo,String option,DisposeDataListener listener) {

        RequestParams params = new RequestParams();
        params.put("userId",userId);
        params.put("pageNo", pageNo);
        params.put("option",option);
        RequestCenter.getRequest(HttpConstants.PASSPERSONBYCONDITION, params, listener, PersonListResponse.class);
    }

    /**
     * 获取人员列表
     * params:userId(必须)，pageNo(分页页码)，option(必须，0/1/2 员工/访客/体温异常)
     */
    public static void getListPerson(String userId,String departmentId,String pageNo,DisposeDataListener listener) {

        RequestParams params = new RequestParams();
        params.put("departmentId",departmentId);
        params.put("userId",userId);
        params.put("pageNo",pageNo);
        RequestCenter.getRequest(HttpConstants.PERSONLIST, params, listener, PersonListResponse.class);
    }

    /**
     * 用户通行统计
     */
    public static void getPassSum(String userId,DisposeDataListener listener) {

        RequestParams params = new RequestParams();
        params.put("userId",userId);
        RequestCenter.getRequest(HttpConstants.PASSSUM, params, listener, PassSum.class);
    }

    /**
     * 查询部门
     */
    public static void getDepartment(DisposeDataListener listener) {

        RequestParams params = new RequestParams();
        params.put("userId", MyApp.getUserID());
        RequestCenter.getRequest(HttpConstants.DEPARTMENT, params, listener, DePartmentResonse.class);
    }

    //根据参数发送所有get请求
    public static void getRequest(String url, RequestParams params, DisposeDataListener listener,
                                  Class<?> clazz) {
        CommonOkHttpClient.get(CommonRequest.
                createGetRequest(url, params), new DisposeDataHandle(listener, clazz));
    }

    //根据参数发送所有post请求
    public static void postRequest(String url, RequestParams params, DisposeDataListener listener,
                                  Class<?> clazz) {
        CommonOkHttpClient.post(CommonRequest.
                createGetRequest(url, params), new DisposeDataHandle(listener, clazz));
    }

}
