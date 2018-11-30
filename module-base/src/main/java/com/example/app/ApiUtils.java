package com.example.app;

public class ApiUtils {
    /**
     * 服务器地址
     * <p>
     * 测试8081
     * 正式9999
     */
    public static final String MAIN_URL = "http://222.209.82.13:9999/HTOA/";
    /**
     * 图片地址
     */
    public static final String IMAGE_PATH = "http://222.209.82.13:9999/htappimg/";


    //==============================================================================================
    //======================================================================================User====
    //==============================================================================================
    /**
     * 获取短信验证码
     * <p>
     * phone(用户输入的手机号)
     */
    public static final String CODE = MAIN_URL + "verificationCode.action";


    /**
     * 注册
     * phone(用户输入的手机号)
     * pwd(用户输入的密码)
     * pwd2(用户再次输入的密码)
     * code(用户收到的验证码)
     */
    public static final String REGISTER = MAIN_URL + "register.action";
    /**
     * 登录
     * <p>
     * phone(用户电话)
     * pwd(用户密码)
     */
    public static final String LOGIN = MAIN_URL + "phoneLogin.action";
    /**
     * 获取所有用户
     * personnelId(用户Id)
     */
    public static final String GET_ALL_CUSTOMER = MAIN_URL + "customer/getAllCustomer.action";
    /**
     * 获取所有用户
     * personnelId(用户Id)
     */
    public static final String GET_ORDER_CUSTOMER = MAIN_URL + "customer/getDealCustomer.action";


    /**
     * 获取用户信息
     * personnelId(用户Id)
     */
    public static final String GET_USER_INFO = MAIN_URL + "ph_personnelAction/getPersonnelById.action";


    /**
     * 修改用户信息
     * personnelId(用户Id)
     * imgStr
     */
    public static final String CHANGE_USER_INFO = MAIN_URL + "ph_personnelAction/updatePersonnelById.action";


    /**
     * 修改密码
     * personnelId(用户Id)
     * oldPwd（旧密码）
     * newPwd（新密码）
     */
    public static final String CHANGE_PASSWORD = MAIN_URL + "ph_personnelAction/changePwd.action";


    /**
     * 忘记密码
     * phone(手机号)
     * code(验证码)
     * newPwd(新密码)
     */
    public static final String FORGET_PASSWORD = MAIN_URL + "ph_personnelAction/forgetChangePwd.action";


    /**
     * 忘记密码验证码
     * phone(手机号)
     */
    public static final String GET_FORGER_CODE = MAIN_URL + "ph_personnelAction/forgetPwd.action";


    //==============================================================================================
    //=======================================================================================Life===
    //==============================================================================================
    /**
     * 获取寿险保单
     * personnelId（人员ID）
     * startDate(起始日期)
     * endDate(结束日期)
     */
    public static final String GET_LIFE_ORDER = MAIN_URL + "ph_lifeInsurance/getInsuranceByTime.action";

    /**
     * 获取保单详情
     * insureNo（保单号）
     */
    public static final String GET_LIFE_ORDER_DETAIL = MAIN_URL + "ph_lifeInsurance/getInsuranceByInsureNo.action";

    /**
     * 续保提醒
     * personnelId（人员ID）
     * startDate(起始日期)
     * endDate(结束日期)
     */
    public static final String GET_LIFE_ORDER_CONTINUE = MAIN_URL + "ph_lifeInsurance/getInsuranceActionByTime.action";


    /**
     * 我的团队
     * personnelId（人员ID）
     */
    public static final String GET_TEAM = MAIN_URL + "ph_personnelAction/getPersonnelAndSubordinate.action";


    /**
     * 信息完善
     * id（人员ID）
     * <p>
     * personnelName(人员姓名)
     * <p>
     * idCard(身份证号)
     * <p>
     * gender(性别)
     */
    public static final String POST_USER_INFO = MAIN_URL + "ph_personnelAction/realNameAuthentication.action";


    //==============================================================================================
    //=======================================================================================Home===
    //==============================================================================================
    /**
     * 轮播
     * 无
     */
    public static final String GET_BANNER = MAIN_URL + "ph_index/getLunBo.action";
    /**
     * 智通Token
     */
    public static final String GET_ZHI_TONG_TOKEN = MAIN_URL + "ztwltech/queryLoginToken.action";


    /**
     * 获取新闻
     */
    public static final String GET_NEWS = MAIN_URL + "ph_index/findAllNews.action";


    //==============================================================================================
    //======================================================================================Me====
    //==============================================================================================

    /**
     * 团队业绩
     * personnelId（人员ID）
     * startDate(起始日期)
     * endDate(结束日期)
     */
    public static final String GET_TEAM_PERFORMANCE = MAIN_URL + "ph_lifeInsurance/getInsuranceByPersonId.action";


}
