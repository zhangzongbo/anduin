package com.zogbo.common.utils.enums;


import com.zogbo.common.config.Config;

/**
 * Created by chenwen on 16/6/23.
 */
public enum BasicErrorCodeEnum implements IErrorCode {
    OK_E000000("请求成功"),
    SERVER_ERROR_E000001("系统繁忙,请稍后再试"),
    ILLEGAL_ARGUMENT_ERROR_E000002("请求参数不合法,请核对参数"),
    ARGUMENT_PARSE_ERROR_E000003("参数解析异常"),
    NOT_SUPPORTED_METHOD_ERROR_E000004("不支持的请求方式"),
    PERMISSION_ERROR_E000005("权限不足"),
    NOT_LOGIN_E000006("登录信息失效,请重新登录"),
    USERNAME_OR_PASSWORD_ERROR_E000007("用户名或密码错误"),
    ACCOUNT_INVALID_ERROR_E000008("账号无效"),
    ACCOUNT_FREEZE_ERROR_E000008("账号已暂停，请联系后台管理员"),
    ACCOUNT_NOT_EXISTS_ERROR_E000009("用户名不存在"),
    ACCOUNT_LOGIN_MANY_COUNT_ERROR_E000010("您的账号在其他地方登录,当前登录状态失效"),
    CAPTCHA_TIMES_LIMIT_ERROR_E000011(String.format("获取验证码次数达到上限,请%s分钟后重试", Config.getInt("captcha.times.timeout.limit", 30))),
    CAPTCHA_TIMEOUT_ERROR_E000012("验证码已超过有效期，请重新获取验证码"),
    CAPTCHA_WRONG_ERROR_E000013("验证码输入有误，请重新获取验证码"),
    CAPTCHA_WRONG_TIME_LIMIT_ERROR_E000013(String.format("验证码错误次数超过%s次,请%s分钟之后重试", Config.getInt("captcha.wrong.times.limit", 5), Config.getInt("captcha.wrong.times.timeout.limit", 30))),
    NO_GOODS_ERROR_E000014("商品不存在，请刷新商品列表"),
    NO_PICTURE_ERROR_E000019("图片不存在"),
    USERNAME_EXISTS_ERROR_E000015("用户名已存在"),
    DEPARTMENT_EXISTS_ERROR_E000016("部门已存在"),
    ROLE_EXISTS_ERROR_E000017("职位已存在"),
    NOT_UPDATE_ERROR_E000018("未提交更新"),
    PASSWORD_LIMIT_ERROR("密码错误已经输错%s次,连续输错%s次账号将被锁定"),
    PASSWORD_LIMIT_MESSAGE_ERROR_E000019("密码错误次数过多,账号已被锁定"),
    SUBMIT_ERROR_E000019("提交失败"),
    ICE_SERVICE_ERROR_E000020("冰鉴黑名单接口异常"),
    NOT_NEED_ADVANCE_ERROR_E000123("不需要支付首付"),
    NO_ADDITIONAL_INFO_ERROR_E000124("未找到家庭住址信息"),
    QUOTA_IS_NO_ENOUGH_ERROR_E000125("额度不足，暂无法申请"),

    CAN_NOT_MODIFY_BANKCARD_ERROR_E000123("暂时不能更换银行卡"),
    UPLOAD_ERROR_E000019("上传数据异常"),
    ID_CARD_INFO_NEEDED_ERROR_E000020("身份证信息未填写"),
    BANKCARD_INFO_NEEDED_ERROR_E000021("银行卡信息未填写"),
    BANKCARD_AUTHED_ERROR_E000021("此卡已经是默认扣款银行卡，无需绑定"),
    NO_AUTO_BANKCARD_ERROR_E000021("未找到默认扣款银行卡"),
    NO_GOOD_CONF_ERROR_E000022("商品配置不存在"),
    NO_GOOD_TYPE_ERROR_E000023("商品类型不存在"),
    NEED_NECESSARY_AUTH_ERROR_E000024("必要授权未完成"),
    OPEN_SDK_ERROR_E000025("调用第三方接口返回异常"),
    ID_CARD_VALIDATE_ERROR_E000026("身份证姓名和身份证号不符合"),
    ID_CARD_CAN_NOT_VALIDATE_ERROR_E000027("无法校验的姓名和身份证号"),
    BANKCARD_VALIDATE_ERROR_E000028("银行卡信息和姓名身份证不符"),
    BANKCARD_CAN_NOT_VALIDATE_ERROR_E000029("无法校验的银行卡"),
    AUTH_ID_CARD_FIRST_ERROR_E000030("请先实名认证"),
    NO_USER_ERROR_E000031("暂无用户"),
    OPEN_PLATFORM_SIGNATURE_ERROR_E000032("开放平台签名校验不通过"),
    OPEN_PLATFORM_E000032("调用开放平台报错"),
    OPEN_PLATFORM_OPERATOR_NO_DATA_E000032("未找到运营商数据"),
    OPEN_PLATFORM_OPERATOR_DATA_FORMAT_ERROR_E000032("格式化运营商数据异常"),
    UNKNOW_CHANNEL_ERROR_E000033("未知渠道"),
    NO_ORDER_ERROR_E000034("不存在订单号"),
    NO_SNAPSHOT_ERROR_E000035("请联系分期顾问，重新打开商品二维码进行操作"),
    NO_SELLER_ERROR_E000036("不存在商家"),
    NO_CLERK_ERROR_E000037("不存在办单员"),
    NO_STAGENUM_ERROR_E000038("非法的分期参数"),
    ANXIN_INSRUANCE_ERROR_E000039("AX接口出错"),
    ORDER_REFUSE_IN_TIME_ERROR_E000040("已有拒绝未满三个月的申请"),
    ORDER_HANDLING_ERROR_E000041("有正在处理中的订单,请前往我的订单中查看"),
    NO_CHANNEL_CONF_ERROR_E000042("未找到渠道配置"),
    NO_CHANNEL_INSURANCE_CONF_ERROR_E000043("未找到渠道保险配置"),
    ORDER_STATUS_ERROR_E000044("违规操作"),
    ACCEPT_INSURANCE_POLICY_ERROR_E000045("请先同意保险协议"),
    JINGDONG_PAY_SIGNATURE_ERROR_E000046("京东代付校验签名未通过"),
    JINGDONG_AGREEMENT_APPLY_ERROR_E000047("京东签约失败"),
    PICTURE_LIMIT_ERROR_E000039("上传图片数量超出限制,请重新上传"),
    PICTURE_IS_NULL_E000339("发货照片为空,请重新上传"),
    ORDER_NOT_BELONG_USER_ERROR_E000040("该订单不属于当前登录的办单员或渠道"),
    OPEN_PLATFORM_FAILED_ERROR_E000047("开放平台回调爬虫失败,暂不绑定授权关系"),
    NOT_CHANNEL_GOOD_ERROR_E000048("请联系分期顾问，检查APP与门店是否在同一渠道"),
    USER_REGISTERED_ERROR_E000049("身份证已关联其他手机号，如有疑问，请咨询客服咨询"),
    EMERGENCY_PHONE_EXIT_ERROR_E000050("紧急联系人号码不可重复，请检查"),
    EMERGENCY_PHONE_UPDATE_ERROR_E000050("紧急联系人不存在，请检查"),
    EMERGENCY_PHONE_EQUAL_SELF_EXIT_ERROR_E000051("紧急联系人联系电话与本人电话相同"),
    CHANNEL_SELLERLIST_ERROR_E000050("渠道用户仅能操作本渠道下商户"),
    CAFINTECH_BILL_GENERATE_FAIL_ERROR_E000051("生成平台用户还款计划失败"),
    CHANNEL_UNREACHABLE_ERROR_E000052("门店状态异常，请分期顾问咨询后台运营人员"),
    SELLER_UNREACHABLE_ERROR_E000053("门店状态异常，请分期顾问咨询后台运营人员"),
    GOODS_OUT_SALE_ERROR_E000054("商品已下架"),
    GOODTYPE_ERROR_E000055("商品类别已失效"),
    ID_CARD_MINOR_ERROR_E000056("请检查身份证是否满足18-55周岁"),
    INSURANCE_INIT_ERROR_E000057("保险信息初始化出错,请联系技术."),
    NO_INSURANCE_CONF_ERROR_E000058("未找到有效保险配置"),
    NO_INSURANCE_USER_FIELDS_ERROR_E000059("未找到保险导出配置"),
    INSURANCE_HAVE_SUCCESS_ERROR_E000060("保单已签约"),
    NO_BILL_TO_PAY_ERROR_E000060("该订单没有需要还款的账单"),
    BILL_MONEY_ERROR_E000061("账单金额错误，请联系技术人员"),
    ALIPAY_SIGNATURE_ERROR_E000062("ALIPAY支付签名校验未通过"),
    ALIPAY_ORDER_INFO_ERROR_E000063("获取支付宝支付结果失败"),
    NO_VERSION_ERROR_E000062("未找到版本号"),
    NO_EXPORT_DATA_ERROR_E000063("没有要导出的数据"),
    RUNNING_WATER_IN_THE_DEDUCTION("有正在扣款中的流水"),
    NO_BILL_ERROR_E000064("未查询到账单"),
    BILL_STATUS_ERROR_E000064("账单不是可扣款状态"),
    BILL_REPAY_STATUS_ERROR_E000064("账单已还款,请勿重复推送还款流水"),
    BILL_HAS_MODIFY_ERROR_E000064("账单已被修改,请重新设置提前结清信息"),
    BILL_NOT_ALLOW_EARLY_SETTLEMENT_ERROR_E000064("该订单不符合提前结清条件"),
    BILL_NOT_ALLOW_NY_EARLY_SETTLEMENT_ERROR_E000064("该订单不符合诺远提前结清条件"),
    BILL_NOT_FIND_SETTLEMENT_ERROR_E000064("未找到订单风险管理费记录"),
    BILL_MONEY_IS_INVALID_ERROR_E000064("提前结清扣款金额不合法,请重新设置"),
    BILL_CHANNEL_MONEY_IS_INVALID_ERROR_E000064("提前结清扣款渠道应补应退金额不合法,请重新设置"),
    NORMAL_REPAY_LESS_THAN_SIX_MONTH_ERROR_E000123("存在正常还款小于6个月的订单，暂不能发起申请"),
    NOT_NORMAL_REPAY_ERROR_E000123("存在非正常还款的订单，暂不能发起申请"),
    NO_BILL_TO_PAY_ERROR_E000065("到期还款为0"),
    HANDLING_BILL_ERROR_E000066("系统中有正在发起的扣款，请稍后查询结果"),
    CHANGE_HOLD_PICTURE_ERROR_E000068("授权成功，提交后由办单员确认"),
    OTHER_PICTURE_ERROR_E000070("其他照片材料授权少于两张"),
    NO_PEOPLE_ERROR_E000071("图片中不含有头像"),
    RISK_USER_ERROR_E000072("非常抱歉,预审拒绝，请三个月后重新申请"),
    INTEREST_ERROR("年化利率应该大于0小于1"),
    PASSWORD_LIMIT_ERROR_E000019("密码错误次数过多,账号已被锁定"),
    USER_CHANNEL_EMPTY_ERROR_E000020("用户关联渠道为空,请先给用户关联渠道"),
    EXIST_GOODS_ERROR_E000024("商品已存在，请直接选择原有商品修改"),
    GOODS_TYPE_INVALID_ERROR_E000025("商品类型已失效，请选择其他类型"),
    UPLOAD_ERROR_E000021("上传数据解析异常"),
    UPLOAD_FORMAT_ERROR_E000021("上传数据格式有问题,请核对"),
    ORDER_NOT_EXISTS_E000022("订单不存在"),
    ORDER_STATE_NOT_EXISTS_E000023("订单状态不存在"),
    BANKCARD_INFO_NEEDED_ERROR_E000024("已存在此渠道"),
    ID_CARD_INFO_NEEDED_ERROR_E000025("身份证信息未填写"),
    BANKCARD_INFO_NEEDED_ERROR_E000026("银行卡信息未填写"),
    BANKCARD_INFO_EXISTS_ERROR_E000027("银行卡信息不存在"),
    NOT_SKIP_ERROR_E000028("订单不能跳过"),

    NO_COMPENSATE_BATCH_ERROR("未找理赔批次信息"),
    DETAIL_CAN_NO_RELAUNCH("理赔明细状态不支持重新发起理赔"),
    MORE_THAN_ONE_BATCH_WANT_RELAUNCH("不同批次的理赔明细不能在同一批发起"),
    CAN_NOT_INSURANCE_CONFIRM("当前状态不能发起理赔结清确认"),
    CAN_NOT_INVESTOR_CONFIRM("当前状态不能发起资方结清确认"),

    INVESTOR_NOT_EXISTS_E000039("资方不存在"),
    USER_NOT_EXISTS_ERROR_E000028("用户不存在"),

    CHANNEL_ACCOUNT_EXISTS_ERROR_E000029("渠道账号已经创建"),
    ICE_LOGIN_ERROR_E000029("冰鉴登录失败"),
    RISK_ERROR_E000029("调用风控异常"),
    NO_RISK_FOUND_ERROR_E000029("未找到合适的风控"),
    RISK_DATA_FORMAT_ERROR_E000029("风控数据格式化异常"),

    CHANNEL_EXISTS_ERROR_E000029("渠道已经创建"),

    INSURANCE_ACCOUNT_EXISTS_ERROR_E000030("保险账号已经创建"),

    INVESTOR_ACCOUNT_EXISTS_ERROR_E000030("资方账号已经创建"),

    UNSUPPORTED_ERROR_E000030("不支持的操作"),

    ACCOUNT_NOT_EXISTS_E000031("账户不存在"),

    ORDER_STATE_UN_SUPPORT_EXISTS_E000032("订单所在状态不支持该操作"),

    UPLOAD_FILE_NOT_EXISTS_E000033("请选择文件上传"),

    GOODS_TYPE_EXISTS_E000034("该金融方案已经存在"),

    GOODS_TYPE_NOT_EXISTS_E000034("该金融方案不存在"),

    NAME_EXISTS_E000034("名字已经存在"),

    CHANNEL_CONF_NOT_EXISTS_E000034("渠道配置不存在"),

    RATE_NOT_EXISTS_E000034("所填费率必须在指定区间内"),

    AUDIT_AMOUNT_INVALID_E000034("审核金额不能大于申请金额"),

    AUDIT_PERIOD_INVALID_E000034("该商品不存在审核期数"),

    AUDIT_RATE_INVALID_E000034("审核费率超出渠道配置范围"),

    ORDER_ALREADY_AUDITING_PASS_ERROR_E000034("订单已审核通过,请勿重复操作"),

    ORDER_ALREADY_AUDITING_ERROR_E000034("订单未通过审核,请先进行审核通过处理操作"),

    LOGIN_FAILED_E000035("登录失效,请重新登录"),

    INSURANCE_EXISTS_E000036("保险方已经存在"),

    INSURANCE_NOT_EXISTS_ERROR_E000037("保险方不存在"),

    INVESTOR_NOT_EXISTS_ERROR_E000038("资金方不存在"),

    ORDER_NOT_DELIVER_E000036("订单未发货"),

    INVESTOR_NOT_LOAN_E000037("资方未放款"),

    ORDER_CHECK_FAIL_E000038("核对失败"),

    CHANNEL_AREA_IS_NULL_E000039("该渠道负责区域为空,请在渠道配置中进行添加"),

    USER_AREA_NOT_BELONG_CHANNEL_AREA_E000039("添加的区域有不属于渠道负责的地区,请检查后重新添加"),

    USER_AREA_MAY_BE_NULL_E000039("添加的区域信息为空"),

    CLERK_IDCARD_ALREADY_EXIST_E000036("办单员身份信息已存在,请勿重复添加"),

    CHANNEL_NOT_EXISTS_INSURANCE_E000036("渠道没有配置保险方案,所以不能给保险方充值"),

    REPEAT_IMPORT_E000037("重复导入"),

    DELETE_SELF_ERROR_E000037("不能删除自己"),

    MODIFY_SELF_ERROR_E000037("不能修改自己的状态"),

    EXPORT_FILE_ERROR_E000038("导出文件失败"),

    DOWNLOAD_FILE_TO_LOCAL_ERROR("照片同步到本地失败"),

    IMPORT_SUCCESS_E000037("导入成功"),

    NOT_REALOAN_E000038("已经核对,不能重复放款"),

    NOT_AllOWED_E000039("不符合核对条件(检查发货及放款情况！)"),

    NO_CHANNEL_CONF_E00040("未找到渠道配置"),

    EXPORT_DATA_NOT_AllOWED_EMPTY_E000041("导出的数据不能为空"),

    EXPORT_DATA_APP_USER_EMPTY_E000042("该筛选条件下,无对应用户数据,无法导出."),

    EXPORT_DATA_CHANNEL_EMPTY_E000043("该筛选条件下,无对应渠道数据,无法导出."),

    EXPORT_DATA_FREQUENCY_E000044("导出操作过于频繁,请10s后再试"),

    EXIST_SELLER_NAME_E000045("该商户名称已存在,请勿重复添加"),

    EXIST_USER_NAME_E000046("登录账号已被注册,请更换其他号码"),

    CLEARING_CASE_NOT_EXIST_E000047("结算方案不存在"),
    SELLER_NOT_EXIST_E000047("商户不存在"),

    JD_PAYMENT_E000047("支付失败"),
    PAY_FAILED_E000047("支付失败"),

    CHANNEL_UNAVAILABLE_E000047("渠道暂未开放，请耐心等待通知"),

    CASH_LOAN_CHANNEL_UNAVAILABLE_E000047("现金贷款暂未开放，请耐心等待通知"),
    NO_CASH_QUOTA_ERROR_E000050("取现额度不足，请提升额度后再来"),

    CASH_LOAN_CHANNEL_LIMIT_E000048("24小时内已申请三次超过限制,请明天再申请"),

    CASH_LOAN_RATE_UPDATED_E000049("费率变化，请确认后提交"),

    INSURANCE_CONFORM_FAIL_E000888("投保失败,请返回上一页,请重新投保"),

    INSURANCE_NOTICE_REFUSE_E000889("保险签约拒绝,申请失败,3个月后可重新申请"),
    INSURANCE_OK_E000000("签约成功,请确认下一步"),

    USER_NOT_IN_WHITELIST_E999900("对不起,您不满足风行贷申请资格.如有疑问请联系0471-2941666"),


    FACE_BANK_VALID_SIGIN_FAIL_E000999("笑脸回调接口验签失败,如有疑问请联系zzm"),
    KE_YI_VALID_SIGIN_FAIL_E000998("科易回调接口验签失败,如有疑问请联系zzm"),
    KE_YI_GET_SIGINDATA_FAIL_E000997("科易签约数据失败,如有疑问请联系zzm"),
    FACE_BANK_COMPENSATE_NOTIFY_FAIL_ERROR("通知笑脸理赔结清失败，黄聘查日志"),

    USER_NOT_IN_WHITE_LIST_FXD_E999900("对不起,您不满足风行贷申请资格.如有疑问请联系0471-2941666"),

    USER_NOT_IN_WHITE_LIST_YDH_E999900("对不起,您不满足一点花申请资格.请关注微信公众号:一点花"),

    USER_NOT_IN_WHITE_LIST_XLYX_E999900("对不起,您不满足响亮宜信申请资格.如有疑问请联系0952-4691111"),

    SIGNATURE_ERROR("签名错误"),

    NO_DETAIL_ERROR("批次号没有关联理赔明细"),

    USER_NOT_IN_WHITE_LIST_E999900("对不起,您不满足申请资格.如有疑问请联系客服"),
    GET_CONTACTS_E7777700("未找到通讯录"),
    ORDER_ALREADY_PUSH_FIELDS_E333300("订单已推送过,请勿重复推送"),
    ORDER_PUSH_SNAPSHOT_E333301("订单推送快照不存在"),
    ORDER_DETAIL_SNAPSHOT_E333301("订单资产明细快照不存在"),
    ORDER_PICTURES_MORE_E333301("图片资料数异常"),
    INVESTOR_CASE_NOT_EXIST_E333302("资金方案不存在"),
    INVESTOR_CASE_QUERY_RESULT_ONE_MORE_E333303("存在多个资金方案,暂不支持推送"),
    NO_INVESTOR_USER_FIELDS_ERROR_E333304("资方配置推送字段为空,不能进行推送"),
    KE_YI_INVESTOR_USER_FIELDS_NOT_EXIST_ERROR_E333305("资方配置推送字段枚举值为空"),
    KE_YI_PRODUCT_PRICE_IS_NULL_ERROR_E333306("科益进件,商品金额字段为null"),
    KE_YI_CUST_REPAYMENT_AMOUNT_IS_NULL_ERROR_E333307("科益进件,用户还款额额字段为null"),
    KE_YI_AGE_IS_NULL_ERROR_E333308("科益进件,用户年龄字段为null"),
    KE_YI_MARRIAGE_IS_NULL_ERROR_E333309("科益进件,婚姻字段为null"),
    ORDER_ALREADY_LOAN_SUCCESS_ERROR_E333310("订单已经放款成功,请勿重复操作"),
    ORDER_NOT_BELONG_THIS_INVESTOR_ERROR_E333311("权限不足,订单不属于该资金方"),
    KE_YI_ASSET_NOTIFY_ENUM_IS_NULL_E333312("科益审核枚举值不存在"),
    KE_YI_RESPONSE_IS_NULL_E333313("科益返回值为空"),
    CONF_IS_NOT_CONTAIN_KE_YI_ID_E333314("配置文件中未配置科益资方id"),
    ORDER_NOT_BELONG_THIS_INVESTOR_E333315("此订单不属于该资方"),
    INSURANCE_CONF_PRIMARY_KEY_IS_NULL_E333316("配置文件中,个人借款模式保险方案primaryKey取值为空"),
    INSURANCE_CONF_IS_NULL_E333317("个人借款模式保险方案为null"),
    INSURANCE_CONF_RATE_IS_NULL_E333318("个人借款模式保险费率为null"),
    CHANNEL_REPAYMENT_AMOUNT_IS_BE_MODIFIED_E333319("代还款账单金额发生修改,请重新发起"),
    KE_YI_VALID_SIGN_FAIL_E000999("科益回调接口验签失败"),
    SMS_OF_LAN_SHU_YE_ERROR_E999900("短信接口调用失败"),
    THIS_PERIOD_NOT_ALLOW_CHANNEL_REPAYMENT_E333321("代还款的账单不能超过当期账单时间"),
    CHANNEL_REPAYMENT_SELECT_PERIOD_INVALID_E333322("代还款的账单期数选择不合法"),
    CHANNEL_REPAYMENT_FAIL_E333323("渠道代还款失败"),
    EARLY_SETTLEMENT_OFF_LINE_FAIL_E333324("线下提前结清失败"),
    ACCOUNT_LEAST_AMOUNT_ERROR_E333325("账户余额不足"),
    INVESTOR_NOT_ALLOW_CHANNEL_PAYMENT_ERROR_E333326("科易资方订单不在发起时间范围内(仅限每日9-22时)"),
    KY_VALID_SIGIN_FAIL_E001000("科易回调接口验签失败,如有疑问请联系zzm"),
    NOT_INVESTOR_CASE_KEY_EXISTS_ERROR_E00000089("请按照资金方案筛选订单后,发起推送,资金方案不能为空"),
    NOT_BANK_LIST_ERROR_E000000089("请选择所支持的银行卡筛选条件"),
    NOT_EQ_INVESTOR_CASE_KEY_PUSH_ERROR_E00000090("暂不支持,不同资金方案推送,请重新筛选订单"),
    NOT_DATA_PUSHT_EXISTS_ERROR_E00000091("请选择推送订单"),
    NOT_INVESTOR_CONF_EXISTS_ERROR_E00000092("资金方案配置不存在"),
    IMPORT_INVESOTR_RESULT_FILE_ERROR_E00000093("审核结果导入文件不存在"),
    IMPORT_INVESOTR_RESULT_LOANAMOUNT_ERROR_E00000094("资金方放款金额和预期放款金额不匹配"),
    IMPORT_INVESOTR_RESULT_PERIOD_ERROR_E00000095("资金方放款期数和申请期数不匹配"),
    LAUNCH_NO1SIGN_ERROR_E00000094("发起失败，所选订单中包含异常订单，请筛选后再重新操作"),
    LAUNCH_NO1SIGN_NOT_DATA_EXISTS_ERROR_E00000094("导入的签约文件中订单编号不能为空"),
    NO1SIGN_RESULT_ERROR_E00000095("导入失败，含有非签约中订单"),
    NO1SIGN_RESULT_ERROR_E00000096("导入失败，导入文件数据异常"),
    LAUNCH_NO1SIGN_NOT_CONTRACTNO_E00000097("签约合同号不能为空"),
    LAUNCH_NO1SIGN_NOT_E00000098("不需要一号签约订单请先导入签约结果"),
    INVESTOR_CASEKEY_NOT_EXISTS_ERROR_E00000090("请选择资金方案！"),
    INVESTOR_CASEKEY_NOT_EXISTS_ERROR_E00000091("更改目标资金方案不能为空！"),
    NOT_DATA_UPDATE_INVESOTR_CASE_KEY_EXISTS_ERROR_E00000091("更改资金方案的订单不能为空"),
    NOT_INVESOTR_CASE_KEY_EXISTS_ERROR_E00000092("改资金方案未匹配到可更改的资金方案"),
    NOT_INSURANCE_ERROR_E000060("保单不存在"),
    NOT_CONTRACTNO_E00000093("科易合同号不能为空"),
    NOT_LOAN_AMOUNT_E00000094("放款金额不能为空"),
    NOT_EQ_INVESTOR_CASEKEY_ERROR_E00000095("暂不支持不同资金方案订单修改其他资金方案"),
    EQ_INVESTOR_CASEKEY_ERROR_E00000096("订单资金方案相同无需更改"),
    ERROR_INVESTOR_CONF_ERROR_E00000097("资金方案理赔回款账号配置错误或未配置"),
    NOT_DATA_EXISTS_ERROR_E0000003("没有符合条件的推送订单"),

    INVESTOR_PUSH_ORDER_NOT_EXISTS_ERROR_E0000001("请先选择订单状态为'未推送'的订单"),

    LAUNCH_NO1SIGN_NOT_DATA_EXISTS_ERROR_E00000008("请选择订单或者直接导入订单编号发起"),

    NOT_LAUNCH_NO1SIGN_ERROR_E0000006("发起失败，所选订单中包含异常订单，请筛选后再重新操作"),

    GENERATE_INSURANCE_POLICY_DATA_EXISTS_ERROR_E0000007("请选择订单或者直接导入订单编号发起"),
    GENERATE_INSURANCE_POLICY_DATA_EXISTS_ERROR_E0000009("导入文件不存在"),
    GENERATE_INSURANCE_POLICY_ERROR_E0000008("发起失败，所选订单中包含异常订单，请筛选后再重新操作"),
    GENERATE_INSURANCE_POLICY_ANXIN_ERROR_E00000010("安心保单生成失败"),
    GENERATE_INSURANCE_POLICY_ERROR_E00000012("发起失败，所选订单属于配置无保险，请筛选后再重新操作"),


    NOT_ALLOW_REPAYMENT_ERROR_E999900("科易资方每日9时-每日22时期间才可扣款"),
    NOT_ALLOW_NUO_YUAN_REPAYMENT_ERROR_E999900("诺远资金方到期后每日14时后才可还款"),
    NOT_ALLOW_NUO_YUAN_REPAYMENT_ERROR_E999901("诺远资金方到期后才可还款"),
    NOT_ALLOW_REPAYMENT_ERROR_E999990("维纳2017-09-01后发货的不能发起主动扣款"),

    INSURANCE_OK_E9999900("保单发起成功，请稍后查看结果"),
    PRE_INSURANCE_OK_E9999900("预备保单发起成功，请稍后查看结果"),
    SIGN_OK_E9999910("签约发起成功，请稍后查看结果"),
    SIGN_RESULT_OK_E9999910("签约导入成功，请稍后查看结果"),
    VISIT_RESULT_OK_E9999910("回访导入成功，请稍后查看结果"),
    ONLINE_INVESTOR_E9999910("线上资方请和相关人员确认签约回调"),
    NO_RISK_FOUND_ERROR("未找到风控方"),
    GOODS_TYPE_NOT_EXISTS_E333327("金融方案不存在"),
    GOODS_TYPE_CONF_NOT_EXISTS_E333328("金融方案配置不存在"),
    REPAY_PACKAGE_CONF_NOT_EXISTS_E333329("未找到还款服务包费用"),
    REPAY_PACKAGE_TIME_OUT_E333330("未在有效时间内操作,已不能购买"),
    GOODS_TYPE_CONF_DETAIL_E333331("请配置金融方案利率明细"),
    A_LI_PAY_AMOUNT_DISACCORD_E333332("支付宝通知金额,与应收金额不一致"),
    ONLY_CUSTOMER_LOAN_ALLOW_REPAY_PACKAGE_E333333("当前仅允许消费贷支付还款服务包"),
    CHANNEL_CONF_MONTH_RATE_INVALID_E333334("商品金融方案费率不在修改客户总月费率区间中"),
    BILL_NOT_IN_ALLOW_REPAY_TIME_E333335("科易账单不在允许还款时间范围内"),
    BILL_NOT_IN_ALLOW_REPAY_TIME_E333336("优易资方账单不在允许还款时间范围内"),
    ORDER_NOT_ALLOW_EARLY_SETTLEMENT_E333337("该资金方不允许发起提前结清"),
    DATE_PARSE_ERROR_E333338("时间格式转换失败"),
    ICE_OPERATOR_CUST_ID_IS_NULL_ERROR_E333339("运营商授权custid为空"),
    ICE_OPERATOR_UUID_IS_NULL_ERROR_E333340("运营商授权获取uuid为空"),
    ICE_OPERATOR_IS_SUCCESS_ERROR_E333341("运营商已授权完成,请稍后提单"),
    AUTH_RELATION_IS_NULL_ERROR_E333341("绑定授权关系记录为空"),
    APP_PAY_TYPE_ERROR_E333342("违规操作"),
    REPEATEDLY_PAY_ADDED_VALUE_SERVICE_ERROR_E333343("已购买过增值服务,稍后会有支付结果"),
    ADDED_VALUE_SERVICE_IS_NULL_ERROR_E333344("未查询到该增值服务"),
    PAYMENT_INFO_RECORD_IS_NULL_ERROR_E333345("未查询到支付记录"),
    PAYMENT_INFO_OUT_TRADE_NO_ERROR_E333346("支付宝回调流水号不存在"),
    APP_FORCE_UPDATE_ERROR_E333346("更新提示,请重新下载app"),
    REPEATEDLY_PAY_ERROR_E333347("查询到有待支付的记录,请及时支付或稍后再次发起支付"),
    NO_LOAN_INFO_RECORD_ERROR_E333348("未找到放款记录"),
    CHANNEL_NOT_EXIST_ERROR_E333348("未找到渠道"),
    INSURANCE_POLICY_NOT_EXIST_ERROR_E333348("未找到保险记录"),
    INSURANCE_POLICY_STATUS_ERROR_E333348("保险记录状态不对"),
    BANKCARD_NOT_EXIST_ERROR_E333348("未找到银行卡记录"),
    FILE_NOT_EXIST_ERROR_E333348("未找到文件"),
    ORDER_ALREADY_PUSH_RESOURCE_ERROR_E333348("订单已上传过资料"),
    ORDER_NOT_PRE_PUSH_ERROR_E333348("订单未进行预推参数校验,请先手动推送"),
    ORDER_NOT_TBDPOLICY_ERROR_E333348("订单不没有预保单"),
    ORDER_NOT_BELONG_NUO_YUAN_ERROR_E333348("订单不属于诺远资方"),
    ORDER_NOT_BELONG_XIAO_NUO_ERROR_E333348("订单不属于小诺资方"),
    ORDER_CONTACT_NO_IS_NULL_ERROR_E333348("订单签约编号不能为空"),
    NUO_YUAN_PUSH_RESOURCE_ERROR_E333348("诺远上传资料失败,请重新推送"),
    XIAO_NUO_PUSH_RESOURCE_ERROR_E333348("小诺上传资料失败,请重新推送"),
    NUO_YUAN_PUSH_ORDER_ERROR_E333348("诺远推送数据失败"),
    NUO_YUAN_BANK_CODE_ERROR_E333348("诺远银行卡信息转换失败"),
    NUO_YUAN_SYNCHRONOUS_BILL_ERROR_E333348("诺远同步账单失败"),
    NUO_YUAN_AND_CAFINTECH_BILL_DIFF_ERROR_E333348("诺远账单与平台账单不一致"),
    INVESTOR_BILL_ALREADY_EXIST_ERROR_E333348("资方账单已存在"),
    NUO_YUAN_HTTP_ERROR_E333348("诺远http请求失败"),
    NUO_YUAN_STATUS_ERROR_E333349("订单进件失败,无法同步标的状态,请检查数据"),
    NUO_YUAN_PUSHRESOURCE_STATUS_ERROR_E333349("未上传资料,无法进行操作"),
    NUO_YUAN_LOAN_STATUS_ERROR_E333349("诺远资方未进行放款,无法拉取协议"),
    XIAO_NUO_LOAN_STATUS_ERROR_E333349("小诺资方未进行放款,无法拉取协议"),
    NUO_YUAN_AUDIT_STATUS_ERROR_E333349("诺远资方审核未成功,无法拉取协议"),
    XIAO_NUO_AUDIT_STATUS_ERROR_E333349("小诺资方审核未成功,无法拉取协议"),
    NUO_YUAN_PULL_ERROR_E333349("拉取诺远协议失败,url地址为空"),
    XIAO_NUO_PULL_ERROR_E333349("拉取小诺协议失败,url地址为空"),
    NUO_YUAN_LOAN_ERROR_E333349("诺远资方放款异常"),
    NUO_YUAN_PUSH_STATUS_ERROR_E333349("诺远资方推送状态异常"),
    XIAO_NUO_PUSH_STATUS_ERROR_E333349("小诺资方推送状态异常"),
    NUO_YUAN_IMPORT_INVESTOR_RESULT_ERROR_E333349("已进行保单推送的订单不允许再次操作审核结果导入"),
    RULES_PLATFORM_UPLOAD_DATA_ERROR_E333350("规则平台推送数据出错"),
    RULES_PLATFORM_UPLOAD_DATA_SERVICE_TYPE_ERROR_E333351("规则平台服务类型转换未能找到对应枚举"),
    RULES_PLATFORM_UPLOAD_DATA_RETURN_RESULT_ERROR_E333352("未能获取到规则平台返回数据"),
    NUO_YUAN_INVESOTR_REPAYMENT_ERROR_E333349("资方还款金额和应还款金额不匹配,请线下进行调解"),
    NUO_YUAN_INVESOTR_BILL_ERROR_E333349("资方账单逾期状态与宇海不符,请线下进行调解"),
    NUO_YUAN_ORDER_EARLY_SETTLEMENT_ERROR_E333349("此订单无法进行提前结清操作,请线下联系技术人员"),
    CHANNEL_CONF_ASSET_TYPE_IS_NULL_ERROR_E333360("渠道配置业务类型不能为空,请核对请求参数"),
    CHANNEL_CONF_PRIMARY_KEY_IS_NULL_ERROR_E333361("渠道配置主键不能为空,请核对请求参数"),
    CHANNEL_CONF_INVALID_ERROR_E333362("渠道配置已失效"),
    NOT_FOUND_CASH_LOAN_GOODS_TYPE_ERROR_E333363("未找到现金贷配置方案"),
    CHANNEL_ID_NOT_ALLOW_NULL_ERROR_E333364("请选择渠道,渠道不能为空"),
    ALIPAY_GENERATE_QRCODE_ERROR_E333365("生成支付二维码失败"),
    ALIPAY_SIGNATURE_ERROR_E333366("支付宝回调验签出现异常"),
    ALIPAY_QUERY_TRADE_ERROR_E333367("支付宝查询交易状态出现异常"),
    ALIPAY_QUERY_TRADE_ENUM_ERROR_E333368("支付宝查询交易状态未找到对应交易枚举"),
    OTHER_PAY_CHANNEL_PENDING_PAY_ERROR_E333369("订单有处于待支付的流水,不能发起支付"),
    QRCODE_PERIOD_ERROR_E333370("不满足到期应还未还条件，不可进行扫码支付"),
    INVESTOR_NOT_SUPPORT_OPEN_ACCOUNT_E333371("该资金方不需要进行开户操作"),
    INVESTOR_ALREADY_OPEN_ACCOUNT_E333372("已开户成功,请勿重复操作"),
    XIAO_NUO_OPEN_ACCOUNT_STATUS_E333373("开户状态错误,请勿重复提交验证码"),
    XIAO_NUO_OPEN_ACCOUNT_STEP_ERROR_E333374("验证码已失效,请重新获取验证码"),
    XIAO_NUO_OPEN_ACCOUNT_FAIL_E333375("开户失败,请重新获取验证码"),
    XIAO_NUO_NOT_OPEN_ACCOUNT_E333376("小诺资方未开户,不能进行推送"),
    XIAO_NUO_REPEAT_OPEN_ACCOUNT_E333377("该用户已开户成功,可直接跳转"),
    LOAN_INFO_IMPORT_ERR_E9999920("放款结果导入数据异常"),
    IMPORT_NOT_INSURANCE_PASS_E00000999("订单未生成保险"),
    USER_NOT_AUTH_ERROR_E000123("用户未审核"),

    UPDATE_INFO_IS_NOT_NULL("修改的信息不能为空"),
    UPDATE_INFO_IS_NOT_ALLOW("不能进行修改操作"),

    PERSON_INFO_ERROR("理赔人信息有误"),
    DUE_TIME_ERROR("逾期时间有误,请检查"),
    NOT_CHECK_COMPENSATE("已进行理赔,请勿重复推送理赔明细"),
    COMPENSATE_ERROR("核查失败,请重新核算是否满足理赔条件"),
    CHECk_ERROR("推送未成功,请检查是否为已核查的理赔订单"),
    HAD_CHECK("已进行罚息核查,不能再次发起"),
    COMPENSATE_AMOUNT_ERROR("理赔金额不符,请重新核算是否满足理赔条件"),

    MARKET_NOT_ALLOW_READD_E0000000104("红包名称不能重复"),

    MARKET_CHANNEL_EXIST_E0000000103("渠道有未关闭的红报活动"),
    MARKET_IS_READY_TRIGGER("已经领过红包了"),

    MARKET_NOT_EXIST_E0000000100("红包不存在"),
    CLERK_NOT_AUTH_E0000000101("办单员未授权支付宝"),
    MARKET_EXCEED_E0000000102("红包过期"),

    YXT_NOT_SIGN_ERR_E6666661("未签约易行通,请先签约"),
    YXT_NOT_SIGN_ERR_E6666662("易行通签约未成功,请重新签约"),
    YXT_NOT_SIGN_ERR_E6666663("易行通签约信息错误,请重新签约"),
    REPAY_BACK_ERR_E6666661("查询未录入交易背景,请检查"),
    REPAY_BACK_ERR_E6666662("录入交易背景失败"),
    YXT_PAYMENT_ERR_E6666661("交易失败,交易账单有误"),


    GOODS_MANAGER_ERR_E700000("商品管理存在异常"),


    YXT_PAYMENT_ERR_E6666662("易行通不支持,请使用京东"),
    YXT_EARLY_PAYMENT_ERR_E6666662("邮政交行不支持线上提前结清,请使用线下提前结清"),
    YXT_PAYMENT_ERR_E6666663("京东不支持,请使用易行通"),
    DEDUCT_CONFIRM_ERR_E6666661("有处于发起中的支付流水,请等待支付渠道结果"),
    NOT_FOUND_PAYMENT_DATA_ERR_E6666666("未查询到支付流水"),

    PRE_AUDIT_REFUSE_ERR_E0999999("对不起,您暂不满足申请资格,请三个月后申请"),
    CAN_NOT_MODIFY_TO_GRIPAY_ERROR_E100100("不能更换资方为掌钱"),
    HAS_NOT_OPEN_ACCOUNT_GRIPAY_ERROR_E100101("要更换资方的订单中有未开户的订单"),

    /**
     * 第三方服务错误code  E3xxxxx
     */
    ID_CARD_SERVICE_ERROR_E300001("身份证服务调用出错，请联系技术人员。"),
    ACCOUNT_HAVE_OPENED_ERROR_E300101("账号信息已经开户成功"),
    ACCOUNT_OPEN_ERROR_E300102("未获取到开户地址"),
    GRIPAY_SYNC_INFO_ERROR_E300103("同步订单信息出错，请联系技术人员。"),
    CAN_NOT_OPEN_ACCOUNT_ERROR_E300104("用户已开户，无需重复开户"),
    TRUST_PAY_AUTH_ERROR_E300105("用户委托支付授权异常，请联系技术人员。"),
    SIGN_AUTH_ERROR_E300106("签名错误，请联系技术人员。"),
    NOT_GRIPAY_INVESTOR_ERROR_E300107("非掌钱资方，请核实"),
    NOT_XIAONUO_INVESTOR_ERROR_E300107("非小诺资方，请核实"),
    NO_GRIPAY_ORDER_ERROR_E300034("掌钱不存在该订单"),
    IMPORT_INSURANCE_POLICY_PUSH_FILE_ERROR_E30000093("掌钱保单推送导入文件不存在"),
    IMPORT_XIAO_NUO_INSURANCE_POLICY_PUSH_FILE_ERROR_E30000093("小诺预保单导入文件不存在"),
    GRIPAY_LOAN_AMONNT_ERROR_E3000009("掌钱放款金额不一致"),
    GOODS_MANAGER_EXIT_ERROR_E000050("商品信息输入重复，请您重新输入后保存"),

    CREDITOR_NOT_EXIT_ERROR_E000066666("债权人信息不存在"),

    PERMISSION_ERROR_AND_LOGOUT_E100001("用户权限异常，非法访问，请重新登录"),
    ROLE_NOT_EXISTS_ERROR_E100002("被更改的角色不存在"),
    ROLE_PERMISSION_UPDATE_ERROR_E100003("当前用户没有更改此角色权限的资格"),


    NOT_BILL_EXISTS_ERROR_E8000001("没有符合条件的账单"),
    CAN_NOT_INVESTOR_CONFIRM_ERROR_E8000001("资方账单状态已还,不能发起资方已还"),
    EXCEL_NOT_BE_EMPTY_ERROR_E8000001("表格中数据存在空值,请检查数据后重新导入"),
    EXCEL_FORMAT_ERROR_E8000001("期数编号等需合法并为纯数字,请检查数据后重新导入"),
    EXCEL_NOT_EXISTS_ERROR_E8000001("文件解析异常,请重新上传或检查文件格式"),
    LOANCONTRACTNO_NOT_EQUALS_ERROR_E8000001("订单写入合同号与系统不一致,请检查正确性"),
    INVESTORID_NOT_EQUALS_ERROR_E8000001("订单写入资方编号与系统不一致,请检查正确性"),
    CHANNELID_NOT_EQUALS_ERROR_E8000001("订单写入渠道编号与系统不一致,请检查正确性"),
    IDCARD_NOT_EQUALS_ERROR_E8000001("订单写入身份证号与系统不一致,请检查正确性"),
    CHANNELNUM_NOT_EQUALS_ERROR_E8000001("导入数据的渠道数量不符,请检查正确性"),
    INVESTORNUM_NOT_EQUALS_ERROR_E8000001("导入数据的资方数量不符,请检查正确性"),
    CHECKNUM_NOT_EQUALS_ERROR_E8000001("导入数据的核对条数不符,请检查正确性"),
    CHENCKAMOUNT_NOT_EQUALS_ERROR_E8000001("导入数据的核对金额不符,请检查正确性"),
    CHENCKAMOUNT_ERROR_E8000001("掌钱(800053)请暂时在页面进行已还操作，待优化后产品通知"),
    CHENCKAMOUNT_GRIPAY_ERROR_E8000001("此功能仅支持掌钱资方已还导入,请检查数据正确性"),

    DEVICE_IMEI_IS_OLD_E000051("用户需使用新机登陆账号，否则无法发货"),
    DEVICE_IMEI_FOR_CUSTOMER_LOAN_E000052("消费贷才需做设备指纹校验"),
    DEVICE_IMEI_REPEAT_E000053("办理手机商品用户需使用新机登陆账号，非手机商品请输入正确设备指纹，否则无法发货"),
    DEVICE_IMEI_VALIDATE_NOT_PASS_E000054("设备指纹校验未通过，请先进行设备指纹校验"),
    NOT_SUPPORT_GOODS_APPLY_E555503("暂不支持%s商品办单，请联系渠道相关人员确认"),


    APPLY_CASH_QUOTA_MAX_ERROR_E555504("申请金额设置过大，请返回重新填写"),
    NOT_ALLOW_REFUND_ERROR_E455504("不可操退款"),

    /**
     * 电核
     */
    CLERK_GRADE_REPEAT_ERROR("办单员等级数据已存在"),
    SELLER_GRADE_REPEAT_ERROR("商户等级数据已存在"),
    ALLOT_AUDIT_ORDER_ERROR("该单子终审状态或分派状态不正确,暂不可手动分配"),
    AUDIT_ORDER_STATUS_NOT_AUDITING_ERROR("当前操作单子非终审状态"),
    RECALL_AUDIT_ORDER_ERROR("该单子不可撤回"),
    UPDATE_CLERK_GRADE_ERROR("办单员默认等级不可操作"),
    UPDATE_SELLER_GRADE_ERROR("办单员默认等级不可操作"),
    IMPORT_CLERK_GRADE_NULL_ERROR("无导入数据或导入数据中存在等级编号或身份证号为空"),
    IMPORT_SELLER_GRADE_NULL_ERROR("无导入数据或导入数据中存在等级编号或商户编号为空"),
    AUDIT_ORDER_LIMIT_NUMBER_ERROR("接单上限参数设置错误"),
    ALLOT_AUDIT_ORDER_RECEIVER_ERROR("此接单人员暂无法使用"),
    ALLOT_AUDIT_ORDER_BELONG_ERROR("当前操作用户不属于此单子归属方不能进行派单操作"),
    RECALL_AUDIT_ORDER_BELONG_ERROR("当前操作用户不属于此单子归属方暂不能对此单子进行撤单操作"),
    ALLOT_AUDIT_NOT_SAME_CHANNEL("接单人员与当前登录用户不是同一渠道"),
    AUDIT_ORDER_OUT_LIMET_ERROR("超出领单上限，不可再领单"),
    AUDIT_ORDER_COUNT_IS_NULL_ERROR("此时单子已被领完，请稍后再领单"),
    AUDIT_ORDER_NOT_EXIST_ERROR("该订单电核记录不存在"),
    CLERK_GRADE_NOT_EXIST("办单员等级不存在"),
    SELLER_GRADE_NOT_EXIST("商户等级不存在"),




    BILL_NOT_ALLOW_INTEREST_FREE_ERROR_E8000010("该订单不符合免息条件"),
    BILL_NOT_ALLOW_INVESTOR_FREE_ERROR_E8000010("仅合益-徐州小贷（新）可进行免息还款,该订单不符合免息条件"),
    INTEREST_FREE_PERIOD_INVALID_ERROR_E8000010("该订单免息还款的账单期数选择不合法"),
    BANKCARD_NOT_SUPPORT_INTEREST_FREE_ERROR_E8000010("该银行卡未进行京东签约,不支持免息还款"),
    INTEREST_FREE_SHOULDAMOUNT_ERROR_E8000010("查询金额与发起扣款金额不一致,请检查账单详情"),
    INTEREST_FREE__ERROR_E8000010("存在已还的账单,请检查数据是否异常"),

    APP_USER_WHITE_IMPORT_EMPTY("白名单用户导入数据为空"),
    APP_USER_WHITE_IMPORT_FORMAT_ERROR("数据格式不正确,请检查execl单元格格式是否为文本"),
    APP_USER_WHITE_IMPORT_ERROR("白名单用户导入错误"),
    CONTACTS_NOT_ENOUGH_ERROR_E100333("有效联系人不足，请补充后重新授权！"),
    ;


    private String message;

    BasicErrorCodeEnum(String message) {
        this.message = message;
    }

    @Override
    public String getCode() {
        return name().substring(name().lastIndexOf("_") + 1);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
